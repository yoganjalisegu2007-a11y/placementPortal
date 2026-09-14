package placementPortal;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/AdminResetPasswordServlet")
public class AdminResetPasswordServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Admin Password Reset</title>");

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
        out.println("    display: flex;");
        out.println("    justify-content: center;");
        out.println("    align-items: center;");
        out.println("    padding: 30px 20px;");
        out.println("    color: #27364b;");
        out.println("}");

        out.println(".page {");
        out.println("    width: 100%;");
        out.println("    max-width: 480px;");
        out.println("}");

        out.println(".topbar {");
        out.println("    background: #172b4d;");
        out.println("    color: white;");
        out.println("    padding: 22px 25px;");
        out.println("    border-radius: 8px 8px 0 0;");
        out.println("    text-align: center;");
        out.println("}");

        out.println(".topbar .small-title {");
        out.println("    font-size: 12px;");
        out.println("    letter-spacing: 2px;");
        out.println("    color: #c5a15b;");
        out.println("    font-weight: bold;");
        out.println("    margin-bottom: 7px;");
        out.println("}");

        out.println(".topbar h1 {");
        out.println("    font-size: 24px;");
        out.println("    font-weight: 600;");
        out.println("}");

        out.println(".card {");
        out.println("    background: #ffffff;");
        out.println("    padding: 30px;");
        out.println("    border: 1px solid #dfe4ea;");
        out.println("    border-top: none;");
        out.println("    border-radius: 0 0 8px 8px;");
        out.println("    box-shadow: 0 8px 25px rgba(23, 43, 77, 0.08);");
        out.println("}");

        out.println(".card-header {");
        out.println("    margin-bottom: 25px;");
        out.println("}");

        out.println(".card-header h2 {");
        out.println("    font-size: 21px;");
        out.println("    color: #172b4d;");
        out.println("    margin-bottom: 7px;");
        out.println("}");

        out.println(".card-header p {");
        out.println("    font-size: 14px;");
        out.println("    color: #718096;");
        out.println("    line-height: 1.6;");
        out.println("}");

        out.println(".notice {");
        out.println("    background: #f8f6f0;");
        out.println("    border-left: 4px solid #c5a15b;");
        out.println("    padding: 12px 14px;");
        out.println("    margin-bottom: 23px;");
        out.println("    font-size: 13px;");
        out.println("    color: #596579;");
        out.println("    line-height: 1.5;");
        out.println("}");

        out.println(".form-group {");
        out.println("    margin-bottom: 18px;");
        out.println("}");

        out.println("label {");
        out.println("    display: block;");
        out.println("    margin-bottom: 7px;");
        out.println("    font-size: 13px;");
        out.println("    font-weight: bold;");
        out.println("    color: #344054;");
        out.println("}");

        out.println("input[type='text'],");
        out.println("input[type='password'] {");
        out.println("    width: 100%;");
        out.println("    padding: 12px 13px;");
        out.println("    border: 1px solid #cfd6df;");
        out.println("    border-radius: 5px;");
        out.println("    font-size: 14px;");
        out.println("    color: #27364b;");
        out.println("    background: #ffffff;");
        out.println("    outline: none;");
        out.println("    transition: border-color 0.2s, box-shadow 0.2s;");
        out.println("}");

        out.println("input[type='text']:focus,");
        out.println("input[type='password']:focus {");
        out.println("    border-color: #172b4d;");
        out.println("    box-shadow: 0 0 0 3px rgba(23, 43, 77, 0.08);");
        out.println("}");

        out.println("input::placeholder {");
        out.println("    color: #9aa4b2;");
        out.println("}");

        out.println(".submit-btn {");
        out.println("    width: 100%;");
        out.println("    padding: 13px;");
        out.println("    margin-top: 5px;");
        out.println("    background: #172b4d;");
        out.println("    color: #ffffff;");
        out.println("    border: none;");
        out.println("    border-radius: 5px;");
        out.println("    font-size: 14px;");
        out.println("    font-weight: bold;");
        out.println("    cursor: pointer;");
        out.println("    transition: background 0.2s;");
        out.println("}");

        out.println(".submit-btn:hover {");
        out.println("    background: #223b63;");
        out.println("}");

        out.println(".links {");
        out.println("    text-align: center;");
        out.println("    margin-top: 22px;");
        out.println("    padding-top: 18px;");
        out.println("    border-top: 1px solid #e6e9ed;");
        out.println("}");

        out.println(".links a {");
        out.println("    color: #536b8f;");
        out.println("    text-decoration: none;");
        out.println("    font-size: 13px;");
        out.println("    font-weight: 600;");
        out.println("}");

        out.println(".links a:hover {");
        out.println("    color: #172b4d;");
        out.println("    text-decoration: underline;");
        out.println("}");

        out.println(".footer {");
        out.println("    text-align: center;");
        out.println("    margin-top: 15px;");
        out.println("    color: #8a94a3;");
        out.println("    font-size: 11px;");
        out.println("}");

        out.println("@media (max-width: 520px) {");
        out.println("    body {");
        out.println("        padding: 15px;");
        out.println("        align-items: flex-start;");
        out.println("        padding-top: 30px;");
        out.println("    }");

        out.println("    .card {");
        out.println("        padding: 23px 20px;");
        out.println("    }");

        out.println("    .topbar {");
        out.println("        padding: 19px 20px;");
        out.println("    }");

        out.println("    .topbar h1 {");
        out.println("        font-size: 21px;");
        out.println("    }");
        out.println("}");

        out.println("</style>");
        out.println("</head>");

        out.println("<body>");

        out.println("<div class='page'>");

        out.println("<div class='topbar'>");
        out.println("<div class='small-title'>ADMINISTRATION PANEL</div>");
        out.println("<h1>Placement Management System</h1>");
        out.println("</div>");

        out.println("<div class='card'>");

        out.println("<div class='card-header'>");
        out.println("<h2>Reset Administrator Password</h2>");
        out.println("<p>Verify your administrator details and create a new password.</p>");
        out.println("</div>");

        out.println("<div class='notice'>");
        out.println("Enter the Admin ID and registered email associated with your administrator account.");
        out.println("</div>");

        out.println("<form action='AdminResetPasswordServlet' method='post'>");

        out.println("<div class='form-group'>");
        out.println("<label for='userId'>Admin ID</label>");
        out.println("<input type='text' id='userId' name='userId' ");
        out.println("placeholder='Enter your Admin ID' required>");
        out.println("</div>");

        out.println("<div class='form-group'>");
        out.println("<label for='email'>Registered Email</label>");
        out.println("<input type='text' id='email' name='email' ");
        out.println("placeholder='Enter your administrator email' required>");
        out.println("</div>");

        out.println("<div class='form-group'>");
        out.println("<label for='newPassword'>New Password</label>");
        out.println("<input type='password' id='newPassword' name='newPassword' ");
        out.println("placeholder='Enter new password' required>");
        out.println("</div>");

        out.println("<div class='form-group'>");
        out.println("<label for='confirmPassword'>Re-type New Password</label>");
        out.println("<input type='password' id='confirmPassword' name='confirmPassword' ");
        out.println("placeholder='Re-enter new password' required>");
        out.println("</div>");

        out.println("<input type='submit' class='submit-btn' value='Change Password'>");

        out.println("</form>");

        out.println("<div class='links'>");
        out.println("<a href='LoginServlet'>Back to Login</a>");
        out.println("</div>");

        out.println("</div>");

        out.println("<div class='footer'>");
        out.println("Administrator Account Security");
        out.println("</div>");

        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        String userIdStr = request.getParameter("userId");
        String email = request.getParameter("email");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!newPassword.equals(confirmPassword)) {

            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Password Reset Error</title>");

            out.println("<style>");
            out.println("* { box-sizing:border-box; font-family:Arial, sans-serif; }");
            out.println("body { margin:0; min-height:100vh; background:#f3f5f8; display:flex; justify-content:center; align-items:center; padding:20px; }");
            out.println(".result { width:100%; max-width:470px; background:#fff; border:1px solid #dfe4ea; border-radius:8px; padding:35px; text-align:center; box-shadow:0 8px 25px rgba(23,43,77,0.08); }");
            out.println(".icon { width:52px; height:52px; margin:0 auto 18px; border-radius:50%; background:#f7e9e9; color:#9b3a3a; display:flex; align-items:center; justify-content:center; font-size:24px; font-weight:bold; }");
            out.println("h2 { color:#172b4d; margin-bottom:10px; font-size:21px; }");
            out.println("p { color:#718096; font-size:14px; margin-bottom:22px; }");
            out.println("a { display:inline-block; padding:11px 20px; background:#172b4d; color:white; text-decoration:none; border-radius:5px; font-size:13px; font-weight:bold; }");
            out.println("a:hover { background:#223b63; }");
            out.println("</style></head><body>");

            out.println("<div class='result'>");
            out.println("<div class='icon'>!</div>");
            out.println("<h2>Password Mismatch</h2>");
            out.println("<p>The new password and confirmation password do not match.</p>");
            out.println("<a href='AdminResetPasswordServlet'>Try Again</a>");
            out.println("</div>");

            out.println("</body></html>");
            return;
        }

        String url = "jdbc:mysql://localhost:3306/placement_Portal";
        String user = "root";
        String dbPassword = "YOUR_DATABASE_PASSWORD";

        try {

            int userId = Integer.parseInt(userIdStr);

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, user, dbPassword);

            // Verify Admin ID and Email match existing record
            String verifySql = "SELECT * FROM admin WHERE id = ? AND email = ?";

            PreparedStatement verifyPs = con.prepareStatement(verifySql);

            verifyPs.setInt(1, userId);
            verifyPs.setString(2, email);

            ResultSet rs = verifyPs.executeQuery();

            if (rs.next()) {

                String updateSql = "UPDATE admin SET password = ? WHERE id = ?";

                PreparedStatement updatePs = con.prepareStatement(updateSql);

                updatePs.setString(1, newPassword);
                updatePs.setInt(2, userId);

                updatePs.executeUpdate();

                updatePs.close();

                out.println("<!DOCTYPE html>");
                out.println("<html><head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<title>Password Updated</title>");

                out.println("<style>");
                out.println("* { box-sizing:border-box; font-family:Arial, sans-serif; }");
                out.println("body { margin:0; min-height:100vh; background:#f3f5f8; display:flex; justify-content:center; align-items:center; padding:20px; }");
                out.println(".result { width:100%; max-width:470px; background:#fff; border:1px solid #dfe4ea; border-radius:8px; padding:35px; text-align:center; box-shadow:0 8px 25px rgba(23,43,77,0.08); }");
                out.println(".icon { width:52px; height:52px; margin:0 auto 18px; border-radius:50%; background:#e8f1eb; color:#3e7650; display:flex; align-items:center; justify-content:center; font-size:24px; font-weight:bold; }");
                out.println("h2 { color:#172b4d; margin-bottom:10px; font-size:21px; }");
                out.println("p { color:#718096; font-size:14px; margin-bottom:22px; }");
                out.println("a { display:inline-block; padding:11px 20px; background:#172b4d; color:white; text-decoration:none; border-radius:5px; font-size:13px; font-weight:bold; }");
                out.println("a:hover { background:#223b63; }");
                out.println("</style></head><body>");

                out.println("<div class='result'>");
                out.println("<div class='icon'>✓</div>");
                out.println("<h2>Password Updated Successfully</h2>");
                out.println("<p>Your administrator password has been changed successfully.</p>");
                out.println("<a href='LoginServlet'>Go to Login Page</a>");
                out.println("</div>");

                out.println("</body></html>");

            } else {

                out.println("<!DOCTYPE html>");
                out.println("<html><head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
                out.println("<title>Invalid Administrator Details</title>");

                out.println("<style>");
                out.println("* { box-sizing:border-box; font-family:Arial, sans-serif; }");
                out.println("body { margin:0; min-height:100vh; background:#f3f5f8; display:flex; justify-content:center; align-items:center; padding:20px; }");
                out.println(".result { width:100%; max-width:470px; background:#fff; border:1px solid #dfe4ea; border-radius:8px; padding:35px; text-align:center; box-shadow:0 8px 25px rgba(23,43,77,0.08); }");
                out.println(".icon { width:52px; height:52px; margin:0 auto 18px; border-radius:50%; background:#f7e9e9; color:#9b3a3a; display:flex; align-items:center; justify-content:center; font-size:24px; font-weight:bold; }");
                out.println("h2 { color:#172b4d; margin-bottom:10px; font-size:21px; }");
                out.println("p { color:#718096; font-size:14px; margin-bottom:22px; }");
                out.println("a { display:inline-block; padding:11px 20px; background:#172b4d; color:white; text-decoration:none; border-radius:5px; font-size:13px; font-weight:bold; }");
                out.println("a:hover { background:#223b63; }");
                out.println("</style></head><body>");

                out.println("<div class='result'>");
                out.println("<div class='icon'>!</div>");
                out.println("<h2>Invalid Administrator Details</h2>");
                out.println("<p>The Admin ID and registered email could not be verified.</p>");
                out.println("<a href='AdminResetPasswordServlet'>Try Again</a>");
                out.println("</div>");

                out.println("</body></html>");
            }

            rs.close();
            verifyPs.close();
            con.close();

        } catch (NumberFormatException e) {

            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Invalid Admin ID</title>");

            out.println("<style>");
            out.println("* { box-sizing:border-box; font-family:Arial, sans-serif; }");
            out.println("body { margin:0; min-height:100vh; background:#f3f5f8; display:flex; justify-content:center; align-items:center; padding:20px; }");
            out.println(".result { width:100%; max-width:470px; background:#fff; border:1px solid #dfe4ea; border-radius:8px; padding:35px; text-align:center; box-shadow:0 8px 25px rgba(23,43,77,0.08); }");
            out.println(".icon { width:52px; height:52px; margin:0 auto 18px; border-radius:50%; background:#f7e9e9; color:#9b3a3a; display:flex; align-items:center; justify-content:center; font-size:24px; font-weight:bold; }");
            out.println("h2 { color:#172b4d; margin-bottom:10px; font-size:21px; }");
            out.println("p { color:#718096; font-size:14px; margin-bottom:22px; }");
            out.println("a { display:inline-block; padding:11px 20px; background:#172b4d; color:white; text-decoration:none; border-radius:5px; font-size:13px; font-weight:bold; }");
            out.println("a:hover { background:#223b63; }");
            out.println("</style></head><body>");

            out.println("<div class='result'>");
            out.println("<div class='icon'>!</div>");
            out.println("<h2>Invalid Admin ID</h2>");
            out.println("<p>Please enter a valid numeric Admin ID.</p>");
            out.println("<a href='AdminResetPasswordServlet'>Try Again</a>");
            out.println("</div>");

            out.println("</body></html>");

        } catch (Exception e) {

            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Password Reset Error</title>");

            out.println("<style>");
            out.println("* { box-sizing:border-box; font-family:Arial, sans-serif; }");
            out.println("body { margin:0; min-height:100vh; background:#f3f5f8; display:flex; justify-content:center; align-items:center; padding:20px; }");
            out.println(".result { width:100%; max-width:470px; background:#fff; border:1px solid #dfe4ea; border-radius:8px; padding:35px; text-align:center; box-shadow:0 8px 25px rgba(23,43,77,0.08); }");
            out.println(".icon { width:52px; height:52px; margin:0 auto 18px; border-radius:50%; background:#f7e9e9; color:#9b3a3a; display:flex; align-items:center; justify-content:center; font-size:24px; font-weight:bold; }");
            out.println("h2 { color:#172b4d; margin-bottom:10px; font-size:21px; }");
            out.println("p { color:#718096; font-size:14px; margin-bottom:22px; word-break:break-word; }");
            out.println("a { display:inline-block; padding:11px 20px; background:#172b4d; color:white; text-decoration:none; border-radius:5px; font-size:13px; font-weight:bold; }");
            out.println("a:hover { background:#223b63; }");
            out.println("</style></head><body>");

            out.println("<div class='result'>");
            out.println("<div class='icon'>!</div>");
            out.println("<h2>Password Reset Error</h2>");
            out.println("<p>An unexpected error occurred while updating the password.</p>");
            out.println("<a href='AdminResetPasswordServlet'>Try Again</a>");
            out.println("</div>");

            out.println("</body></html>");
        }
    }
}