package placementPortal;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/EditInterviewServlet")
public class EditInterviewServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // =========================
    // ADMIN SECURITY
    // =========================
    private boolean isAdmin(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        return session != null
                && Boolean.TRUE.equals(session.getAttribute("isAdmin"));
    }

    // =========================
    // GET - LOAD INTERVIEW
    // =========================
    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        if (!isAdmin(request)) {
            response.sendRedirect("AdminLoginServlet");
            return;
        }

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("AdminInterviewServlet");
            return;
        }

        int interviewId;

        try {
            interviewId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("AdminInterviewServlet");
            return;
        }

        String interviewDate = "";
        String interviewTime = "";
        String interviewMode = "";
        String interviewLocation = "";
        String interviewerName = "";
        String meetingLink = "";
        String remarks = "";

        String companyName = "";
        String studentName = "";

        String sql =
                "SELECT i.interview_date, "
                + "i.interview_time, "
                + "i.interview_mode, "
                + "i.interview_location, "
                + "i.interviewer_name, "
                + "i.meeting_link, "
                + "i.remarks, "
                + "c.name AS company_name, "
                + "s.name AS student_name "
                + "FROM interviews i "
                + "JOIN applications a ON i.application_id = a.id "
                + "JOIN companies c ON a.company_id = c.id "
                + "JOIN students s ON a.student_id = s.id "
                + "WHERE i.id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, interviewId);

            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.next()) {
                    response.sendRedirect("AdminInterviewServlet");
                    return;
                }

                interviewDate = rs.getString("interview_date");
                interviewTime = rs.getString("interview_time");
                interviewMode = rs.getString("interview_mode");
                interviewLocation = rs.getString("interview_location");
                interviewerName = rs.getString("interviewer_name");
                meetingLink = rs.getString("meeting_link");
                remarks = rs.getString("remarks");

                companyName = rs.getString("company_name");
                studentName = rs.getString("student_name");
            }

        } catch (SQLException e) {

            e.printStackTrace();

            PrintWriter out = response.getWriter();

            out.println("<h2>Database Error</h2>");
            out.println("<p>Unable to load interview details.</p>");
            out.println("<pre>");
            out.println(e.getMessage());
            out.println("</pre>");

            return;
        }

        // =========================
        // EDIT PAGE
        // =========================

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");

        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");

        out.println("<title>Edit Interview - Placement Portal</title>");

        out.println("<style>");

        out.println("*{box-sizing:border-box;}");

        out.println("body{");
        out.println("font-family:Arial,sans-serif;");
        out.println("background:#f4f6f9;");
        out.println("margin:0;");
        out.println("padding:30px;");
        out.println("}");

        out.println(".container{");
        out.println("max-width:850px;");
        out.println("margin:auto;");
        out.println("background:white;");
        out.println("padding:30px;");
        out.println("border-radius:12px;");
        out.println("box-shadow:0 4px 15px rgba(0,0,0,0.08);");
        out.println("}");

        out.println("h1{");
        out.println("margin-top:0;");
        out.println("color:#222;");
        out.println("}");

        out.println(".info{");
        out.println("background:#f1f5f9;");
        out.println("padding:15px;");
        out.println("border-radius:8px;");
        out.println("margin-bottom:25px;");
        out.println("}");

        out.println(".info p{");
        out.println("margin:7px 0;");
        out.println("}");

        out.println("label{");
        out.println("display:block;");
        out.println("font-weight:bold;");
        out.println("margin-top:16px;");
        out.println("margin-bottom:7px;");
        out.println("}");

        out.println("input,select,textarea{");
        out.println("width:100%;");
        out.println("padding:11px;");
        out.println("border:1px solid #ccc;");
        out.println("border-radius:6px;");
        out.println("font-size:14px;");
        out.println("}");

        out.println("textarea{");
        out.println("min-height:100px;");
        out.println("resize:vertical;");
        out.println("}");

        out.println(".buttons{");
        out.println("margin-top:25px;");
        out.println("display:flex;");
        out.println("gap:12px;");
        out.println("}");

        out.println("button{");
        out.println("border:none;");
        out.println("padding:12px 22px;");
        out.println("border-radius:6px;");
        out.println("cursor:pointer;");
        out.println("font-weight:bold;");
        out.println("}");

        out.println(".save{");
        out.println("background:#2563eb;");
        out.println("color:white;");
        out.println("}");

        out.println(".cancel{");
        out.println("background:#e5e7eb;");
        out.println("color:#222;");
        out.println("text-decoration:none;");
        out.println("padding:12px 22px;");
        out.println("border-radius:6px;");
        out.println("}");

        out.println("</style>");

        out.println("</head>");
        out.println("<body>");

        out.println("<div class='container'>");

        out.println("<h1>Edit Interview</h1>");

        out.println("<div class='info'>");

        out.println("<p><strong>Student:</strong> "
                + escapeHtml(studentName)
                + "</p>");

        out.println("<p><strong>Company:</strong> "
                + escapeHtml(companyName)
                + "</p>");

        out.println("</div>");

        out.println("<form action='EditInterviewServlet' method='post'>");

        out.println("<input type='hidden' name='id' value='"
                + interviewId
                + "'>");

        // DATE
        out.println("<label>Interview Date</label>");

        out.println("<input type='date' "
                + "name='interview_date' "
                + "value='"
                + escapeHtml(interviewDate)
                + "' required>");

        // TIME
        out.println("<label>Interview Time</label>");

        String cleanTime = interviewTime;

        if (cleanTime != null && cleanTime.length() >= 5) {
            cleanTime = cleanTime.substring(0, 5);
        }

        out.println("<input type='time' "
                + "name='interview_time' "
                + "value='"
                + escapeHtml(cleanTime)
                + "' required>");

        // MODE
        out.println("<label>Interview Mode</label>");

        out.println("<select name='interview_mode' required>");

        out.println("<option value='Online' "
                + ("Online".equalsIgnoreCase(interviewMode)
                        ? "selected" : "")
                + ">Online</option>");

        out.println("<option value='Offline' "
                + ("Offline".equalsIgnoreCase(interviewMode)
                        ? "selected" : "")
                + ">Offline</option>");

        out.println("</select>");

        // LOCATION
        out.println("<label>Interview Location</label>");

        out.println("<input type='text' "
                + "name='interview_location' "
                + "value='"
                + escapeHtml(interviewLocation)
                + "'>");

        // INTERVIEWER
        out.println("<label>Interviewer Name</label>");

        out.println("<input type='text' "
                + "name='interviewer_name' "
                + "value='"
                + escapeHtml(interviewerName)
                + "' required>");

        // MEETING LINK
        out.println("<label>Meeting Link</label>");

        out.println("<input type='url' "
                + "name='meeting_link' "
                + "value='"
                + escapeHtml(meetingLink)
                + "'>");

        // REMARKS
        out.println("<label>Remarks</label>");

        out.println("<textarea name='remarks'>"
                + escapeHtml(remarks)
                + "</textarea>");

        // BUTTONS
        out.println("<div class='buttons'>");

        out.println("<button type='submit' class='save'>"
                + "Update Interview"
                + "</button>");

        out.println("<a href='AdminInterviewServlet' class='cancel'>"
                + "Cancel"
                + "</a>");

        out.println("</div>");

        out.println("</form>");

        out.println("</div>");

        out.println("</body>");
        out.println("</html>");
    }

    // =========================
    // POST - UPDATE INTERVIEW
    // =========================
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        if (!isAdmin(request)) {
            response.sendRedirect("AdminLoginServlet");
            return;
        }

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect("AdminInterviewServlet");
            return;
        }

        int interviewId;

        try {
            interviewId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("AdminInterviewServlet");
            return;
        }

        String interviewDate =
                request.getParameter("interview_date");

        String interviewTime =
                request.getParameter("interview_time");

        String interviewMode =
                request.getParameter("interview_mode");

        String interviewLocation =
                request.getParameter("interview_location");

        String interviewerName =
                request.getParameter("interviewer_name");

        String meetingLink =
                request.getParameter("meeting_link");

        String remarks =
                request.getParameter("remarks");

        if (interviewDate == null
                || interviewDate.trim().isEmpty()
                || interviewTime == null
                || interviewTime.trim().isEmpty()
                || interviewMode == null
                || interviewMode.trim().isEmpty()
                || interviewerName == null
                || interviewerName.trim().isEmpty()) {

            response.getWriter().println(
                    "<h3>Please fill all required fields.</h3>"
            );

            return;
        }

        String sql =
                "UPDATE interviews SET "
                + "interview_date = ?, "
                + "interview_time = ?, "
                + "interview_mode = ?, "
                + "interview_location = ?, "
                + "interviewer_name = ?, "
                + "meeting_link = ?, "
                + "remarks = ? "
                + "WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, interviewDate);
            ps.setString(2, interviewTime);
            ps.setString(3, interviewMode);
            ps.setString(4, emptyToNull(interviewLocation));
            ps.setString(5, interviewerName);
            ps.setString(6, emptyToNull(meetingLink));
            ps.setString(7, emptyToNull(remarks));
            ps.setInt(8, interviewId);

            int updated = ps.executeUpdate();

            if (updated > 0) {
                response.sendRedirect("AdminInterviewServlet");
            } else {

                PrintWriter out = response.getWriter();

                out.println("<h3>Interview was not found.</h3>");
                out.println("<a href='AdminInterviewServlet'>"
                        + "Back to Interviews"
                        + "</a>");
            }

        } catch (SQLException e) {

            e.printStackTrace();

            PrintWriter out = response.getWriter();

            out.println("<h2>Database Error</h2>");
            out.println("<p>Unable to update interview.</p>");
            out.println("<pre>");
            out.println(e.getMessage());
            out.println("</pre>");

            out.println("<a href='AdminInterviewServlet'>"
                    + "Back to Interviews"
                    + "</a>");
        }
    }

    // =========================
    // HTML ESCAPE
    // =========================
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

    // =========================
    // EMPTY STRING TO NULL
    // =========================
    private String emptyToNull(String value) {

        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        return value.trim();
    }
}