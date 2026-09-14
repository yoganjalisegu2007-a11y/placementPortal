package placementPortal;

import java.sql.Connection;

public class DBTest {

    public static void main(String[] args) {

        Connection con = null;

        try {

            System.out.println("Testing database connection...");

            con = DBConnection.getConnection();

            if (con != null && !con.isClosed()) {

                System.out.println("Database connection successful!");

                System.out.println(
                        "Connected to: " + con.getMetaData().getDatabaseProductName()
                );

                System.out.println(
                        "Database: " + con.getCatalog()
                );

            } else {

                System.out.println("Database connection failed.");

            }

        } catch (Exception e) {

            System.out.println("Database connection failed!");
            System.out.println("Error: " + e.getMessage());

            e.printStackTrace();

        } finally {

            try {

                if (con != null) {
                    con.close();
                    System.out.println("Database connection closed.");
                }

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }
}