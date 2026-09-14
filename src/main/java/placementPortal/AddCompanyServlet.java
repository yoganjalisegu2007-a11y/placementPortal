
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

@WebServlet("/AddCompanyServlet")
public class AddCompanyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // ==============================
    // DATABASE DETAILS
    // ==============================

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/placement_portal";

    private static final String DB_USER = "root";

    private static final String DB_PASSWORD =
            "Yoganjali@123";


    // ==============================
    // DO POST
    // ==============================

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();


        // ==============================
        // ADMIN LOGIN CHECK
        // ==============================

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("isAdmin") == null ||
            !Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {

            response.sendRedirect("AdminLoginServlet");
            return;
        }


        // ==============================
        // GET FORM DATA
        // ==============================

        String companyId = request.getParameter("company_id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String contactNo = request.getParameter("contact_no");
        String hrName = request.getParameter("hr_name");
        String cutoffText = request.getParameter("cutoff");
        String branches = request.getParameter("branches");
        String branchType = request.getParameter("branch_type");
        String requiredSkills = request.getParameter("required_skills");
        String address = request.getParameter("address");


        // ==============================
        // CHECK EMPTY VALUES
        // ==============================

        if (companyId == null || companyId.trim().isEmpty() ||
            name == null || name.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            contactNo == null || contactNo.trim().isEmpty() ||
            hrName == null || hrName.trim().isEmpty() ||
            cutoffText == null || cutoffText.trim().isEmpty() ||
            branches == null || branches.trim().isEmpty() ||
            branchType == null || branchType.trim().isEmpty() ||
            requiredSkills == null || requiredSkills.trim().isEmpty() ||
            address == null || address.trim().isEmpty()) {

            showMessage(out,
                    "Error",
                    "Please fill all company details.",
                    false);

            return;
        }


        // ==============================
        // CONVERT CUTOFF
        // ==============================

        double cutoff;

        try {

            cutoff = Double.parseDouble(cutoffText);

        } catch (NumberFormatException e) {

            showMessage(out,
                    "Invalid Cutoff",
                    "CGPA cutoff must be a number. Example: 7.5",
                    false);

            return;
        }


        // ==============================
        // CHECK CUTOFF RANGE
        // ==============================

        if (cutoff < 0 || cutoff > 10) {

            showMessage(out,
                    "Invalid Cutoff",
                    "CGPA cutoff must be between 0 and 10.",
                    false);

            return;
        }


        Connection con = null;
        PreparedStatement checkPs = null;
        PreparedStatement insertPs = null;
        ResultSet rs = null;


        try {

            // ==============================
            // LOAD MYSQL DRIVER
            // ==============================

            Class.forName("com.mysql.cj.jdbc.Driver");


            // ==============================
            // CONNECT DATABASE
            // ==============================

            con = DriverManager.getConnection(
                    DB_URL,
                    DB_USER,
                    DB_PASSWORD
            );


            // ==============================
            // CHECK DUPLICATE COMPANY ID
            // ==============================

            String checkSql =
                    "SELECT id FROM companies WHERE company_id = ?";

            checkPs = con.prepareStatement(checkSql);

            checkPs.setString(1, companyId.trim());

            rs = checkPs.executeQuery();


            if (rs.next()) {

                showMessage(out,
                        "Company Already Exists",
                        "Company ID '" + escapeHtml(companyId)
                                + "' is already registered.",
                        false);

                return;
            }


            // Close duplicate-check resources
            rs.close();
            rs = null;

            checkPs.close();
            checkPs = null;


            // ==============================
            // INSERT COMPANY
            // ==============================

            String insertSql =
                    "INSERT INTO companies "
                    + "(company_id, name, email, contact_no, "
                    + "address, hr_name, cutoff, required_skills, "
                    + "branches, branch_type) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


            insertPs = con.prepareStatement(insertSql);


            insertPs.setString(1, companyId.trim());
            insertPs.setString(2, name.trim());
            insertPs.setString(3, email.trim());
            insertPs.setString(4, contactNo.trim());
            insertPs.setString(5, address.trim());
            insertPs.setString(6, hrName.trim());
            insertPs.setDouble(7, cutoff);
            insertPs.setString(8, requiredSkills.trim());
            insertPs.setString(9, branches.trim());
            insertPs.setString(10, branchType.trim());


            int rows = insertPs.executeUpdate();


            // ==============================
            // CHECK INSERT RESULT
            // ==============================

            if (rows > 0) {

                response.sendRedirect("CompanyServlet");

            } else {

                showMessage(out,
                        "Error",
                        "Company could not be added.",
                        false);
            }


        } catch (ClassNotFoundException e) {

            e.printStackTrace();

            showMessage(out,
                    "Driver Error",
                    "MySQL JDBC Driver was not found. "
                    + "Please check your MySQL Connector/J library.",
                    false);


        } catch (SQLException e) {

            e.printStackTrace();

            showMessage(out,
                    "Database Error",
                    "Unable to save company.<br><br>"
                    + "Error: " + escapeHtml(e.getMessage()),
                    false);


        } finally {

            // ==============================
            // CLOSE RESOURCES
            // ==============================

            try {

                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }


            try {

                if (checkPs != null) {
                    checkPs.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }


            try {

                if (insertPs != null) {
                    insertPs.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }


            try {

                if (con != null) {
                    con.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // ==============================
    // IF SOMEONE OPENS URL DIRECTLY
    // ==============================

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect("CompanyServlet");
    }


    // ==============================
    // SHOW MESSAGE
    // ==============================

    private void showMessage(PrintWriter out,
                             String title,
                             String message,
                             boolean success) {

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");

        out.println("<meta charset='UTF-8'>");

        out.println("<meta name='viewport' "
                + "content='width=device-width, initial-scale=1.0'>");

        out.println("<title>" + escapeHtml(title) + "</title>");

        out.println("<style>");

        out.println("body {");
        out.println("    font-family: Arial, sans-serif;");
        out.println("    background: #f4f6f9;");
        out.println("    display: flex;");
        out.println("    justify-content: center;");
        out.println("    align-items: center;");
        out.println("    min-height: 100vh;");
        out.println("}");

        out.println(".box {");
        out.println("    background: white;");
        out.println("    padding: 35px;");
        out.println("    border-radius: 12px;");
        out.println("    box-shadow: 0 4px 15px rgba(0,0,0,0.1);");
        out.println("    text-align: center;");
        out.println("    width: 450px;");
        out.println("}");

        out.println("h2 {");
        out.println("    color: " + (success ? "#16a34a" : "#dc2626") + ";");
        out.println("}");

        out.println("p {");
        out.println("    color: #555;");
        out.println("    line-height: 1.6;");
        out.println("}");

        out.println("a {");
        out.println("    display: inline-block;");
        out.println("    margin-top: 20px;");
        out.println("    padding: 10px 20px;");
        out.println("    background: #2563eb;");
        out.println("    color: white;");
        out.println("    text-decoration: none;");
        out.println("    border-radius: 6px;");
        out.println("}");

        out.println("</style>");

        out.println("</head>");

        out.println("<body>");

        out.println("<div class='box'>");

        out.println("<h2>" + escapeHtml(title) + "</h2>");

        out.println("<p>" + message + "</p>");

        out.println("<a href='CompanyServlet'>Back to Companies</a>");

        out.println("</div>");

        out.println("</body>");

        out.println("</html>");
    }


    // ==============================
    // HTML ESCAPE
    // ==============================

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
