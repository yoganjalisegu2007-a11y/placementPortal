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

@WebServlet("/AdminApplicationsServlet")
public class AdminApplicationsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/placement_portal";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD =
            "Yoganjali@123";

    // ---------------------------------------------------------
    // GET - Display all applications
    // ---------------------------------------------------------
    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Check admin login
        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("isAdmin") == null ||
            !Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {

            response.sendRedirect("AdminLoginServlet");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        int totalApplications = 0;
        int applied = 0;
        int shortlisted = 0;
        int interview = 0;
        int selected = 0;
        int rejected = 0;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD);

            String sql =
                    "SELECT a.id, a.status, a.applied_date, " +
                    "s.name AS student_name, " +
                    "s.email AS student_email, " +
                    "s.branch, s.cgpa, " +
                    "c.name AS company_name, " +
                    "c.company_id " +
                    "FROM applications a " +
                    "JOIN students s ON a.student_id = s.id " +
                    "JOIN companies c ON a.company_id = c.id " +
                    "ORDER BY a.applied_date DESC";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            // -------------------------------------------------
            // HTML START
            // -------------------------------------------------

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");

            out.println("<title>Manage Applications - Admin</title>");

            out.println("<style>");

            out.println("* {");
            out.println("    box-sizing: border-box;");
            out.println("    margin: 0;");
            out.println("    padding: 0;");
            out.println("    font-family: Arial, sans-serif;");
            out.println("}");

            out.println("body {");
            out.println("    background: #f4f7fb;");
            out.println("    color: #222;");
            out.println("}");

            // Sidebar
            out.println(".sidebar {");
            out.println("    position: fixed;");
            out.println("    left: 0;");
            out.println("    top: 0;");
            out.println("    width: 250px;");
            out.println("    height: 100vh;");
            out.println("    background: #172554;");
            out.println("    color: white;");
            out.println("    padding: 25px 15px;");
            out.println("}");

            out.println(".logo {");
            out.println("    font-size: 24px;");
            out.println("    font-weight: bold;");
            out.println("    text-align: center;");
            out.println("    margin-bottom: 35px;");
            out.println("}");

            out.println(".logo span {");
            out.println("    color: #60a5fa;");
            out.println("}");

            out.println(".menu a {");
            out.println("    display: block;");
            out.println("    color: #dbeafe;");
            out.println("    text-decoration: none;");
            out.println("    padding: 14px 18px;");
            out.println("    margin: 7px 0;");
            out.println("    border-radius: 8px;");
            out.println("    font-size: 15px;");
            out.println("}");

            out.println(".menu a:hover {");
            out.println("    background: #1e3a8a;");
            out.println("}");

            out.println(".menu .active {");
            out.println("    background: #2563eb;");
            out.println("    color: white;");
            out.println("}");

            // Main
            out.println(".main {");
            out.println("    margin-left: 250px;");
            out.println("    padding: 30px;");
            out.println("}");

            out.println(".topbar {");
            out.println("    display: flex;");
            out.println("    justify-content: space-between;");
            out.println("    align-items: center;");
            out.println("    margin-bottom: 25px;");
            out.println("}");

            out.println(".topbar h1 {");
            out.println("    font-size: 28px;");
            out.println("    color: #172554;");
            out.println("}");

            out.println(".admin-name {");
            out.println("    background: white;");
            out.println("    padding: 10px 16px;");
            out.println("    border-radius: 8px;");
            out.println("    box-shadow: 0 2px 8px rgba(0,0,0,0.08);");
            out.println("}");

            // Stats
            out.println(".stats {");
            out.println("    display: grid;");
            out.println("    grid-template-columns: repeat(6, 1fr);");
            out.println("    gap: 15px;");
            out.println("    margin-bottom: 25px;");
            out.println("}");

            out.println(".stat {");
            out.println("    background: white;");
            out.println("    padding: 18px;");
            out.println("    border-radius: 12px;");
            out.println("    box-shadow: 0 2px 10px rgba(0,0,0,0.07);");
            out.println("}");

            out.println(".stat h3 {");
            out.println("    font-size: 13px;");
            out.println("    color: #64748b;");
            out.println("    margin-bottom: 8px;");
            out.println("}");

            out.println(".stat p {");
            out.println("    font-size: 25px;");
            out.println("    font-weight: bold;");
            out.println("    color: #172554;");
            out.println("}");

            // Table container
            out.println(".table-container {");
            out.println("    background: white;");
            out.println("    border-radius: 12px;");
            out.println("    padding: 20px;");
            out.println("    box-shadow: 0 2px 10px rgba(0,0,0,0.07);");
            out.println("    overflow-x: auto;");
            out.println("}");

            out.println(".table-container h2 {");
            out.println("    margin-bottom: 18px;");
            out.println("    color: #172554;");
            out.println("}");

            out.println("table {");
            out.println("    width: 100%;");
            out.println("    border-collapse: collapse;");
            out.println("    min-width: 1100px;");
            out.println("}");

            out.println("th {");
            out.println("    background: #172554;");
            out.println("    color: white;");
            out.println("    padding: 13px;");
            out.println("    text-align: left;");
            out.println("    font-size: 13px;");
            out.println("}");

            out.println("td {");
            out.println("    padding: 13px;");
            out.println("    border-bottom: 1px solid #e5e7eb;");
            out.println("    font-size: 13px;");
            out.println("}");

            out.println("tr:hover {");
            out.println("    background: #f8fafc;");
            out.println("}");

            // Status
            out.println(".status {");
            out.println("    padding: 6px 10px;");
            out.println("    border-radius: 20px;");
            out.println("    font-size: 12px;");
            out.println("    font-weight: bold;");
            out.println("    display: inline-block;");
            out.println("}");

            out.println(".applied {");
            out.println("    background: #dbeafe;");
            out.println("    color: #1d4ed8;");
            out.println("}");

            out.println(".shortlisted {");
            out.println("    background: #fef3c7;");
            out.println("    color: #92400e;");
            out.println("}");

            out.println(".interview {");
            out.println("    background: #ede9fe;");
            out.println("    color: #6d28d9;");
            out.println("}");

            out.println(".selected {");
            out.println("    background: #dcfce7;");
            out.println("    color: #166534;");
            out.println("}");

            out.println(".rejected {");
            out.println("    background: #fee2e2;");
            out.println("    color: #991b1b;");
            out.println("}");

            // Form
            out.println(".status-form {");
            out.println("    display: flex;");
            out.println("    gap: 7px;");
            out.println("}");

            out.println("select {");
            out.println("    padding: 7px;");
            out.println("    border: 1px solid #cbd5e1;");
            out.println("    border-radius: 6px;");
            out.println("    background: white;");
            out.println("}");

            out.println(".update-btn {");
            out.println("    padding: 7px 12px;");
            out.println("    border: none;");
            out.println("    border-radius: 6px;");
            out.println("    background: #2563eb;");
            out.println("    color: white;");
            out.println("    cursor: pointer;");
            out.println("}");

            out.println(".update-btn:hover {");
            out.println("    background: #1d4ed8;");
            out.println("}");

            out.println(".empty {");
            out.println("    text-align: center;");
            out.println("    padding: 40px;");
            out.println("    color: #64748b;");
            out.println("}");

            // Responsive
            out.println("@media(max-width: 1000px) {");
            out.println("    .stats {");
            out.println("        grid-template-columns: repeat(3, 1fr);");
            out.println("    }");
            out.println("}");

            out.println("@media(max-width: 700px) {");
            out.println("    .sidebar {");
            out.println("        position: relative;");
            out.println("        width: 100%;");
            out.println("        height: auto;");
            out.println("    }");

            out.println("    .main {");
            out.println("        margin-left: 0;");
            out.println("    }");

            out.println("    .stats {");
            out.println("        grid-template-columns: repeat(2, 1fr);");
            out.println("    }");
            out.println("}");

            out.println("</style>");
            out.println("</head>");

            out.println("<body>");

            // -------------------------------------------------
            // SIDEBAR
            // -------------------------------------------------

            out.println("<div class='sidebar'>");

            out.println("<div class='logo'>");
            out.println("Placement<span>Portal</span>");
            out.println("</div>");

            out.println("<div class='menu'>");

            out.println("<a href='AdminDashboardServlet'>Dashboard</a>");

            out.println("<a href='StudentServlet'>Students</a>");

            out.println("<a href='CompanyServlet'>Companies</a>");

            out.println("<a href='AdminApplicationsServlet' class='active'>Applications</a>");

            out.println("<a href='AdminInterviewServlet'>Interviews</a>");

            out.println("</div>");

            out.println("</div>");

            // -------------------------------------------------
            // MAIN
            // -------------------------------------------------

            out.println("<div class='main'>");

            out.println("<div class='topbar'>");

            out.println("<h1>Application Management</h1>");

            String adminEmail =
                    (String) session.getAttribute("adminEmail");

            out.println("<div class='admin-name'>");
            out.println("Admin: " + escapeHtml(adminEmail));
            out.println("</div>");

            out.println("</div>");

            // -------------------------------------------------
            // We need to process the ResultSet twice.
            // So first collect rows in HTML StringBuilder.
            // -------------------------------------------------

            StringBuilder rows = new StringBuilder();

            while (rs.next()) {

                totalApplications++;

                String status = rs.getString("status");

                if (status == null) {
                    status = "APPLIED";
                }

                switch (status.toUpperCase()) {

                    case "APPLIED":
                        applied++;
                        break;

                    case "SHORTLISTED":
                        shortlisted++;
                        break;

                    case "INTERVIEW":
                        interview++;
                        break;

                    case "SELECTED":
                        selected++;
                        break;

                    case "REJECTED":
                        rejected++;
                        break;
                }

                String statusClass =
                        status.toLowerCase().replace(" ", "");

                rows.append("<tr>");

                rows.append("<td>")
                    .append(rs.getInt("id"))
                    .append("</td>");

                rows.append("<td>")
                    .append(escapeHtml(rs.getString("student_name")))
                    .append("<br><small>")
                    .append(escapeHtml(rs.getString("student_email")))
                    .append("</small></td>");

                rows.append("<td>")
                    .append(escapeHtml(rs.getString("branch")))
                    .append("</td>");

                rows.append("<td>")
                    .append(rs.getObject("cgpa") == null
                            ? "-"
                            : rs.getObject("cgpa"))
                    .append("</td>");

                rows.append("<td>")
                    .append(escapeHtml(rs.getString("company_name")))
                    .append("<br><small>")
                    .append(escapeHtml(rs.getString("company_id")))
                    .append("</small></td>");

                rows.append("<td>")
                    .append(rs.getTimestamp("applied_date"))
                    .append("</td>");

                rows.append("<td>")
                    .append("<span class='status ")
                    .append(statusClass)
                    .append("'>")
                    .append(escapeHtml(status))
                    .append("</span>")
                    .append("</td>");

                // Admin status update form
                rows.append("<td>");

                rows.append("<form class='status-form' ")
                    .append("action='AdminApplicationsServlet' ")
                    .append("method='post'>");

                rows.append("<input type='hidden' ")
                    .append("name='applicationId' ")
                    .append("value='")
                    .append(rs.getInt("id"))
                    .append("'>");

                rows.append("<select name='status'>");

                String[] statuses = {
                    "APPLIED",
                    "SHORTLISTED",
                    "INTERVIEW",
                    "SELECTED",
                    "REJECTED"
                };

                for (String option : statuses) {

                    rows.append("<option value='")
                        .append(option)
                        .append("'");

                    if (option.equalsIgnoreCase(status)) {
                        rows.append(" selected");
                    }

                    rows.append(">")
                        .append(option)
                        .append("</option>");
                }

                rows.append("</select>");

                rows.append("<button type='submit' ")
                    .append("class='update-btn'>Update</button>");

                rows.append("</form>");

                rows.append("</td>");

                rows.append("</tr>");
            }

            out.println("<div class='stats'>");

            out.println("<div class='stat'>");
            out.println("<h3>Total Applications</h3>");
            out.println("<p>" + totalApplications + "</p>");
            out.println("</div>");

            out.println("<div class='stat'>");
            out.println("<h3>Applied</h3>");
            out.println("<p>" + applied + "</p>");
            out.println("</div>");

            out.println("<div class='stat'>");
            out.println("<h3>Shortlisted</h3>");
            out.println("<p>" + shortlisted + "</p>");
            out.println("</div>");

            out.println("<div class='stat'>");
            out.println("<h3>Interview</h3>");
            out.println("<p>" + interview + "</p>");
            out.println("</div>");

            out.println("<div class='stat'>");
            out.println("<h3>Selected</h3>");
            out.println("<p>" + selected + "</p>");
            out.println("</div>");

            out.println("<div class='stat'>");
            out.println("<h3>Rejected</h3>");
            out.println("<p>" + rejected + "</p>");
            out.println("</div>");

            out.println("</div>");

            // -------------------------------------------------
            // TABLE
            // -------------------------------------------------

            out.println("<div class='table-container'>");

            out.println("<h2>All Student Applications</h2>");

            if (totalApplications == 0) {

                out.println("<div class='empty'>");
                out.println("<h3>No applications found</h3>");
                out.println("<p>Students have not applied to any company yet.</p>");
                out.println("</div>");

            } else {

                out.println("<table>");

                out.println("<thead>");
                out.println("<tr>");

                out.println("<th>ID</th>");
                out.println("<th>Student</th>");
                out.println("<th>Branch</th>");
                out.println("<th>CGPA</th>");
                out.println("<th>Company</th>");
                out.println("<th>Applied Date</th>");
                out.println("<th>Current Status</th>");
                out.println("<th>Change Status</th>");

                out.println("</tr>");
                out.println("</thead>");

                out.println("<tbody>");

                out.println(rows.toString());

                out.println("</tbody>");

                out.println("</table>");
            }

            out.println("</div>");

            out.println("</div>");

            out.println("</body>");
            out.println("</html>");

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            e.printStackTrace();

            out.println("<h2>Error loading applications</h2>");
            out.println("<p>" + escapeHtml(e.getMessage()) + "</p>");
        }
    }

    // ---------------------------------------------------------
    // POST - Admin changes application status
    // ---------------------------------------------------------
    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        // Check admin login
        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("isAdmin") == null ||
            !Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {

            response.sendRedirect("AdminLoginServlet");
            return;
        }

        String applicationIdParam =
                request.getParameter("applicationId");

        String status =
                request.getParameter("status");

        // Validate application ID
        if (applicationIdParam == null ||
            status == null ||
            status.trim().isEmpty()) {

            response.sendRedirect("AdminApplicationsServlet");
            return;
        }

        int applicationId;

        try {

            applicationId = Integer.parseInt(applicationIdParam);

        } catch (NumberFormatException e) {

            response.sendRedirect("AdminApplicationsServlet");
            return;
        }

        // -----------------------------------------------------
        // Only these statuses are allowed
        // -----------------------------------------------------

        String[] allowedStatuses = {
            "APPLIED",
            "SHORTLISTED",
            "INTERVIEW",
            "SELECTED",
            "REJECTED"
        };

        boolean validStatus = false;

        for (String allowed : allowedStatuses) {

            if (allowed.equals(status)) {
                validStatus = true;
                break;
            }
        }

        if (!validStatus) {

            response.sendRedirect("AdminApplicationsServlet");
            return;
        }

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD);

            String sql =
                    "UPDATE applications " +
                    "SET status = ? " +
                    "WHERE id = ?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, status);
            ps.setInt(2, applicationId);

            ps.executeUpdate();

            ps.close();
            con.close();

            // Return to application page
            response.sendRedirect("AdminApplicationsServlet");

        } catch (Exception e) {

            e.printStackTrace();

            response.setContentType("text/html;charset=UTF-8");

            PrintWriter out = response.getWriter();

            out.println("<h2>Unable to update application status</h2>");
            out.println("<p>" + escapeHtml(e.getMessage()) + "</p>");
            out.println("<a href='AdminApplicationsServlet'>Back</a>");
        }
    }

    // ---------------------------------------------------------
    // HTML escape helper
    // ---------------------------------------------------------
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