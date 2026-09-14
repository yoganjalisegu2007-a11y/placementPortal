
package placementPortal;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/CompanyLoginServlet")
public class CompanyLoginServlet extends HttpServlet {

    // Render Company Login Page
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Company Login | Placement Portal</title>");

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
        out.println("    margin-top: 8px;");
        out.println("    color: #c5a15b;");
        out.println("    font-size: 11px;");
        out.println("    letter-spacing: 1.5px;");
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

        /* ---------- FORM ---------- */
        out.println("label {");
        out.println("    display: block;");
        out.println("    color: #394454;");
        out.println("    font-size: 12px;");
        out.println("    font-weight: 600;");
        out.println("    margin-bottom: 7px;");
        out.println("}");

        out.println("input[type='text'],");
        out.println("input[type='email'] {");
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
        out.println("input[type='email']:focus {");
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

        /* ---------- INFORMATION BOX ---------- */
        out.println(".info-box {");
        out.println("    margin-top: 22px;");
        out.println("    padding: 13px 15px;");
        out.println("    background: #f7f8fa;");
        out.println("    border: 1px solid #e1e5ea;");
        out.println("    border-left: 3px solid #c5a15b;");
        out.println("}");

        out.println(".info-box p {");
        out.println("    color: #6f7885;");
        out.println("    font-size: 12px;");
        out.println("    line-height: 1.5;");
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

        out.println("<h1>COMPANY PORTAL</h1>");

        out.println("<p>Placement Management System</p>");

        out.println("</div>");

        /* ---------- FORM AREA ---------- */
        out.println("<div class='form-area'>");

        out.println("<div class='form-title'>Company Login</div>");

        out.println("<div class='form-description'>");
        out.println("Authorized company representatives can access the company placement management portal.");
        out.println("</div>");

        /* ---------- LOGIN FORM ---------- */
        out.println("<form action='CompanyLoginServlet' method='post'>");

        out.println("<label>Company ID</label>");

        out.println("<input type='text' name='companyId'");
        out.println(" placeholder='Enter your company ID' required>");

        out.println("<label>Company Email</label>");

        out.println("<input type='email' name='email'");
        out.println(" placeholder='Enter official company email' required>");

        out.println("<input type='submit' value='Company Login'>");

        out.println("</form>");

        /* ---------- INFORMATION ---------- */
        out.println("<div class='info-box'>");

        out.println("<p>");
        out.println("Please use the Company ID and official email registered with the placement portal.");
        out.println("</p>");

        out.println("</div>");

        /* ---------- LINK ---------- */
        out.println("<div class='links'>");

        out.println("<a href='CompanyServlet'>Back to Companies Page</a>");

        out.println("</div>");

        out.println("</div>");

        /* ---------- FOOTER ---------- */
        out.println("<div class='card-footer'>");
        out.println("Placement Management System &nbsp;|&nbsp; Company Access");
        out.println("</div>");

        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }

    // Authenticate Company Details
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String companyId = request.getParameter("companyId");
        String email = request.getParameter("email");

        String url = "jdbc:mysql://localhost:3306/placement_Portal";
        String user = "root";
        String dbPassword = "YOUR_DATABASE_PASSWORD";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, user, dbPassword);

            String sql = "SELECT * FROM companies WHERE company_id = ? AND email = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, companyId);
            ps.setString(2, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                HttpSession session = request.getSession();

                session.setAttribute("companyDbId", rs.getInt("id"));

                session.setAttribute("companyName", rs.getString("name"));

                // Redirect to Company management panel upon success
                response.sendRedirect("CompanyServlet");

            } else {

                response.setContentType("text/html; charset=UTF-8");

                PrintWriter out = response.getWriter();

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<title>Login Failed | Company Portal</title>");

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
                out.println("    line-height: 1.5;");
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

                out.println("<h2>Invalid Company Credentials</h2>");

                out.println("<p>");
                out.println("The Company ID and email combination could not be verified.");
                out.println(" Please check your details and try again.");
                out.println("</p>");

                out.println("<a class='try-again' href='CompanyLoginServlet'>Try Again</a>");

                out.println("</div>");

                out.println("</body>");
                out.println("</html>");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            response.setContentType("text/html; charset=UTF-8");

            PrintWriter out = response.getWriter();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Company Login Error</title>");

            out.println("<style>");

            out.println("body {");
            out.println("    margin: 0;");
            out.println("    min-height: 100vh;");
            out.println("    display: flex;");
            out.println("    justify-content: center;");
            out.println("    align-items: center;");
            out.println("    background: #f3f5f8;");
            out.println("    font-family: Arial, Helvetica, sans-serif;");
            out.println("}");

            out.println(".error-box {");
            out.println("    width: 100%;");
            out.println("    max-width: 500px;");
            out.println("    background: #ffffff;");
            out.println("    border: 1px solid #e0e4e9;");
            out.println("    border-left: 4px solid #a65a5a;");
            out.println("    border-radius: 4px;");
            out.println("    padding: 25px;");
            out.println("    color: #8b3f3f;");
            out.println("}");

            out.println(".error-box strong {");
            out.println("    font-size: 15px;");
            out.println("}");

            out.println(".error-box p {");
            out.println("    margin-top: 10px;");
            out.println("    font-size: 13px;");
            out.println("    color: #737d8a;");
            out.println("    word-break: break-word;");
            out.println("}");

            out.println("</style>");
            out.println("</head>");

            out.println("<body>");

            out.println("<div class='error-box'>");

            out.println("<strong>Unable to process company login.</strong>");

            out.println("<p>");
            out.println("Error: " + e.getMessage());
            out.println("</p>");

            out.println("</div>");

            out.println("</body>");
            out.println("</html>");
        }
    }
}
