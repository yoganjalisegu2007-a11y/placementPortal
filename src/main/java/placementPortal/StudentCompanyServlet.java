
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

@WebServlet("/StudentCompanyServlet")
public class StudentCompanyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // ==========================================
    // SHOW COMPANIES
    // ==========================================

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        // ==========================================
        // STUDENT LOGIN CHECK
        // ==========================================

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("studentId") == null) {

            response.sendRedirect("LoginServlet");
            return;
        }

        int studentId;

        try {

            studentId = Integer.parseInt(
                    session.getAttribute("studentId").toString()
            );

        } catch (Exception e) {

            response.sendRedirect("LoginServlet");
            return;
        }

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            // ==========================================
            // DATABASE CONNECTION
            // ==========================================

            con = DBConnection.getConnection();

            // ==========================================
            // GET COMPANIES
            // ==========================================

            String sql =
                    "SELECT c.id, c.company_id, c.name, "
                    + "c.email, c.contact_no, c.address, "
                    + "c.hr_name, c.cutoff, "
                    + "c.required_skills, c.branches, "
                    + "c.branch_type, "
                    + "a.id AS application_id, "
                    + "a.status AS application_status "
                    + "FROM companies c "
                    + "LEFT JOIN applications a "
                    + "ON c.id = a.company_id "
                    + "AND a.student_id = ? "
                    + "ORDER BY c.id DESC";

            ps = con.prepareStatement(sql);

            ps.setInt(1, studentId);

            rs = ps.executeQuery();

            // ==========================================
            // HTML START
            // ==========================================

            out.println("<!DOCTYPE html>");
            out.println("<html>");

            out.println("<head>");

            out.println("<meta charset='UTF-8'>");

            out.println("<meta name='viewport' "
                    + "content='width=device-width, initial-scale=1.0'>");

            out.println("<title>Available Companies</title>");

            // ==========================================
            // CSS
            // ==========================================

            out.println("<style>");

            out.println("* {");
            out.println("box-sizing:border-box;");
            out.println("font-family:Arial,sans-serif;");
            out.println("}");

            out.println("body {");
            out.println("margin:0;");
            out.println("background:#f4f6f9;");
            out.println("color:#333;");
            out.println("}");

            // ==========================================
            // SIDEBAR
            // ==========================================

            out.println(".sidebar {");
            out.println("position:fixed;");
            out.println("left:0;");
            out.println("top:0;");
            out.println("width:240px;");
            out.println("height:100vh;");
            out.println("background:#1f2937;");
            out.println("color:white;");
            out.println("padding-top:25px;");
            out.println("}");

            out.println(".sidebar h2 {");
            out.println("text-align:center;");
            out.println("margin-bottom:30px;");
            out.println("}");

            out.println(".sidebar a {");
            out.println("display:block;");
            out.println("padding:15px 25px;");
            out.println("color:white;");
            out.println("text-decoration:none;");
            out.println("font-size:14px;");
            out.println("}");

            out.println(".sidebar a:hover {");
            out.println("background:#374151;");
            out.println("}");

            out.println(".sidebar .active {");
            out.println("background:#2563eb;");
            out.println("}");

            // ==========================================
            // MAIN
            // ==========================================

            out.println(".main {");
            out.println("margin-left:240px;");
            out.println("padding:30px;");
            out.println("}");

            out.println(".header {");
            out.println("display:flex;");
            out.println("justify-content:space-between;");
            out.println("align-items:center;");
            out.println("margin-bottom:25px;");
            out.println("}");

            out.println(".header h1 {");
            out.println("color:#111827;");
            out.println("margin:0;");
            out.println("}");

            // ==========================================
            // COMPANY GRID
            // ==========================================

            out.println(".company-grid {");
            out.println("display:grid;");
            out.println("grid-template-columns:"
                    + "repeat(auto-fit,minmax(330px,1fr));");
            out.println("gap:22px;");
            out.println("}");

            // ==========================================
            // COMPANY CARD
            // ==========================================

            out.println(".company-card {");
            out.println("background:white;");
            out.println("border-radius:12px;");
            out.println("padding:22px;");
            out.println("box-shadow:0 3px 12px "
                    + "rgba(0,0,0,0.08);");
            out.println("transition:transform 0.2s;");
            out.println("}");

            out.println(".company-card:hover {");
            out.println("transform:translateY(-3px);");
            out.println("}");

            out.println(".company-card h2 {");
            out.println("color:#1f2937;");
            out.println("margin-bottom:5px;");
            out.println("}");

            out.println(".company-id {");
            out.println("color:#6b7280;");
            out.println("font-size:13px;");
            out.println("margin-bottom:18px;");
            out.println("}");

            // ==========================================
            // DETAILS
            // ==========================================

            out.println(".detail {");
            out.println("margin:9px 0;");
            out.println("font-size:14px;");
            out.println("line-height:1.5;");
            out.println("}");

            out.println(".detail strong {");
            out.println("color:#374151;");
            out.println("}");

            // ==========================================
            // CUTOFF
            // ==========================================

            out.println(".cutoff {");
            out.println("display:inline-block;");
            out.println("background:#dbeafe;");
            out.println("color:#1d4ed8;");
            out.println("padding:6px 10px;");
            out.println("border-radius:15px;");
            out.println("font-weight:bold;");
            out.println("font-size:12px;");
            out.println("}");

            // ==========================================
            // APPLY BUTTON
            // ==========================================

            out.println(".apply-btn {");
            out.println("display:block;");
            out.println("width:100%;");
            out.println("text-align:center;");
            out.println("padding:11px;");
            out.println("margin-top:18px;");
            out.println("background:#2563eb;");
            out.println("color:white;");
            out.println("text-decoration:none;");
            out.println("border-radius:7px;");
            out.println("font-weight:bold;");
            out.println("}");

            out.println(".apply-btn:hover {");
            out.println("background:#1d4ed8;");
            out.println("}");

            // ==========================================
            // ALREADY APPLIED
            // ==========================================

            out.println(".applied {");
            out.println("display:block;");
            out.println("width:100%;");
            out.println("text-align:center;");
            out.println("padding:11px;");
            out.println("margin-top:18px;");
            out.println("background:#dcfce7;");
            out.println("color:#166534;");
            out.println("border-radius:7px;");
            out.println("font-weight:bold;");
            out.println("}");

            // ==========================================
            // STATUS
            // ==========================================

            out.println(".status {");
            out.println("display:inline-block;");
            out.println("margin-top:8px;");
            out.println("padding:5px 9px;");
            out.println("border-radius:12px;");
            out.println("font-size:11px;");
            out.println("font-weight:bold;");
            out.println("}");

            out.println(".shortlisted {");
            out.println("background:#fef3c7;");
            out.println("color:#92400e;");
            out.println("}");

            out.println(".interview {");
            out.println("background:#ede9fe;");
            out.println("color:#6d28d9;");
            out.println("}");

            out.println(".selected {");
            out.println("background:#dcfce7;");
            out.println("color:#166534;");
            out.println("}");

            out.println(".rejected {");
            out.println("background:#fee2e2;");
            out.println("color:#991b1b;");
            out.println("}");

            // ==========================================
            // NO COMPANIES
            // ==========================================

            out.println(".empty {");
            out.println("background:white;");
            out.println("padding:40px;");
            out.println("text-align:center;");
            out.println("border-radius:12px;");
            out.println("color:#6b7280;");
            out.println("}");

            // ==========================================
            // RESPONSIVE
            // ==========================================

            out.println("@media(max-width:800px) {");

            out.println(".sidebar {");
            out.println("width:200px;");
            out.println("}");

            out.println(".main {");
            out.println("margin-left:200px;");
            out.println("}");

            out.println("}");

            out.println("</style>");

            out.println("</head>");

            // ==========================================
            // BODY
            // ==========================================

            out.println("<body>");

            // ==========================================
            // SIDEBAR
            // ==========================================

            out.println("<div class='sidebar'>");

            out.println("<h2>Student Portal</h2>");

            out.println("<a href='HomeServlet'>"
                    + "Dashboard</a>");

            out.println("<a href='StudentCompanyServlet' "
                    + "class='active'>"
                    + "Companies</a>");

            out.println("<a href='AppliedServlet'>"
                    + "My Applications</a>");

            out.println("<a href='StudentInterviewServlet'>"
                    + "My Interviews</a>");

            out.println("<a href='ProfileServlet'>"
                    + "My Profile</a>");

            out.println("<a href='LogoutServlet'>"
                    + "Logout</a>");

            out.println("</div>");

            // ==========================================
            // MAIN CONTENT
            // ==========================================

            out.println("<div class='main'>");

            out.println("<div class='header'>");

            out.println("<h1>Available Companies</h1>");

            out.println("</div>");

            out.println("<div class='company-grid'>");

            boolean found = false;

            // ==========================================
            // DISPLAY COMPANIES
            // ==========================================

            while (rs.next()) {

                found = true;

                int id =
                        rs.getInt("id");

                String companyId =
                        rs.getString("company_id");

                String name =
                        rs.getString("name");

                String email =
                        rs.getString("email");

                String contact =
                        rs.getString("contact_no");

                String address =
                        rs.getString("address");

                String hrName =
                        rs.getString("hr_name");

                double cutoff =
                        rs.getDouble("cutoff");

                String skills =
                        rs.getString("required_skills");

                String branches =
                        rs.getString("branches");

                String branchType =
                        rs.getString("branch_type");

                int applicationId =
                        rs.getInt("application_id");

                String applicationStatus =
                        rs.getString("application_status");

                // ==========================================
                // COMPANY CARD
                // ==========================================

                out.println("<div class='company-card'>");

                out.println("<h2>"
                        + escapeHtml(name)
                        + "</h2>");

                out.println("<div class='company-id'>");

                out.println("Company ID: "
                        + escapeHtml(companyId));

                out.println("</div>");

                out.println("<div class='detail'>");

                out.println("<strong>HR:</strong> "
                        + escapeHtml(hrName));

                out.println("</div>");

                out.println("<div class='detail'>");

                out.println("<strong>Email:</strong> "
                        + escapeHtml(email));

                out.println("</div>");

                out.println("<div class='detail'>");

                out.println("<strong>Contact:</strong> "
                        + escapeHtml(contact));

                out.println("</div>");

                out.println("<div class='detail'>");

                out.println("<strong>Branches:</strong> "
                        + escapeHtml(branches));

                out.println("</div>");

                out.println("<div class='detail'>");

                out.println("<strong>Branch Type:</strong> "
                        + escapeHtml(branchType));

                out.println("</div>");

                out.println("<div class='detail'>");

                out.println("<strong>Required Skills:</strong><br>"
                        + escapeHtml(skills));

                out.println("</div>");

                out.println("<div class='detail'>");

                out.println("<strong>Address:</strong><br>"
                        + escapeHtml(address));

                out.println("</div>");

                out.println("<div class='detail'>");

                out.println("<span class='cutoff'>");

                out.println("CGPA Cutoff: "
                        + cutoff);

                out.println("</span>");

                out.println("</div>");

                // ==========================================
                // APPLICATION STATUS / APPLY
                // ==========================================

                if (applicationId > 0) {

                    String statusClass =
                            getStatusClass(applicationStatus);

                    out.println("<div class='applied'>");

                    out.println("✓ Already Applied");

                    out.println("<br>");

                    out.println("<span class='status "
                            + statusClass
                            + "'>");

                    out.println(
                            escapeHtml(applicationStatus)
                    );

                    out.println("</span>");

                    out.println("</div>");

                } else {

                    out.println("<a class='apply-btn' "
                            + "href='ApplyServlet?companyId="
                            + id
                            + "'>");

                    out.println("Apply Now");

                    out.println("</a>");
                }

                out.println("</div>");
            }

            // ==========================================
            // NO COMPANIES
            // ==========================================

            if (!found) {

                out.println("<div class='empty'>");

                out.println("<h2>No Companies Available</h2>");

                out.println("<p>"
                        + "There are currently no placement "
                        + "companies available."
                        + "</p>");

                out.println("</div>");
            }

            out.println("</div>");

            out.println("</div>");

            out.println("</body>");

            out.println("</html>");

        } catch (SQLException e) {

            e.printStackTrace();

            showError(
                    out,
                    "Database Error",
                    e.getMessage()
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
            // CLOSE STATEMENT
            // ==========================================

            try {

                if (ps != null) {
                    ps.close();
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
    // STATUS CLASS
    // ==========================================

    private String getStatusClass(String status) {

        if (status == null) {
            return "applied";
        }

        String value =
                status.trim().toUpperCase();

        if (value.equals("SHORTLISTED")) {
            return "shortlisted";
        }

        if (value.equals("INTERVIEW")) {
            return "interview";
        }

        if (value.equals("SELECTED")) {
            return "selected";
        }

        if (value.equals("REJECTED")) {
            return "rejected";
        }

        return "applied";
    }

    // ==========================================
    // ERROR PAGE
    // ==========================================

    private void showError(PrintWriter out,
                           String title,
                           String message) {

        out.println("<!DOCTYPE html>");

        out.println("<html>");

        out.println("<head>");

        out.println("<meta charset='UTF-8'>");

        out.println("<title>Error</title>");

        out.println("</head>");

        out.println("<body>");

        out.println("<div style='"
                + "font-family:Arial;"
                + "text-align:center;"
                + "margin-top:100px;'>");

        out.println("<h2 style='color:#dc2626;'>"
                + escapeHtml(title)
                + "</h2>");

        out.println("<p>"
                + escapeHtml(message)
                + "</p>");

        out.println("<a href='HomeServlet'>"
                + "Back to Dashboard"
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