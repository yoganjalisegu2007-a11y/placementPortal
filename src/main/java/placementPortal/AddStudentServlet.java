
package placementPortal;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/AddStudentServlet")
public class AddStudentServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieving existing form fields
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String branch = request.getParameter("branch");

        // Retrieving additional student fields
        String phone = request.getParameter("phone");
        String collegeName = request.getParameter("collegeName");
        double cgpa = Double.parseDouble(request.getParameter("cgpa"));
        String gender = request.getParameter("gender");
        String disabled = request.getParameter("disabled");
        String skills = request.getParameter("skills");
        String address = request.getParameter("address");

        String url = "jdbc:mysql://localhost:3306/placement_Portal";
        String user = "root";
        String dbPassword = "YOUR_DATABASE_PASSWORD";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    url, user, dbPassword
            );

            // Insert student details into students table
            String sql = "INSERT INTO students "
                    + "(name, email, password, branch, phone, college_name, cgpa, "
                    + "gender, disabled, skills, address) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            ps.setString(4, branch);
            ps.setString(5, phone);
            ps.setString(6, collegeName);
            ps.setDouble(7, cgpa);
            ps.setString(8, gender);
            ps.setString(9, disabled);
            ps.setString(10, skills);
            ps.setString(11, address);

            ps.executeUpdate();

            ps.close();
            con.close();

            // Return to student management page
            response.sendRedirect("StudentServlet");

        } catch (Exception e) {

            response.setContentType("text/html; charset=UTF-8");

            PrintWriter out = response.getWriter();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");

            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");

            out.println("<title>Student Registration Error | Placement Portal</title>");

            out.println("<style>");

            /* ---------- RESET ---------- */

            out.println("* {");
            out.println("    margin: 0;");
            out.println("    padding: 0;");
            out.println("    box-sizing: border-box;");
            out.println("    font-family: Arial, Helvetica, sans-serif;");
            out.println("}");

            /* ---------- PAGE ---------- */

            out.println("body {");
            out.println("    min-height: 100vh;");
            out.println("    display: flex;");
            out.println("    justify-content: center;");
            out.println("    align-items: center;");
            out.println("    background: #f3f5f8;");
            out.println("    padding: 20px;");
            out.println("}");

            /* ---------- ERROR CARD ---------- */

            out.println(".error-card {");
            out.println("    width: 100%;");
            out.println("    max-width: 540px;");
            out.println("    background: #ffffff;");
            out.println("    border: 1px solid #dfe4ea;");
            out.println("    border-left: 4px solid #a65a5a;");
            out.println("    border-radius: 5px;");
            out.println("    box-shadow: 0 8px 25px rgba(23,43,77,0.08);");
            out.println("    overflow: hidden;");
            out.println("}");

            /* ---------- HEADER ---------- */

            out.println(".header {");
            out.println("    background: #172b4d;");
            out.println("    padding: 25px 30px;");
            out.println("}");

            out.println(".header h1 {");
            out.println("    color: #ffffff;");
            out.println("    font-size: 20px;");
            out.println("    letter-spacing: 1px;");
            out.println("}");

            out.println(".header p {");
            out.println("    color: #c5a15b;");
            out.println("    font-size: 10px;");
            out.println("    margin-top: 6px;");
            out.println("    letter-spacing: 1.3px;");
            out.println("    text-transform: uppercase;");
            out.println("}");

            /* ---------- CONTENT ---------- */

            out.println(".content {");
            out.println("    padding: 30px;");
            out.println("}");

            out.println(".content h2 {");
            out.println("    color: #8b3f3f;");
            out.println("    font-size: 19px;");
            out.println("    margin-bottom: 10px;");
            out.println("}");

            out.println(".content p {");
            out.println("    color: #737d8a;");
            out.println("    font-size: 13px;");
            out.println("    line-height: 1.6;");
            out.println("}");

            /* ---------- ERROR DETAILS ---------- */

            out.println(".error-details {");
            out.println("    margin-top: 18px;");
            out.println("    padding: 14px;");
            out.println("    background: #f7f8fa;");
            out.println("    border: 1px solid #e1e5ea;");
            out.println("    color: #6d7682;");
            out.println("    font-size: 12px;");
            out.println("    line-height: 1.5;");
            out.println("    word-break: break-word;");
            out.println("}");

            /* ---------- ACTION ---------- */

            out.println(".actions {");
            out.println("    margin-top: 22px;");
            out.println("}");

            out.println(".back-button {");
            out.println("    display: inline-block;");
            out.println("    padding: 11px 23px;");
            out.println("    background: #172b4d;");
            out.println("    color: #ffffff;");
            out.println("    text-decoration: none;");
            out.println("    border-radius: 4px;");
            out.println("    font-size: 12px;");
            out.println("    font-weight: 600;");
            out.println("}");

            out.println(".back-button:hover {");
            out.println("    background: #223b63;");
            out.println("}");

            /* ---------- RESPONSIVE ---------- */

            out.println("@media (max-width: 600px) {");

            out.println("    body {");
            out.println("        padding: 15px;");
            out.println("    }");

            out.println("    .content {");
            out.println("        padding: 25px 20px;");
            out.println("    }");

            out.println("    .header {");
            out.println("        padding: 23px 20px;");
            out.println("    }");

            out.println("}");

            out.println("</style>");

            out.println("</head>");

            out.println("<body>");

            /* ---------- ERROR CARD ---------- */

            out.println("<div class='error-card'>");

            out.println("<div class='header'>");

            out.println("<h1>PLACEMENT PORTAL</h1>");

            out.println("<p>Student Administration</p>");

            out.println("</div>");

            out.println("<div class='content'>");

            out.println("<h2>Unable to Save Student Details</h2>");

            out.println("<p>");
            out.println("The student information could not be saved to the placement portal.");
            out.println(" Please review the submitted details and try again.");
            out.println("</p>");

            out.println("<div class='error-details'>");
            out.println("System message: " + e.getMessage());
            out.println("</div>");

            out.println("<div class='actions'>");

            out.println("<a class='back-button' href='StudentServlet'>");
            out.println("Return to Students");
            out.println("</a>");

            out.println("</div>");

            out.println("</div>");

            out.println("</div>");

            out.println("</body>");
            out.println("</html>");
        }
    }
}
