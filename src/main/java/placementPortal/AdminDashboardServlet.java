
package placementPortal;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/AdminDashboardServlet")
public class AdminDashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /* =========================================================
           ADMIN SECURITY CHECK
           ========================================================= */

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("isAdmin") == null ||
            !Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {

            response.sendRedirect("AdminLoginServlet");
            return;
        }

        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();

        /* =========================================================
           DATABASE
           ========================================================= */

        String url =
                "jdbc:mysql://localhost:3306/placement_portal";

        String user = "root";

        String password =
                "Yoganjali@123";

        int totalStudents = 0;
        int totalCompanies = 0;
        int totalApplications = 0;
        int selectedStudents = 0;
        int shortlistedStudents = 0;
        int pendingApplications = 0;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con =
                    DriverManager.getConnection(
                            url,
                            user,
                            password)) {

                /* TOTAL STUDENTS */

                String sqlStudents =
                        "SELECT COUNT(*) FROM students";

                try (PreparedStatement ps =
                        con.prepareStatement(sqlStudents);
                     ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        totalStudents = rs.getInt(1);
                    }
                }

                /* TOTAL COMPANIES */

                String sqlCompanies =
                        "SELECT COUNT(*) FROM companies";

                try (PreparedStatement ps =
                        con.prepareStatement(sqlCompanies);
                     ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        totalCompanies = rs.getInt(1);
                    }
                }

                /* TOTAL APPLICATIONS */

                String sqlApplications =
                        "SELECT COUNT(*) FROM applications";

                try (PreparedStatement ps =
                        con.prepareStatement(sqlApplications);
                     ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        totalApplications = rs.getInt(1);
                    }
                }

                /* SELECTED */

                String sqlSelected =
                        "SELECT COUNT(*) FROM applications " +
                        "WHERE status = 'SELECTED'";

                try (PreparedStatement ps =
                        con.prepareStatement(sqlSelected);
                     ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        selectedStudents = rs.getInt(1);
                    }
                }

                /* SHORTLISTED */

                String sqlShortlisted =
                        "SELECT COUNT(*) FROM applications " +
                        "WHERE status = 'SHORTLISTED'";

                try (PreparedStatement ps =
                        con.prepareStatement(sqlShortlisted);
                     ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        shortlistedStudents = rs.getInt(1);
                    }
                }

                /* APPLIED / PENDING */

                String sqlPending =
                        "SELECT COUNT(*) FROM applications " +
                        "WHERE status = 'APPLIED'";

                try (PreparedStatement ps =
                        con.prepareStatement(sqlPending);
                     ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        pendingApplications = rs.getInt(1);
                    }
                }
            }

        } catch (Exception e) {

            out.println("<h3>Database Error</h3>");
            out.println("<p>Unable to load dashboard information.</p>");
            return;
        }

        /* =========================================================
           HTML
           ========================================================= */

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");

        out.println("<meta charset='UTF-8'>");

        out.println("<meta name='viewport' " +
                "content='width=device-width, initial-scale=1.0'>");

        out.println("<title>Admin Dashboard | Placement Portal</title>");

        /* =========================================================
           CSS
           ========================================================= */

        out.println("<style>");

        out.println("* {");
        out.println("margin:0;");
        out.println("padding:0;");
        out.println("box-sizing:border-box;");
        out.println("font-family:Arial, Helvetica, sans-serif;");
        out.println("}");

        out.println("body {");
        out.println("background:#f4f6f9;");
        out.println("color:#263238;");
        out.println("min-height:100vh;");
        out.println("}");

        /* SIDEBAR */

        out.println(".sidebar {");
        out.println("position:fixed;");
        out.println("left:0;");
        out.println("top:0;");
        out.println("width:245px;");
        out.println("height:100vh;");
        out.println("background:#172b4d;");
        out.println("padding:28px 18px;");
        out.println("box-shadow:3px 0 15px rgba(0,0,0,.08);");
        out.println("}");

        out.println(".brand {");
        out.println("padding:0 12px 22px;");
        out.println("border-bottom:1px solid rgba(255,255,255,.15);");
        out.println("}");

        out.println(".brand h2 {");
        out.println("font-size:18px;");
        out.println("letter-spacing:1.5px;");
        out.println("color:#fff;");
        out.println("}");

        out.println(".brand p {");
        out.println("font-size:11px;");
        out.println("color:#b7c2d0;");
        out.println("margin-top:6px;");
        out.println("letter-spacing:.5px;");
        out.println("}");

        out.println(".sidebar ul {");
        out.println("list-style:none;");
        out.println("margin-top:28px;");
        out.println("}");

        out.println(".sidebar li {");
        out.println("margin-bottom:5px;");
        out.println("}");

        out.println(".sidebar li a {");
        out.println("display:block;");
        out.println("padding:12px 14px;");
        out.println("border-radius:6px;");
        out.println("text-decoration:none;");
        out.println("color:#d7dee8;");
        out.println("font-size:14px;");
        out.println("}");

        out.println(".sidebar li a:hover {");
        out.println("background:#223b63;");
        out.println("color:#fff;");
        out.println("}");

        out.println(".sidebar li.active a {");
        out.println("background:#223b63;");
        out.println("color:#fff;");
        out.println("border-left:3px solid #c5a15b;");
        out.println("}");

        /* MAIN */

        out.println(".main {");
        out.println("margin-left:245px;");
        out.println("padding:30px 38px;");
        out.println("}");

        /* HEADER */

        out.println(".topbar {");
        out.println("background:#fff;");
        out.println("border:1px solid #e1e5ea;");
        out.println("border-radius:9px;");
        out.println("padding:24px 26px;");
        out.println("margin-bottom:25px;");
        out.println("}");

        out.println(".topbar h1 {");
        out.println("font-size:26px;");
        out.println("font-weight:600;");
        out.println("color:#172b4d;");
        out.println("}");

        out.println(".topbar p {");
        out.println("font-size:13px;");
        out.println("color:#77808c;");
        out.println("margin-top:6px;");
        out.println("}");

        /* STATISTICS */

        out.println(".stats {");
        out.println("display:grid;");
        out.println("grid-template-columns:repeat(3,1fr);");
        out.println("gap:17px;");
        out.println("margin-bottom:28px;");
        out.println("}");

        out.println(".stat-card {");
        out.println("background:#fff;");
        out.println("border:1px solid #e1e5ea;");
        out.println("border-radius:9px;");
        out.println("padding:22px;");
        out.println("}");

        out.println(".stat-card h3 {");
        out.println("font-size:11px;");
        out.println("color:#7a838d;");
        out.println("text-transform:uppercase;");
        out.println("letter-spacing:.7px;");
        out.println("}");

        out.println(".stat-number {");
        out.println("font-size:30px;");
        out.println("font-weight:700;");
        out.println("color:#172b4d;");
        out.println("margin-top:8px;");
        out.println("}");

        /* QUICK ACTIONS */

        out.println(".section-title {");
        out.println("font-size:18px;");
        out.println("font-weight:600;");
        out.println("color:#172b4d;");
        out.println("margin-bottom:15px;");
        out.println("}");

        out.println(".actions {");
        out.println("display:grid;");
        out.println("grid-template-columns:repeat(2,1fr);");
        out.println("gap:16px;");
        out.println("}");

        out.println(".action-card {");
        out.println("background:#fff;");
        out.println("border:1px solid #e1e5ea;");
        out.println("border-radius:9px;");
        out.println("padding:22px;");
        out.println("text-decoration:none;");
        out.println("transition:.2s;");
        out.println("}");

        out.println(".action-card:hover {");
        out.println("transform:translateY(-2px);");
        out.println("box-shadow:0 6px 18px rgba(0,0,0,.07);");
        out.println("}");

        out.println(".action-card h3 {");
        out.println("font-size:16px;");
        out.println("color:#172b4d;");
        out.println("margin-bottom:7px;");
        out.println("}");

        out.println(".action-card p {");
        out.println("font-size:12px;");
        out.println("line-height:1.5;");
        out.println("color:#7a838d;");
        out.println("}");

        /* ADMIN INFO */

        out.println(".admin-info {");
        out.println("margin-top:25px;");
        out.println("background:#fff;");
        out.println("border:1px solid #e1e5ea;");
        out.println("border-radius:9px;");
        out.println("padding:20px;");
        out.println("}");

        out.println(".admin-info strong {");
        out.println("color:#172b4d;");
        out.println("}");

        out.println(".logout {");
        out.println("display:inline-block;");
        out.println("margin-top:12px;");
        out.println("padding:9px 16px;");
        out.println("background:#172b4d;");
        out.println("color:#fff;");
        out.println("text-decoration:none;");
        out.println("border-radius:5px;");
        out.println("font-size:12px;");
        out.println("}");

        /* RESPONSIVE */

        out.println("@media(max-width:900px) {");

        out.println(".sidebar {");
        out.println("width:200px;");
        out.println("}");

        out.println(".main {");
        out.println("margin-left:200px;");
        out.println("padding:25px;");
        out.println("}");

        out.println(".stats {");
        out.println("grid-template-columns:repeat(2,1fr);");
        out.println("}");

        out.println("}");

        out.println("@media(max-width:650px) {");

        out.println(".sidebar {");
        out.println("position:relative;");
        out.println("width:100%;");
        out.println("height:auto;");
        out.println("}");

        out.println(".sidebar ul {");
        out.println("display:flex;");
        out.println("flex-wrap:wrap;");
        out.println("gap:5px;");
        out.println("}");

        out.println(".sidebar li {");
        out.println("margin:0;");
        out.println("}");

        out.println(".main {");
        out.println("margin-left:0;");
        out.println("padding:18px;");
        out.println("}");

        out.println(".stats {");
        out.println("grid-template-columns:1fr;");
        out.println("}");

        out.println(".actions {");
        out.println("grid-template-columns:1fr;");
        out.println("}");

        out.println("}");

        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        /* =========================================================
           SIDEBAR
           ========================================================= */

        out.println("<div class='sidebar'>");

        out.println("<div class='brand'>");
        out.println("<h2>PLACEMENT PORTAL</h2>");
        out.println("<p>ADMINISTRATION PANEL</p>");
        out.println("</div>");

        out.println("<ul>");

        out.println("<li class='active'>");
        out.println("<a href='AdminDashboardServlet'>Dashboard</a>");
        out.println("</li>");

        out.println("<li>");
        out.println("<a href='StudentServlet'>Students</a>");
        out.println("</li>");

        out.println("<li>");
        out.println("<a href='CompanyServlet'>Companies</a>");
        out.println("</li>");

        out.println("<li>");
        out.println("<a href='AdminApplicationsServlet'>Applications</a>");
        out.println("</li>");

        out.println("<li>");
        out.println("<a href='AdminInterviewServlet'>Interviews</a>");
        out.println("</li>");

        out.println("</ul>");

        out.println("</div>");

        /* =========================================================
           MAIN
           ========================================================= */

        out.println("<div class='main'>");

        /* HEADER */

        out.println("<div class='topbar'>");

        out.println("<h1>Administrator Dashboard</h1>");

        out.println("<p>");
        out.println("Manage students, companies and placement activities from one place.");
        out.println("</p>");

        out.println("</div>");

        /* =========================================================
           STATISTICS
           ========================================================= */

        out.println("<div class='stats'>");

        out.println("<div class='stat-card'>");
        out.println("<h3>Total Students</h3>");
        out.println("<div class='stat-number'>"
                + totalStudents
                + "</div>");
        out.println("</div>");

        out.println("<div class='stat-card'>");
        out.println("<h3>Total Companies</h3>");
        out.println("<div class='stat-number'>"
                + totalCompanies
                + "</div>");
        out.println("</div>");

        out.println("<div class='stat-card'>");
        out.println("<h3>Total Applications</h3>");
        out.println("<div class='stat-number'>"
                + totalApplications
                + "</div>");
        out.println("</div>");

        out.println("<div class='stat-card'>");
        out.println("<h3>Applications Pending</h3>");
        out.println("<div class='stat-number'>"
                + pendingApplications
                + "</div>");
        out.println("</div>");

        out.println("<div class='stat-card'>");
        out.println("<h3>Shortlisted</h3>");
        out.println("<div class='stat-number'>"
                + shortlistedStudents
                + "</div>");
        out.println("</div>");

        out.println("<div class='stat-card'>");
        out.println("<h3>Selected</h3>");
        out.println("<div class='stat-number'>"
                + selectedStudents
                + "</div>");
        out.println("</div>");

        out.println("</div>");

        /* =========================================================
           QUICK ACTIONS
           ========================================================= */

        out.println("<div class='section-title'>Management</div>");

        out.println("<div class='actions'>");

        out.println("<a class='action-card' href='StudentServlet'>");

        out.println("<h3>Manage Students</h3>");

        out.println("<p>");
        out.println("View registered students and their placement information.");
        out.println("</p>");

        out.println("</a>");

        out.println("<a class='action-card' href='CompanyServlet'>");

        out.println("<h3>Manage Companies</h3>");

        out.println("<p>");
        out.println("Add and manage companies participating in campus recruitment.");
        out.println("</p>");

        out.println("</a>");

        out.println("<a class='action-card' href='AdminApplicationsServlet'>");

        out.println("<h3>Manage Applications</h3>");

        out.println("<p>");
        out.println("Review student applications and update their placement status.");
        out.println("</p>");

        out.println("</a>");

        out.println("<a class='action-card' href='AdminInterviewServlet'>");

        out.println("<h3>Manage Interviews</h3>");

        out.println("<p>");
        out.println("Schedule and manage interviews for shortlisted students.");
        out.println("</p>");

        out.println("</a>");

        out.println("</div>");

        /* =========================================================
           ADMIN INFORMATION
           ========================================================= */

        String adminEmail =
                (String) session.getAttribute("adminEmail");

        out.println("<div class='admin-info'>");

        out.println("<strong>Logged in as Administrator</strong>");

        out.println("<br>");

        out.println("<span style='font-size:12px;color:#7a838d;'>");

        out.println(adminEmail != null
                ? adminEmail
                : "Administrator");

        out.println("</span>");

        out.println("<br>");

        out.println("<a class='logout' href='AdminLogoutServlet'>");

        out.println("Logout");

        out.println("</a>");

        out.println("</div>");

        out.println("</div>");

        out.println("</body>");

        out.println("</html>");
    }
}