
package placementPortal;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/DeleteStudentServlet")
public class DeleteStudentServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("StudentServlet");
            return;
        }

        int id = Integer.parseInt(idParam);

        String url = "jdbc:mysql://localhost:3306/placement_Portal";
        String user = "root";
        String password = "YOUR_DATABASE_PASSWORD";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, user, password);

            // Delete child foreign key references in applications table first
            PreparedStatement deleteApps =
                    con.prepareStatement(
                            "DELETE FROM applications WHERE student_id=?"
                    );

            deleteApps.setInt(1, id);
            deleteApps.executeUpdate();
            deleteApps.close();

            // Delete student record from students table
            PreparedStatement ps =
                    con.prepareStatement(
                            "DELETE FROM students WHERE id=?"
                    );

            ps.setInt(1, id);
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
            out.println("<title>Delete Student - Placement Portal</title>");

            out.println("<style>");

            out.println("* {");
            out.println("    box-sizing: border-box;");
            out.println("    margin: 0;");
            out.println("    padding: 0;");
            out.println("}");

            out.println("body {");
            out.println("    font-family: Arial, Helvetica, sans-serif;");
            out.println("    background: #f3f5f8;");
            out.println("    color: #26364d;");
            out.println("    min-height: 100vh;");
            out.println("}");

            /* Top Header */
            out.println(".topbar {");
            out.println("    height: 70px;");
            out.println("    background: #172b4d;");
            out.println("    color: white;");
            out.println("    display: flex;");
            out.println("    align-items: center;");
            out.println("    padding: 0 35px;");
            out.println("    border-bottom: 3px solid #c5a15b;");
            out.println("}");

            out.println(".topbar h1 {");
            out.println("    font-size: 21px;");
            out.println("    letter-spacing: 1px;");
            out.println("    font-weight: 600;");
            out.println("}");

            /* Main Container */
            out.println(".container {");
            out.println("    min-height: calc(100vh - 70px);");
            out.println("    display: flex;");
            out.println("    align-items: center;");
            out.println("    justify-content: center;");
            out.println("    padding: 30px 20px;");
            out.println("}");

            /* Error Card */
            out.println(".error-card {");
            out.println("    width: 100%;");
            out.println("    max-width: 520px;");
            out.println("    background: white;");
            out.println("    border: 1px solid #dce1e8;");
            out.println("    border-radius: 8px;");
            out.println("    padding: 40px;");
            out.println("    text-align: center;");
            out.println("    box-shadow: 0 8px 25px rgba(23, 43, 77, 0.08);");
            out.println("}");

            /* Error Icon */
            out.println(".icon {");
            out.println("    width: 58px;");
            out.println("    height: 58px;");
            out.println("    margin: 0 auto 20px;");
            out.println("    border: 2px solid #b94a48;");
            out.println("    border-radius: 50%;");
            out.println("    display: flex;");
            out.println("    align-items: center;");
            out.println("    justify-content: center;");
            out.println("    color: #b94a48;");
            out.println("    font-size: 28px;");
            out.println("    font-weight: bold;");
            out.println("}");

            out.println(".error-card h2 {");
            out.println("    color: #172b4d;");
            out.println("    margin-bottom: 12px;");
            out.println("    font-size: 24px;");
            out.println("}");

            out.println(".error-card p {");
            out.println("    color: #687386;");
            out.println("    line-height: 1.6;");
            out.println("    margin-bottom: 25px;");
            out.println("}");

            /* Error Message */
            out.println(".error-message {");
            out.println("    background: #fff5f5;");
            out.println("    border: 1px solid #e3b7b7;");
            out.println("    color: #8f3636;");
            out.println("    padding: 13px 15px;");
            out.println("    border-radius: 5px;");
            out.println("    text-align: left;");
            out.println("    margin-bottom: 25px;");
            out.println("    font-size: 14px;");
            out.println("    word-break: break-word;");
            out.println("}");

            /* Button */
            out.println(".btn {");
            out.println("    display: inline-block;");
            out.println("    padding: 11px 24px;");
            out.println("    background: #172b4d;");
            out.println("    color: white;");
            out.println("    text-decoration: none;");
            out.println("    border-radius: 5px;");
            out.println("    font-size: 14px;");
            out.println("    font-weight: 600;");
            out.println("    border: 1px solid #172b4d;");
            out.println("}");

            out.println(".btn:hover {");
            out.println("    background: #223b63;");
            out.println("}");

            /* Responsive */
            out.println("@media (max-width: 600px) {");

            out.println("    .topbar {");
            out.println("        padding: 0 20px;");
            out.println("    }");

            out.println("    .topbar h1 {");
            out.println("        font-size: 18px;");
            out.println("    }");

            out.println("    .error-card {");
            out.println("        padding: 30px 22px;");
            out.println("    }");

            out.println("}");

            out.println("</style>");
            out.println("</head>");

            out.println("<body>");

            out.println("<div class='topbar'>");
            out.println("    <h1>PLACEMENT PORTAL</h1>");
            out.println("</div>");

            out.println("<div class='container'>");

            out.println("    <div class='error-card'>");

            out.println("        <div class='icon'>!</div>");

            out.println("        <h2>Unable to Delete Student</h2>");

            out.println("        <p>");
            out.println("            The student record could not be deleted due to an unexpected error.");
            out.println("            Please return to the student management page and try again.");
            out.println("        </p>");

            out.println("        <div class='error-message'>");
            out.println("            " + e.getMessage());
            out.println("        </div>");

            out.println("        <a class='btn' href='StudentServlet'>");
            out.println("            Return to Students");
            out.println("        </a>");

            out.println("    </div>");

            out.println("</div>");

            out.println("</body>");
            out.println("</html>");
        }
    }
}