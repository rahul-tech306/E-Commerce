package banking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class BankManagement {

    static Connection con = DBConnection.getConnection();

    public static boolean createAccount(String name, int passCode) {

        if (con == null) {
            System.out.println("Database connection not established!");
            return false;
        }

        if (name == null || name.trim().isEmpty() || passCode <= 0) {
            System.out.println("All fields are required!");
            return false;
        }

        String sql = "INSERT INTO customer(cname, pass_code, balance) VALUES (?, ?, 1000)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, passCode);

            if (ps.executeUpdate() == 1) {
                System.out.println("Account Created Successfully!");
                return true;
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Username already exists!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ---------------- LOGIN ----------------
    public static boolean loginAccount(String name, int passCode) {

        if (con == null) {
            System.out.println("Database connection not established!");
            return false;
        }

        String sql = "SELECT ac_no FROM customer WHERE cname=? AND pass_code=?";

        try (PreparedStatement ps = con.prepareStatement(sql);
             BufferedReader sc = new BufferedReader(new InputStreamReader(System.in))) {

            ps.setString(1, name);
            ps.setInt(2, passCode);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("Invalid Login!");
                return false;
            }

            int sender = rs.getInt("ac_no");

            while (true) {
                System.out.println("\n1) Transfer Money");
                System.out.println("2) View Balance");
                System.out.println("3) Logout");
                System.out.print("Enter Choice: ");

                int ch = Integer.parseInt(sc.readLine());

                if (ch == 1) {

                    System.out.print("Receiver Account: ");
                    int rec = Integer.parseInt(sc.readLine());

                    System.out.print("Amount: ");
                    int amt = Integer.parseInt(sc.readLine());

                    transferMoney(sender, rec, amt);

                } else if (ch == 2) {

                    getBalance(sender);

                } else {
                    System.out.println("Logged out successfully.");
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ---------------- BALANCE ----------------
    public static void getBalance(int acNo) {

        String sql = "SELECT balance FROM customer WHERE ac_no=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, acNo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Current Balance: " + rs.getInt("balance"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------- TRANSFER MONEY ----------------
    public static boolean transferMoney(int sender, int receiver, int amount) {

        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return false;
        }

        try {
            con.setAutoCommit(false);

            // Check sender balance
            PreparedStatement checkBal = con.prepareStatement(
                    "SELECT balance FROM customer WHERE ac_no=?");
            checkBal.setInt(1, sender);
            ResultSet rsBal = checkBal.executeQuery();

            if (!rsBal.next() || rsBal.getInt("balance") < amount) {
                System.out.println("Insufficient balance!");
                con.rollback();
                return false;
            }

            // Check receiver existence
            PreparedStatement checkRec = con.prepareStatement(
                    "SELECT ac_no FROM customer WHERE ac_no=?");
            checkRec.setInt(1, receiver);
            ResultSet rsRec = checkRec.executeQuery();

            if (!rsRec.next()) {
                System.out.println("Receiver account not found!");
                con.rollback();
                return false;
            }

            // Deduct sender
            PreparedStatement debit = con.prepareStatement(
                    "UPDATE customer SET balance = balance - ? WHERE ac_no=?");
            debit.setInt(1, amount);
            debit.setInt(2, sender);
            debit.executeUpdate();

            // Credit receiver
            PreparedStatement credit = con.prepareStatement(
                    "UPDATE customer SET balance = balance + ? WHERE ac_no=?");
            credit.setInt(1, amount);
            credit.setInt(2, receiver);
            credit.executeUpdate();

            con.commit();
            System.out.println("Transaction Successful!");
            return true;

        } catch (Exception e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Transaction Failed!");
            e.printStackTrace();
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}
