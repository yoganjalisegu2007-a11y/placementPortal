
package placementPortal;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // ==============================
        // ADMIN SECURITY CHECK
        // ==============================

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("isAdmin") == null ||
            !Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {

            response.sendRedirect("AdminLoginServlet");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        // ==============================
        // HTML START
        // ==============================

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");

        out.println("<meta charset='UTF-8'>");

        out.println("<meta name='viewport' " +
                    "content='width=device-width, initial-scale=1.0'>");

        out.println("<title>Manage Students - Placement Portal</title>");

        // ==============================
        // CSS
        // ==============================

        out.println("<style>");

        out.println("* { box-sizing: border-box; }");

        out.println("body {");
        out.println("margin: 0;");
        out.println("font-family: Arial, sans-serif;");
        out.println("background: #f4f6f9;");
        out.println("color: #333;");
        out.println("}");

        out.println(".sidebar {");
        out.println("position: fixed;");
        out.println("left: 0;");
        out.println("top: 0;");
        out.println("width: 240px;");
        out.println("height: 100vh;");
        out.println("background: #172554;");
        out.println("color: white;");
        out.println("padding: 25px 15px;");
        out.println("}");

        out.println(".sidebar h2 {");
        out.println("text-align: center;");
        out.println("margin-bottom: 30px;");
        out.println("}");

        out.println(".sidebar a {");
        out.println("display: block;");
        out.println("color: white;");
        out.println("text-decoration: none;");
        out.println("padding: 13px 15px;");
        out.println("margin: 7px 0;");
        out.println("border-radius: 8px;");
        out.println("}");

        out.println(".sidebar a:hover {");
        out.println("background: #2563eb;");
        out.println("}");

        out.println(".logout {");
        out.println("margin-top: 30px !important;");
        out.println("background: #dc2626;");
        out.println("}");

        out.println(".main {");
        out.println("margin-left: 240px;");
        out.println("padding: 30px;");
        out.println("}");

        out.println(".header {");
        out.println("margin-bottom: 25px;");
        out.println("}");

        out.println(".header h1 {");
        out.println("margin: 0;");
        out.println("color: #172554;");
        out.println("}");

        out.println(".header p {");
        out.println("color: #666;");
        out.println("}");

        out.println(".stats {");
        out.println("display: grid;");
        out.println("grid-template-columns: repeat(3, 1fr);");
        out.println("gap: 20px;");
        out.println("margin-bottom: 25px;");
        out.println("}");

        out.println(".stat-card {");
        out.println("background: white;");
        out.println("padding: 22px;");
        out.println("border-radius: 12px;");
        out.println("box-shadow: 0 3px 12px rgba(0,0,0,0.08);");
        out.println("}");

        out.println(".stat-card h3 {");
        out.println("margin: 0;");
        out.println("font-size: 30px;");
        out.println("color: #2563eb;");
        out.println("}");

        out.println(".stat-card p {");
        out.println("margin: 8px 0 0;");
        out.println("color: #666;");
        out.println("}");

        out.println(".table-container {");
        out.println("background: white;");
        out.println("padding: 20px;");
        out.println("border-radius: 12px;");
        out.println("box-shadow: 0 3px 12px rgba(0,0,0,0.08);");
        out.println("overflow-x: auto;");
        out.println("}");

        out.println(".table-container h2 {");
        out.println("margin-top: 0;");
        out.println("color: #172554;");
        out.println("}");

        out.println("table {");
        out.println("width: 100%;");
        out.println("border-collapse: collapse;");
        out.println("min-width: 1000px;");
        out.println("}");

        out.println("th {");
        out.println("background: #172554;");
        out.println("color: white;");
        out.println("padding: 14px;");
        out.println("text-align: left;");
        out.println("}");

        out.println("td {");
        out.println("padding: 13px;");
        out.println("border-bottom: 1px solid #eee;");
        out.println("}");

        out.println("tr:hover {");
        out.println("background: #f8fafc;");
        out.println("}");

        out.println(".status {");
        out.println("padding: 6px 10px;");
        out.println("border-radius: 20px;");
        out.println("font-size: 12px;");
        out.println("font-weight: bold;");
        out.println("background: #e0f2fe;");
        out.println("color: #0369a1;");
        out.println("}");

        out.println(".placed {");
        out.println("background: #dcfce7;");
        out.println("color: #166534;");
        out.println("}");

        out.println(".not-placed {");
        out.println("background: #fee2e2;");
        out.println("color: #991b1b;");
        out.println("}");

        out.println(".empty {");
        out.println("text-align: center;");
        out.println("padding: 40px;");
        out.println("color: #777;");
        out.println("}");

        out.println("@media(max-width: 900px) {");

        out.println(".sidebar {");
        out.println("position: relative;");
        out.println("width: 100%;");
        out.println("height: auto;");
        out.println("}");

        out.println(".main {");
        out.println("margin-left: 0;");
        out.println("}");

        out.println(".stats {");
        out.println("grid-template-columns: 1fr;");
        out.println("}");

        out.println("}");

        out.println("</style>");

        out.println("</head>");

        out.println("<body>");

        // ==============================
        // SIDEBAR
        // ==============================

        out.println("<div class='sidebar'>");

        out.println("<h2>🎓 Placement Portal</h2>");

        out.println("<a href='AdminDashboardServlet'>🏠 Dashboard</a>");

        out.println("<a href='StudentServlet'>👨‍🎓 Students</a>");

        out.println("<a href='CompanyServlet'>🏢 Companies</a>");

        out.println("<a href='AdminApplicationsServlet'>📋 Applications</a>");

        out.println("<a href='AdminInterviewServlet'>📅 Interviews</a>");

        out.println("<a href='AdminLogoutServlet' class='logout'>🚪 Logout</a>");

        out.println("</div>");

        // ==============================
        // MAIN CONTENT
        // ==============================

        out.println("<div class='main'>");

        out.println("<div class='header'>");

        out.println("<h1>Manage Students</h1>");

        out.println("<p>");
        out.println("View and monitor all registered students");
        out.println("</p>");

        out.println("</div>");

        // ==============================
        // DATABASE VARIABLES
        // ==============================

        int totalStudents = 0;
        int placedStudents = 0;
        int fresherStudents = 0;

        try {

            // ==============================
            // CONNECT USING DBConnection
            // ==============================

            Connection con = DBConnection.getConnection();

            // ==============================
            // TOTAL STUDENTS
            // ==============================

            String totalSql =
                    "SELECT COUNT(*) FROM students";

            try (PreparedStatement ps =
                         con.prepareStatement(totalSql);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    totalStudents = rs.getInt(1);
                }
            }

            // ==============================
            // PLACED STUDENTS
            // ==============================

            String placedSql =
                    "SELECT COUNT(*) FROM students " +
                    "WHERE placed = 'Yes' " +
                    "OR placement_status = 'Placed'";

            try (PreparedStatement ps =
                         con.prepareStatement(placedSql);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    placedStudents = rs.getInt(1);
                }
            }

            // ==============================
            // FRESHER STUDENTS
            // ==============================

            String fresherSql =
                    "SELECT COUNT(*) FROM students " +
                    "WHERE placement_status = 'Fresher'";

            try (PreparedStatement ps =
                         con.prepareStatement(fresherSql);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    fresherStudents = rs.getInt(1);
                }
            }

            // ==============================
            // STATISTICS CARDS
            // ==============================

            out.println("<div class='stats'>");

            out.println("<div class='stat-card'>");
            out.println("<h3>" + totalStudents + "</h3>");
            out.println("<p>Total Students</p>");
            out.println("</div>");

            out.println("<div class='stat-card'>");
            out.println("<h3>" + placedStudents + "</h3>");
            out.println("<p>Placed Students</p>");
            out.println("</div>");

            out.println("<div class='stat-card'>");
            out.println("<h3>" + fresherStudents + "</h3>");
            out.println("<p>Freshers</p>");
            out.println("</div>");

            out.println("</div>");

            // ==============================
            // STUDENT TABLE
            // ==============================

            out.println("<div class='table-container'>");

            out.println("<h2>Registered Students</h2>");

            String sql =
                    "SELECT id, name, email, prn, branch, " +
                    "year, cgpa, phone_no, placement_status, " +
                    "previous_company, placed " +
                    "FROM students " +
                    "ORDER BY id DESC";

            try (PreparedStatement ps =
                         con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                boolean hasStudents = false;

                out.println("<table>");

                out.println("<tr>");

                out.println("<th>ID</th>");
                out.println("<th>Name</th>");
                out.println("<th>Email</th>");
                out.println("<th>PRN</th>");
                out.println("<th>Branch</th>");
                out.println("<th>Year</th>");
                out.println("<th>CGPA</th>");
                out.println("<th>Phone</th>");
                out.println("<th>Placement Status</th>");
                out.println("<th>Previous Company</th>");
                out.println("<th>Placed</th>");

                out.println("</tr>");

                while (rs.next()) {

                    hasStudents = true;

                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String prn = rs.getString("prn");
                    String branch = rs.getString("branch");
                    String year = rs.getString("year");
                    String cgpa = rs.getString("cgpa");
                    String phone = rs.getString("phone_no");

                    String placementStatus =
                            rs.getString("placement_status");

                    String previousCompany =
                            rs.getString("previous_company");

                    String placed =
                            rs.getString("placed");

                    if (placementStatus == null ||
                        placementStatus.trim().isEmpty()) {

                        placementStatus = "Fresher";
                    }

                    if (previousCompany == null ||
                        previousCompany.trim().isEmpty()) {

                        previousCompany = "None";
                    }

                    if (placed == null ||
                        placed.trim().isEmpty()) {

                        placed = "No";
                    }

                    out.println("<tr>");

                    out.println("<td>" +
                            rs.getInt("id") +
                            "</td>");

                    out.println("<td>" +
                            escapeHtml(name) +
                            "</td>");

                    out.println("<td>" +
                            escapeHtml(email) +
                            "</td>");

                    out.println("<td>" +
                            escapeHtml(prn) +
                            "</td>");

                    out.println("<td>" +
                            escapeHtml(branch) +
                            "</td>");

                    out.println("<td>" +
                            escapeHtml(year) +
                            "</td>");

                    out.println("<td>" +
                            escapeHtml(cgpa) +
                            "</td>");

                    out.println("<td>" +
                            escapeHtml(phone) +
                            "</td>");

                    out.println("<td>");

                    out.println("<span class='status'>");

                    out.println(
                            escapeHtml(placementStatus)
                    );

                    out.println("</span>");

                    out.println("</td>");

                    out.println("<td>" +
                            escapeHtml(previousCompany) +
                            "</td>");

                    if ("Yes".equalsIgnoreCase(placed)) {

                        out.println("<td>");

                        out.println(
                                "<span class='status placed'>YES</span>"
                        );

                        out.println("</td>");

                    } else {

                        out.println("<td>");

                        out.println(
                                "<span class='status not-placed'>NO</span>"
                        );

                        out.println("</td>");
                    }

                    out.println("</tr>");
                }

                out.println("</table>");

                if (!hasStudents) {

                    out.println("<div class='empty'>");

                    out.println(
                            "<h3>No students registered yet</h3>"
                    );

                    out.println(
                            "<p>Student registrations will appear here.</p>"
                    );

                    out.println("</div>");
                }
            }

            con.close();

        } catch (Exception e) {

            out.println("<div class='table-container'>");

            out.println("<h2>Unable to Load Students</h2>");

            out.println(
                    "<p>Please check your MySQL connection, " +
                    "database name and password.</p>"
            );

            out.println("</div>");

            e.printStackTrace();
        }

        out.println("</div>");

        out.println("</body>");

        out.println("</html>");
    }

    // ==============================
    // HTML ESCAPE METHOD
    // ==============================

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