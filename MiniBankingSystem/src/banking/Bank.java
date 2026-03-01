package banking;

import java.util.Scanner;

public class Bank {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- MINI BANKING SYSTEM ---");
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); 

            if (choice == 1) {
                System.out.print("Enter Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Passcode: ");
                int pass = sc.nextInt();

                BankManagement.createAccount(name, pass);

            } else if (choice == 2) {
                System.out.print("Enter Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Passcode: ");
                int pass = sc.nextInt();

                BankManagement.loginAccount(name, pass);

            } else {
                System.out.println("Thank you for using Banking System!");
                break;
            }
        }

        sc.close();
    }
}
