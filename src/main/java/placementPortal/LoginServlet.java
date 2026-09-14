
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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/placement_portal";

    private static final String DB_USER = "root";

    private static final String DB_PASSWORD =
            "Yoganjali@123";

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Prevent old login pages from being cached
        response.setHeader("Cache-Control",
                "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Login | Placement Portal</title>");

        out.println("<style>");

        out.println("* {");
        out.println("    margin: 0;");
        out.println("    padding: 0;");
        out.println("    box-sizing: border-box;");
        out.println("    font-family: Arial, Helvetica, sans-serif;");
        out.println("}");

        out.println("body {");
        out.println("    min-height: 100vh;");
        out.println("    background: #f3f5f8;");
        out.println("    color: #273142;");
        out.println("    display: flex;");
        out.println("    align-items: center;");
        out.println("    justify-content: center;");
        out.println("    padding: 30px;");
        out.println("}");

        out.println(".login-container {");
        out.println("    width: 100%;");
        out.println("    max-width: 980px;");
        out.println("    background: #ffffff;");
        out.println("    border: 1px solid #dfe4ea;");
        out.println("    border-radius: 6px;");
        out.println("    box-shadow: 0 8px 25px rgba(23,43,77,0.08);");
        out.println("    overflow: hidden;");
        out.println("}");

        out.println(".portal-header {");
        out.println("    background: #172b4d;");
        out.println("    padding: 28px 35px;");
        out.println("    text-align: center;");
        out.println("}");

        out.println(".portal-header h1 {");
        out.println("    color: #ffffff;");
        out.println("    font-size: 24px;");
        out.println("    letter-spacing: 1.5px;");
        out.println("    font-weight: 700;");
        out.println("}");

        out.println(".portal-header p {");
        out.println("    color: #c5a15b;");
        out.println("    font-size: 11px;");
        out.println("    letter-spacing: 1.5px;");
        out.println("    margin-top: 8px;");
        out.println("    text-transform: uppercase;");
        out.println("}");

        out.println(".login-area {");
        out.println("    display: grid;");
        out.println("    grid-template-columns: 1fr 1fr;");
        out.println("}");

        out.println(".login-card {");
        out.println("    padding: 38px 42px;");
        out.println("}");

        out.println(".login-card:first-child {");
        out.println("    border-right: 1px solid #e2e6eb;");
        out.println("}");

        out.println(".login-card h2 {");
        out.println("    color: #172b4d;");
        out.println("    font-size: 20px;");
        out.println("    font-weight: 600;");
        out.println("    margin-bottom: 7px;");
        out.println("}");

        out.println(".card-description {");
        out.println("    color: #7d8794;");
        out.println("    font-size: 13px;");
        out.println("    line-height: 1.5;");
        out.println("    margin-bottom: 25px;");
        out.println("}");

        out.println("form {");
        out.println("    width: 100%;");
        out.println("}");

        out.println("label {");
        out.println("    display: block;");
        out.println("    color: #394454;");
        out.println("    font-size: 12px;");
        out.println("    font-weight: 600;");
        out.println("    margin-bottom: 7px;");
        out.println("}");

        out.println("input[type='text'],");
        out.println("input[type='password'] {");
        out.println("    width: 100%;");
        out.println("    height: 44px;");
        out.println("    padding: 0 13px;");
        out.println("    margin-bottom: 17px;");
        out.println("    border: 1px solid #cfd5dc;");
        out.println("    border-radius: 4px;");
        out.println("    background: #ffffff;");
        out.println("    color: #273142;");
        out.println("    font-size: 13px;");
        out.println("    outline: none;");
        out.println("}");

        out.println("input[type='text']:focus,");
        out.println("input[type='password']:focus {");
        out.println("    border-color: #7b8da6;");
        out.println("    box-shadow: 0 0 0 2px rgba(23,43,77,0.08);");
        out.println("}");

        out.println("input::placeholder {");
        out.println("    color: #a0a7b0;");
        out.println("}");

        out.println("input[type='submit'] {");
        out.println("    width: 100%;");
        out.println("    height: 44px;");
        out.println("    border: none;");
        out.println("    border-radius: 4px;");
        out.println("    background: #172b4d;");
        out.println("    color: #ffffff;");
        out.println("    font-size: 13px;");
        out.println("    font-weight: 600;");
        out.println("    letter-spacing: 0.4px;");
        out.println("    cursor: pointer;");
        out.println("}");

        out.println("input[type='submit']:hover {");
        out.println("    background: #223b63;");
        out.println("}");

        out.println(".admin-card input[type='submit'] {");
        out.println("    background: #4d5d75;");
        out.println("}");

        out.println(".admin-card input[type='submit']:hover {");
        out.println("    background: #394b66;");
        out.println("}");

        out.println(".links {");
        out.println("    margin-top: 18px;");
        out.println("    display: flex;");
        out.println("    justify-content: space-between;");
        out.println("    gap: 12px;");
        out.println("    flex-wrap: wrap;");
        out.println("}");

        out.println(".links a {");
        out.println("    color: #5b6f8c;");
        out.println("    text-decoration: none;");
        out.println("    font-size: 12px;");
        out.println("}");

        out.println(".links a:hover {");
        out.println("    color: #172b4d;");
        out.println("    text-decoration: underline;");
        out.println("}");

        out.println(".footer {");
        out.println("    text-align: center;");
        out.println("    padding: 17px;");
        out.println("    border-top: 1px solid #e2e6eb;");
        out.println("    color: #929aa5;");
        out.println("    font-size: 11px;");
        out.println("}");

        out.println("@media (max-width: 700px) {");

        out.println("    body {");
        out.println("        padding: 15px;");
        out.println("    }");

        out.println("    .login-area {");
        out.println("        grid-template-columns: 1fr;");
        out.println("    }");

        out.println("    .login-card {");
        out.println("        padding: 30px 25px;");
        out.println("    }");

        out.println("    .login-card:first-child {");
        out.println("        border-right: none;");
        out.println("        border-bottom: 1px solid #e2e6eb;");
        out.println("    }");

        out.println("    .portal-header {");
        out.println("        padding: 24px 20px;");
        out.println("    }");

        out.println("    .portal-header h1 {");
        out.println("        font-size: 21px;");
        out.println("    }");

        out.println("}");

        out.println("</style>");
        out.println("</head>");

        out.println("<body>");

        out.println("<div class='login-container'>");

        out.println("<div class='portal-header'>");
        out.println("<h1>PLACEMENT PORTAL</h1>");
        out.println("<p>Student &amp; Administration Login</p>");
        out.println("</div>");

        out.println("<div class='login-area'>");

        // ================= STUDENT LOGIN =================

        out.println("<div class='login-card student-card'>");

        out.println("<h2>Student Login</h2>");

        out.println("<p class='card-description'>");
        out.println("Access your placement profile, company applications and placement status.");
        out.println("</p>");

        out.println("<form action='LoginServlet' method='post'>");

        out.println("<label>Email Address</label>");

        out.println("<input type='text' name='email'");
        out.println(" placeholder='Enter your email address' required>");

        out.println("<label>Password</label>");

        out.println("<input type='password' name='password'");
        out.println(" placeholder='Enter your password' required>");

        out.println("<input type='submit' value='Student Login'>");

        out.println("</form>");

        out.println("<div class='links'>");

        // IMPORTANT: RegisterServlet, NOT StudentServlet
        out.println("<a href='RegisterServlet'>Register New Account</a>");

        out.println("<a href='ResetPasswordServlet'>Forgot / Reset Password?</a>");

        out.println("</div>");

        out.println("</div>");

        // ================= ADMIN LOGIN =================

        out.println("<div class='login-card admin-card'>");

        out.println("<h2>Administrator Login</h2>");

        out.println("<p class='card-description'>");
        out.println("Authorized administrators can manage companies, students and placement records.");
        out.println("</p>");

        out.println("<form action='AdminLoginServlet' method='post'>");

        out.println("<label>Admin Email</label>");

        out.println("<input type='text' name='email'");
        out.println(" placeholder='Enter admin email' required>");

        out.println("<label>Password</label>");

        out.println("<input type='password' name='password'");
        out.println(" placeholder='Enter your password' required>");

        out.println("<input type='submit' value='Administrator Login'>");

        out.println("</form>");

        out.println("<div class='links'>");

        out.println("<a href='AdminResetPasswordServlet'>Forgot / Reset Password?</a>");

        out.println("</div>");

        out.println("</div>");

        out.println("</div>");

        out.println("<div class='footer'>");
        out.println("Placement Management System &nbsp;|&nbsp; Secure Portal Access");
        out.println("</div>");

        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {

            showMessage(response,
                    "Login Failed",
                    "Please enter both email and password.");

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
                    DB_PASSWORD);

            String sql =
                    "SELECT id, name FROM students " +
                    "WHERE email = ? AND password = ?";

            ps = con.prepareStatement(sql);

            ps.setString(1, email.trim());
            ps.setString(2, password);

            rs = ps.executeQuery();

            if (rs.next()) {

                /*
                 * Remove any previous session before creating
                 * the student login session.
                 */
                HttpSession oldSession =
                        request.getSession(false);

                if (oldSession != null) {
                    oldSession.invalidate();
                }

                HttpSession session =
                        request.getSession(true);

                session.setAttribute(
                        "studentId",
                        rs.getInt("id"));

                session.setAttribute(
                        "studentName",
                        rs.getString("name"));

                // Prevent cached protected pages
                response.setHeader(
                        "Cache-Control",
                        "no-cache, no-store, must-revalidate");

                response.setHeader(
                        "Pragma",
                        "no-cache");

                response.setDateHeader(
                        "Expires",
                        0);

                response.sendRedirect("HomeServlet");

            } else {

                showMessage(
                        response,
                        "Invalid Email or Password",
                        "The login details you entered could not be verified. Please try again."
                );
            }

        } catch (Exception e) {

            showMessage(
                    response,
                    "Unable to Process Login",
                    "Please check the database connection and try again."
            );

            e.printStackTrace();

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception ignored) {
            }

            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception ignored) {
            }

            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception ignored) {
            }
        }
    }

    private void showMessage(HttpServletResponse response,
                             String title,
                             String message)
            throws IOException {

        response.setContentType(
                "text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");

        out.println("<meta charset='UTF-8'>");

        out.println("<meta name='viewport' " +
                "content='width=device-width, initial-scale=1.0'>");

        out.println("<title>" + escapeHtml(title) +
                " | Placement Portal</title>");

        out.println("<style>");

        out.println("* {");
        out.println("    box-sizing: border-box;");
        out.println("    font-family: Arial, Helvetica, sans-serif;");
        out.println("}");

        out.println("body {");
        out.println("    margin: 0;");
        out.println("    min-height: 100vh;");
        out.println("    display: flex;");
        out.println("    justify-content: center;");
        out.println("    align-items: center;");
        out.println("    background: #f3f5f8;");
        out.println("    padding: 20px;");
        out.println("}");

        out.println(".message-card {");
        out.println("    width: 100%;");
        out.println("    max-width: 450px;");
        out.println("    background: #ffffff;");
        out.println("    border: 1px solid #e0e4e9;");
        out.println("    border-radius: 5px;");
        out.println("    padding: 35px;");
        out.println("    text-align: center;");
        out.println("    box-shadow: 0 5px 18px rgba(23,43,77,0.07);");
        out.println("}");

        out.println(".message-card h2 {");
        out.println("    color: #8b3f3f;");
        out.println("    font-size: 20px;");
        out.println("    margin-bottom: 10px;");
        out.println("}");

        out.println(".message-card p {");
        out.println("    color: #737d8a;");
        out.println("    font-size: 14px;");
        out.println("    margin-bottom: 22px;");
        out.println("}");

        out.println(".try-again {");
        out.println("    display: inline-block;");
        out.println("    padding: 11px 25px;");
        out.println("    background: #172b4d;");
        out.println("    color: #ffffff;");
        out.println("    text-decoration: none;");
        out.println("    border-radius: 4px;");
        out.println("    font-size: 13px;");
        out.println("    font-weight: 600;");
        out.println("}");

        out.println(".try-again:hover {");
        out.println("    background: #223b63;");
        out.println("}");

        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        out.println("<div class='message-card'>");

        out.println("<h2>" + escapeHtml(title) + "</h2>");

        out.println("<p>" + escapeHtml(message) + "</p>");

        out.println("<a class='try-again' " +
                "href='LoginServlet'>Try Again</a>");

        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }

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

