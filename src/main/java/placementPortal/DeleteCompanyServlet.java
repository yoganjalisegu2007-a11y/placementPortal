
package placementPortal;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/DeleteCompanyServlet")
public class DeleteCompanyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // ==========================================
    // DATABASE DETAILS
    // ==========================================

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/placement_portal";

    private static final String DB_USER = "root";

    private static final String DB_PASSWORD =
            "Yoganjali@123";


    // ==========================================
    // DELETE COMPANY
    // ==========================================

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();


        // ==========================================
        // ADMIN LOGIN CHECK
        // ==========================================

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("isAdmin") == null ||
            !Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {

            response.sendRedirect("AdminLoginServlet");
            return;
        }


        // ==========================================
        // GET COMPANY ID
        // ==========================================

        String idText = request.getParameter("id");


        if (idText == null || idText.trim().isEmpty()) {

            response.sendRedirect("CompanyServlet");
            return;
        }


        int companyId;


        try {

            companyId = Integer.parseInt(idText);

        } catch (NumberFormatException e) {

            response.sendRedirect("CompanyServlet");
            return;
        }


        Connection con = null;
        PreparedStatement checkPs = null;
        PreparedStatement deletePs = null;
        ResultSet rs = null;


        try {

            // ==========================================
            // LOAD MYSQL DRIVER
            // ==========================================

            Class.forName("com.mysql.cj.jdbc.Driver");


            // ==========================================
            // CONNECT DATABASE
            // ==========================================

            con = DriverManager.getConnection(
                    DB_URL,
                    DB_USER,
                    DB_PASSWORD
            );


            // ==========================================
            // CHECK WHETHER COMPANY EXISTS
            // ==========================================

            String checkSql =
                    "SELECT id, name FROM companies WHERE id = ?";

            checkPs = con.prepareStatement(checkSql);

            checkPs.setInt(1, companyId);

            rs = checkPs.executeQuery();


            if (!rs.next()) {

                rs.close();
                checkPs.close();

                showMessage(
                        out,
                        "Company Not Found",
                        "The company you are trying to delete does not exist."
                );

                return;
            }


            String companyName = rs.getString("name");


            rs.close();
            rs = null;

            checkPs.close();
            checkPs = null;


            // ==========================================
            // DELETE COMPANY
            // ==========================================

            String deleteSql =
                    "DELETE FROM companies WHERE id = ?";

            deletePs = con.prepareStatement(deleteSql);

            deletePs.setInt(1, companyId);


            int rows = deletePs.executeUpdate();


            // ==========================================
            // RESULT
            // ==========================================

            if (rows > 0) {

                response.sendRedirect("CompanyServlet");

            } else {

                showMessage(
                        out,
                        "Delete Failed",
                        "The company could not be deleted."
                );
            }


        } catch (ClassNotFoundException e) {

            e.printStackTrace();

            showMessage(
                    out,
                    "Driver Error",
                    "MySQL JDBC Driver was not found."
            );


        } catch (SQLException e) {

            e.printStackTrace();

            /*
             * This can happen if applications are connected
             * to this company and the database does not allow
             * deletion because of a foreign-key relationship.
             */

            showMessage(
                    out,
                    "Database Error",
                    "The company could not be deleted.<br><br>"
                    + "Reason: " + escapeHtml(e.getMessage())
            );


        } finally {


            // ==========================================
            // CLOSE RESULT SET
            // ==========================================

            try {

                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException e) {

                e.printStackTrace();
            }


            // ==========================================
            // CLOSE CHECK STATEMENT
            // ==========================================

            try {

                if (checkPs != null) {
                    checkPs.close();
                }

            } catch (SQLException e) {

                e.printStackTrace();
            }


            // ==========================================
            // CLOSE DELETE STATEMENT
            // ==========================================

            try {

                if (deletePs != null) {
                    deletePs.close();
                }

            } catch (SQLException e) {

                e.printStackTrace();
            }


            // ==========================================
            // CLOSE CONNECTION
            // ==========================================

            try {

                if (con != null) {
                    con.close();
                }

            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }


    // ==========================================
    // POST REQUEST
    // ==========================================

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }


    // ==========================================
    // SHOW ERROR MESSAGE
    // ==========================================

    private void showMessage(PrintWriter out,
                             String title,
                             String message) {

        out.println("<!DOCTYPE html>");

        out.println("<html>");

        out.println("<head>");

        out.println("<meta charset='UTF-8'>");

        out.println("<meta name='viewport' "
                + "content='width=device-width, initial-scale=1.0'>");

        out.println("<title>" + escapeHtml(title) + "</title>");


        out.println("<style>");

        out.println("body {");

        out.println("font-family: Arial, sans-serif;");

        out.println("background: #f4f6f9;");

        out.println("display: flex;");

        out.println("justify-content: center;");

        out.println("align-items: center;");

        out.println("min-height: 100vh;");

        out.println("}");


        out.println(".box {");

        out.println("background: white;");

        out.println("padding: 35px;");

        out.println("border-radius: 12px;");

        out.println("box-shadow: 0 4px 15px "
                + "rgba(0,0,0,0.1);");

        out.println("width: 500px;");

        out.println("text-align: center;");

        out.println("}");


        out.println("h2 {");

        out.println("color: #dc2626;");

        out.println("}");


        out.println("p {");

        out.println("color: #555;");

        out.println("line-height: 1.6;");

        out.println("}");


        out.println("a {");

        out.println("display: inline-block;");

        out.println("margin-top: 20px;");

        out.println("padding: 10px 20px;");

        out.println("background: #2563eb;");

        out.println("color: white;");

        out.println("text-decoration: none;");

        out.println("border-radius: 6px;");

        out.println("}");


        out.println("</style>");

        out.println("</head>");


        out.println("<body>");

        out.println("<div class='box'>");

        out.println("<h2>"
                + escapeHtml(title)
                + "</h2>");

        out.println("<p>"
                + message
                + "</p>");

        out.println("<a href='CompanyServlet'>"
                + "Back to Companies"
                + "</a>");

        out.println("</div>");

        out.println("</body>");

        out.println("</html>");
    }


    // ==========================================
    // HTML ESCAPE
    // ==========================================

    private String escapeHtml(String text) {

        if (text == null) {
            return "";
        }

        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}