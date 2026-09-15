
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

@WebServlet("/PlacedServlet")
public class PlacedServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Prevent browser cache
        response.setHeader(
                "Cache-Control",
                "no-cache, no-store, must-revalidate"
        );

        response.setHeader("Pragma", "no-cache");

        response.setDateHeader("Expires", 0);

        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();

        // ==================================================
        // STUDENT LOGIN CHECK
        // ==================================================

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

        // ==================================================
        // HTML START
        // ==================================================

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");

        out.println("<meta charset='UTF-8'>");

        out.println("<meta name='viewport' "
                + "content='width=device-width, initial-scale=1.0'>");

        out.println("<title>Placed Companies | Placement Portal</title>");

        out.println("<style>");

        /* ==================================================
           RESET
           ================================================== */

        out.println("* {");
        out.println("margin: 0;");
        out.println("padding: 0;");
        out.println("box-sizing: border-box;");
        out.println("font-family: Arial, Helvetica, sans-serif;");
        out.println("}");

        out.println("body {");
        out.println("background: #f3f5f8;");
        out.println("color: #273142;");
        out.println("}");

        /* ==================================================
           SIDEBAR
           ================================================== */

        out.println(".sidebar {");
        out.println("width: 245px;");
        out.println("height: 100vh;");
        out.println("position: fixed;");
        out.println("left: 0;");
        out.println("top: 0;");
        out.println("background: #172b4d;");
        out.println("padding: 28px 18px;");
        out.println("box-shadow: 3px 0 12px rgba(0,0,0,0.08);");
        out.println("}");

        out.println(".brand {");
        out.println("color: #ffffff;");
        out.println("font-size: 19px;");
        out.println("font-weight: 700;");
        out.println("letter-spacing: 1px;");
        out.println("padding: 0 12px 20px;");
        out.println("border-bottom: 1px solid rgba(255,255,255,0.15);");
        out.println("}");

        out.println(".brand span {");
        out.println("display: block;");
        out.println("color: #c5a15b;");
        out.println("font-size: 11px;");
        out.println("margin-top: 7px;");
        out.println("letter-spacing: 1.5px;");
        out.println("}");

        out.println(".sidebar ul {");
        out.println("list-style: none;");
        out.println("margin-top: 25px;");
        out.println("}");

        out.println(".sidebar ul li {");
        out.println("margin-bottom: 5px;");
        out.println("border-left: 3px solid transparent;");
        out.println("}");

        out.println(".sidebar ul li a {");
        out.println("display: block;");
        out.println("padding: 13px 14px;");
        out.println("text-decoration: none;");
        out.println("color: #d9e0eb;");
        out.println("font-size: 14px;");
        out.println("border-radius: 4px;");
        out.println("}");

        out.println(".sidebar ul li a:hover {");
        out.println("background: #1e355a;");
        out.println("color: #ffffff;");
        out.println("}");

        out.println(".sidebar ul li.active {");
        out.println("border-left: 3px solid #c5a15b;");
        out.println("}");

        out.println(".sidebar ul li.active a {");
        out.println("background: #223b63;");
        out.println("color: #ffffff;");
        out.println("font-weight: 600;");
        out.println("}");

        /* ==================================================
           MAIN
           ================================================== */

        out.println(".main {");
        out.println("margin-left: 245px;");
        out.println("min-height: 100vh;");
        out.println("}");

        /* ==================================================
           TOP BAR
           ================================================== */

        out.println(".topbar {");
        out.println("height: 72px;");
        out.println("background: #ffffff;");
        out.println("border-bottom: 1px solid #e1e5ea;");
        out.println("display: flex;");
        out.println("align-items: center;");
        out.println("justify-content: space-between;");
        out.println("padding: 0 35px;");
        out.println("}");

        out.println(".topbar h1 {");
        out.println("font-size: 22px;");
        out.println("color: #172b4d;");
        out.println("font-weight: 600;");
        out.println("}");

        out.println(".topbar-label {");
        out.println("font-size: 12px;");
        out.println("color: #7b8492;");
        out.println("letter-spacing: 1px;");
        out.println("text-transform: uppercase;");
        out.println("}");

        /* ==================================================
           CONTENT
           ================================================== */

        out.println(".content {");
        out.println("padding: 32px 35px;");
        out.println("}");

        out.println(".page-heading {");
        out.println("margin-bottom: 25px;");
        out.println("}");

        out.println(".page-heading h2 {");
        out.println("color: #172b4d;");
        out.println("font-size: 24px;");
        out.println("margin-bottom: 7px;");
        out.println("}");

        out.println(".page-heading p {");
        out.println("color: #7b8492;");
        out.println("font-size: 14px;");
        out.println("}");

        /* ==================================================
           PLACEMENT CARD
           ================================================== */

        out.println(".placement-card {");
        out.println("background: #ffffff;");
        out.println("border: 1px solid #e0e4e9;");
        out.println("border-left: 4px solid #5b8068;");
        out.println("border-radius: 5px;");
        out.println("margin-bottom: 18px;");
        out.println("padding: 24px;");
        out.println("box-shadow: 0 2px 8px rgba(23,43,77,0.05);");
        out.println("}");

        out.println(".placement-header {");
        out.println("display: flex;");
        out.println("align-items: center;");
        out.println("justify-content: space-between;");
        out.println("gap: 15px;");
        out.println("padding-bottom: 17px;");
        out.println("border-bottom: 1px solid #edf0f3;");
        out.println("margin-bottom: 17px;");
        out.println("}");

        out.println(".company-title h3 {");
        out.println("color: #172b4d;");
        out.println("font-size: 19px;");
        out.println("font-weight: 600;");
        out.println("margin-bottom: 5px;");
        out.println("}");

        out.println(".company-id {");
        out.println("color: #8a929d;");
        out.println("font-size: 12px;");
        out.println("}");

        /* ==================================================
           STATUS
           ================================================== */

        out.println(".badge {");
        out.println("display: inline-block;");
        out.println("background: #e8f1eb;");
        out.println("color: #41644d;");
        out.println("border: 1px solid #cbded0;");
        out.println("padding: 6px 13px;");
        out.println("border-radius: 4px;");
        out.println("font-size: 11px;");
        out.println("font-weight: 700;");
        out.println("letter-spacing: 0.7px;");
        out.println("}");

        /* ==================================================
           DETAILS
           ================================================== */

        out.println(".details-grid {");
        out.println("display: grid;");
        out.println("grid-template-columns: repeat(2, minmax(0, 1fr));");
        out.println("gap: 15px 25px;");
        out.println("}");

        out.println(".detail-item {");
        out.println("padding: 12px 0;");
        out.println("}");

        out.println(".detail-label {");
        out.println("display: block;");
        out.println("color: #89919c;");
        out.println("font-size: 11px;");
        out.println("text-transform: uppercase;");
        out.println("letter-spacing: 0.7px;");
        out.println("margin-bottom: 6px;");
        out.println("}");

        out.println(".detail-value {");
        out.println("color: #394454;");
        out.println("font-size: 14px;");
        out.println("line-height: 1.5;");
        out.println("}");

        /* ==================================================
           EMPTY STATE
           ================================================== */

        out.println(".empty-card {");
        out.println("background: #ffffff;");
        out.println("border: 1px solid #e0e4e9;");
        out.println("border-radius: 5px;");
        out.println("padding: 45px 25px;");
        out.println("text-align: center;");
        out.println("box-shadow: 0 2px 8px rgba(23,43,77,0.04);");
        out.println("}");

        out.println(".empty-card h3 {");
        out.println("color: #172b4d;");
        out.println("font-size: 18px;");
        out.println("margin-bottom: 8px;");
        out.println("}");

        out.println(".empty-card p {");
        out.println("color: #8a929d;");
        out.println("font-size: 14px;");
        out.println("}");

        /* ==================================================
           ERROR
           ================================================== */

        out.println(".error-box {");
        out.println("background: #f8eeee;");
        out.println("color: #8b3f3f;");
        out.println("border: 1px solid #e8caca;");
        out.println("border-left: 4px solid #a65a5a;");
        out.println("padding: 14px 16px;");
        out.println("border-radius: 4px;");
        out.println("font-size: 14px;");
        out.println("}");

        /* ==================================================
           RESPONSIVE
           ================================================== */

        out.println("@media (max-width: 850px) {");

        out.println(".sidebar {");
        out.println("width: 200px;");
        out.println("}");

        out.println(".main {");
        out.println("margin-left: 200px;");
        out.println("}");

        out.println(".details-grid {");
        out.println("grid-template-columns: 1fr;");
        out.println("}");

        out.println("}");

        out.println("@media (max-width: 650px) {");

        out.println(".sidebar {");
        out.println("width: 100%;");
        out.println("height: auto;");
        out.println("position: relative;");
        out.println("padding: 15px;");
        out.println("}");

        out.println(".sidebar ul {");
        out.println("display: flex;");
        out.println("flex-wrap: wrap;");
        out.println("margin-top: 15px;");
        out.println("gap: 5px;");
        out.println("}");

        out.println(".sidebar ul li {");
        out.println("margin-bottom: 0;");
        out.println("}");

        out.println(".sidebar ul li a {");
        out.println("padding: 9px 11px;");
        out.println("font-size: 12px;");
        out.println("}");

        out.println(".main {");
        out.println("margin-left: 0;");
        out.println("}");

        out.println(".topbar {");
        out.println("padding: 0 18px;");
        out.println("}");

        out.println(".content {");
        out.println("padding: 22px 18px;");
        out.println("}");

        out.println(".placement-header {");
        out.println("align-items: flex-start;");
        out.println("flex-direction: column;");
        out.println("}");

        out.println("}");

        out.println("</style>");

        out.println("</head>");

        out.println("<body>");

        // ==================================================
        // SIDEBAR
        // ==================================================

        out.println("<div class='sidebar'>");

        out.println("<div class='brand'>");
        out.println("PLACEMENT PORTAL");
        out.println("<span>STUDENT PLACEMENT SYSTEM</span>");
        out.println("</div>");

        out.println("<ul>");

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

        out.println("<li class='active'>");
        out.println("<a href='PlacedServlet'>Placed</a>");
        out.println("</li>");

        out.println("<li>");
        out.println("<a href='ProfileServlet'>Profile</a>");
        out.println("</li>");

        out.println("<li>");
        out.println("<a href='LogoutServlet'>Logout</a>");
        out.println("</li>");

        out.println("</ul>");

        out.println("</div>");

        // ==================================================
        // MAIN
        // ==================================================

        out.println("<div class='main'>");

        // ==================================================
        // TOP BAR
        // ==================================================

        out.println("<div class='topbar'>");

        out.println("<h1>Placed Companies</h1>");

        out.println("<span class='topbar-label'>Placement Records</span>");

        out.println("</div>");

        // ==================================================
        // CONTENT
        // ==================================================

        out.println("<div class='content'>");

        out.println("<div class='page-heading'>");

        out.println("<h2>Placement Offers</h2>");

        out.println("<p>");
        out.println("Companies where your placement has been successfully confirmed.");
        out.println("</p>");

        out.println("</div>");

        // ==================================================
        // DATABASE
        // ==================================================

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            // Use the common database connection.
            // Railway variables are used automatically
            // when the application is deployed.

            con = DBConnection.getConnection();

            /*
             * SELECTED is the final placement status
             * controlled by the administrator.
             *
             * Students cannot change this status.
             */

            String sql =
                    "SELECT c.id, c.company_id, c.name, "
                    + "c.email, c.contact_no, c.address, "
                    + "c.hr_name, c.cutoff, c.required_skills, "
                    + "c.branches, c.branch_type, "
                    + "a.status, a.applied_date "
                    + "FROM companies c "
                    + "JOIN applications a "
                    + "ON c.id = a.company_id "
                    + "WHERE a.student_id = ? "
                    + "AND a.status = 'SELECTED' "
                    + "ORDER BY a.applied_date DESC";

            ps = con.prepareStatement(sql);

            ps.setInt(1, studentId);

            rs = ps.executeQuery();

            boolean found = false;

            // ==================================================
            // DISPLAY PLACED COMPANIES
            // ==================================================

            while (rs.next()) {

                found = true;

                out.println("<div class='placement-card'>");

                // ---------- HEADER ----------

                out.println("<div class='placement-header'>");

                out.println("<div class='company-title'>");

                out.println("<h3>"
                        + escapeHtml(rs.getString("name"))
                        + "</h3>");

                out.println("<span class='company-id'>");

                out.println("Company ID: "
                        + escapeHtml(rs.getString("company_id")));

                out.println("</span>");

                out.println("</div>");

                out.println("<span class='badge'>SELECTED</span>");

                out.println("</div>");

                // ---------- DETAILS ----------

                out.println("<div class='details-grid'>");

                // HR Contact

                out.println("<div class='detail-item'>");

                out.println("<span class='detail-label'>HR Contact</span>");

                out.println("<span class='detail-value'>"
                        + escapeHtml(rs.getString("hr_name"))
                        + "</span>");

                out.println("</div>");

                // Email

                out.println("<div class='detail-item'>");

                out.println("<span class='detail-label'>Email</span>");

                out.println("<span class='detail-value'>"
                        + escapeHtml(rs.getString("email"))
                        + "</span>");

                out.println("</div>");

                // Contact Number

                out.println("<div class='detail-item'>");

                out.println("<span class='detail-label'>Contact Number</span>");

                out.println("<span class='detail-value'>"
                        + escapeHtml(rs.getString("contact_no"))
                        + "</span>");

                out.println("</div>");

                // Allowed Branches

                out.println("<div class='detail-item'>");

                out.println("<span class='detail-label'>Allowed Branches</span>");

                out.println("<span class='detail-value'>"
                        + escapeHtml(rs.getString("branches"))
                        + "</span>");

                out.println("</div>");

                // Branch Type

                out.println("<div class='detail-item'>");

                out.println("<span class='detail-label'>Branch Type</span>");

                out.println("<span class='detail-value'>"
                        + escapeHtml(rs.getString("branch_type"))
                        + "</span>");

                out.println("</div>");

                // Required Skills

                out.println("<div class='detail-item'>");

                out.println("<span class='detail-label'>Required Skills</span>");

                out.println("<span class='detail-value'>"
                        + escapeHtml(rs.getString("required_skills"))
                        + "</span>");

                out.println("</div>");

                // Cutoff

                out.println("<div class='detail-item'>");

                out.println("<span class='detail-label'>Company Cutoff</span>");

                out.println("<span class='detail-value'>"
                        + rs.getBigDecimal("cutoff")
                        + "</span>");

                out.println("</div>");

                // Applied Date

                out.println("<div class='detail-item'>");

                out.println("<span class='detail-label'>Applied Date</span>");

                out.println("<span class='detail-value'>"
                        + rs.getTimestamp("applied_date")
                        + "</span>");

                out.println("</div>");

                out.println("</div>");

                out.println("</div>");
            }

            // ==================================================
            // NO PLACEMENT
            // ==================================================

            if (!found) {

                out.println("<div class='empty-card'>");

                out.println("<h3>No Placement Offers Yet</h3>");

                out.println("<p>");
                out.println("There are currently no confirmed placement "
                        + "offers for your account.");
                out.println("</p>");

                out.println("</div>");
            }

        } catch (Exception e) {

            e.printStackTrace();

            out.println("<div class='error-box'>");

            out.println("<strong>Unable to load placement records.</strong><br>");

            out.println("Please check the database connection and try again.");

            out.println("</div>");

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

        out.println("</div>");

        out.println("</div>");

        out.println("</body>");

        out.println("</html>");
    }

    // ==================================================
    // HTML ESCAPE
    // ==================================================

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