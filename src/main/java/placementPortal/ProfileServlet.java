
package placementPortal;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();

        // ------------------------------------------------
        // Check student login
        // ------------------------------------------------

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("studentId") == null) {

            response.sendRedirect("LoginServlet");
            return;
        }

        Integer studentId;

        try {

            studentId = (Integer) session.getAttribute("studentId");

        } catch (Exception e) {

            response.sendRedirect("LoginServlet");
            return;
        }

        // ------------------------------------------------
        // HTML START
        // ------------------------------------------------

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");

        out.println("<meta charset='UTF-8'>");

        out.println("<meta name='viewport' "
                + "content='width=device-width, initial-scale=1.0'>");

        out.println("<title>My Profile | Placement Portal</title>");

        out.println("<style>");

        out.println("* {");
        out.println("margin: 0;");
        out.println("padding: 0;");
        out.println("box-sizing: border-box;");
        out.println("font-family: Arial, Helvetica, sans-serif;");
        out.println("}");

        out.println("body {");
        out.println("background: #f3f5f8;");
        out.println("color: #263238;");
        out.println("}");

        /* Sidebar */

        out.println(".sidebar {");
        out.println("width: 245px;");
        out.println("height: 100vh;");
        out.println("background: #172b4d;");
        out.println("position: fixed;");
        out.println("left: 0;");
        out.println("top: 0;");
        out.println("padding: 28px 18px;");
        out.println("}");

        out.println(".brand {");
        out.println("color: white;");
        out.println("font-size: 19px;");
        out.println("font-weight: bold;");
        out.println("letter-spacing: 1px;");
        out.println("padding: 0 12px 24px;");
        out.println("border-bottom: 1px solid rgba(255,255,255,0.15);");
        out.println("}");

        out.println(".brand span {");
        out.println("display: block;");
        out.println("color: #c5a15b;");
        out.println("font-size: 11px;");
        out.println("margin-top: 7px;");
        out.println("letter-spacing: 1.5px;");
        out.println("}");

        out.println(".nav {");
        out.println("list-style: none;");
        out.println("margin-top: 25px;");
        out.println("}");

        out.println(".nav li {");
        out.println("margin-bottom: 6px;");
        out.println("}");

        out.println(".nav a {");
        out.println("display: block;");
        out.println("padding: 12px 14px;");
        out.println("color: #d8e0eb;");
        out.println("text-decoration: none;");
        out.println("border-radius: 4px;");
        out.println("font-size: 14px;");
        out.println("transition: 0.2s;");
        out.println("}");

        out.println(".nav a:hover {");
        out.println("background: #20375d;");
        out.println("color: white;");
        out.println("}");

        out.println(".nav li.active a {");
        out.println("background: #223b63;");
        out.println("color: white;");
        out.println("border-left: 4px solid #c5a15b;");
        out.println("padding-left: 10px;");
        out.println("}");

        /* Main */

        out.println(".main {");
        out.println("margin-left: 245px;");
        out.println("min-height: 100vh;");
        out.println("}");

        /* Topbar */

        out.println(".topbar {");
        out.println("height: 78px;");
        out.println("background: white;");
        out.println("border-bottom: 1px solid #e1e5ea;");
        out.println("display: flex;");
        out.println("align-items: center;");
        out.println("justify-content: space-between;");
        out.println("padding: 0 35px;");
        out.println("}");

        out.println(".topbar h1 {");
        out.println("color: #172b4d;");
        out.println("font-size: 22px;");
        out.println("font-weight: 600;");
        out.println("}");

        out.println(".topbar p {");
        out.println("color: #7a869a;");
        out.println("font-size: 13px;");
        out.println("margin-top: 4px;");
        out.println("}");

        out.println(".topbar-badge {");
        out.println("padding: 8px 14px;");
        out.println("border: 1px solid #d8dde5;");
        out.println("border-radius: 4px;");
        out.println("color: #172b4d;");
        out.println("font-size: 13px;");
        out.println("font-weight: 600;");
        out.println("}");

        /* Content */

        out.println(".content {");
        out.println("padding: 35px;");
        out.println("}");

        out.println(".profile-container {");
        out.println("max-width: 850px;");
        out.println("margin: 0 auto;");
        out.println("}");

        /* Profile header */

        out.println(".profile-header {");
        out.println("background: #172b4d;");
        out.println("color: white;");
        out.println("padding: 28px 30px;");
        out.println("border-radius: 6px 6px 0 0;");
        out.println("border-bottom: 3px solid #c5a15b;");
        out.println("}");

        out.println(".profile-header h2 {");
        out.println("font-size: 22px;");
        out.println("font-weight: 600;");
        out.println("}");

        out.println(".profile-header p {");
        out.println("margin-top: 7px;");
        out.println("color: #cbd5e1;");
        out.println("font-size: 13px;");
        out.println("}");

        /* Profile card */

        out.println(".profile-card {");
        out.println("background: white;");
        out.println("border: 1px solid #e0e5eb;");
        out.println("border-top: none;");
        out.println("border-radius: 0 0 6px 6px;");
        out.println("padding: 30px;");
        out.println("}");

        out.println(".section-title {");
        out.println("color: #172b4d;");
        out.println("font-size: 16px;");
        out.println("font-weight: 600;");
        out.println("padding-bottom: 12px;");
        out.println("margin-bottom: 5px;");
        out.println("border-bottom: 1px solid #e5e7eb;");
        out.println("}");

        out.println(".profile-row {");
        out.println("display: flex;");
        out.println("min-height: 48px;");
        out.println("align-items: center;");
        out.println("border-bottom: 1px solid #edf0f3;");
        out.println("}");

        out.println(".profile-row:last-child {");
        out.println("border-bottom: none;");
        out.println("}");

        out.println(".profile-label {");
        out.println("width: 210px;");
        out.println("color: #6b7280;");
        out.println("font-size: 13px;");
        out.println("font-weight: 600;");
        out.println("}");

        out.println(".profile-value {");
        out.println("flex: 1;");
        out.println("color: #263238;");
        out.println("font-size: 14px;");
        out.println("line-height: 1.6;");
        out.println("}");

        out.println(".status-badge {");
        out.println("display: inline-block;");
        out.println("padding: 5px 11px;");
        out.println("background: #eef2f6;");
        out.println("color: #344054;");
        out.println("border: 1px solid #d8dee7;");
        out.println("border-radius: 4px;");
        out.println("font-size: 12px;");
        out.println("font-weight: 600;");
        out.println("}");

        out.println(".skills-box {");
        out.println("background: #f7f8fa;");
        out.println("border: 1px solid #e1e5ea;");
        out.println("padding: 10px 12px;");
        out.println("border-radius: 4px;");
        out.println("}");

        out.println(".address-box {");
        out.println("white-space: pre-line;");
        out.println("}");

        /* Error */

        out.println(".error-box {");
        out.println("background: #fff5f5;");
        out.println("border: 1px solid #f0caca;");
        out.println("border-left: 4px solid #b42318;");
        out.println("color: #8a1c13;");
        out.println("padding: 15px 18px;");
        out.println("border-radius: 4px;");
        out.println("}");

        /* Responsive */

        out.println("@media (max-width: 768px) {");

        out.println(".sidebar {");
        out.println("width: 100%;");
        out.println("height: auto;");
        out.println("position: relative;");
        out.println("padding: 15px;");
        out.println("}");

        out.println(".brand {");
        out.println("text-align: center;");
        out.println("padding-bottom: 15px;");
        out.println("}");

        out.println(".nav {");
        out.println("display: flex;");
        out.println("flex-wrap: wrap;");
        out.println("gap: 5px;");
        out.println("margin-top: 15px;");
        out.println("}");

        out.println(".nav li {");
        out.println("margin-bottom: 0;");
        out.println("}");

        out.println(".nav a {");
        out.println("padding: 9px 10px;");
        out.println("font-size: 12px;");
        out.println("}");

        out.println(".main {");
        out.println("margin-left: 0;");
        out.println("}");

        out.println(".topbar {");
        out.println("height: auto;");
        out.println("padding: 20px;");
        out.println("gap: 10px;");
        out.println("}");

        out.println(".content {");
        out.println("padding: 20px;");
        out.println("}");

        out.println(".profile-card {");
        out.println("padding: 20px;");
        out.println("}");

        out.println(".profile-row {");
        out.println("display: block;");
        out.println("padding: 12px 0;");
        out.println("}");

        out.println(".profile-label {");
        out.println("width: auto;");
        out.println("margin-bottom: 5px;");
        out.println("}");

        out.println("}");

        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        // ------------------------------------------------
        // Student Sidebar
        // ------------------------------------------------

        out.println("<aside class='sidebar'>");

        out.println("<div class='brand'>");
        out.println("PLACEMENT PORTAL");
        out.println("<span>STUDENT PLACEMENT SYSTEM</span>");
        out.println("</div>");

        out.println("<ul class='nav'>");

        out.println("<li>");
        out.println("<a href='HomeServlet'>Dashboard</a>");
        out.println("</li>");

        out.println("<li>");
        out.println("<a href='StudentCompanyServlet'>Companies</a>");
        out.println("</li>");

        out.println("<li>");
        out.println("<a href='AppliedServlet'>My Applications</a>");
        out.println("</li>");

        out.println("<li>");
        out.println("<a href='StudentInterviewServlet'>My Interviews</a>");
        out.println("</li>");

        out.println("<li>");
        out.println("<a href='PlacedServlet'>Placed Students</a>");
        out.println("</li>");

        out.println("<li class='active'>");
        out.println("<a href='ProfileServlet'>My Profile</a>");
        out.println("</li>");

        out.println("<li>");
        out.println("<a href='LogoutServlet'>Logout</a>");
        out.println("</li>");

        out.println("</ul>");

        out.println("</aside>");

        // ------------------------------------------------
        // Main
        // ------------------------------------------------

        out.println("<main class='main'>");

        // Topbar

        out.println("<div class='topbar'>");

        out.println("<div>");

        out.println("<h1>My Profile</h1>");

        out.println("<p>View your registered placement information</p>");

        out.println("</div>");

        out.println("<div class='topbar-badge'>STUDENT</div>");

        out.println("</div>");

        // Content

        out.println("<div class='content'>");

        out.println("<div class='profile-container'>");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            // ==========================================
            // DATABASE CONNECTION
            // ==========================================

            con = DBConnection.getConnection();

            // ==========================================
            // GET STUDENT PROFILE
            // ==========================================

            String sql =
                    "SELECT id, name, email, phone_no, branch, "
                    + "college_name, cgpa, gender, disabled, "
                    + "skills, address, placement_status, "
                    + "previous_company "
                    + "FROM students "
                    + "WHERE id = ?";

            ps = con.prepareStatement(sql);

            ps.setInt(1, studentId);

            rs = ps.executeQuery();

            if (rs.next()) {

                String name =
                        rs.getString("name");

                String email =
                        rs.getString("email");

                String phone =
                        rs.getString("phone_no");

                String branch =
                        rs.getString("branch");

                String collegeName =
                        rs.getString("college_name");

                double cgpa =
                        rs.getDouble("cgpa");

                String gender =
                        rs.getString("gender");

                String disabled =
                        rs.getString("disabled");

                String skills =
                        rs.getString("skills");

                String address =
                        rs.getString("address");

                String placementStatus =
                        rs.getString("placement_status");

                String previousCompany =
                        rs.getString("previous_company");

                // ----------------------------------------
                // Profile Header
                // ----------------------------------------

                out.println("<div class='profile-header'>");

                out.println("<h2>"
                        + escapeHtml(name)
                        + "</h2>");

                out.println("<p>"
                        + escapeHtml(email)
                        + "</p>");

                out.println("</div>");

                // ----------------------------------------
                // Profile Card
                // ----------------------------------------

                out.println("<div class='profile-card'>");

                out.println("<div class='section-title'>");
                out.println("Personal & Academic Information");
                out.println("</div>");

                // Name

                printRow(
                        out,
                        "Full Name",
                        escapeHtml(name)
                );

                // Email

                printRow(
                        out,
                        "Email Address",
                        escapeHtml(email)
                );

                // Phone

                printRow(
                        out,
                        "Phone Number",
                        escapeHtml(phone)
                );

                // Branch

                printRow(
                        out,
                        "Branch",
                        escapeHtml(branch)
                );

                // College

                printRow(
                        out,
                        "College Name",
                        escapeHtml(collegeName)
                );

                // CGPA

                printRow(
                        out,
                        "CGPA",
                        String.valueOf(cgpa)
                );

                // Gender

                printRow(
                        out,
                        "Gender",
                        escapeHtml(gender)
                );

                // Disability

                out.println("<div class='profile-row'>");

                out.println("<div class='profile-label'>");
                out.println("PWD Status");
                out.println("</div>");

                out.println("<div class='profile-value'>");

                out.println("<span class='status-badge'>"
                        + escapeHtml(disabled)
                        + "</span>");

                out.println("</div>");

                out.println("</div>");

                // Skills

                out.println("<div class='profile-row'>");

                out.println("<div class='profile-label'>");
                out.println("Skills");
                out.println("</div>");

                out.println("<div class='profile-value skills-box'>");

                out.println(escapeHtml(skills));

                out.println("</div>");

                out.println("</div>");

                // Address

                out.println("<div class='profile-row'>");

                out.println("<div class='profile-label'>");
                out.println("Address");
                out.println("</div>");

                out.println("<div class='profile-value address-box'>");

                out.println(escapeHtml(address));

                out.println("</div>");

                out.println("</div>");

                // ----------------------------------------
                // Placement Information
                // ----------------------------------------

                out.println("<br>");

                out.println("<div class='section-title'>");
                out.println("Placement Information");
                out.println("</div>");

                // Placement Status

                out.println("<div class='profile-row'>");

                out.println("<div class='profile-label'>");
                out.println("Placement Status");
                out.println("</div>");

                out.println("<div class='profile-value'>");

                out.println("<span class='status-badge'>"
                        + escapeHtml(placementStatus)
                        + "</span>");

                out.println("</div>");

                out.println("</div>");

                // Previous Company

                printRow(
                        out,
                        "Previous Company",
                        escapeHtml(previousCompany)
                );

                out.println("</div>");

            } else {

                out.println("<div class='error-box'>");

                out.println("Student profile could not be found.");

                out.println("</div>");
            }

        } catch (SQLException e) {

            e.printStackTrace();

            out.println("<div class='error-box'>");

            out.println("Unable to load your profile.");

            out.println("</div>");

        } finally {

            // Close ResultSet

            try {

                if (rs != null) {
                    rs.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Close PreparedStatement

            try {

                if (ps != null) {
                    ps.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Close Connection

            try {

                if (con != null) {
                    con.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        out.println("</div>");
        out.println("</div>");

        out.println("</main>");

        out.println("</body>");
        out.println("</html>");
    }

    // ------------------------------------------------
    // Print profile row
    // ------------------------------------------------

    private void printRow(PrintWriter out,
                          String label,
                          String value) {

        out.println("<div class='profile-row'>");

        out.println("<div class='profile-label'>");
        out.println(label);
        out.println("</div>");

        out.println("<div class='profile-value'>");

        out.println(value == null || value.isEmpty()
                ? "Not provided"
                : value);

        out.println("</div>");

        out.println("</div>");
    }

    // ------------------------------------------------
    // HTML Escape
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