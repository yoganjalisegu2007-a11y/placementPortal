
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

@WebServlet("/EditCompanyServlet")
public class EditCompanyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/placement_portal";

    private static final String DB_USER = "root";

    private static final String DB_PASSWORD =
            "Yoganjali@123";


    // ==========================================
    // SHOW EDIT FORM
    // ==========================================

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        // ==========================================
        // ADMIN CHECK
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

        String idText = request.getParameter("id");

        if (idText == null || idText.trim().isEmpty()) {

            response.sendRedirect("CompanyServlet");
            return;
        }

        int id;

        try {

            id = Integer.parseInt(idText);

        } catch (NumberFormatException e) {

            response.sendRedirect("CompanyServlet");
            return;
        }


        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    DB_URL,
                    DB_USER,
                    DB_PASSWORD
            );


            // ==========================================
            // GET COMPANY
            // ==========================================

            String sql =
                    "SELECT * FROM companies WHERE id = ?";

            ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            rs = ps.executeQuery();


            if (!rs.next()) {

                out.println("<h2>Company not found.</h2>");
                return;
            }


            String companyId = rs.getString("company_id");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String contactNo = rs.getString("contact_no");
            String address = rs.getString("address");
            String hrName = rs.getString("hr_name");
            double cutoff = rs.getDouble("cutoff");
            String requiredSkills = rs.getString("required_skills");
            String branches = rs.getString("branches");
            String branchType = rs.getString("branch_type");


            // ==========================================
            // HTML
            // ==========================================

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");

            out.println("<meta charset='UTF-8'>");

            out.println("<meta name='viewport' "
                    + "content='width=device-width, initial-scale=1.0'>");

            out.println("<title>Edit Company</title>");


            out.println("<style>");

            out.println("* {");
            out.println("box-sizing:border-box;");
            out.println("font-family:Arial,sans-serif;");
            out.println("}");

            out.println("body {");
            out.println("background:#f4f6f9;");
            out.println("margin:0;");
            out.println("padding:30px;");
            out.println("}");

            out.println(".container {");
            out.println("max-width:850px;");
            out.println("margin:auto;");
            out.println("background:white;");
            out.println("padding:30px;");
            out.println("border-radius:12px;");
            out.println("box-shadow:0 4px 15px rgba(0,0,0,0.1);");
            out.println("}");

            out.println("h1 {");
            out.println("color:#1f2937;");
            out.println("margin-bottom:25px;");
            out.println("}");

            out.println(".form-grid {");
            out.println("display:grid;");
            out.println("grid-template-columns:1fr 1fr;");
            out.println("gap:18px;");
            out.println("}");

            out.println(".group {");
            out.println("display:flex;");
            out.println("flex-direction:column;");
            out.println("}");

            out.println(".full {");
            out.println("grid-column:span 2;");
            out.println("}");

            out.println("label {");
            out.println("font-weight:bold;");
            out.println("margin-bottom:7px;");
            out.println("color:#374151;");
            out.println("}");

            out.println("input,select,textarea {");
            out.println("padding:11px;");
            out.println("border:1px solid #d1d5db;");
            out.println("border-radius:7px;");
            out.println("font-size:14px;");
            out.println("}");

            out.println("textarea {");
            out.println("min-height:90px;");
            out.println("resize:vertical;");
            out.println("}");

            out.println(".buttons {");
            out.println("margin-top:25px;");
            out.println("display:flex;");
            out.println("gap:10px;");
            out.println("}");

            out.println("button,a {");
            out.println("padding:11px 20px;");
            out.println("border-radius:7px;");
            out.println("text-decoration:none;");
            out.println("border:none;");
            out.println("cursor:pointer;");
            out.println("font-size:14px;");
            out.println("}");

            out.println("button {");
            out.println("background:#2563eb;");
            out.println("color:white;");
            out.println("}");

            out.println("a {");
            out.println("background:#6b7280;");
            out.println("color:white;");
            out.println("}");

            out.println("@media(max-width:700px) {");

            out.println(".form-grid {");
            out.println("grid-template-columns:1fr;");
            out.println("}");

            out.println(".full {");
            out.println("grid-column:span 1;");
            out.println("}");

            out.println("}");

            out.println("</style>");

            out.println("</head>");

            out.println("<body>");

            out.println("<div class='container'>");

            out.println("<h1>Edit Company</h1>");


            // ==========================================
            // EDIT FORM
            // ==========================================

            out.println("<form action='EditCompanyServlet' method='post'>");

            // Hidden database ID
            out.println("<input type='hidden' name='id' value='"
                    + id + "'>");


            out.println("<div class='form-grid'>");


            // Company ID
            out.println("<div class='group'>");

            out.println("<label>Company ID</label>");

            out.println("<input type='text' "
                    + "name='company_id' "
                    + "value='" + escapeHtml(companyId)
                    + "' required>");

            out.println("</div>");


            // Company Name
            out.println("<div class='group'>");

            out.println("<label>Company Name</label>");

            out.println("<input type='text' "
                    + "name='name' "
                    + "value='" + escapeHtml(name)
                    + "' required>");

            out.println("</div>");


            // Email
            out.println("<div class='group'>");

            out.println("<label>Company Email</label>");

            out.println("<input type='email' "
                    + "name='email' "
                    + "value='" + escapeHtml(email)
                    + "' required>");

            out.println("</div>");


            // Contact
            out.println("<div class='group'>");

            out.println("<label>Contact Number</label>");

            out.println("<input type='text' "
                    + "name='contact_no' "
                    + "value='" + escapeHtml(contactNo)
                    + "' required>");

            out.println("</div>");


            // HR
            out.println("<div class='group'>");

            out.println("<label>HR Name</label>");

            out.println("<input type='text' "
                    + "name='hr_name' "
                    + "value='" + escapeHtml(hrName)
                    + "' required>");

            out.println("</div>");


            // Cutoff
            out.println("<div class='group'>");

            out.println("<label>CGPA Cutoff</label>");

            out.println("<input type='number' "
                    + "step='0.01' "
                    + "min='0' "
                    + "max='10' "
                    + "name='cutoff' "
                    + "value='" + cutoff
                    + "' required>");

            out.println("</div>");


            // Branches
            out.println("<div class='group'>");

            out.println("<label>Eligible Branches</label>");

            out.println("<input type='text' "
                    + "name='branches' "
                    + "value='" + escapeHtml(branches)
                    + "' required>");

            out.println("</div>");


            // Branch Type
            out.println("<div class='group'>");

            out.println("<label>Branch Type</label>");

            out.println("<select name='branch_type' required>");

            out.println("<option value='Engineering' "
                    + selected(branchType, "Engineering")
                    + ">Engineering</option>");

            out.println("<option value='IT' "
                    + selected(branchType, "IT")
                    + ">IT</option>");

            out.println("<option value='Core' "
                    + selected(branchType, "Core")
                    + ">Core</option>");

            out.println("<option value='Management' "
                    + selected(branchType, "Management")
                    + ">Management</option>");

            out.println("<option value='All' "
                    + selected(branchType, "All")
                    + ">All Branches</option>");

            out.println("</select>");

            out.println("</div>");


            // Skills
            out.println("<div class='group full'>");

            out.println("<label>Required Skills</label>");

            out.println("<input type='text' "
                    + "name='required_skills' "
                    + "value='" + escapeHtml(requiredSkills)
                    + "' required>");

            out.println("</div>");


            // Address
            out.println("<div class='group full'>");

            out.println("<label>Company Address</label>");

            out.println("<textarea name='address' required>"
                    + escapeHtml(address)
                    + "</textarea>");

            out.println("</div>");


            out.println("</div>");


            // Buttons
            out.println("<div class='buttons'>");

            out.println("<button type='submit'>Update Company</button>");

            out.println("<a href='CompanyServlet'>Cancel</a>");

            out.println("</div>");

            out.println("</form>");

            out.println("</div>");

            out.println("</body>");

            out.println("</html>");


        } catch (ClassNotFoundException e) {

            e.printStackTrace();

            out.println("<h2>MySQL JDBC Driver not found.</h2>");

        } catch (SQLException e) {

            e.printStackTrace();

            out.println("<h2>Database Error</h2>");
            out.println("<p>" + escapeHtml(e.getMessage()) + "</p>");

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (ps != null) {
                    ps.close();
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
    // UPDATE COMPANY
    // ==========================================

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // ==========================================
        // ADMIN CHECK
        // ==========================================

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("isAdmin") == null ||
            !Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {

            response.sendRedirect("AdminLoginServlet");
            return;
        }


        // ==========================================
        // GET FORM DATA
        // ==========================================

        String idText = request.getParameter("id");

        String companyId = request.getParameter("company_id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String contactNo = request.getParameter("contact_no");
        String hrName = request.getParameter("hr_name");
        String cutoffText = request.getParameter("cutoff");
        String branches = request.getParameter("branches");
        String branchType = request.getParameter("branch_type");
        String requiredSkills = request.getParameter("required_skills");
        String address = request.getParameter("address");


        int id;

        double cutoff;


        try {

            id = Integer.parseInt(idText);

        } catch (Exception e) {

            response.sendRedirect("CompanyServlet");
            return;
        }


        try {

            cutoff = Double.parseDouble(cutoffText);

        } catch (Exception e) {

            response.sendRedirect("CompanyServlet");
            return;
        }


        // ==========================================
        // VALIDATION
        // ==========================================

        if (companyId == null || companyId.trim().isEmpty() ||
            name == null || name.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            contactNo == null || contactNo.trim().isEmpty() ||
            hrName == null || hrName.trim().isEmpty() ||
            branches == null || branches.trim().isEmpty() ||
            branchType == null || branchType.trim().isEmpty() ||
            requiredSkills == null || requiredSkills.trim().isEmpty() ||
            address == null || address.trim().isEmpty()) {

            response.sendRedirect("CompanyServlet");
            return;
        }


        if (cutoff < 0 || cutoff > 10) {

            response.sendRedirect("CompanyServlet");
            return;
        }


        Connection con = null;
        PreparedStatement ps = null;


        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    DB_URL,
                    DB_USER,
                    DB_PASSWORD
            );


            // ==========================================
            // UPDATE QUERY
            // ==========================================

            String sql =
                    "UPDATE companies SET "
                    + "company_id = ?, "
                    + "name = ?, "
                    + "email = ?, "
                    + "contact_no = ?, "
                    + "address = ?, "
                    + "hr_name = ?, "
                    + "cutoff = ?, "
                    + "required_skills = ?, "
                    + "branches = ?, "
                    + "branch_type = ? "
                    + "WHERE id = ?";


            ps = con.prepareStatement(sql);

            ps.setString(1, companyId.trim());
            ps.setString(2, name.trim());
            ps.setString(3, email.trim());
            ps.setString(4, contactNo.trim());
            ps.setString(5, address.trim());
            ps.setString(6, hrName.trim());
            ps.setDouble(7, cutoff);
            ps.setString(8, requiredSkills.trim());
            ps.setString(9, branches.trim());
            ps.setString(10, branchType.trim());
            ps.setInt(11, id);


            int rows = ps.executeUpdate();


            if (rows > 0) {

                response.sendRedirect("CompanyServlet");

            } else {

                response.sendRedirect("CompanyServlet");
            }


        } catch (ClassNotFoundException e) {

            e.printStackTrace();

            response.sendRedirect("CompanyServlet");

        } catch (SQLException e) {

            e.printStackTrace();

            response.sendRedirect("CompanyServlet");

        } finally {

            try {
                if (ps != null) {
                    ps.close();
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
    // SELECT OPTION
    // ==========================================

    private String selected(String current,
                            String value) {

        if (current != null && current.equals(value)) {
            return "selected";
        }

        return "";
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
