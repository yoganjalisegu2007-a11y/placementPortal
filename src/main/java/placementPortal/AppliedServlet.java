package placementPortal;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AppliedServlet")
public class AppliedServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();

        // =========================================================
        // CHECK STUDENT LOGIN
        // =========================================================

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("studentId") == null) {

            response.sendRedirect("LoginServlet");
            return;
        }

        int studentId;

        try {

            Object studentIdObject =
                    session.getAttribute("studentId");

            studentId =
                    Integer.parseInt(
                            studentIdObject.toString()
                    );

        } catch (Exception e) {

            response.sendRedirect("LoginServlet");
            return;
        }

        // =========================================================
        // HTML START
        // =========================================================

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");

        out.println("<meta charset='UTF-8'>");

        out.println(
                "<meta name='viewport' " +
                "content='width=device-width, initial-scale=1.0'>"
        );

        out.println(
                "<title>My Applications - Placement Portal</title>"
        );

        // =========================================================
        // CSS
        // =========================================================

        out.println("<style>");

        out.println("*{");
        out.println("margin:0;");
        out.println("padding:0;");
        out.println("box-sizing:border-box;");
        out.println("font-family:Arial,Helvetica,sans-serif;");
        out.println("}");

        out.println("body{");
        out.println("background:#f4f6f9;");
        out.println("color:#263238;");
        out.println("min-height:100vh;");
        out.println("}");

        // SIDEBAR

        out.println(".sidebar{");
        out.println("position:fixed;");
        out.println("left:0;");
        out.println("top:0;");
        out.println("width:245px;");
        out.println("height:100vh;");
        out.println("background:#172b4d;");
        out.println("padding:28px 18px;");
        out.println("box-shadow:3px 0 15px rgba(0,0,0,0.08);");
        out.println("}");

        out.println(".brand{");
        out.println("padding:0 12px 22px;");
        out.println("border-bottom:1px solid rgba(255,255,255,0.15);");
        out.println("}");

        out.println(".brand h2{");
        out.println("font-size:18px;");
        out.println("letter-spacing:1.5px;");
        out.println("color:#fff;");
        out.println("}");

        out.println(".brand p{");
        out.println("font-size:11px;");
        out.println("color:#b7c2d0;");
        out.println("margin-top:6px;");
        out.println("letter-spacing:.5px;");
        out.println("}");

        out.println(".sidebar ul{");
        out.println("list-style:none;");
        out.println("margin-top:28px;");
        out.println("}");

        out.println(".sidebar li{");
        out.println("margin-bottom:5px;");
        out.println("}");

        out.println(".sidebar li a{");
        out.println("display:block;");
        out.println("padding:12px 14px;");
        out.println("border-radius:6px;");
        out.println("text-decoration:none;");
        out.println("color:#d7dee8;");
        out.println("font-size:14px;");
        out.println("}");

        out.println(".sidebar li a:hover{");
        out.println("background:#223b63;");
        out.println("color:#fff;");
        out.println("}");

        out.println(".sidebar li.active a{");
        out.println("background:#223b63;");
        out.println("color:#fff;");
        out.println("border-left:3px solid #c5a15b;");
        out.println("}");

        // MAIN

        out.println(".main{");
        out.println("margin-left:245px;");
        out.println("padding:30px 38px;");
        out.println("}");

        // HEADER

        out.println(".topbar{");
        out.println("background:#fff;");
        out.println("border:1px solid #e1e5ea;");
        out.println("border-radius:8px;");
        out.println("padding:24px 26px;");
        out.println("margin-bottom:22px;");
        out.println("}");

        out.println(".topbar h1{");
        out.println("font-size:26px;");
        out.println("font-weight:600;");
        out.println("color:#172b4d;");
        out.println("}");

        out.println(".topbar p{");
        out.println("font-size:13px;");
        out.println("color:#77808c;");
        out.println("margin-top:6px;");
        out.println("}");

        // STATS

        out.println(".stats{");
        out.println("display:grid;");
        out.println("grid-template-columns:repeat(3,1fr);");
        out.println("gap:16px;");
        out.println("margin-bottom:24px;");
        out.println("}");

        out.println(".stat-card{");
        out.println("background:#fff;");
        out.println("border:1px solid #e1e5ea;");
        out.println("border-radius:8px;");
        out.println("padding:20px;");
        out.println("}");

        out.println(".stat-card h3{");
        out.println("font-size:12px;");
        out.println("font-weight:600;");
        out.println("color:#7a838d;");
        out.println("text-transform:uppercase;");
        out.println("letter-spacing:.6px;");
        out.println("}");

        out.println(".stat-number{");
        out.println("font-size:28px;");
        out.println("font-weight:700;");
        out.println("color:#172b4d;");
        out.println("margin-top:8px;");
        out.println("}");

        // SECTION TITLE

        out.println(".section-title{");
        out.println("font-size:18px;");
        out.println("font-weight:600;");
        out.println("color:#172b4d;");
        out.println("margin-bottom:15px;");
        out.println("}");

        // APPLICATION CARD

        out.println(".application-card{");
        out.println("background:#fff;");
        out.println("border:1px solid #e1e5ea;");
        out.println("border-radius:9px;");
        out.println("padding:23px;");
        out.println("margin-bottom:16px;");
        out.println("box-shadow:0 2px 8px rgba(0,0,0,.04);");
        out.println("}");

        out.println(".application-header{");
        out.println("display:flex;");
        out.println("justify-content:space-between;");
        out.println("align-items:center;");
        out.println("padding-bottom:15px;");
        out.println("margin-bottom:16px;");
        out.println("border-bottom:1px solid #edf0f2;");
        out.println("}");

        out.println(".company-title h2{");
        out.println("font-size:19px;");
        out.println("font-weight:600;");
        out.println("color:#172b4d;");
        out.println("}");

        out.println(".company-id{");
        out.println("font-size:11px;");
        out.println("color:#7a838d;");
        out.println("margin-top:5px;");
        out.println("}");

        // BADGES

        out.println(".badge{");
        out.println("display:inline-block;");
        out.println("padding:7px 13px;");
        out.println("border-radius:20px;");
        out.println("font-size:11px;");
        out.println("font-weight:700;");
        out.println("letter-spacing:.3px;");
        out.println("}");

        out.println(".applied{");
        out.println("background:#eef3ff;");
        out.println("color:#3157a4;");
        out.println("}");

        out.println(".shortlisted{");
        out.println("background:#fff5d9;");
        out.println("color:#856404;");
        out.println("}");

        out.println(".interview{");
        out.println("background:#f0eaff;");
        out.println("color:#6040a5;");
        out.println("}");

        out.println(".selected{");
        out.println("background:#e8f7ee;");
        out.println("color:#207044;");
        out.println("}");

        out.println(".rejected{");
        out.println("background:#fdecec;");
        out.println("color:#a33a3a;");
        out.println("}");

        out.println(".default-status{");
        out.println("background:#f1f3f5;");
        out.println("color:#59636f;");
        out.println("}");

        // DETAILS

        out.println(".details{");
        out.println("display:grid;");
        out.println("grid-template-columns:repeat(2,1fr);");
        out.println("gap:13px 35px;");
        out.println("}");

        out.println(".detail{");
        out.println("font-size:13px;");
        out.println("line-height:1.6;");
        out.println("color:#59636f;");
        out.println("}");

        out.println(".detail strong{");
        out.println("color:#263238;");
        out.println("font-weight:600;");
        out.println("}");

        out.println(".skills{");
        out.println("grid-column:1/-1;");
        out.println("}");

        // DATE

        out.println(".application-date{");
        out.println("margin-top:18px;");
        out.println("padding-top:14px;");
        out.println("border-top:1px solid #edf0f2;");
        out.println("font-size:12px;");
        out.println("color:#7a838d;");
        out.println("}");

        // EMPTY

        out.println(".empty-box{");
        out.println("background:#fff;");
        out.println("border:1px solid #e1e5ea;");
        out.println("border-radius:9px;");
        out.println("padding:55px 25px;");
        out.println("text-align:center;");
        out.println("}");

        out.println(".empty-box .icon{");
        out.println("font-size:42px;");
        out.println("margin-bottom:15px;");
        out.println("}");

        out.println(".empty-box h3{");
        out.println("font-size:18px;");
        out.println("color:#172b4d;");
        out.println("margin-bottom:8px;");
        out.println("}");

        out.println(".empty-box p{");
        out.println("font-size:13px;");
        out.println("color:#7a838d;");
        out.println("margin-bottom:18px;");
        out.println("}");

        out.println(".browse-btn{");
        out.println("display:inline-block;");
        out.println("padding:10px 18px;");
        out.println("background:#172b4d;");
        out.println("color:#fff;");
        out.println("text-decoration:none;");
        out.println("border-radius:5px;");
        out.println("font-size:13px;");
        out.println("}");

        // ERROR

        out.println(".error-box{");
        out.println("background:#fff5f5;");
        out.println("border:1px solid #e3b4b4;");
        out.println("color:#8b3636;");
        out.println("padding:14px 16px;");
        out.println("border-radius:6px;");
        out.println("font-size:13px;");
        out.println("}");

        // RESPONSIVE

        out.println("@media(max-width:900px){");

        out.println(".sidebar{");
        out.println("width:200px;");
        out.println("}");

        out.println(".main{");
        out.println("margin-left:200px;");
        out.println("padding:25px;");
        out.println("}");

        out.println(".stats{");
        out.println("grid-template-columns:1fr;");
        out.println("}");

        out.println(".details{");
        out.println("grid-template-columns:1fr;");
        out.println("}");

        out.println(".skills{");
        out.println("grid-column:auto;");
        out.println("}");

        out.println("}");

        out.println("@media(max-width:650px){");

        out.println(".sidebar{");
        out.println("position:relative;");
        out.println("width:100%;");
        out.println("height:auto;");
        out.println("}");

        out.println(".main{");
        out.println("margin-left:0;");
        out.println("padding:18px;");
        out.println("}");

        out.println(".application-header{");
        out.println("flex-direction:column;");
        out.println("align-items:flex-start;");
        out.println("gap:12px;");
        out.println("}");

        out.println("}");

        out.println("</style>");

        out.println("</head>");

        out.println("<body>");

        // =========================================================
        // SIDEBAR
        // =========================================================

        out.println("<div class='sidebar'>");

        out.println("<div class='brand'>");

        out.println("<h2>PLACEMENT PORTAL</h2>");

        out.println("<p>STUDENT PLACEMENT SYSTEM</p>");

        out.println("</div>");

        out.println("<ul>");

        out.println("<li>");
        out.println("<a href='HomeServlet'>Dashboard</a>");
        out.println("</li>");

        out.println("<li>");
        out.println("<a href='StudentCompanyServlet'>Companies</a>");
        out.println("</li>");

        out.println("<li class='active'>");
        out.println("<a href='AppliedServlet'>My Applications</a>");
        out.println("</li>");

        out.println("<li>");
        out.println("<a href='StudentInterviewServlet'>Interviews</a>");
        out.println("</li>");

        out.println("<li>");
        out.println("<a href='PlacedServlet'>Placed</a>");
        out.println("</li>");

        out.println("<li>");
        out.println("<a href='ProfileServlet'>Profile</a>");
        out.println("</li>");

        out.println("<li>");
        out.println("<a href='LogoutServlet'>Logout</a>");
        out.println("</li>");

        out.println("</ul>");

        out.println("</div>");

        // =========================================================
        // MAIN
        // =========================================================

        out.println("<div class='main'>");

        out.println("<div class='topbar'>");

        out.println("<h1>My Applications</h1>");

        out.println(
                "<p>Track your placement applications "
                + "and their current status.</p>"
        );

        out.println("</div>");

        // =========================================================
        // VARIABLES
        // =========================================================

        int totalApplications = 0;
        int shortlistedCount = 0;
        int selectedCount = 0;

        boolean found = false;

        StringBuilder applicationsHTML =
                new StringBuilder();

        // =========================================================
        // DATABASE CONNECTION
        // =========================================================

        try {

            // IMPORTANT:
            // Use DBConnection instead of localhost.
            //
            // On Railway -> Railway MySQL
            // On laptop  -> Local MySQL

            Connection con =
                    DBConnection.getConnection();

            String sql =
                    "SELECT a.id, a.status, a.applied_date, "
                    + "c.name, c.company_id, c.hr_name, c.email, "
                    + "c.branches, c.branch_type, c.cutoff, "
                    + "c.required_skills "
                    + "FROM applications a "
                    + "JOIN companies c "
                    + "ON a.company_id = c.id "
                    + "WHERE a.student_id = ? "
                    + "ORDER BY a.applied_date DESC";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, studentId);

            ResultSet rs =
                    ps.executeQuery();

            // =====================================================
            // READ APPLICATIONS
            // =====================================================

            while (rs.next()) {

                found = true;

                totalApplications++;

                String status =
                        rs.getString("status");

                if (status == null ||
                    status.trim().isEmpty()) {

                    status = "APPLIED";
                }

                String statusUpper =
                        status.toUpperCase();

                if (statusUpper.equals("SHORTLISTED")) {
                    shortlistedCount++;
                }

                if (statusUpper.equals("SELECTED")) {
                    selectedCount++;
                }

                String statusClass =
                        "default-status";

                if (statusUpper.equals("APPLIED")) {

                    statusClass = "applied";

                } else if (statusUpper.equals("SHORTLISTED")) {

                    statusClass = "shortlisted";

                } else if (statusUpper.equals("INTERVIEW")) {

                    statusClass = "interview";

                } else if (statusUpper.equals("SELECTED")) {

                    statusClass = "selected";

                } else if (statusUpper.equals("REJECTED")) {

                    statusClass = "rejected";
                }

                // =================================================
                // APPLICATION CARD
                // =================================================

                applicationsHTML.append(
                        "<div class='application-card'>"
                );

                // HEADER

                applicationsHTML.append(
                        "<div class='application-header'>"
                );

                applicationsHTML.append(
                        "<div class='company-title'>"
                );

                applicationsHTML.append(
                        "<h2>"
                );

                applicationsHTML.append(
                        escapeHTML(
                                rs.getString("name")
                        )
                );

                applicationsHTML.append(
                        "</h2>"
                );

                applicationsHTML.append(
                        "<div class='company-id'>"
                );

                applicationsHTML.append(
                        "Company ID: "
                );

                applicationsHTML.append(
                        escapeHTML(
                                rs.getString("company_id")
                        )
                );

                applicationsHTML.append(
                        "</div>"
                );

                applicationsHTML.append(
                        "</div>"
                );

                // STATUS

                applicationsHTML.append(
                        "<span class='badge "
                );

                applicationsHTML.append(
                        statusClass
                );

                applicationsHTML.append(
                        "'>"
                );

                applicationsHTML.append(
                        escapeHTML(status)
                );

                applicationsHTML.append(
                        "</span>"
                );

                applicationsHTML.append(
                        "</div>"
                );

                // DETAILS

                applicationsHTML.append(
                        "<div class='details'>"
                );

                applicationsHTML.append(
                        "<div class='detail'>"
                );

                applicationsHTML.append(
                        "<strong>HR Contact:</strong> "
                );

                applicationsHTML.append(
                        escapeHTML(
                                rs.getString("hr_name")
                        )
                );

                applicationsHTML.append(
                        "</div>"
                );

                applicationsHTML.append(
                        "<div class='detail'>"
                );

                applicationsHTML.append(
                        "<strong>Email:</strong> "
                );

                applicationsHTML.append(
                        escapeHTML(
                                rs.getString("email")
                        )
                );

                applicationsHTML.append(
                        "</div>"
                );

                applicationsHTML.append(
                        "<div class='detail'>"
                );

                applicationsHTML.append(
                        "<strong>Allowed Branches:</strong> "
                );

                applicationsHTML.append(
                        escapeHTML(
                                rs.getString("branches")
                        )
                );

                applicationsHTML.append(
                        "</div>"
                );

                applicationsHTML.append(
                        "<div class='detail'>"
                );

                applicationsHTML.append(
                        "<strong>Branch Type:</strong> "
                );

                applicationsHTML.append(
                        escapeHTML(
                                rs.getString("branch_type")
                        )
                );

                applicationsHTML.append(
                        "</div>"
                );

                applicationsHTML.append(
                        "<div class='detail'>"
                );

                applicationsHTML.append(
                        "<strong>Cutoff CGPA:</strong> "
                );

                applicationsHTML.append(
                        String.valueOf(
                                rs.getDouble("cutoff")
                        )
                );

                applicationsHTML.append(
                        "</div>"
                );

                applicationsHTML.append(
                        "<div class='detail skills'>"
                );

                applicationsHTML.append(
                        "<strong>Required Skills:</strong> "
                );

                applicationsHTML.append(
                        escapeHTML(
                                rs.getString("required_skills")
                        )
                );

                applicationsHTML.append(
                        "</div>"
                );

                applicationsHTML.append(
                        "</div>"
                );

                // DATE

                applicationsHTML.append(
                        "<div class='application-date'>"
                );

                applicationsHTML.append(
                        "<strong>Applied Date:</strong> "
                );

                Timestamp appliedDate =
                        rs.getTimestamp("applied_date");

                if (appliedDate != null) {

                    applicationsHTML.append(
                            escapeHTML(
                                    appliedDate.toString()
                            )
                    );

                } else {

                    applicationsHTML.append(
                            "Not available"
                    );
                }

                applicationsHTML.append(
                        "</div>"
                );

                applicationsHTML.append(
                        "</div>"
                );
            }

            rs.close();
            ps.close();
            con.close();

            // =====================================================
            // STATISTICS
            // =====================================================

            out.println("<div class='stats'>");

            out.println("<div class='stat-card'>");

            out.println(
                    "<h3>Total Applications</h3>"
            );

            out.println(
                    "<div class='stat-number'>"
                    + totalApplications
                    + "</div>"
            );

            out.println("</div>");

            out.println("<div class='stat-card'>");

            out.println(
                    "<h3>Shortlisted</h3>"
            );

            out.println(
                    "<div class='stat-number'>"
                    + shortlistedCount
                    + "</div>"
            );

            out.println("</div>");

            out.println("<div class='stat-card'>");

            out.println(
                    "<h3>Selected</h3>"
            );

            out.println(
                    "<div class='stat-number'>"
                    + selectedCount
                    + "</div>"
            );

            out.println("</div>");

            out.println("</div>");

            // =====================================================
            // TITLE
            // =====================================================

            out.println(
                    "<div class='section-title'>"
                    + "Application History"
                    + "</div>"
            );

            // =====================================================
            // APPLICATIONS
            // =====================================================

            if (found) {

                out.println(
                        applicationsHTML.toString()
                );

            } else {

                out.println("<div class='empty-box'>");

                out.println(
                        "<div class='icon'>📄</div>"
                );

                out.println(
                        "<h3>No Applications Yet</h3>"
                );

                out.println(
                        "<p>"
                        + "You have not applied to any "
                        + "placement opportunities yet."
                        + "</p>"
                );

                out.println(
                        "<a class='browse-btn' "
                        + "href='StudentCompanyServlet'>"
                        + "Browse Companies"
                        + "</a>"
                );

                out.println("</div>");
            }

        } catch (Exception e) {

            // Print actual error in Railway logs
            e.printStackTrace();

            out.println(
                    "<div class='error-box'>"
                    + "Unable to load your applications. "
                    + "Please try again later."
                    + "</div>"
            );
        }

        out.println("</div>");

        out.println("</body>");

        out.println("</html>");
    }

    // =============================================================
    // POST
    // =============================================================

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

    // =============================================================
    // HTML ESCAPE
    // =============================================================

    private String escapeHTML(String text) {

        if (text == null) {
            return "";
        }

        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}