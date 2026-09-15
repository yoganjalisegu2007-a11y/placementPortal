package placementPortal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // ==============================
    // LOCAL MYSQL SETTINGS
    // ==============================

    private static final String LOCAL_URL =
            "jdbc:mysql://localhost:3306/placement_portal"
            + "?useSSL=false"
            + "&allowPublicKeyRetrieval=true"
            + "&serverTimezone=UTC"
            + "&connectTimeout=10000"
            + "&socketTimeout=10000";

    private static final String LOCAL_USER = "root";


    public static Connection getConnection() throws SQLException {

        // ==============================
        // LOAD MYSQL JDBC DRIVER
        // ==============================

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {

            throw new SQLException(
                    "MySQL JDBC Driver not found. "
                    + "Check mysql-connector-j JAR.",
                    e
            );
        }


        // ==============================
        // RAILWAY VARIABLES
        // ==============================

        String host = System.getenv("MYSQLHOST");
        String port = System.getenv("MYSQLPORT");
        String database = System.getenv("MYSQLDATABASE");
        String user = System.getenv("MYSQLUSER");
        String password = System.getenv("MYSQLPASSWORD");


        // ==============================
        // CHECK RAILWAY VARIABLES
        // ==============================

        if (host != null && !host.trim().isEmpty()
                && port != null && !port.trim().isEmpty()
                && database != null && !database.trim().isEmpty()
                && user != null && !user.trim().isEmpty()
                && password != null && !password.trim().isEmpty()) {


            System.out.println("================================");
            System.out.println("DATABASE CONNECTION");
            System.out.println("Using Railway MySQL");
            System.out.println("Host available: YES");
            System.out.println("Port available: YES");
            System.out.println("Database available: YES");
            System.out.println("User available: YES");
            System.out.println("Password available: YES");
            System.out.println("================================");


            // ==============================
            // CREATE JDBC URL
            // ==============================

            String railwayUrl =
                    "jdbc:mysql://"
                    + host
                    + ":"
                    + port
                    + "/"
                    + database
                    + "?useSSL=false"
                    + "&allowPublicKeyRetrieval=true"
                    + "&serverTimezone=UTC"
                    + "&connectTimeout=10000"
                    + "&socketTimeout=10000";


            System.out.println("Connecting to Railway MySQL...");
            System.out.println("Database name: " + database);


            // ==============================
            // CONNECT TO RAILWAY
            // ==============================

            return DriverManager.getConnection(
                    railwayUrl,
                    user,
                    password
            );
        }


        // ==============================
        // LOCAL MYSQL
        // ==============================

        System.out.println("================================");
        System.out.println("DATABASE CONNECTION");
        System.out.println("Railway variables are not available.");
        System.out.println("Using local MySQL.");
        System.out.println("================================");


        String localPassword =
                System.getenv("LOCAL_DB_PASSWORD");


        if (localPassword == null
                || localPassword.trim().isEmpty()) {

            throw new SQLException(
                    "LOCAL_DB_PASSWORD is not set. "
                    + "Set your local MySQL password "
                    + "as an environment variable."
            );
        }


        return DriverManager.getConnection(
                LOCAL_URL,
                LOCAL_USER,
                localPassword
        );
    }
}