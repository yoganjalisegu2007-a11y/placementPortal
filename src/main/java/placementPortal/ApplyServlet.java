
package placementPortal;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ApplyServlet")
public class ApplyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/placement_portal";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Yoganjali@123";

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");

        // Check student login
        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("studentId") == null) {

            response.sendRedirect("LoginServlet");
            return;
        }

        // Get student ID
        int studentId;

        try {
            studentId = (Integer) session.getAttribute("studentId");
        } catch (Exception e) {
            response.sendRedirect("LoginServlet");
            return;
        }

        // Get company ID from URL
        String companyIdParameter = request.getParameter("companyId");

        if (companyIdParameter == null ||
            companyIdParameter.trim().isEmpty()) {

            showMessage(response,
                    "Invalid Company",
                    "Company ID is missing.",
                    "StudentCompanyServlet");
            return;
        }

        int companyId;

        try {
            companyId = Integer.parseInt(companyIdParameter);
        } catch (NumberFormatException e) {

            showMessage(response,
                    "Invalid Company",
                    "Invalid company ID.",
                    "StudentCompanyServlet");
            return;
        }

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    DB_URL,
                    DB_USER,
                    DB_PASSWORD
            );

            // ------------------------------------------------
            // STEP 1: Check whether company exists
            // ------------------------------------------------

            String companySQL =
                    "SELECT id, name FROM companies WHERE id = ?";

            ps = con.prepareStatement(companySQL);
            ps.setInt(1, companyId);

            rs = ps.executeQuery();

            String companyName = null;

            if (rs.next()) {
                companyName = rs.getString("name");
            }

            rs.close();
            ps.close();

            if (companyName == null) {

                showMessage(response,
                        "Company Not Found",
                        "The selected company does not exist.",
                        "StudentCompanyServlet");
                return;
            }

            // ------------------------------------------------
            // STEP 2: Check duplicate application
            // ------------------------------------------------

            String checkSQL =
                    "SELECT id FROM applications " +
                    "WHERE student_id = ? AND company_id = ?";

            ps = con.prepareStatement(checkSQL);

            ps.setInt(1, studentId);
            ps.setInt(2, companyId);

            rs = ps.executeQuery();

            if (rs.next()) {

                rs.close();
                ps.close();

                showMessage(response,
                        "Already Applied",
                        "You have already applied to " +
                        escapeHtml(companyName) + ".",
                        "AppliedServlet");
                return;
            }

            rs.close();
            ps.close();

            // ------------------------------------------------
            // STEP 3: Insert application
            // ------------------------------------------------

            String insertSQL =
                    "INSERT INTO applications " +
                    "(student_id, company_id, status) " +
                    "VALUES (?, ?, 'APPLIED')";

            ps = con.prepareStatement(insertSQL);

            ps.setInt(1, studentId);
            ps.setInt(2, companyId);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                response.sendRedirect("AppliedServlet");

            } else {

                showMessage(response,
                        "Application Failed",
                        "Your application could not be submitted.",
                        "StudentCompanyServlet");
            }

        } catch (Exception e) {

            e.printStackTrace();

            showMessage(response,
                    "Error",
                    "Something went wrong while submitting your application.",
                    "StudentCompanyServlet");

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }

            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
            }

            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
    }

    // ------------------------------------------------
    // POST also uses the same application process
    // ------------------------------------------------

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

    // ------------------------------------------------
    // Message page
    // ------------------------------------------------

    private void showMessage(HttpServletResponse response,
                             String title,
                             String message,
                             String redirectPage)
            throws IOException {

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>" + escapeHtml(title) + "</title>");

        out.println("<style>");

        out.println(
                "body{" +
                "font-family:Arial,sans-serif;" +
                "background:#f4f6f9;" +
                "display:flex;" +
                "justify-content:center;" +
                "align-items:center;" +
                "height:100vh;" +
                "margin:0;" +
                "}"
        );

        out.println(
                ".box{" +
                "background:white;" +
                "padding:40px;" +
                "border-radius:15px;" +
                "box-shadow:0 10px 30px rgba(0,0,0,0.12);" +
                "text-align:center;" +
                "width:400px;" +
                "}"
        );

        out.println(
                "h2{" +
                "margin-bottom:15px;" +
                "}"
        );

        out.println(
                "p{" +
                "color:#555;" +
                "line-height:1.6;" +
                "}"
        );

        out.println(
                ".btn{" +
                "display:inline-block;" +
                "margin-top:20px;" +
                "padding:12px 22px;" +
                "background:#2563eb;" +
                "color:white;" +
                "text-decoration:none;" +
                "border-radius:8px;" +
                "}"
        );

        out.println("</style>");
        out.println("</head>");

        out.println("<body>");

        out.println("<div class='box'>");

        out.println("<h2>" + escapeHtml(title) + "</h2>");

        out.println("<p>" + message + "</p>");

        out.println("<a class='btn' href='" +
                escapeHtml(redirectPage) +
                "'>Continue</a>");

        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }

    // ------------------------------------------------
    // HTML escape
    // ------------------------------------------------

    private String escapeHtml(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
