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

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // ==============================
    // GET - SHOW REGISTRATION FORM
    // ==============================

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        setNoCache(response);

        showRegistrationForm(response);
    }

    // ==============================
    // POST - REGISTER STUDENT
    // ==============================

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        setNoCache(response);

        // ==============================
        // GET FORM VALUES
        // ==============================

        String name = request.getParameter("name");

        String email = request.getParameter("email");

        String password = request.getParameter("password");

        String confirmPassword =
                request.getParameter("confirm_password");

        String phone = request.getParameter("phone");

        String branch = request.getParameter("branch");

        String collegeName =
                request.getParameter("college_name");

        String cgpaText =
                request.getParameter("cgpa");

        String gender =
                request.getParameter("gender");

        String disabled =
                request.getParameter("disabled");

        String skills =
                request.getParameter("skills");

        String address =
                request.getParameter("address");

        String placementStatus =
                request.getParameter("placement_status");

        String previousCompany =
                request.getParameter("previous_company");

        // ==============================
        // BASIC VALIDATION
        // ==============================

        if (isEmpty(name)
                || isEmpty(email)
                || isEmpty(password)
                || isEmpty(confirmPassword)
                || isEmpty(phone)
                || isEmpty(branch)
                || isEmpty(collegeName)
                || isEmpty(cgpaText)
                || isEmpty(gender)
                || isEmpty(skills)
                || isEmpty(address)) {

            showMessage(
                    response,
                    "Registration Failed",
                    "Please fill all required fields.",
                    "RegisterServlet"
            );

            return;
        }

        // ==============================
        // PASSWORD CONFIRMATION
        // ==============================

        if (!password.equals(confirmPassword)) {

            showMessage(
                    response,
                    "Registration Failed",
                    "Password and Confirm Password do not match.",
                    "RegisterServlet"
            );

            return;
        }

        // ==============================
        // CONVERT CGPA
        // ==============================

        double cgpa;

        try {

            cgpa = Double.parseDouble(cgpaText.trim());

        } catch (NumberFormatException e) {

            showMessage(
                    response,
                    "Registration Failed",
                    "Please enter a valid CGPA.",
                    "RegisterServlet"
            );

            return;
        }

        // ==============================
        // CGPA VALIDATION
        // ==============================

        if (cgpa < 0 || cgpa > 10) {

            showMessage(
                    response,
                    "Registration Failed",
                    "CGPA must be between 0 and 10.",
                    "RegisterServlet"
            );

            return;
        }

        // ==============================
        // DEFAULT VALUES
        // ==============================

        if (isEmpty(placementStatus)) {

            placementStatus = "Fresher";
        }

        if (isEmpty(previousCompany)) {

            previousCompany = "None";
        }

        if (isEmpty(disabled)) {

            disabled = "No";
        }

        // ==============================
        // DATABASE VARIABLES
        // ==============================

        Connection con = null;

        PreparedStatement checkPs = null;

        PreparedStatement insertPs = null;

        ResultSet rs = null;

        try {

            // ==============================
            // CONNECT TO DATABASE
            // ==============================

            con = DBConnection.getConnection();

            // ==============================
            // CHECK DUPLICATE EMAIL
            // ==============================

            String checkSql =
                    "SELECT id FROM students WHERE email = ?";

            checkPs =
                    con.prepareStatement(checkSql);

            checkPs.setString(1, email.trim());

            rs = checkPs.executeQuery();

            if (rs.next()) {

                showMessage(
                        response,
                        "Registration Failed",
                        "An account with this email already exists.",
                        "RegisterServlet"
                );

                return;
            }

            // Close duplicate-check resources

            rs.close();
            rs = null;

            checkPs.close();
            checkPs = null;

            // ==============================
            // INSERT STUDENT
            // ==============================

            String sql =
                    "INSERT INTO students "
                    + "(name, email, password, phone_no, branch, "
                    + "college_name, cgpa, gender, disabled, skills, "
                    + "address, placement_status, previous_company) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            insertPs =
                    con.prepareStatement(sql);

            insertPs.setString(1, name.trim());

            insertPs.setString(2, email.trim());

            insertPs.setString(3, password);

            insertPs.setString(4, phone.trim());

            insertPs.setString(5, branch.trim());

            insertPs.setString(6, collegeName.trim());

            insertPs.setDouble(7, cgpa);

            insertPs.setString(8, gender);

            insertPs.setString(9, disabled);

            insertPs.setString(10, skills.trim());

            insertPs.setString(11, address.trim());

            insertPs.setString(12, placementStatus);

            insertPs.setString(13, previousCompany.trim());

            int rows =
                    insertPs.executeUpdate();

            // ==============================
            // SUCCESS
            // ==============================

            if (rows > 0) {

                showMessage(
                        response,
                        "Registration Successful",
                        "Your student account has been created successfully. "
                        + "You can now login.",
                        "LoginServlet"
                );

            } else {

                showMessage(
                        response,
                        "Registration Failed",
                        "Student registration could not be completed.",
                        "RegisterServlet"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            showMessage(
                    response,
                    "Registration Error",
                    "Unable to complete registration. "
                    + "Please check your database connection and try again.",
                    "RegisterServlet"
            );

        } finally {

            // ==============================
            // CLOSE RESOURCES
            // ==============================

            try {

                if (rs != null) {
                    rs.close();
                }

            } catch (Exception e) {
            }

            try {

                if (checkPs != null) {
                    checkPs.close();
                }

            } catch (Exception e) {
            }

            try {

                if (insertPs != null) {
                    insertPs.close();
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
    }

    // =========================================================
    // REGISTRATION FORM
    // =========================================================

    private void showRegistrationForm(
            HttpServletResponse response)
            throws IOException {

        response.setContentType(
                "text/html; charset=UTF-8");

        PrintWriter out =
                response.getWriter();

        out.println("<!DOCTYPE html>");

        out.println("<html>");

        out.println("<head>");

        out.println("<meta charset='UTF-8'>");

        out.println("<meta name='viewport' "
                + "content='width=device-width, initial-scale=1.0'>");

        out.println("<title>Student Registration</title>");

        out.println("<style>");

        out.println("* {");

        out.println("    margin: 0;");

        out.println("    padding: 0;");

        out.println("    box-sizing: border-box;");

        out.println("    font-family: Arial, Helvetica, sans-serif;");

        out.println("}");

        out.println("body {");

        out.println("    background: #f3f5f8;");

        out.println("    color: #27364b;");

        out.println("    min-height: 100vh;");

        out.println("    padding: 30px 20px;");

        out.println("}");

        out.println(".container {");

        out.println("    max-width: 850px;");

        out.println("    margin: auto;");

        out.println("}");

        out.println(".header {");

        out.println("    background: #172b4d;");

        out.println("    color: white;");

        out.println("    padding: 25px;");

        out.println("    text-align: center;");

        out.println("    border-radius: 9px 9px 0 0;");

        out.println("}");

        out.println(".header .small {");

        out.println("    color: #c5a15b;");

        out.println("    font-size: 12px;");

        out.println("    font-weight: bold;");

        out.println("    letter-spacing: 2px;");

        out.println("    margin-bottom: 8px;");

        out.println("}");

        out.println(".header h1 {");

        out.println("    font-size: 25px;");

        out.println("    font-weight: 600;");

        out.println("}");

        out.println(".card {");

        out.println("    background: white;");

        out.println("    padding: 30px;");

        out.println("    border: 1px solid #dfe4ea;");

        out.println("    border-top: none;");

        out.println("    border-radius: 0 0 9px 9px;");

        out.println("    box-shadow: 0 8px 25px rgba(23,43,77,0.08);");

        out.println("}");

        out.println(".intro {");

        out.println("    margin-bottom: 25px;");

        out.println("}");

        out.println(".intro h2 {");

        out.println("    color: #172b4d;");

        out.println("    font-size: 21px;");

        out.println("    margin-bottom: 7px;");

        out.println("}");

        out.println(".intro p {");

        out.println("    color: #718096;");

        out.println("    font-size: 14px;");

        out.println("}");

        out.println(".section-title {");

        out.println("    margin-top: 25px;");

        out.println("    margin-bottom: 15px;");

        out.println("    padding-bottom: 8px;");

        out.println("    border-bottom: 1px solid #e6e9ed;");

        out.println("    color: #172b4d;");

        out.println("    font-size: 16px;");

        out.println("    font-weight: bold;");

        out.println("}");

        out.println(".grid {");

        out.println("    display: grid;");

        out.println("    grid-template-columns: 1fr 1fr;");

        out.println("    gap: 17px;");

        out.println("}");

        out.println(".form-group {");

        out.println("    margin-bottom: 3px;");

        out.println("}");

        out.println("label {");

        out.println("    display: block;");

        out.println("    margin-bottom: 7px;");

        out.println("    color: #344054;");

        out.println("    font-size: 13px;");

        out.println("    font-weight: bold;");

        out.println("}");

        out.println("input, select, textarea {");

        out.println("    width: 100%;");

        out.println("    padding: 11px 12px;");

        out.println("    border: 1px solid #cfd6df;");

        out.println("    border-radius: 5px;");

        out.println("    font-size: 14px;");

        out.println("    outline: none;");

        out.println("    background: white;");

        out.println("    color: #27364b;");

        out.println("}");

        out.println("input:focus, select:focus, textarea:focus {");

        out.println("    border-color: #172b4d;");

        out.println("    box-shadow: 0 0 0 3px rgba(23,43,77,0.08);");

        out.println("}");

        out.println("textarea {");

        out.println("    min-height: 90px;");

        out.println("    resize: vertical;");

        out.println("}");

        out.println(".full {");

        out.println("    grid-column: 1 / -1;");

        out.println("}");

        out.println(".submit {");

        out.println("    margin-top: 28px;");

        out.println("    width: 100%;");

        out.println("    padding: 13px;");

        out.println("    background: #172b4d;");

        out.println("    color: white;");

        out.println("    border: none;");

        out.println("    border-radius: 5px;");

        out.println("    font-size: 14px;");

        out.println("    font-weight: bold;");

        out.println("    cursor: pointer;");

        out.println("}");

        out.println(".submit:hover {");

        out.println("    background: #223b63;");

        out.println("}");

        out.println(".back {");

        out.println("    text-align: center;");

        out.println("    margin-top: 20px;");

        out.println("    padding-top: 18px;");

        out.println("    border-top: 1px solid #e6e9ed;");

        out.println("}");

        out.println(".back a {");

        out.println("    color: #536b8f;");

        out.println("    text-decoration: none;");

        out.println("    font-size: 13px;");

        out.println("    font-weight: bold;");

        out.println("}");

        out.println(".back a:hover {");

        out.println("    text-decoration: underline;");

        out.println("}");

        out.println("@media(max-width:650px) {");

        out.println("    .grid {");

        out.println("        grid-template-columns: 1fr;");

        out.println("    }");

        out.println("    .full {");

        out.println("        grid-column: auto;");

        out.println("    }");

        out.println("    .card {");

        out.println("        padding: 22px 18px;");

        out.println("    }");

        out.println("}");

        out.println("</style>");

        out.println("</head>");

        out.println("<body>");

        out.println("<div class='container'>");

        // HEADER

        out.println("<div class='header'>");

        out.println("<div class='small'>STUDENT PORTAL</div>");

        out.println("<h1>Placement Management System</h1>");

        out.println("</div>");

        out.println("<div class='card'>");

        out.println("<div class='intro'>");

        out.println("<h2>Create Student Account</h2>");

        out.println("<p>Enter your details to create your placement portal account.</p>");

        out.println("</div>");

        out.println("<form action='RegisterServlet' method='post'>");

        // ==============================
        // PERSONAL INFORMATION
        // ==============================

        out.println("<div class='section-title'>Personal Information</div>");

        out.println("<div class='grid'>");

        out.println("<div class='form-group'>");

        out.println("<label>Full Name *</label>");

        out.println("<input type='text' name='name' "
                + "placeholder='Enter your full name' required>");

        out.println("</div>");

        out.println("<div class='form-group'>");

        out.println("<label>Email *</label>");

        out.println("<input type='email' name='email' "
                + "placeholder='Enter your email' required>");

        out.println("</div>");

        out.println("<div class='form-group'>");

        out.println("<label>Password *</label>");

        out.println("<input type='password' name='password' "
                + "placeholder='Create password' required>");

        out.println("</div>");

        out.println("<div class='form-group'>");

        out.println("<label>Confirm Password *</label>");

        out.println("<input type='password' name='confirm_password' "
                + "placeholder='Re-enter password' required>");

        out.println("</div>");

        out.println("<div class='form-group'>");

        out.println("<label>Phone Number *</label>");

        out.println("<input type='text' name='phone' "
                + "placeholder='Enter phone number' required>");

        out.println("</div>");

        out.println("<div class='form-group'>");

        out.println("<label>Gender *</label>");

        out.println("<select name='gender' required>");

        out.println("<option value=''>Select Gender</option>");

        out.println("<option value='Male'>Male</option>");

        out.println("<option value='Female'>Female</option>");

        out.println("<option value='Other'>Other</option>");

        out.println("</select>");

        out.println("</div>");

        out.println("</div>");

        // ==============================
        // ACADEMIC INFORMATION
        // ==============================

        out.println("<div class='section-title'>Academic Information</div>");

        out.println("<div class='grid'>");

        out.println("<div class='form-group'>");

        out.println("<label>Branch *</label>");

        out.println("<input type='text' name='branch' "
                + "placeholder='Example: IT' required>");

        out.println("</div>");

        out.println("<div class='form-group'>");

        out.println("<label>College Name *</label>");

        out.println("<input type='text' name='college_name' "
                + "placeholder='Enter college name' required>");

        out.println("</div>");

        out.println("<div class='form-group'>");

        out.println("<label>CGPA *</label>");

        out.println("<input type='number' name='cgpa' "
                + "min='0' max='10' step='0.01' "
                + "placeholder='Example: 8.50' required>");

        out.println("</div>");

        out.println("<div class='form-group'>");

        out.println("<label>Disability Status</label>");

        out.println("<select name='disabled'>");

        out.println("<option value='No'>No</option>");

        out.println("<option value='Yes'>Yes</option>");

        out.println("</select>");

        out.println("</div>");

        out.println("<div class='form-group full'>");

        out.println("<label>Skills *</label>");

        out.println("<input type='text' name='skills' "
                + "placeholder='Example: Java, C, SQL, HTML' required>");

        out.println("</div>");

        out.println("</div>");

        // ==============================
        // ADDRESS
        // ==============================

        out.println("<div class='section-title'>Address</div>");

        out.println("<div class='form-group'>");

        out.println("<label>Address *</label>");

        out.println("<textarea name='address' "
                + "placeholder='Enter your address' required></textarea>");

        out.println("</div>");

        // Hidden/default placement values

        out.println("<input type='hidden' "
                + "name='placement_status' value='Fresher'>");

        out.println("<input type='hidden' "
                + "name='previous_company' value='None'>");

        // SUBMIT

        out.println("<input type='submit' class='submit' "
                + "value='Create Student Account'>");

        out.println("</form>");

        out.println("<div class='back'>");

        out.println("<a href='LoginServlet'>Already have an account? Login</a>");

        out.println("</div>");

        out.println("</div>");

        out.println("</div>");

        out.println("</body>");

        out.println("</html>");
    }

    // =========================================================
    // MESSAGE PAGE
    // =========================================================

    private void showMessage(HttpServletResponse response,
                             String title,
                             String message,
                             String redirectPage)
            throws IOException {

        setNoCache(response);

        response.setContentType(
                "text/html; charset=UTF-8");

        PrintWriter out =
                response.getWriter();

        out.println("<!DOCTYPE html>");

        out.println("<html>");

        out.println("<head>");

        out.println("<meta charset='UTF-8'>");

        out.println("<meta name='viewport' "
                + "content='width=device-width, initial-scale=1.0'>");

        out.println("<title>"
                + escapeHtml(title)
                + "</title>");

        out.println("<style>");

        out.println("* {");

        out.println("    box-sizing: border-box;");

        out.println("    font-family: Arial, Helvetica, sans-serif;");

        out.println("}");

        out.println("body {");

        out.println("    margin: 0;");

        out.println("    min-height: 100vh;");

        out.println("    background: #f3f5f8;");

        out.println("    display: flex;");

        out.println("    justify-content: center;");

        out.println("    align-items: center;");

        out.println("    padding: 20px;");

        out.println("}");

        out.println(".result {");

        out.println("    width: 100%;");

        out.println("    max-width: 480px;");

        out.println("    background: white;");

        out.println("    border: 1px solid #dfe4ea;");

        out.println("    border-radius: 9px;");

        out.println("    padding: 35px;");

        out.println("    text-align: center;");

        out.println("    box-shadow: 0 8px 25px rgba(23,43,77,0.08);");

        out.println("}");

        out.println(".icon {");

        out.println("    width: 52px;");

        out.println("    height: 52px;");

        out.println("    margin: 0 auto 18px;");

        out.println("    border-radius: 50%;");

        out.println("    background: #e8f1eb;");

        out.println("    color: #3e7650;");

        out.println("    display: flex;");

        out.println("    align-items: center;");

        out.println("    justify-content: center;");

        out.println("    font-size: 24px;");

        out.println("    font-weight: bold;");

        out.println("}");

        out.println("h2 {");

        out.println("    color: #172b4d;");

        out.println("    margin-bottom: 10px;");

        out.println("    font-size: 21px;");

        out.println("}");

        out.println("p {");

        out.println("    color: #718096;");

        out.println("    font-size: 14px;");

        out.println("    line-height: 1.6;");

        out.println("    margin-bottom: 22px;");

        out.println("}");

        out.println("a {");

        out.println("    display: inline-block;");

        out.println("    padding: 11px 22px;");

        out.println("    background: #172b4d;");

        out.println("    color: white;");

        out.println("    text-decoration: none;");

        out.println("    border-radius: 5px;");

        out.println("    font-size: 13px;");

        out.println("    font-weight: bold;");

        out.println("}");

        out.println("a:hover {");

        out.println("    background: #223b63;");

        out.println("}");

        out.println("</style>");

        out.println("</head>");

        out.println("<body>");

        out.println("<div class='result'>");

        out.println("<div class='icon'>✓</div>");

        out.println("<h2>"
                + escapeHtml(title)
                + "</h2>");

        out.println("<p>"
                + escapeHtml(message)
                + "</p>");

        out.println("<a href='"
                + escapeHtml(redirectPage)
                + "'>Continue</a>");

        out.println("</div>");

        out.println("</body>");

        out.println("</html>");
    }

    // =========================================================
    // EMPTY CHECK
    // =========================================================

    private boolean isEmpty(String value) {

        return value == null ||
               value.trim().isEmpty();
    }

    // =========================================================
    // PREVENT CACHE
    // =========================================================

    private void setNoCache(
            HttpServletResponse response) {

        response.setHeader(
                "Cache-Control",
                "no-cache, no-store, must-revalidate"
        );

        response.setHeader(
                "Pragma",
                "no-cache"
        );

        response.setDateHeader(
                "Expires",
                0
        );
    }

    // =========================================================
    // HTML ESCAPE
    // =========================================================

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