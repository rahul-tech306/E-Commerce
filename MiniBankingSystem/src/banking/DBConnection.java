package banking;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static Connection con;

    public static Connection getConnection() {

        try {
            String driver = "com.mysql.cj.jdbc.Driver";

            String url = "jdbc:mysql://localhost:3306/banking?useSSL=false&serverTimezone=UTC";

            String user = "root";
            String pass = "2210";

            Class.forName(driver);

            con = DriverManager.getConnection(url, user, pass);

            System.out.println("Database Connected Successfully!");

        } catch (Exception e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();   
        }

        return con;
    }
}
