
package placementPortal;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CompanyServlet")
public class CompanyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Database details
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/placement_portal";

    private static final String DB_USER = "root";

    private static final String DB_PASSWORD =
            "YOUR_DATABASE_PASSWORD";

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();

        // ============================
        // ADMIN LOGIN CHECK
        // ============================

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("isAdmin") == null ||
            !Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {

            response.sendRedirect("AdminLoginServlet");
            return;
        }

        // ============================
        // HTML START
        // ============================

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Company Management - Admin</title>");

        // ============================
        // CSS
        // ============================

        out.println("<style>");

        out.println("* {");
        out.println("    box-sizing: border-box;");
        out.println("    margin: 0;");
        out.println("    padding: 0;");
        out.println("    font-family: Arial, sans-serif;");
        out.println("}");

        out.println("body {");
        out.println("    background: #f4f6f9;");
        out.println("    color: #333;");
        out.println("}");

        // Sidebar
        out.println(".sidebar {");
        out.println("    position: fixed;");
        out.println("    left: 0;");
        out.println("    top: 0;");
        out.println("    width: 240px;");
        out.println("    height: 100vh;");
        out.println("    background: #1f2937;");
        out.println("    color: white;");
        out.println("    padding-top: 25px;");
        out.println("}");

        out.println(".sidebar h2 {");
        out.println("    text-align: center;");
        out.println("    margin-bottom: 30px;");
        out.println("    font-size: 22px;");
        out.println("}");

        out.println(".sidebar a {");
        out.println("    display: block;");
        out.println("    padding: 15px 25px;");
        out.println("    color: white;");
        out.println("    text-decoration: none;");
        out.println("    font-size: 15px;");
        out.println("}");

        out.println(".sidebar a:hover {");
        out.println("    background: #374151;");
        out.println("}");

        out.println(".sidebar .active {");
        out.println("    background: #2563eb;");
        out.println("}");

        out.println(".logout {");
        out.println("    margin-top: 30px;");
        out.println("}");

        // Main content
        out.println(".main {");
        out.println("    margin-left: 240px;");
        out.println("    padding: 30px;");
        out.println("}");

        out.println(".header {");
        out.println("    display: flex;");
        out.println("    justify-content: space-between;");
        out.println("    align-items: center;");
        out.println("    margin-bottom: 25px;");
        out.println("}");

        out.println(".header h1 {");
        out.println("    font-size: 28px;");
        out.println("    color: #111827;");
        out.println("}");

        // Card
        out.println(".card {");
        out.println("    background: white;");
        out.println("    padding: 25px;");
        out.println("    border-radius: 12px;");
        out.println("    box-shadow: 0 3px 12px rgba(0,0,0,0.08);");
        out.println("    margin-bottom: 25px;");
        out.println("}");

        out.println(".card h2 {");
        out.println("    margin-bottom: 20px;");
        out.println("    color: #1f2937;");
        out.println("}");

        // Form
        out.println(".form-grid {");
        out.println("    display: grid;");
        out.println("    grid-template-columns: repeat(2, 1fr);");
        out.println("    gap: 18px;");
        out.println("}");

        out.println(".form-group {");
        out.println("    display: flex;");
        out.println("    flex-direction: column;");
        out.println("}");

        out.println(".form-group.full {");
        out.println("    grid-column: span 2;");
        out.println("}");

        out.println("label {");
        out.println("    margin-bottom: 7px;");
        out.println("    font-weight: bold;");
        out.println("    color: #374151;");
        out.println("}");

        out.println("input, select, textarea {");
        out.println("    padding: 11px;");
        out.println("    border: 1px solid #d1d5db;");
        out.println("    border-radius: 7px;");
        out.println("    font-size: 14px;");
        out.println("    outline: none;");
        out.println("}");

        out.println("input:focus, select:focus, textarea:focus {");
        out.println("    border-color: #2563eb;");
        out.println("}");

        out.println("textarea {");
        out.println("    resize: vertical;");
        out.println("    min-height: 80px;");
        out.println("}");

        // Buttons
        out.println(".button-group {");
        out.println("    margin-top: 20px;");
        out.println("    display: flex;");
        out.println("    gap: 10px;");
        out.println("}");

        out.println(".btn {");
        out.println("    border: none;");
        out.println("    padding: 11px 20px;");
        out.println("    border-radius: 7px;");
        out.println("    cursor: pointer;");
        out.println("    font-size: 14px;");
        out.println("}");

        out.println(".btn-save {");
        out.println("    background: #2563eb;");
        out.println("    color: white;");
        out.println("}");

        out.println(".btn-save:hover {");
        out.println("    background: #1d4ed8;");
        out.println("}");

        out.println(".btn-reset {");
        out.println("    background: #6b7280;");
        out.println("    color: white;");
        out.println("}");

        out.println(".btn-reset:hover {");
        out.println("    background: #4b5563;");
        out.println("}");

        // Table
        out.println(".table-container {");
        out.println("    overflow-x: auto;");
        out.println("}");

        out.println("table {");
        out.println("    width: 100%;");
        out.println("    border-collapse: collapse;");
        out.println("    margin-top: 10px;");
        out.println("}");

        out.println("th {");
        out.println("    background: #1f2937;");
        out.println("    color: white;");
        out.println("    padding: 13px;");
        out.println("    text-align: left;");
        out.println("    font-size: 13px;");
        out.println("}");

        out.println("td {");
        out.println("    padding: 12px;");
        out.println("    border-bottom: 1px solid #e5e7eb;");
        out.println("    font-size: 13px;");
        out.println("}");

        out.println("tr:hover {");
        out.println("    background: #f9fafb;");
        out.println("}");

        // Action buttons
        out.println(".action-btn {");
        out.println("    display: inline-block;");
        out.println("    padding: 7px 10px;");
        out.println("    border-radius: 5px;");
        out.println("    text-decoration: none;");
        out.println("    color: white;");
        out.println("    font-size: 12px;");
        out.println("    margin: 2px;");
        out.println("}");

        out.println(".edit {");
        out.println("    background: #f59e0b;");
        out.println("}");

        out.println(".delete {");
        out.println("    background: #dc2626;");
        out.println("}");

        out.println(".applicants {");
        out.println("    background: #16a34a;");
        out.println("}");

        // Responsive
        out.println("@media(max-width: 900px) {");
        out.println("    .sidebar {");
        out.println("        width: 200px;");
        out.println("    }");

        out.println("    .main {");
        out.println("        margin-left: 200px;");
        out.println("    }");

        out.println("    .form-grid {");
        out.println("        grid-template-columns: 1fr;");
        out.println("    }");

        out.println("    .form-group.full {");
        out.println("        grid-column: span 1;");
        out.println("    }");
        out.println("}");

        out.println("</style>");
        out.println("</head>");

        // ============================
        // BODY
        // ============================

        out.println("<body>");

        // ============================
        // SIDEBAR
        // ============================

        out.println("<div class='sidebar'>");

        out.println("<h2>Admin Panel</h2>");

        out.println("<a href='AdminDashboardServlet'>Dashboard</a>");

        out.println("<a href='StudentServlet'>Students</a>");

        out.println("<a href='CompanyServlet' class='active'>Companies</a>");

        out.println("<a href='AdminApplicationsServlet'>Applications</a>");

        out.println("<a href='AdminInterviewServlet'>Interviews</a>");

        out.println("<a href='AdminLogoutServlet' class='logout'>Logout</a>");

        out.println("</div>");

        // ============================
        // MAIN
        // ============================

        out.println("<div class='main'>");

        out.println("<div class='header'>");
        out.println("<h1>Company Management</h1>");
        out.println("</div>");

        // ============================
        // ADD COMPANY FORM
        // ============================

        out.println("<div class='card'>");

        out.println("<h2>Add New Company</h2>");

        out.println("<form action='AddCompanyServlet' method='post'>");

        out.println("<div class='form-grid'>");

        // Company ID
        out.println("<div class='form-group'>");
        out.println("<label>Company ID</label>");
        out.println("<input type='text' name='company_id' required>");
        out.println("</div>");

        // Company Name
        out.println("<div class='form-group'>");
        out.println("<label>Company Name</label>");
        out.println("<input type='text' name='name' required>");
        out.println("</div>");

        // Email
        out.println("<div class='form-group'>");
        out.println("<label>Company Email</label>");
        out.println("<input type='email' name='email' required>");
        out.println("</div>");

        // Contact
        out.println("<div class='form-group'>");
        out.println("<label>Contact Number</label>");
        out.println("<input type='text' name='contact_no' required>");
        out.println("</div>");

        // HR Name
        out.println("<div class='form-group'>");
        out.println("<label>HR Name</label>");
        out.println("<input type='text' name='hr_name' required>");
        out.println("</div>");

        // Cutoff
        out.println("<div class='form-group'>");
        out.println("<label>CGPA Cutoff</label>");
        out.println("<input type='number' step='0.01' min='0' max='10' "
                + "name='cutoff' required>");
        out.println("</div>");

        // Branches
        out.println("<div class='form-group'>");
        out.println("<label>Eligible Branches</label>");
        out.println("<input type='text' name='branches' "
                + "placeholder='IT, CSE, ECE' required>");
        out.println("</div>");

        // Branch Type
        out.println("<div class='form-group'>");
        out.println("<label>Branch Type</label>");

        out.println("<select name='branch_type' required>");

        out.println("<option value=''>Select Branch Type</option>");
        out.println("<option value='Engineering'>Engineering</option>");
        out.println("<option value='IT'>IT</option>");
        out.println("<option value='Core'>Core</option>");
        out.println("<option value='Management'>Management</option>");
        out.println("<option value='All'>All Branches</option>");

        out.println("</select>");
        out.println("</div>");

        // Skills
        out.println("<div class='form-group full'>");

        out.println("<label>Required Skills</label>");

        out.println("<input type='text' name='required_skills' "
                + "placeholder='Java, Python, SQL, Communication' required>");

        out.println("</div>");

        // Address
        out.println("<div class='form-group full'>");

        out.println("<label>Company Address</label>");

        out.println("<textarea name='address' "
                + "placeholder='Enter complete company address' required></textarea>");

        out.println("</div>");

        out.println("</div>");

        // Buttons
        out.println("<div class='button-group'>");

        out.println("<button type='submit' name='action' "
                + "value='save' class='btn btn-save'>");

        out.println("Add Company");

        out.println("</button>");

        out.println("<button type='reset' class='btn btn-reset'>");

        out.println("Clear");

        out.println("</button>");

        out.println("</div>");

        out.println("</form>");

        out.println("</div>");

        // ============================
        // COMPANY LIST
        // ============================

        out.println("<div class='card'>");

        out.println("<h2>Registered Companies</h2>");

        out.println("<div class='table-container'>");

        out.println("<table>");

        out.println("<tr>");

        out.println("<th>ID</th>");
        out.println("<th>Company ID</th>");
        out.println("<th>Name</th>");
        out.println("<th>Email</th>");
        out.println("<th>Contact</th>");
        out.println("<th>HR</th>");
        out.println("<th>Cutoff</th>");
        out.println("<th>Branches</th>");
        out.println("<th>Skills</th>");
        out.println("<th>Actions</th>");

        out.println("</tr>");

        // ============================
        // DATABASE CONNECTION
        // ============================

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con =
                    DriverManager.getConnection(
                            DB_URL,
                            DB_USER,
                            DB_PASSWORD
                    );

            String sql =
                    "SELECT id, company_id, name, email, contact_no, "
                    + "hr_name, cutoff, branches, required_skills "
                    + "FROM companies "
                    + "ORDER BY id DESC";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                int id = rs.getInt("id");

                String companyId = rs.getString("company_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String contact = rs.getString("contact_no");
                String hrName = rs.getString("hr_name");
                double cutoff = rs.getDouble("cutoff");
                String branches = rs.getString("branches");
                String skills = rs.getString("required_skills");

                out.println("<tr>");

                out.println("<td>" + id + "</td>");

                out.println("<td>" + escapeHtml(companyId) + "</td>");

                out.println("<td>" + escapeHtml(name) + "</td>");

                out.println("<td>" + escapeHtml(email) + "</td>");

                out.println("<td>" + escapeHtml(contact) + "</td>");

                out.println("<td>" + escapeHtml(hrName) + "</td>");

                out.println("<td>" + cutoff + "</td>");

                out.println("<td>" + escapeHtml(branches) + "</td>");

                out.println("<td>" + escapeHtml(skills) + "</td>");

                // Actions
                out.println("<td>");

                out.println("<a class='action-btn edit' "
                        + "href='EditCompanyServlet?id="
                        + id + "'>Edit</a>");

                out.println("<a class='action-btn delete' "
                        + "href='DeleteCompanyServlet?id="
                        + id
                        + "' onclick=\"return confirm('Are you sure you want to delete this company?');\">"
                        + "Delete</a>");

                out.println("<a class='action-btn applicants' "
                        + "href='ViewApplicantsServlet?companyId="
                        + id + "'>Applicants</a>");

                out.println("</td>");

                out.println("</tr>");
            }

            if (!found) {

                out.println("<tr>");

                out.println("<td colspan='10' "
                        + "style='text-align:center;padding:25px;'>");

                out.println("No companies registered yet.");

                out.println("</td>");

                out.println("</tr>");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (ClassNotFoundException e) {

            out.println("<tr>");

            out.println("<td colspan='10' "
                    + "style='color:red;text-align:center;'>");

            out.println("MySQL JDBC Driver not found.");

            out.println("</td>");

            out.println("</tr>");

            e.printStackTrace();

        } catch (SQLException e) {

            out.println("<tr>");

            out.println("<td colspan='10' "
                    + "style='color:red;text-align:center;'>");

            out.println("Database Error: "
                    + escapeHtml(e.getMessage()));

            out.println("</td>");

            out.println("</tr>");

            e.printStackTrace();
        }

        out.println("</table>");

        out.println("</div>");

        out.println("</div>");

        out.println("</div>");

        out.println("</body>");

        out.println("</html>");
    }

    // ============================
    // HTML ESCAPE METHOD
    // ============================

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