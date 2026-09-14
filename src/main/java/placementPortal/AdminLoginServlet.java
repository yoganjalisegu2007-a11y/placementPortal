
package placementPortal;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Admin credentials
    // Suitable for college/demo project
    private static final String ADMIN_EMAIL =
            "admin@placement.com";

    private static final String ADMIN_PASSWORD =
            "admin123";

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Prevent browser from showing cached admin pages
        response.setHeader("Cache-Control",
                "no-cache, no-store, must-revalidate");

        response.setHeader("Pragma", "no-cache");

        response.setDateHeader("Expires", 0);

        response.setContentType(
                "text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");

        out.println("<meta charset='UTF-8'>");

        out.println("<meta name='viewport' " +
                "content='width=device-width, initial-scale=1.0'>");

        out.println("<title>Admin Login | Placement Portal</title>");

        out.println("<style>");

        /* ---------- RESET ---------- */

        out.println("* {");
        out.println("    margin: 0;");
        out.println("    padding: 0;");
        out.println("    box-sizing: border-box;");
        out.println("    font-family: Arial, Helvetica, sans-serif;");
        out.println("}");

        /* ---------- PAGE ---------- */

        out.println("body {");
        out.println("    min-height: 100vh;");
        out.println("    display: flex;");
        out.println("    justify-content: center;");
        out.println("    align-items: center;");
        out.println("    background: #f3f5f8;");
        out.println("    padding: 25px;");
        out.println("}");

        /* ---------- MAIN CARD ---------- */

        out.println(".login-card {");
        out.println("    width: 100%;");
        out.println("    max-width: 470px;");
        out.println("    background: #ffffff;");
        out.println("    border: 1px solid #dfe4ea;");
        out.println("    border-radius: 6px;");
        out.println("    box-shadow: 0 8px 25px rgba(23,43,77,0.08);");
        out.println("    overflow: hidden;");
        out.println("}");

        /* ---------- HEADER ---------- */

        out.println(".card-header {");
        out.println("    background: #172b4d;");
        out.println("    padding: 30px;");
        out.println("    text-align: center;");
        out.println("}");

        out.println(".card-header h1 {");
        out.println("    color: #ffffff;");
        out.println("    font-size: 23px;");
        out.println("    font-weight: 700;");
        out.println("    letter-spacing: 1px;");
        out.println("}");

        out.println(".card-header p {");
        out.println("    color: #c5a15b;");
        out.println("    font-size: 11px;");
        out.println("    letter-spacing: 1.5px;");
        out.println("    margin-top: 8px;");
        out.println("    text-transform: uppercase;");
        out.println("}");

        /* ---------- FORM AREA ---------- */

        out.println(".form-area {");
        out.println("    padding: 35px 40px 30px 40px;");
        out.println("}");

        out.println(".form-title {");
        out.println("    color: #172b4d;");
        out.println("    font-size: 19px;");
        out.println("    font-weight: 600;");
        out.println("    margin-bottom: 7px;");
        out.println("}");

        out.println(".form-description {");
        out.println("    color: #7d8794;");
        out.println("    font-size: 13px;");
        out.println("    line-height: 1.5;");
        out.println("    margin-bottom: 25px;");
        out.println("}");

        /* ---------- SECURITY NOTICE ---------- */

        out.println(".security-note {");
        out.println("    margin-bottom: 23px;");
        out.println("    padding: 12px 14px;");
        out.println("    background: #f7f8fa;");
        out.println("    border: 1px solid #e1e5ea;");
        out.println("    border-left: 3px solid #c5a15b;");
        out.println("}");

        out.println(".security-note p {");
        out.println("    color: #6f7885;");
        out.println("    font-size: 12px;");
        out.println("    line-height: 1.5;");
        out.println("}");

        /* ---------- FORM ---------- */

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
        out.println("    margin-bottom: 18px;");
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

        /* ---------- LOGIN BUTTON ---------- */

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

        /* ---------- LINKS ---------- */

        out.println(".links {");
        out.println("    margin-top: 20px;");
        out.println("    text-align: center;");
        out.println("}");

        out.println(".links a {");
        out.println("    color: #5b6f8c;");
        out.println("    font-size: 12px;");
        out.println("    text-decoration: none;");
        out.println("}");

        out.println(".links a:hover {");
        out.println("    color: #172b4d;");
        out.println("    text-decoration: underline;");
        out.println("}");

        /* ---------- FOOTER ---------- */

        out.println(".card-footer {");
        out.println("    border-top: 1px solid #e2e6eb;");
        out.println("    padding: 16px;");
        out.println("    text-align: center;");
        out.println("    color: #929aa5;");
        out.println("    font-size: 11px;");
        out.println("}");

        /* ---------- RESPONSIVE ---------- */

        out.println("@media (max-width: 520px) {");

        out.println("    body {");
        out.println("        padding: 15px;");
        out.println("    }");

        out.println("    .card-header {");
        out.println("        padding: 25px 20px;");
        out.println("    }");

        out.println("    .card-header h1 {");
        out.println("        font-size: 20px;");
        out.println("    }");

        out.println("    .form-area {");
        out.println("        padding: 28px 22px 25px 22px;");
        out.println("    }");

        out.println("}");

        out.println("</style>");

        out.println("</head>");

        out.println("<body>");

        /* ---------- MAIN CARD ---------- */

        out.println("<div class='login-card'>");

        /* ---------- HEADER ---------- */

        out.println("<div class='card-header'>");

        out.println("<h1>ADMINISTRATION PANEL</h1>");

        out.println("<p>Placement Management System</p>");

        out.println("</div>");

        /* ---------- FORM AREA ---------- */

        out.println("<div class='form-area'>");

        out.println("<div class='form-title'>");
        out.println("Administrator Login");
        out.println("</div>");

        out.println("<div class='form-description'>");
        out.println("Authorized administrators can manage companies, students and placement activities.");
        out.println("</div>");

        /* ---------- SECURITY NOTICE ---------- */

        out.println("<div class='security-note'>");

        out.println("<p>");
        out.println("Administrative access is restricted to authorized placement portal personnel.");
        out.println("</p>");

        out.println("</div>");

        /* ---------- LOGIN FORM ---------- */

        out.println("<form action='AdminLoginServlet' method='post'>");

        out.println("<label>Admin Email</label>");

        out.println("<input type='text' name='email'");
        out.println(" placeholder='Enter administrator email' required>");

        out.println("<label>Password</label>");

        out.println("<input type='password' name='password'");
        out.println(" placeholder='Enter your password' required>");

        out.println("<input type='submit' value='Administrator Login'>");

        out.println("</form>");

        /* ---------- LINK ---------- */

        out.println("<div class='links'>");

        out.println("<a href='LoginServlet'>Back to Student Login</a>");

        out.println("</div>");

        out.println("</div>");

        /* ---------- FOOTER ---------- */

        out.println("<div class='card-footer'>");

        out.println("Placement Management System &nbsp;|&nbsp; Restricted Administrative Access");

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

        // Check empty values
        if (email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {

            showMessage(
                    response,
                    "Invalid Login",
                    "Please enter both administrator email and password."
            );

            return;
        }

        email = email.trim();

        // Check admin credentials
        if (ADMIN_EMAIL.equals(email)
                && ADMIN_PASSWORD.equals(password)) {

            /*
             * Remove any existing session.
             * This prevents an old student session from
             * interfering with the admin session.
             */
            HttpSession oldSession =
                    request.getSession(false);

            if (oldSession != null) {
                oldSession.invalidate();
            }

            // Create a new admin session
            HttpSession session =
                    request.getSession(true);

            session.setAttribute("isAdmin", true);

            session.setAttribute(
                    "adminEmail",
                    ADMIN_EMAIL
            );

            // Prevent cached pages
            response.setHeader(
                    "Cache-Control",
                    "no-cache, no-store, must-revalidate");

            response.setHeader(
                    "Pragma",
                    "no-cache");

            response.setDateHeader(
                    "Expires",
                    0);

            /*
             * Go directly to the admin dashboard.
             */
            response.sendRedirect(
                    "AdminDashboardServlet");

        } else {

            showMessage(
                    response,
                    "Invalid Administrator Credentials",
                    "The email address or password entered could not be verified. Please check your credentials and try again."
            );
        }
    }

    private void showMessage(HttpServletResponse response,
                             String title,
                             String message)
            throws IOException {

        response.setContentType(
                "text/html; charset=UTF-8");

        PrintWriter out =
                response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");

        out.println("<meta charset='UTF-8'>");

        out.println("<meta name='viewport' " +
                "content='width=device-width, initial-scale=1.0'>");

        out.println("<title>" +
                escapeHtml(title) +
                " | Placement Portal</title>");

        out.println("<style>");

        out.println("* {");
        out.println("    margin: 0;");
        out.println("    padding: 0;");
        out.println("    box-sizing: border-box;");
        out.println("    font-family: Arial, Helvetica, sans-serif;");
        out.println("}");

        out.println("body {");
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
        out.println("    border: 1px solid #dfe4ea;");
        out.println("    border-left: 4px solid #a65a5a;");
        out.println("    border-radius: 5px;");
        out.println("    box-shadow: 0 8px 25px rgba(23,43,77,0.08);");
        out.println("    overflow: hidden;");
        out.println("}");

        out.println(".message-header {");
        out.println("    background: #172b4d;");
        out.println("    padding: 24px 30px;");
        out.println("}");

        out.println(".message-header h1 {");
        out.println("    color: #ffffff;");
        out.println("    font-size: 19px;");
        out.println("    letter-spacing: 1px;");
        out.println("}");

        out.println(".message-header p {");
        out.println("    color: #c5a15b;");
        out.println("    font-size: 10px;");
        out.println("    margin-top: 6px;");
        out.println("    letter-spacing: 1.2px;");
        out.println("    text-transform: uppercase;");
        out.println("}");

        out.println(".message-content {");
        out.println("    padding: 30px;");
        out.println("    text-align: center;");
        out.println("}");

        out.println(".message-content h2 {");
        out.println("    color: #8b3f3f;");
        out.println("    font-size: 19px;");
        out.println("    margin-bottom: 10px;");
        out.println("}");

        out.println(".message-content p {");
        out.println("    color: #737d8a;");
        out.println("    font-size: 13px;");
        out.println("    line-height: 1.6;");
        out.println("    margin-bottom: 22px;");
        out.println("}");

        out.println(".try-again {");
        out.println("    display: inline-block;");
        out.println("    padding: 11px 24px;");
        out.println("    background: #172b4d;");
        out.println("    color: #ffffff;");
        out.println("    text-decoration: none;");
        out.println("    border-radius: 4px;");
        out.println("    font-size: 12px;");
        out.println("    font-weight: 600;");
        out.println("}");

        out.println(".try-again:hover {");
        out.println("    background: #223b63;");
        out.println("}");

        out.println("</style>");

        out.println("</head>");

        out.println("<body>");

        out.println("<div class='message-card'>");

        out.println("<div class='message-header'>");

        out.println("<h1>ADMINISTRATION PANEL</h1>");

        out.println("<p>Placement Management System</p>");

        out.println("</div>");

        out.println("<div class='message-content'>");

        out.println("<h2>" +
                escapeHtml(title) +
                "</h2>");

        out.println("<p>" +
                escapeHtml(message) +
                "</p>");

        out.println("<a class='try-again' " +
                "href='AdminLoginServlet'>");

        out.println("Try Again");

        out.println("</a>");

        out.println("</div>");

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
