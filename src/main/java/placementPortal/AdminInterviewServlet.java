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

@WebServlet("/AdminInterviewServlet")
public class AdminInterviewServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/placement_portal";

    private static final String DB_USER = "root";

    private static final String DB_PASSWORD =
            "Yoganjali@123";


    // =========================================================
    // GET - Display Interview Management Page
    // =========================================================

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // -----------------------------------------------------
        // Check Admin Login
        // -----------------------------------------------------

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("isAdmin") == null ||
            !Boolean.TRUE.equals(
                    session.getAttribute("isAdmin"))) {

            response.sendRedirect("AdminLoginServlet");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(
                    DB_URL,
                    DB_USER,
                    DB_PASSWORD
            );


            // =================================================
            // Get Students Who Have Applied
            // =================================================

            String applicationSql =
                    "SELECT a.id AS application_id, " +
                    "s.name AS student_name, " +
                    "s.email AS student_email, " +
                    "s.branch, " +
                    "c.name AS company_name, " +
                    "a.status " +
                    "FROM applications a " +
                    "JOIN students s " +
                    "ON a.student_id = s.id " +
                    "JOIN companies c " +
                    "ON a.company_id = c.id " +
                    "WHERE a.status IN " +
                    "('SHORTLISTED', 'INTERVIEW') " +
                    "ORDER BY a.applied_date DESC";


            PreparedStatement applicationPs =
                    con.prepareStatement(applicationSql);

            ResultSet applicationRs =
                    applicationPs.executeQuery();


            // =================================================
            // Start HTML
            // =================================================

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");

            out.println("<meta charset='UTF-8'>");

            out.println(
                "<meta name='viewport' " +
                "content='width=device-width, initial-scale=1.0'>"
            );

            out.println("<title>Interview Management</title>");


            // =================================================
            // CSS
            // =================================================

            out.println("<style>");

            out.println(
                "* { " +
                "box-sizing: border-box; " +
                "margin: 0; " +
                "padding: 0; " +
                "font-family: Arial, sans-serif; " +
                "}"
            );

            out.println(
                "body { " +
                "background: #f4f7fb; " +
                "color: #222; " +
                "}"
            );


            // Sidebar

            out.println(
                ".sidebar { " +
                "position: fixed; " +
                "left: 0; " +
                "top: 0; " +
                "width: 250px; " +
                "height: 100vh; " +
                "background: #172554; " +
                "color: white; " +
                "padding: 25px 15px; " +
                "}"
            );

            out.println(
                ".logo { " +
                "font-size: 24px; " +
                "font-weight: bold; " +
                "text-align: center; " +
                "margin-bottom: 35px; " +
                "}"
            );

            out.println(
                ".logo span { " +
                "color: #60a5fa; " +
                "}"
            );

            out.println(
                ".menu a { " +
                "display: block; " +
                "color: #dbeafe; " +
                "text-decoration: none; " +
                "padding: 14px 18px; " +
                "margin: 7px 0; " +
                "border-radius: 8px; " +
                "font-size: 15px; " +
                "}"
            );

            out.println(
                ".menu a:hover { " +
                "background: #1e3a8a; " +
                "}"
            );

            out.println(
                ".menu .active { " +
                "background: #2563eb; " +
                "color: white; " +
                "}"
            );


            // Main

            out.println(
                ".main { " +
                "margin-left: 250px; " +
                "padding: 30px; " +
                "}"
            );

            out.println(
                ".topbar { " +
                "display: flex; " +
                "justify-content: space-between; " +
                "align-items: center; " +
                "margin-bottom: 25px; " +
                "}"
            );

            out.println(
                ".topbar h1 { " +
                "font-size: 28px; " +
                "color: #172554; " +
                "}"
            );

            out.println(
                ".admin-name { " +
                "background: white; " +
                "padding: 10px 16px; " +
                "border-radius: 8px; " +
                "box-shadow: 0 2px 8px rgba(0,0,0,0.08); " +
                "}"
            );


            // Card

            out.println(
                ".card { " +
                "background: white; " +
                "padding: 25px; " +
                "border-radius: 12px; " +
                "box-shadow: 0 2px 10px rgba(0,0,0,0.07); " +
                "margin-bottom: 25px; " +
                "}"
            );

            out.println(
                ".card h2 { " +
                "color: #172554; " +
                "margin-bottom: 20px; " +
                "}"
            );


            // Form

            out.println(
                ".form-grid { " +
                "display: grid; " +
                "grid-template-columns: repeat(2, 1fr); " +
                "gap: 18px; " +
                "}"
            );

            out.println(
                ".form-group { " +
                "display: flex; " +
                "flex-direction: column; " +
                "}"
            );

            out.println(
                ".form-group label { " +
                "font-weight: bold; " +
                "margin-bottom: 7px; " +
                "color: #334155; " +
                "}"
            );

            out.println(
                ".form-group input, " +
                ".form-group select, " +
                ".form-group textarea { " +
                "padding: 11px; " +
                "border: 1px solid #cbd5e1; " +
                "border-radius: 7px; " +
                "font-size: 14px; " +
                "}"
            );

            out.println(
                ".form-group textarea { " +
                "resize: vertical; " +
                "min-height: 80px; " +
                "}"
            );

            out.println(
                ".full { " +
                "grid-column: 1 / -1; " +
                "}"
            );


            // Button

            out.println(
                ".btn { " +
                "background: #2563eb; " +
                "color: white; " +
                "border: none; " +
                "padding: 12px 22px; " +
                "border-radius: 7px; " +
                "cursor: pointer; " +
                "font-size: 14px; " +
                "font-weight: bold; " +
                "}"
            );

            out.println(
                ".btn:hover { " +
                "background: #1d4ed8; " +
                "}"
            );


            // Table

            out.println(
                ".table-container { " +
                "overflow-x: auto; " +
                "}"
            );

            out.println(
                "table { " +
                "width: 100%; " +
                "border-collapse: collapse; " +
                "min-width: 1000px; " +
                "}"
            );

            out.println(
                "th { " +
                "background: #172554; " +
                "color: white; " +
                "padding: 13px; " +
                "text-align: left; " +
                "}"
            );

            out.println(
                "td { " +
                "padding: 12px; " +
                "border-bottom: 1px solid #e5e7eb; " +
                "}"
            );

            out.println(
                "tr:hover { " +
                "background: #f8fafc; " +
                "}"
            );


            // Delete button

            out.println(
                ".delete-btn { " +
                "background: #dc2626; " +
                "color: white; " +
                "border: none; " +
                "padding: 7px 12px; " +
                "border-radius: 6px; " +
                "cursor: pointer; " +
                "}"
            );

            out.println(
                ".delete-btn:hover { " +
                "background: #b91c1c; " +
                "}"
            );


            // Status

            out.println(
                ".status { " +
                "padding: 6px 10px; " +
                "border-radius: 20px; " +
                "font-size: 12px; " +
                "font-weight: bold; " +
                "}"
            );

            out.println(
                ".online { " +
                "background: #dbeafe; " +
                "color: #1d4ed8; " +
                "}"
            );

            out.println(
                ".offline { " +
                "background: #dcfce7; " +
                "color: #166534; " +
                "}"
            );


            // Responsive

            out.println(
                "@media(max-width: 800px) { " +
                ".sidebar { " +
                "position: relative; " +
                "width: 100%; " +
                "height: auto; " +
                "} " +
                ".main { " +
                "margin-left: 0; " +
                "} " +
                ".form-grid { " +
                "grid-template-columns: 1fr; " +
                "} " +
                ".full { " +
                "grid-column: auto; " +
                "} " +
                "}"
            );

            out.println("</style>");

            out.println("</head>");

            out.println("<body>");


            // =================================================
            // SIDEBAR
            // =================================================

            out.println("<div class='sidebar'>");

            out.println(
                "<div class='logo'>" +
                "Placement<span>Portal</span>" +
                "</div>"
            );

            out.println("<div class='menu'>");

            out.println(
                "<a href='AdminDashboardServlet'>Dashboard</a>"
            );

            out.println(
                "<a href='StudentServlet'>Students</a>"
            );

            out.println(
                "<a href='CompanyServlet'>Companies</a>"
            );

            out.println(
                "<a href='AdminApplicationsServlet'>" +
                "Applications</a>"
            );

            out.println(
                "<a href='AdminInterviewServlet' " +
                "class='active'>Interviews</a>"
            );

            out.println("</div>");

            out.println("</div>");


            // =================================================
            // MAIN
            // =================================================

            out.println("<div class='main'>");


            // Topbar

            out.println("<div class='topbar'>");

            out.println(
                "<h1>Interview Management</h1>"
            );

            String adminEmail =
                    (String) session.getAttribute("adminEmail");

            out.println(
                "<div class='admin-name'>" +
                "Admin: " +
                escapeHtml(adminEmail) +
                "</div>"
            );

            out.println("</div>");


            // =================================================
            // SCHEDULE INTERVIEW FORM
            // =================================================

            out.println("<div class='card'>");

            out.println(
                "<h2>Schedule New Interview</h2>"
            );

            out.println(
                "<form action='AdminInterviewServlet' " +
                "method='post'>"
            );

            out.println("<div class='form-grid'>");


            // Student/Application

            out.println("<div class='form-group full'>");

            out.println(
                "<label>Select Student Application</label>"
            );

            out.println(
                "<select name='applicationId' required>"
            );

            out.println(
                "<option value=''>" +
                "-- Select Application --" +
                "</option>"
            );


            boolean hasApplications = false;

            while (applicationRs.next()) {

                hasApplications = true;

                int applicationId =
                        applicationRs.getInt(
                                "application_id");

                String studentName =
                        applicationRs.getString(
                                "student_name");

                String studentEmail =
                        applicationRs.getString(
                                "student_email");

                String companyName =
                        applicationRs.getString(
                                "company_name");

                String status =
                        applicationRs.getString(
                                "status");

                out.println(
                    "<option value='" +
                    applicationId +
                    "'>" +
                    escapeHtml(studentName) +
                    " - " +
                    escapeHtml(companyName) +
                    " (" +
                    escapeHtml(status) +
                    ")</option>"
                );
            }

            out.println("</select>");

            if (!hasApplications) {

                out.println(
                    "<small style='color:#dc2626; " +
                    "margin-top:6px;'>" +
                    "No shortlisted or interview " +
                    "applications available." +
                    "</small>"
                );
            }

            out.println("</div>");


            // Date

            out.println("<div class='form-group'>");

            out.println(
                "<label>Interview Date</label>"
            );

            out.println(
                "<input type='date' " +
                "name='interviewDate' required>"
            );

            out.println("</div>");


            // Time

            out.println("<div class='form-group'>");

            out.println(
                "<label>Interview Time</label>"
            );

            out.println(
                "<input type='time' " +
                "name='interviewTime' required>"
            );

            out.println("</div>");


            // Mode

            out.println("<div class='form-group'>");

            out.println(
                "<label>Interview Mode</label>"
            );

            out.println(
                "<select name='interviewMode' required>"
            );

            out.println(
                "<option value='Online'>Online</option>"
            );

            out.println(
                "<option value='Offline'>Offline</option>"
            );

            out.println("</select>");

            out.println("</div>");


            // Interviewer

            out.println("<div class='form-group'>");

            out.println(
                "<label>Interviewer Name</label>"
            );

            out.println(
                "<input type='text' " +
                "name='interviewerName' " +
                "placeholder='Enter interviewer name' " +
                "required>"
            );

            out.println("</div>");


            // Location

            out.println("<div class='form-group'>");

            out.println(
                "<label>Interview Location</label>"
            );

            out.println(
                "<input type='text' " +
                "name='interviewLocation' " +
                "placeholder='Office / Campus / Location'>"
            );

            out.println("</div>");


            // Meeting Link

            out.println("<div class='form-group'>");

            out.println(
                "<label>Meeting Link</label>"
            );

            out.println(
                "<input type='text' " +
                "name='meetingLink' " +
                "placeholder='Google Meet / Zoom link'>"
            );

            out.println("</div>");


            // Remarks

            out.println("<div class='form-group full'>");

            out.println(
                "<label>Remarks</label>"
            );

            out.println(
                "<textarea name='remarks' " +
                "placeholder='Additional information'></textarea>"
            );

            out.println("</div>");


            // Submit

            out.println("<div class='full'>");

            out.println(
                "<button type='submit' class='btn'>" +
                "Schedule Interview" +
                "</button>"
            );

            out.println("</div>");


            out.println("</div>");

            out.println("</form>");

            out.println("</div>");


            // Close application result

            applicationRs.close();

            applicationPs.close();


            // =================================================
            // SCHEDULED INTERVIEWS
            // =================================================

            out.println("<div class='card'>");

            out.println(
                "<h2>Scheduled Interviews</h2>"
            );

            out.println("<div class='table-container'>");

            String interviewSql =
                    "SELECT i.id, " +
                    "i.interview_date, " +
                    "i.interview_time, " +
                    "i.interview_mode, " +
                    "i.interview_location, " +
                    "i.interviewer_name, " +
                    "i.meeting_link, " +
                    "i.remarks, " +
                    "s.name AS student_name, " +
                    "c.name AS company_name " +
                    "FROM interviews i " +
                    "JOIN applications a " +
                    "ON i.application_id = a.id " +
                    "JOIN students s " +
                    "ON a.student_id = s.id " +
                    "JOIN companies c " +
                    "ON a.company_id = c.id " +
                    "ORDER BY i.interview_date ASC, " +
                    "i.interview_time ASC";


            PreparedStatement interviewPs =
                    con.prepareStatement(interviewSql);

            ResultSet interviewRs =
                    interviewPs.executeQuery();


            out.println("<table>");

            out.println("<tr>");

            out.println("<th>ID</th>");
            out.println("<th>Student</th>");
            out.println("<th>Company</th>");
            out.println("<th>Date</th>");
            out.println("<th>Time</th>");
            out.println("<th>Mode</th>");
            out.println("<th>Interviewer</th>");
            out.println("<th>Location</th>");
            out.println("<th>Meeting Link</th>");
            out.println("<th>Action</th>");

            out.println("</tr>");


            boolean hasInterviews = false;


            while (interviewRs.next()) {

                hasInterviews = true;

                int interviewId =
                        interviewRs.getInt("id");

                String mode =
                        interviewRs.getString(
                                "interview_mode");


                out.println("<tr>");

                out.println(
                    "<td>" +
                    interviewId +
                    "</td>"
                );

                out.println(
                    "<td>" +
                    escapeHtml(
                        interviewRs.getString(
                            "student_name")
                    ) +
                    "</td>"
                );

                out.println(
                    "<td>" +
                    escapeHtml(
                        interviewRs.getString(
                            "company_name")
                    ) +
                    "</td>"
                );

                out.println(
                    "<td>" +
                    interviewRs.getDate(
                        "interview_date") +
                    "</td>"
                );

                out.println(
                    "<td>" +
                    interviewRs.getTime(
                        "interview_time") +
                    "</td>"
                );


                String modeClass =
                        mode != null &&
                        mode.equalsIgnoreCase("Online")
                        ? "online"
                        : "offline";


                out.println(
                    "<td>" +
                    "<span class='status " +
                    modeClass +
                    "'>" +
                    escapeHtml(mode) +
                    "</span>" +
                    "</td>"
                );


                out.println(
                    "<td>" +
                    escapeHtml(
                        interviewRs.getString(
                            "interviewer_name")
                    ) +
                    "</td>"
                );


                String location =
                        interviewRs.getString(
                                "interview_location");

                out.println(
                    "<td>" +
                    (location == null ||
                     location.trim().isEmpty()
                        ? "-"
                        : escapeHtml(location)) +
                    "</td>"
                );


                String meetingLink =
                        interviewRs.getString(
                                "meeting_link");


                if (meetingLink != null &&
                    !meetingLink.trim().isEmpty()) {

                    out.println(
                        "<td>" +
                        "<a href='" +
                        escapeHtml(meetingLink) +
                        "' target='_blank'>" +
                        "Open Link" +
                        "</a>" +
                        "</td>"
                    );

                } else {

                    out.println(
                        "<td>-</td>"
                    );
                }


                // Delete

                out.println("<td>");

                out.println(
                    "<form action='AdminInterviewServlet' " +
                    "method='post' " +
                    "onsubmit=\"return confirm(" +
                    "'Are you sure you want to delete " +
                    "this interview?'"
                    + ");\">"
                );

                out.println(
                    "<input type='hidden' " +
                    "name='action' " +
                    "value='delete'>"
                );

                out.println(
                    "<input type='hidden' " +
                    "name='interviewId' " +
                    "value='" +
                    interviewId +
                    "'>"
                );

                out.println(
                    "<button type='submit' " +
                    "class='delete-btn'>" +
                    "Delete" +
                    "</button>"
                );

                out.println("</form>");

                out.println("</td>");

                out.println("</tr>");
            }


            if (!hasInterviews) {

                out.println("<tr>");

                out.println(
                    "<td colspan='10' " +
                    "style='text-align:center; " +
                    "padding:30px; " +
                    "color:#64748b;'>" +
                    "No interviews scheduled yet." +
                    "</td>"
                );

                out.println("</tr>");
            }


            out.println("</table>");

            out.println("</div>");

            out.println("</div>");


            out.println("</div>");

            out.println("</body>");

            out.println("</html>");


            interviewRs.close();

            interviewPs.close();

            con.close();


        } catch (Exception e) {

            e.printStackTrace();

            out.println("<h2>Error loading interview page</h2>");

            out.println(
                "<p>" +
                escapeHtml(e.getMessage()) +
                "</p>"
            );
        }
    }


    // =========================================================
    // POST - Schedule or Delete Interview
    // =========================================================

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {


        // -----------------------------------------------------
        // Check Admin Login
        // -----------------------------------------------------

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("isAdmin") == null ||
            !Boolean.TRUE.equals(
                    session.getAttribute("isAdmin"))) {

            response.sendRedirect("AdminLoginServlet");
            return;
        }


        String action =
                request.getParameter("action");


        // =====================================================
        // DELETE INTERVIEW
        // =====================================================

        if ("delete".equals(action)) {

            String interviewIdParam =
                    request.getParameter("interviewId");


            try {

                int interviewId =
                        Integer.parseInt(
                                interviewIdParam);


                Class.forName(
                    "com.mysql.cj.jdbc.Driver"
                );


                Connection con =
                    DriverManager.getConnection(
                        DB_URL,
                        DB_USER,
                        DB_PASSWORD
                    );


                String sql =
                    "DELETE FROM interviews " +
                    "WHERE id = ?";


                PreparedStatement ps =
                    con.prepareStatement(sql);


                ps.setInt(1, interviewId);

                ps.executeUpdate();


                ps.close();

                con.close();


                response.sendRedirect(
                    "AdminInterviewServlet"
                );

                return;


            } catch (Exception e) {

                e.printStackTrace();

                response.sendRedirect(
                    "AdminInterviewServlet"
                );

                return;
            }
        }


        // =====================================================
        // SCHEDULE INTERVIEW
        // =====================================================

        String applicationIdParam =
                request.getParameter("applicationId");

        String interviewDate =
                request.getParameter("interviewDate");

        String interviewTime =
                request.getParameter("interviewTime");

        String interviewMode =
                request.getParameter("interviewMode");

        String interviewerName =
                request.getParameter("interviewerName");

        String interviewLocation =
                request.getParameter(
                        "interviewLocation");

        String meetingLink =
                request.getParameter(
                        "meetingLink");

        String remarks =
                request.getParameter("remarks");


        // -----------------------------------------------------
        // Basic Validation
        // -----------------------------------------------------

        if (applicationIdParam == null ||
            interviewDate == null ||
            interviewTime == null ||
            interviewMode == null ||
            interviewerName == null ||
            applicationIdParam.trim().isEmpty() ||
            interviewDate.trim().isEmpty() ||
            interviewTime.trim().isEmpty() ||
            interviewMode.trim().isEmpty() ||
            interviewerName.trim().isEmpty()) {

            response.sendRedirect(
                "AdminInterviewServlet"
            );

            return;
        }


        try {

            int applicationId =
                    Integer.parseInt(
                            applicationIdParam);


            Class.forName(
                "com.mysql.cj.jdbc.Driver"
            );


            Connection con =
                DriverManager.getConnection(
                    DB_URL,
                    DB_USER,
                    DB_PASSWORD
                );


            // -------------------------------------------------
            // Check whether application exists
            // -------------------------------------------------

            String checkSql =
                "SELECT id FROM applications " +
                "WHERE id = ?";


            PreparedStatement checkPs =
                con.prepareStatement(checkSql);


            checkPs.setInt(1, applicationId);


            ResultSet checkRs =
                checkPs.executeQuery();


            if (!checkRs.next()) {

                checkRs.close();

                checkPs.close();

                con.close();

                response.sendRedirect(
                    "AdminInterviewServlet"
                );

                return;
            }


            checkRs.close();

            checkPs.close();


            // -------------------------------------------------
            // Check whether interview already exists
            // -------------------------------------------------

            String duplicateSql =
                "SELECT id FROM interviews " +
                "WHERE application_id = ?";


            PreparedStatement duplicatePs =
                con.prepareStatement(
                        duplicateSql);


            duplicatePs.setInt(
                1,
                applicationId
            );


            ResultSet duplicateRs =
                duplicatePs.executeQuery();


            if (duplicateRs.next()) {

                duplicateRs.close();

                duplicatePs.close();

                con.close();

                response.sendRedirect(
                    "AdminInterviewServlet"
                );

                return;
            }


            duplicateRs.close();

            duplicatePs.close();


            // -------------------------------------------------
            // Insert Interview
            // -------------------------------------------------

            String insertSql =
                "INSERT INTO interviews " +
                "(application_id, " +
                "interview_date, " +
                "interview_time, " +
                "interview_mode, " +
                "interview_location, " +
                "interviewer_name, " +
                "meeting_link, " +
                "remarks) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";


            PreparedStatement insertPs =
                con.prepareStatement(insertSql);


            insertPs.setInt(
                1,
                applicationId
            );

            insertPs.setString(
                2,
                interviewDate
            );

            insertPs.setString(
                3,
                interviewTime
            );

            insertPs.setString(
                4,
                interviewMode
            );

            insertPs.setString(
                5,
                interviewLocation
            );

            insertPs.setString(
                6,
                interviewerName
            );

            insertPs.setString(
                7,
                meetingLink
            );

            insertPs.setString(
                8,
                remarks
            );


            insertPs.executeUpdate();


            insertPs.close();

            con.close();


            // -------------------------------------------------
            // Change application status to INTERVIEW
            // -------------------------------------------------

            Class.forName(
                "com.mysql.cj.jdbc.Driver"
            );


            Connection con2 =
                DriverManager.getConnection(
                    DB_URL,
                    DB_USER,
                    DB_PASSWORD
                );


            String statusSql =
                "UPDATE applications " +
                "SET status = 'INTERVIEW' " +
                "WHERE id = ?";


            PreparedStatement statusPs =
                con2.prepareStatement(statusSql);


            statusPs.setInt(
                1,
                applicationId
            );


            statusPs.executeUpdate();


            statusPs.close();

            con2.close();


            response.sendRedirect(
                "AdminInterviewServlet"
            );


        } catch (Exception e) {

            e.printStackTrace();

            response.sendRedirect(
                "AdminInterviewServlet"
            );
        }
    }


    // =========================================================
    // HTML Escape Helper
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