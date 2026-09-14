package placementPortal;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/UpdateCompanyServlet")
public class UpdateCompanyServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve primary key ID
        int id = Integer.parseInt(request.getParameter("id"));

        // Retrieve updated input parameters
        String companyId = request.getParameter("company_id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String contactNo = request.getParameter("contact_no");
        String address = request.getParameter("address");
        String hrName = request.getParameter("hr_name");
        double cutoff = Double.parseDouble(request.getParameter("cutoff"));
        String requiredSkills = request.getParameter("required_skills");
        String branches = request.getParameter("branches");
        String branchType = request.getParameter("branch_type");

        String url = "jdbc:mysql://localhost:3306/placement_Portal";
        String user = "root";
        String password = "YOUR_DATABASE_PASSWORD";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, user, password);

            String sql = "UPDATE companies SET "
                    + "company_id=?, "
                    + "name=?, "
                    + "email=?, "
                    + "contact_no=?, "
                    + "address=?, "
                    + "hr_name=?, "
                    + "cutoff=?, "
                    + "required_skills=?, "
                    + "branches=?, "
                    + "branch_type=? "
                    + "WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, companyId);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, contactNo);
            ps.setString(5, address);
            ps.setString(6, hrName);
            ps.setDouble(7, cutoff);
            ps.setString(8, requiredSkills);
            ps.setString(9, branches);
            ps.setString(10, branchType);
            ps.setInt(11, id);

            ps.executeUpdate();

            ps.close();
            con.close();

            response.sendRedirect("CompanyServlet");

        } catch (Exception e) {

            response.setContentType("text/html; charset=UTF-8");

            PrintWriter out = response.getWriter();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Update Company Error</title>");

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
            out.println("    align-items: center;");
            out.println("    justify-content: center;");
            out.println("    padding: 20px;");
            out.println("}");

            out.println(".card {");
            out.println("    width: 100%;");
            out.println("    max-width: 520px;");
            out.println("    background: #ffffff;");
            out.println("    border: 1px solid #e0e5eb;");
            out.println("    border-radius: 6px;");
            out.println("    overflow: hidden;");
            out.println("}");

            out.println(".header {");
            out.println("    background: #172b4d;");
            out.println("    color: #ffffff;");
            out.println("    padding: 24px 28px;");
            out.println("    border-bottom: 3px solid #c5a15b;");
            out.println("}");

            out.println(".header h1 {");
            out.println("    font-size: 20px;");
            out.println("    font-weight: 600;");
            out.println("}");

            out.println(".header p {");
            out.println("    color: #cbd5e1;");
            out.println("    font-size: 13px;");
            out.println("    margin-top: 6px;");
            out.println("}");

            out.println(".content {");
            out.println("    padding: 30px;");
            out.println("}");

            out.println(".error-box {");
            out.println("    background: #fff5f5;");
            out.println("    border: 1px solid #f0caca;");
            out.println("    border-left: 4px solid #b42318;");
            out.println("    color: #8a1c13;");
            out.println("    padding: 16px;");
            out.println("    border-radius: 4px;");
            out.println("    font-size: 14px;");
            out.println("    line-height: 1.6;");
            out.println("}");

            out.println(".button {");
            out.println("    display: inline-block;");
            out.println("    margin-top: 22px;");
            out.println("    padding: 11px 20px;");
            out.println("    background: #172b4d;");
            out.println("    color: #ffffff;");
            out.println("    text-decoration: none;");
            out.println("    border-radius: 4px;");
            out.println("    font-size: 13px;");
            out.println("    font-weight: 600;");
            out.println("}");

            out.println(".button:hover {");
            out.println("    background: #223b63;");
            out.println("}");

            out.println("</style>");
            out.println("</head>");

            out.println("<body>");

            out.println("<div class='card'>");

            out.println("<div class='header'>");
            out.println("<h1>Company Management</h1>");
            out.println("<p>Placement Administration Panel</p>");
            out.println("</div>");

            out.println("<div class='content'>");

            out.println("<div class='error-box'>");
            out.println("<strong>Unable to update company details.</strong><br>");
            out.println("Please verify the entered information and try again.");
            out.println("</div>");

            out.println("<a class='button' href='CompanyServlet'>");
            out.println("Return to Company Management");
            out.println("</a>");

            out.println("</div>");
            out.println("</div>");

            out.println("</body>");
            out.println("</html>");
        }
    }
}