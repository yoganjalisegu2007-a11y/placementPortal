package placementPortal;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBTest {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/placement_Portal";
        String user = "root";
        String password = "YOUR_DATABASE_PASSWORD";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    url,
                    user,
                    password
            );

            System.out.println("Connected successfully!");

            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}