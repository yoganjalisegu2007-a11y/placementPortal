package placementPortal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String LOCAL_URL =
            "jdbc:mysql://localhost:3306/placement_portal?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static final String LOCAL_USER = "root";

    private static final String LOCAL_PASSWORD =
            System.getenv("LOCAL_DB_PASSWORD");

    public static Connection getConnection() throws SQLException {

        try {
            // Explicitly load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                "MySQL JDBC Driver not found. Check mysql-connector-j JAR.",
                e
            );
        }

        // Check whether Railway environment variables exist
        String host = System.getenv("MYSQLHOST");
        String port = System.getenv("MYSQLPORT");
        String database = System.getenv("MYSQLDATABASE");
        String user = System.getenv("MYSQLUSER");
        String password = System.getenv("MYSQLPASSWORD");

        // Railway database
        if (host != null && port != null && database != null
                && user != null && password != null) {

            String railwayUrl =
                    "jdbc:mysql://" + host + ":" + port + "/" + database
                    + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

            return DriverManager.getConnection(
                    railwayUrl,
                    user,
                    password
            );
        }

        // Local MySQL database
        return DriverManager.getConnection(
                LOCAL_URL,
                LOCAL_USER,
                LOCAL_PASSWORD
        );
    }
}