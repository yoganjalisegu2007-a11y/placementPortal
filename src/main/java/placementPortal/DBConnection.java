package placementPortal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Local MySQL database
    private static final String LOCAL_URL =
            "jdbc:mysql://localhost:3306/placement_portal"
            + "?useSSL=false"
            + "&allowPublicKeyRetrieval=true"
            + "&serverTimezone=UTC"
            + "&connectTimeout=10000"
            + "&socketTimeout=10000";

    private static final String LOCAL_USER = "root";

    public static Connection getConnection() throws SQLException {

        // Make Java prefer IPv4.
        // Railway provides both IPv4 and IPv6 addresses.
        System.setProperty("java.net.preferIPv4Stack", "true");

        // Load MySQL JDBC Driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                    "MySQL JDBC Driver not found. Check mysql-connector-j JAR.",
                    e
            );
        }

        // Read Railway MySQL environment variables
        String host = System.getenv("MYSQLHOST");
        String port = System.getenv("MYSQLPORT");
        String database = System.getenv("MYSQLDATABASE");
        String user = System.getenv("MYSQLUSER");
        String password = System.getenv("MYSQLPASSWORD");

        // Safe debug information
        // Password itself is NEVER printed.
        System.out.println("=== DATABASE DEBUG ===");
        System.out.println("MYSQLHOST = " + host);
        System.out.println("MYSQLPORT = " + port);
        System.out.println("MYSQLDATABASE = " + database);
        System.out.println("MYSQLUSER = " + user);
        System.out.println(
                "MYSQLPASSWORD SET = "
                + (password != null && !password.isEmpty())
        );
        System.out.println("======================");

        /*
         * Railway connection
         */
        if (host != null && !host.isEmpty()
                && port != null && !port.isEmpty()
                && database != null && !database.isEmpty()
                && user != null && !user.isEmpty()
                && password != null && !password.isEmpty()) {

            String railwayUrl =
                    "jdbc:mysql://" + host + ":" + port + "/" + database
                    + "?useSSL=false"
                    + "&allowPublicKeyRetrieval=true"
                    + "&serverTimezone=UTC"
                    + "&connectTimeout=10000"
                    + "&socketTimeout=10000";

            System.out.println("Using Railway MySQL database.");
            System.out.println("Railway host = " + host);
            System.out.println("Railway port = " + port);
            System.out.println("Railway database = " + database);

            return DriverManager.getConnection(
                    railwayUrl,
                    user,
                    password
            );
        }

        /*
         * Local Eclipse/Tomcat connection
         */
        System.out.println("Railway variables not available.");
        System.out.println("Using local MySQL database.");

        String localPassword = System.getenv("LOCAL_DB_PASSWORD");

        if (localPassword == null || localPassword.isEmpty()) {
            throw new SQLException(
                    "LOCAL_DB_PASSWORD is not set. "
                    + "Set your local MySQL password as an environment variable."
            );
        }

        return DriverManager.getConnection(
                LOCAL_URL,
                LOCAL_USER,
                localPassword
        );
    }
}