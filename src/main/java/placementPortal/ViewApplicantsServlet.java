
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

@WebServlet("/ViewApplicantsServlet")
public class ViewApplicantsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        // ==========================================
        // ADMIN LOGIN CHECK
        // ==========================================

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("isAdmin") == null ||
            !Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {

            response.sendRedirect("AdminLoginServlet");
            return;
        }

        // ==========================================
        // GET COMPANY ID
        // ==========================================

        String companyIdText =
                request.getParameter("companyId");

        if (companyIdText == null ||
            companyIdText.trim().isEmpty()) {

            response.sendRedirect("CompanyServlet");
            return;
        }

        int companyId;

        try {

            companyId =
                    Integer.parseInt(companyIdText);

        } catch (NumberFormatException e) {

            response.sendRedirect("CompanyServlet");
            return;
        }

        Connection con = null;
        PreparedStatement companyPs = null;
        PreparedStatement applicantPs = null;

        ResultSet companyRs = null;
        ResultSet applicantRs = null;

        try {

            // ==========================================
            // DATABASE CONNECTION
            // ==========================================

            con = DBConnection.getConnection();

            // ==========================================
            // GET COMPANY DETAILS
            // ==========================================

            String companySql =
                    "SELECT company_id, name, email, "
                    + "contact_no, hr_name, cutoff, "
                    + "branches, branch_type, "
                    + "required_skills "
                    + "FROM companies "
                    + "WHERE id = ?";

            companyPs =
                    con.prepareStatement(companySql);

            companyPs.setInt(1, companyId);

            companyRs =
                    companyPs.executeQuery();

            if (!companyRs.next()) {

                showError(
                        out,
                        "Company Not Found",
                        "The selected company does not exist."
                );

                return;
            }

            String companyCode =
                    companyRs.getString("company_id");

            String companyName =
                    companyRs.getString("name");

            String companyEmail =
                    companyRs.getString("email");

            String contactNo =
                    companyRs.getString("contact_no");

            String hrName =
                    companyRs.getString("hr_name");

            double cutoff =
                    companyRs.getDouble("cutoff");

            String branches =
                    companyRs.getString("branches");

            String branchType =
                    companyRs.getString("branch_type");

            String requiredSkills =
                    companyRs.getString("required_skills");

            companyRs.close();
            companyRs = null;

            companyPs.close();
            companyPs = null;

            // ==========================================
            // GET APPLICANTS
            // ==========================================

            String applicantSql =
                    "SELECT a.id AS application_id, "
                    + "a.status, "
                    + "a.applied_date, "
                    + "s.id AS student_id, "
                    + "s.name, "
                    + "s.email, "
                    + "s.prn, "
                    + "s.branch, "
                    + "s.year, "
                    + "s.cgpa, "
                    + "s.phone_no, "
                    + "s.skills, "
                    + "s.placement_status "
                    + "FROM applications a "
                    + "JOIN students s "
                    + "ON a.student_id = s.id "
                    + "WHERE a.company_id = ? "
                    + "ORDER BY a.applied_date DESC";

            applicantPs =
                    con.prepareStatement(applicantSql);

            applicantPs.setInt(1, companyId);

            applicantRs =
                    applicantPs.executeQuery();

            // ==========================================
            // HTML START
            // ==========================================

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");

            out.println("<meta charset='UTF-8'>");

            out.println("<meta name='viewport' "
                    + "content='width=device-width, initial-scale=1.0'>");

            out.println("<title>Company Applicants</title>");

            // ==========================================
            // CSS
            // ==========================================

            out.println("<style>");

            out.println("* {");
            out.println("box-sizing: border-box;");
            out.println("font-family: Arial, sans-serif;");
            out.println("}");

            out.println("body {");
            out.println("margin: 0;");
            out.println("background: #f4f6f9;");
            out.println("color: #333;");
            out.println("}");

            out.println(".sidebar {");
            out.println("position: fixed;");
            out.println("left: 0;");
            out.println("top: 0;");
            out.println("width: 240px;");
            out.println("height: 100vh;");
            out.println("background: #1f2937;");
            out.println("color: white;");
            out.println("padding-top: 25px;");
            out.println("}");

            out.println(".sidebar h2 {");
            out.println("text-align: center;");
            out.println("margin-bottom: 30px;");
            out.println("}");

            out.println(".sidebar a {");
            out.println("display: block;");
            out.println("padding: 15px 25px;");
            out.println("color: white;");
            out.println("text-decoration: none;");
            out.println("}");

            out.println(".sidebar a:hover {");
            out.println("background: #374151;");
            out.println("}");

            out.println(".sidebar .active {");
            out.println("background: #2563eb;");
            out.println("}");

            out.println(".main {");
            out.println("margin-left: 240px;");
            out.println("padding: 30px;");
            out.println("}");

            out.println("h1 {");
            out.println("color: #111827;");
            out.println("margin-bottom: 20px;");
            out.println("}");

            out.println(".company-card {");
            out.println("background: white;");
            out.println("padding: 25px;");
            out.println("border-radius: 12px;");
            out.println("box-shadow: 0 3px 12px rgba(0,0,0,0.08);");
            out.println("margin-bottom: 25px;");
            out.println("}");

            out.println(".company-grid {");
            out.println("display: grid;");
            out.println("grid-template-columns: repeat(3, 1fr);");
            out.println("gap: 15px;");
            out.println("}");

            out.println(".info {");
            out.println("padding: 10px;");
            out.println("background: #f9fafb;");
            out.println("border-radius: 7px;");
            out.println("}");

            out.println(".info strong {");
            out.println("display: block;");
            out.println("color: #374151;");
            out.println("margin-bottom: 5px;");
            out.println("}");

            out.println(".card {");
            out.println("background: white;");
            out.println("padding: 25px;");
            out.println("border-radius: 12px;");
            out.println("box-shadow: 0 3px 12px rgba(0,0,0,0.08);");
            out.println("}");

            out.println(".table-container {");
            out.println("overflow-x: auto;");
            out.println("}");

            out.println("table {");
            out.println("width: 100%;");
            out.println("border-collapse: collapse;");
            out.println("margin-top: 15px;");
            out.println("}");

            out.println("th {");
            out.println("background: #1f2937;");
            out.println("color: white;");
            out.println("padding: 12px;");
            out.println("text-align: left;");
            out.println("font-size: 13px;");
            out.println("}");

            out.println("td {");
            out.println("padding: 12px;");
            out.println("border-bottom: 1px solid #e5e7eb;");
            out.println("font-size: 13px;");
            out.println("}");

            out.println("tr:hover {");
            out.println("background: #f9fafb;");
            out.println("}");

            out.println(".status {");
            out.println("padding: 6px 10px;");
            out.println("border-radius: 15px;");
            out.println("font-size: 11px;");
            out.println("font-weight: bold;");
            out.println("display: inline-block;");
            out.println("}");

            out.println(".applied {");
            out.println("background: #dbeafe;");
            out.println("color: #1d4ed8;");
            out.println("}");

            out.println(".shortlisted {");
            out.println("background: #fef3c7;");
            out.println("color: #92400e;");
            out.println("}");

            out.println(".interview {");
            out.println("background: #ede9fe;");
            out.println("color: #6d28d9;");
            out.println("}");

            out.println(".selected {");
            out.println("background: #dcfce7;");
            out.println("color: #166534;");
            out.println("}");

            out.println(".rejected {");
            out.println("background: #fee2e2;");
            out.println("color: #991b1b;");
            out.println("}");

            out.println(".back {");
            out.println("display: inline-block;");
            out.println("padding: 10px 18px;");
            out.println("background: #6b7280;");
            out.println("color: white;");
            out.println("text-decoration: none;");
            out.println("border-radius: 6px;");
            out.println("margin-bottom: 20px;");
            out.println("}");

            out.println("@media(max-width: 900px) {");

            out.println(".sidebar {");
            out.println("width: 200px;");
            out.println("}");

            out.println(".main {");
            out.println("margin-left: 200px;");
            out.println("}");

            out.println(".company-grid {");
            out.println("grid-template-columns: 1fr;");
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

            out.println("<h2>Admin Panel</h2>");

            out.println("<a href='AdminDashboardServlet'>"
                    + "Dashboard</a>");

            out.println("<a href='StudentServlet'>"
                    + "Students</a>");

            out.println("<a href='CompanyServlet'>"
                    + "Companies</a>");

            out.println("<a href='AdminApplicationsServlet' "
                    + "class='active'>Applications</a>");

            out.println("<a href='AdminInterviewServlet'>"
                    + "Interviews</a>");

            out.println("<a href='AdminLogoutServlet'>"
                    + "Logout</a>");

            out.println("</div>");

            // ==========================================
            // MAIN
            // ==========================================

            out.println("<div class='main'>");

            out.println("<a class='back' "
                    + "href='CompanyServlet'>"
                    + "← Back to Companies"
                    + "</a>");

            out.println("<h1>Company Applicants</h1>");

            // ==========================================
            // COMPANY DETAILS
            // ==========================================

            out.println("<div class='company-card'>");

            out.println("<h2>"
                    + escapeHtml(companyName)
                    + "</h2>");

            out.println("<div class='company-grid'>");

            printInfo(out, "Company ID", companyCode);
            printInfo(out, "Email", companyEmail);
            printInfo(out, "Contact", contactNo);
            printInfo(out, "HR Name", hrName);
            printInfo(out, "CGPA Cutoff",
                    String.valueOf(cutoff));
            printInfo(out, "Branches", branches);
            printInfo(out, "Branch Type", branchType);
            printInfo(out, "Required Skills", requiredSkills);

            out.println("</div>");
            out.println("</div>");

            // ==========================================
            // APPLICANTS
            // ==========================================

            out.println("<div class='card'>");

            out.println("<h2>Students Who Applied</h2>");

            out.println("<div class='table-container'>");

            out.println("<table>");

            out.println("<tr>");

            out.println("<th>Application ID</th>");
            out.println("<th>Student ID</th>");
            out.println("<th>Name</th>");
            out.println("<th>Email</th>");
            out.println("<th>PRN</th>");
            out.println("<th>Branch</th>");
            out.println("<th>Year</th>");
            out.println("<th>CGPA</th>");
            out.println("<th>Phone</th>");
            out.println("<th>Skills</th>");
            out.println("<th>Placement</th>");
            out.println("<th>Status</th>");
            out.println("<th>Applied Date</th>");

            out.println("</tr>");

            boolean found = false;

            // ==========================================
            // DISPLAY APPLICANTS
            // ==========================================

            while (applicantRs.next()) {

                found = true;

                int applicationId =
                        applicantRs.getInt("application_id");

                int studentId =
                        applicantRs.getInt("student_id");

                String studentName =
                        applicantRs.getString("name");

                String studentEmail =
                        applicantRs.getString("email");

                String prn =
                        applicantRs.getString("prn");

                String branch =
                        applicantRs.getString("branch");

                String year =
                        applicantRs.getString("year");

                double cgpa =
                        applicantRs.getDouble("cgpa");

                String phone =
                        applicantRs.getString("phone_no");

                String skills =
                        applicantRs.getString("skills");

                String placementStatus =
                        applicantRs.getString("placement_status");

                String status =
                        applicantRs.getString("status");

                String appliedDate =
                        String.valueOf(
                                applicantRs.getTimestamp(
                                        "applied_date"));

                out.println("<tr>");

                out.println("<td>"
                        + applicationId
                        + "</td>");

                out.println("<td>"
                        + studentId
                        + "</td>");

                out.println("<td>"
                        + escapeHtml(studentName)
                        + "</td>");

                out.println("<td>"
                        + escapeHtml(studentEmail)
                        + "</td>");

                out.println("<td>"
                        + escapeHtml(prn)
                        + "</td>");

                out.println("<td>"
                        + escapeHtml(branch)
                        + "</td>");

                out.println("<td>"
                        + escapeHtml(year)
                        + "</td>");

                out.println("<td>"
                        + cgpa
                        + "</td>");

                out.println("<td>"
                        + escapeHtml(phone)
                        + "</td>");

                out.println("<td>"
                        + escapeHtml(skills)
                        + "</td>");

                out.println("<td>"
                        + escapeHtml(placementStatus)
                        + "</td>");

                out.println("<td>");

                out.println("<span class='status "
                        + getStatusClass(status)
                        + "'>");

                out.println(
                        escapeHtml(status)
                );

                out.println("</span>");

                out.println("</td>");

                out.println("<td>"
                        + escapeHtml(appliedDate)
                        + "</td>");

                out.println("</tr>");
            }

            // ==========================================
            // NO APPLICANTS
            // ==========================================

            if (!found) {

                out.println("<tr>");

                out.println("<td colspan='13' "
                        + "style='text-align:center;"
                        + "padding:30px;'>");

                out.println(
                        "No students have applied "
                        + "to this company yet."
                );

                out.println("</td>");

                out.println("</tr>");
            }

            out.println("</table>");

            out.println("</div>");

            out.println("</div>");

            out.println("</div>");

            out.println("</body>");

            out.println("</html>");

        } catch (SQLException e) {

            e.printStackTrace();

            showError(
                    out,
                    "Database Error",
                    escapeHtml(e.getMessage())
            );

        } finally {

            try {

                if (companyRs != null) {
                    companyRs.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {

                if (applicantRs != null) {
                    applicantRs.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {

                if (companyPs != null) {
                    companyPs.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {

                if (applicantPs != null) {
                    applicantPs.close();
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

    // ==========================================
    // PRINT COMPANY INFORMATION
    // ==========================================

    private void printInfo(PrintWriter out,
                           String label,
                           String value) {

        out.println("<div class='info'>");

        out.println("<strong>"
                + escapeHtml(label)
                + "</strong>");

        out.println(escapeHtml(value));

        out.println("</div>");
    }

    // ==========================================
    // STATUS CSS CLASS
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
                + message
                + "</p>");

        out.println("<a href='CompanyServlet'>"
                + "Back to Companies"
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
