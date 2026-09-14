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

@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {

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

        showResetPage(response, "", "");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword =
                request.getParameter("confirmPassword");

        // Remove extra spaces
        if (email != null) {
            email = email.trim();
        }

        // Check empty fields
        if (email == null || email.isEmpty() ||
            newPassword == null || newPassword.isEmpty() ||
            confirmPassword == null ||
            confirmPassword.isEmpty()) {

            showResetPage(
                response,
                "Please fill all fields.",
                "error"
            );
            return;
        }

        // Check password match
        if (!newPassword.equals(confirmPassword)) {

            showResetPage(
                response,
                "New password and confirm password do not match.",
                "error"
            );
            return;
        }

        Connection con = null;
        PreparedStatement checkPs = null;
        PreparedStatement updatePs = null;
        ResultSet rs = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(
                    DB_URL,
                    DB_USER,
                    DB_PASSWORD
            );

            // Find student using email
            String checkSql =
                    "SELECT id FROM students WHERE email = ?";

            checkPs = con.prepareStatement(checkSql);

            checkPs.setString(1, email);

            rs = checkPs.executeQuery();

            if (!rs.next()) {

                showResetPage(
                    response,
                    "No student account found with this email.",
                    "error"
                );

                return;
            }

            // Get student ID automatically
            int studentId = rs.getInt("id");

            // Update password
            String updateSql =
                    "UPDATE students SET password = ? " +
                    "WHERE id = ?";

            updatePs = con.prepareStatement(updateSql);

            updatePs.setString(1, newPassword);
            updatePs.setInt(2, studentId);

            int updated = updatePs.executeUpdate();

            if (updated > 0) {

                response.setContentType(
                        "text/html;charset=UTF-8"
                );

                PrintWriter out = response.getWriter();

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");

                out.println("<title>Password Changed</title>");

                out.println("<style>");

                out.println("body {");
                out.println("font-family: Arial;");
                out.println("background: #f4f6f9;");
                out.println("display: flex;");
                out.println("justify-content: center;");
                out.println("align-items: center;");
                out.println("height: 100vh;");
                out.println("}");

                out.println(".box {");
                out.println("background: white;");
                out.println("padding: 35px;");
                out.println("border-radius: 12px;");
                out.println("text-align: center;");
                out.println("box-shadow: 0 3px 15px rgba(0,0,0,0.15);");
                out.println("}");

                out.println("h2 { color: green; }");

                out.println("a {");
                out.println("display: inline-block;");
                out.println("margin-top: 15px;");
                out.println("padding: 10px 20px;");
                out.println("background: #007bff;");
                out.println("color: white;");
                out.println("text-decoration: none;");
                out.println("border-radius: 6px;");
                out.println("}");

                out.println("</style>");

                out.println("</head>");

                out.println("<body>");

                out.println("<div class='box'>");

                out.println("<h2>Password Changed Successfully!</h2>");

                out.println("<p>You can now login using your new password.</p>");

                out.println("<a href='LoginServlet'>Go to Student Login</a>");

                out.println("</div>");

                out.println("</body>");

                out.println("</html>");

            } else {

                showResetPage(
                    response,
                    "Password could not be changed. Please try again.",
                    "error"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            showResetPage(
                response,
                "Something went wrong: " + e.getMessage(),
                "error"
            );

        } finally {

            try {
                if (rs != null) rs.close();
            } catch (Exception e) {
            }

            try {
                if (checkPs != null) checkPs.close();
            } catch (Exception e) {
            }

            try {
                if (updatePs != null) updatePs.close();
            } catch (Exception e) {
            }

            try {
                if (con != null) con.close();
            } catch (Exception e) {
            }
        }
    }

    private void showResetPage(HttpServletResponse response,
                               String message,
                               String type)
            throws IOException {

        response.setContentType(
                "text/html;charset=UTF-8"
        );

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");

        out.println("<head>");

        out.println("<title>Reset Password</title>");

        out.println("<style>");

        out.println("body {");
        out.println("font-family: Arial, sans-serif;");
        out.println("background: #f4f6f9;");
        out.println("display: flex;");
        out.println("justify-content: center;");
        out.println("align-items: center;");
        out.println("height: 100vh;");
        out.println("margin: 0;");
        out.println("}");

        out.println(".box {");
        out.println("width: 380px;");
        out.println("background: white;");
        out.println("padding: 30px;");
        out.println("border-radius: 12px;");
        out.println("box-shadow: 0 4px 15px rgba(0,0,0,0.15);");
        out.println("}");

        out.println("h2 {");
        out.println("text-align: center;");
        out.println("margin-bottom: 25px;");
        out.println("}");

        out.println("label {");
        out.println("display: block;");
        out.println("margin-top: 15px;");
        out.println("font-weight: bold;");
        out.println("}");

        out.println("input {");
        out.println("width: 100%;");
        out.println("padding: 10px;");
        out.println("margin-top: 7px;");
        out.println("box-sizing: border-box;");
        out.println("border: 1px solid #ccc;");
        out.println("border-radius: 6px;");
        out.println("}");

        out.println("button {");
        out.println("width: 100%;");
        out.println("padding: 12px;");
        out.println("margin-top: 22px;");
        out.println("background: #007bff;");
        out.println("color: white;");
        out.println("border: none;");
        out.println("border-radius: 6px;");
        out.println("font-size: 16px;");
        out.println("cursor: pointer;");
        out.println("}");

        out.println("button:hover {");
        out.println("background: #0056b3;");
        out.println("}");

        out.println(".message {");
        out.println("margin-bottom: 15px;");
        out.println("padding: 10px;");
        out.println("background: #ffe6e6;");
        out.println("color: red;");
        out.println("border-radius: 6px;");
        out.println("}");

        out.println(".login {");
        out.println("text-align: center;");
        out.println("margin-top: 20px;");
        out.println("}");

        out.println("</style>");

        out.println("</head>");

        out.println("<body>");

        out.println("<div class='box'>");

        out.println("<h2>Reset Student Password</h2>");

        if (message != null && !message.isEmpty()) {

            out.println("<div class='message'>");
            out.println(message);
            out.println("</div>");
        }

        out.println("<form method='post' action='ResetPasswordServlet'>");

        out.println("<label>Email</label>");

        out.println("<input type='email' "
                + "name='email' "
                + "placeholder='Enter registered email' "
                + "required>");

        out.println("<label>New Password</label>");

        out.println("<input type='password' "
                + "name='newPassword' "
                + "placeholder='Enter new password' "
                + "required>");

        out.println("<label>Confirm Password</label>");

        out.println("<input type='password' "
                + "name='confirmPassword' "
                + "placeholder='Confirm new password' "
                + "required>");

        out.println("<button type='submit'>");
        out.println("Change Password");
        out.println("</button>");

        out.println("</form>");

        out.println("<div class='login'>");

        out.println("<a href='LoginServlet'>");
        out.println("Back to Student Login");
        out.println("</a>");

        out.println("</div>");

        out.println("</div>");

        out.println("</body>");

        out.println("</html>");
    }
}