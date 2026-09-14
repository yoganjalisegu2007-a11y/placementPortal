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

@WebServlet("/StudentInterviewServlet")
public class StudentInterviewServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // ==============================
    // DATABASE
    // ==============================

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/placement_portal";

    private static final String DB_USER = "root";

    private static final String DB_PASSWORD =
            "YOUR_DATABASE_PASSWORD";


    // ==============================
    // GET METHOD
    // ==============================

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        // Prevent browser cache
        response.setHeader(
                "Cache-Control",
                "no-cache, no-store, must-revalidate"
        );

        response.setHeader("Pragma", "no-cache");

        response.setDateHeader("Expires", 0);


        // ==============================
        // CHECK STUDENT LOGIN
        // ==============================

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("studentId") == null) {

            response.sendRedirect("LoginServlet");
            return;
        }


        int studentId;

        try {

            studentId = (Integer) session.getAttribute("studentId");

        } catch (Exception e) {

            response.sendRedirect("LoginServlet");
            return;
        }


        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();


        // ==============================
        // DATABASE VARIABLES
        // ==============================

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


        try {

            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");


            // Connect to database
            con = DriverManager.getConnection(
                    DB_URL,
                    DB_USER,
                    DB_PASSWORD
            );


            // ==============================
            // GET STUDENT INTERVIEWS
            // ==============================

            String sql =
                    "SELECT i.id, "
                    + "i.interview_date, "
                    + "i.interview_time, "
                    + "i.interview_mode, "
                    + "i.interview_location, "
                    + "i.interviewer_name, "
                    + "i.meeting_link, "
                    + "i.remarks, "
                    + "c.company_id, "
                    + "c.name AS company_name, "
                    + "a.status "
                    + "FROM interviews i "
                    + "JOIN applications a "
                    + "ON i.application_id = a.id "
                    + "JOIN companies c "
                    + "ON a.company_id = c.id "
                    + "WHERE a.student_id = ? "
                    + "ORDER BY i.interview_date ASC, "
                    + "i.interview_time ASC";


            ps = con.prepareStatement(sql);

            ps.setInt(1, studentId);

            rs = ps.executeQuery();


            // ==============================
            // HTML PAGE
            // ==============================

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");

            out.println("<meta charset='UTF-8'>");

            out.println("<meta name='viewport' "
                    + "content='width=device-width, initial-scale=1.0'>");

            out.println("<title>My Interviews</title>");


            // ==============================
            // CSS
            // ==============================

            out.println("<style>");

            out.println("* {");
            out.println("    margin: 0;");
            out.println("    padding: 0;");
            out.println("    box-sizing: border-box;");
            out.println("    font-family: Arial, sans-serif;");
            out.println("}");

            out.println("body {");
            out.println("    background: #f4f6f9;");
            out.println("    color: #26364a;");
            out.println("}");

            out.println(".layout {");
            out.println("    display: flex;");
            out.println("    min-height: 100vh;");
            out.println("}");


            // SIDEBAR

            out.println(".sidebar {");
            out.println("    width: 240px;");
            out.println("    background: #172b4d;");
            out.println("    color: white;");
            out.println("    padding: 25px 15px;");
            out.println("    position: fixed;");
            out.println("    top: 0;");
            out.println("    bottom: 0;");
            out.println("}");

            out.println(".logo {");
            out.println("    text-align: center;");
            out.println("    padding-bottom: 25px;");
            out.println("    border-bottom: 1px solid #405574;");
            out.println("    margin-bottom: 20px;");
            out.println("}");

            out.println(".logo h2 {");
            out.println("    font-size: 19px;");
            out.println("}");

            out.println(".logo p {");
            out.println("    font-size: 11px;");
            out.println("    color: #b9c5d6;");
            out.println("    margin-top: 5px;");
            out.println("}");

            out.println(".menu a {");
            out.println("    display: block;");
            out.println("    color: #dce4ef;");
            out.println("    text-decoration: none;");
            out.println("    padding: 12px 14px;");
            out.println("    border-radius: 5px;");
            out.println("    margin-bottom: 5px;");
            out.println("    font-size: 14px;");
            out.println("}");

            out.println(".menu a:hover {");
            out.println("    background: #263f64;");
            out.println("    color: white;");
            out.println("}");

            out.println(".menu .active {");
            out.println("    background: #263f64;");
            out.println("    color: white;");
            out.println("}");

            out.println(".logout {");
            out.println("    margin-top: 25px;");
            out.println("    border-top: 1px solid #405574;");
            out.println("    padding-top: 20px;");
            out.println("}");

            out.println(".logout a {");
            out.println("    color: #f0b8b8;");
            out.println("}");


            // MAIN

            out.println(".main {");
            out.println("    margin-left: 240px;");
            out.println("    width: calc(100% - 240px);");
            out.println("    padding: 30px;");
            out.println("}");

            out.println(".topbar {");
            out.println("    background: white;");
            out.println("    padding: 20px 25px;");
            out.println("    border-radius: 8px;");
            out.println("    border: 1px solid #e1e6ec;");
            out.println("    margin-bottom: 25px;");
            out.println("}");

            out.println(".topbar h1 {");
            out.println("    color: #172b4d;");
            out.println("    font-size: 25px;");
            out.println("    margin-bottom: 5px;");
            out.println("}");

            out.println(".topbar p {");
            out.println("    color: #718096;");
            out.println("    font-size: 13px;");
            out.println("}");


            // CARD

            out.println(".card {");
            out.println("    background: white;");
            out.println("    border: 1px solid #e1e6ec;");
            out.println("    border-radius: 8px;");
            out.println("    padding: 25px;");
            out.println("    margin-bottom: 20px;");
            out.println("}");

            out.println(".card h2 {");
            out.println("    font-size: 18px;");
            out.println("    color: #172b4d;");
            out.println("    margin-bottom: 20px;");
            out.println("}");


            // INTERVIEW BOX

            out.println(".interview {");
            out.println("    border: 1px solid #dfe5ec;");
            out.println("    border-radius: 7px;");
            out.println("    padding: 20px;");
            out.println("    margin-bottom: 18px;");
            out.println("    background: #fafbfd;");
            out.println("}");

            out.println(".interview:last-child {");
            out.println("    margin-bottom: 0;");
            out.println("}");

            out.println(".company {");
            out.println("    font-size: 19px;");
            out.println("    color: #172b4d;");
            out.println("    font-weight: bold;");
            out.println("    margin-bottom: 5px;");
            out.println("}");

            out.println(".company-id {");
            out.println("    color: #718096;");
            out.println("    font-size: 12px;");
            out.println("    margin-bottom: 15px;");
            out.println("}");

            out.println(".details {");
            out.println("    display: grid;");
            out.println("    grid-template-columns: 1fr 1fr;");
            out.println("    gap: 15px;");
            out.println("}");

            out.println(".detail {");
            out.println("    background: white;");
            out.println("    border: 1px solid #e7ebef;");
            out.println("    padding: 12px;");
            out.println("    border-radius: 5px;");
            out.println("}");

            out.println(".label {");
            out.println("    color: #718096;");
            out.println("    font-size: 11px;");
            out.println("    text-transform: uppercase;");
            out.println("    margin-bottom: 5px;");
            out.println("}");

            out.println(".value {");
            out.println("    color: #27364b;");
            out.println("    font-size: 14px;");
            out.println("    font-weight: 600;");
            out.println("}");

            out.println(".remarks {");
            out.println("    margin-top: 15px;");
            out.println("    padding: 13px;");
            out.println("    background: #f4f6f9;");
            out.println("    border-radius: 5px;");
            out.println("}");

            out.println(".remarks strong {");
            out.println("    font-size: 12px;");
            out.println("}");

            out.println(".remarks p {");
            out.println("    margin-top: 5px;");
            out.println("    font-size: 13px;");
            out.println("    color: #596579;");
            out.println("}");

            out.println(".meeting {");
            out.println("    margin-top: 15px;");
            out.println("}");

            out.println(".meeting a {");
            out.println("    display: inline-block;");
            out.println("    background: #172b4d;");
            out.println("    color: white;");
            out.println("    padding: 9px 15px;");
            out.println("    text-decoration: none;");
            out.println("    border-radius: 5px;");
            out.println("    font-size: 12px;");
            out.println("}");

            out.println(".empty {");
            out.println("    text-align: center;");
            out.println("    padding: 50px 20px;");
            out.println("    color: #718096;");
            out.println("}");

            out.println(".empty h3 {");
            out.println("    color: #172b4d;");
            out.println("    margin-bottom: 8px;");
            out.println("}");

            out.println("@media(max-width:800px) {");

            out.println("    .sidebar {");
            out.println("        width: 190px;");
            out.println("    }");

            out.println("    .main {");
            out.println("        margin-left: 190px;");
            out.println("        width: calc(100% - 190px);");
            out.println("        padding: 20px;");
            out.println("    }");

            out.println("    .details {");
            out.println("        grid-template-columns: 1fr;");
            out.println("    }");

            out.println("}");

            out.println("</style>");

            out.println("</head>");


            // ==============================
            // BODY
            // ==============================

            out.println("<body>");

            out.println("<div class='layout'>");


            // ==============================
            // SIDEBAR
            // ==============================

            out.println("<aside class='sidebar'>");

            out.println("<div class='logo'>");
            out.println("<h2>Placement Portal</h2>");
            out.println("<p>STUDENT PANEL</p>");
            out.println("</div>");

            out.println("<div class='menu'>");

            out.println("<a href='HomeServlet'>Dashboard</a>");

            out.println("<a href='StudentCompanyServlet'>Companies</a>");

            out.println("<a href='AppliedServlet'>My Applications</a>");

            out.println("<a href='StudentInterviewServlet' "
                    + "class='active'>My Interviews</a>");

            out.println("<a href='PlacedServlet'>Placed</a>");

            out.println("<a href='ProfileServlet'>My Profile</a>");

            out.println("</div>");

            out.println("<div class='logout'>");

            out.println("<a href='LogoutServlet'>Logout</a>");

            out.println("</div>");

            out.println("</aside>");


            // ==============================
            // MAIN CONTENT
            // ==============================

            out.println("<main class='main'>");

            out.println("<div class='topbar'>");

            out.println("<h1>My Interviews</h1>");

            out.println("<p>"
                    + "View your scheduled placement interviews and "
                    + "interview details."
                    + "</p>");

            out.println("</div>");


            out.println("<div class='card'>");

            out.println("<h2>Scheduled Interviews</h2>");


            boolean found = false;


            // ==============================
            // DISPLAY INTERVIEWS
            // ==============================

            while (rs.next()) {

                found = true;

                String companyName =
                        rs.getString("company_name");

                String companyId =
                        rs.getString("company_id");

                String interviewDate =
                        rs.getString("interview_date");

                String interviewTime =
                        rs.getString("interview_time");

                String mode =
                        rs.getString("interview_mode");

                String location =
                        rs.getString("interview_location");

                String interviewer =
                        rs.getString("interviewer_name");

                String meetingLink =
                        rs.getString("meeting_link");

                String remarks =
                        rs.getString("remarks");

                String status =
                        rs.getString("status");


                out.println("<div class='interview'>");


                // COMPANY

                out.println("<div class='company'>"
                        + escapeHtml(companyName)
                        + "</div>");

                out.println("<div class='company-id'>"
                        + "Company ID: "
                        + escapeHtml(companyId)
                        + "</div>");


                // DETAILS

                out.println("<div class='details'>");


                // DATE

                out.println("<div class='detail'>");

                out.println("<div class='label'>Interview Date</div>");

                out.println("<div class='value'>"
                        + escapeHtml(interviewDate)
                        + "</div>");

                out.println("</div>");


                // TIME

                out.println("<div class='detail'>");

                out.println("<div class='label'>Interview Time</div>");

                out.println("<div class='value'>"
                        + escapeHtml(interviewTime)
                        + "</div>");

                out.println("</div>");


                // MODE

                out.println("<div class='detail'>");

                out.println("<div class='label'>Interview Mode</div>");

                out.println("<div class='value'>"
                        + escapeHtml(mode)
                        + "</div>");

                out.println("</div>");


                // INTERVIEWER

                out.println("<div class='detail'>");

                out.println("<div class='label'>Interviewer</div>");

                out.println("<div class='value'>"
                        + escapeHtml(interviewer)
                        + "</div>");

                out.println("</div>");


                // LOCATION

                if (location != null
                        && !location.trim().isEmpty()) {

                    out.println("<div class='detail'>");

                    out.println("<div class='label'>Location</div>");

                    out.println("<div class='value'>"
                            + escapeHtml(location)
                            + "</div>");

                    out.println("</div>");
                }


                // STATUS

                out.println("<div class='detail'>");

                out.println("<div class='label'>Application Status</div>");

                out.println("<div class='value'>"
                        + escapeHtml(status)
                        + "</div>");

                out.println("</div>");


                out.println("</div>");


                // REMARKS

                if (remarks != null
                        && !remarks.trim().isEmpty()) {

                    out.println("<div class='remarks'>");

                    out.println("<strong>Remarks</strong>");

                    out.println("<p>"
                            + escapeHtml(remarks)
                            + "</p>");

                    out.println("</div>");
                }


                // MEETING LINK

                if (meetingLink != null
                        && !meetingLink.trim().isEmpty()) {

                    out.println("<div class='meeting'>");

                    out.println("<a href='"
                            + escapeHtml(meetingLink)
                            + "' target='_blank'>");

                    out.println("Open Meeting Link");

                    out.println("</a>");

                    out.println("</div>");
                }


                out.println("</div>");
            }


            // ==============================
            // NO INTERVIEWS
            // ==============================

            if (!found) {

                out.println("<div class='empty'>");

                out.println("<h3>No Interviews Scheduled</h3>");

                out.println("<p>"
                        + "You currently do not have any scheduled "
                        + "placement interviews."
                        + "</p>");

                out.println("</div>");
            }


            out.println("</div>");

            out.println("</main>");

            out.println("</div>");

            out.println("</body>");

            out.println("</html>");


        } catch (Exception e) {

            e.printStackTrace();

            response.setContentType("text/html;charset=UTF-8");

            out.println("<h2>Unable to load interviews</h2>");

            out.println("<p>"
                    + "Please check the database connection "
                    + "and interview table."
                    + "</p>");

        } finally {

            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }

            try {
                if (ps != null) {
                    ps.close();
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


    // ==============================
    // HTML ESCAPE
    // ==============================

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


    // ==============================
    // POST
    // ==============================

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Students should not modify interviews.
        // Only display the interview page.

        response.sendRedirect("StudentInterviewServlet");
    }
}