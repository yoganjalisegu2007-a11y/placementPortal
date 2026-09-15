
package placementPortal;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = response.getWriter();

        /*
         * =========================================================
         * STUDENT SESSION
         * =========================================================
         */

        HttpSession session = request.getSession(false);

        if (session == null ||
                session.getAttribute("studentId") == null) {

            response.sendRedirect("LoginServlet");
            return;
        }

        Integer studentId =
                (Integer) session.getAttribute("studentId");

        String studentName = "Student";

        int totalCompanies = 0;
        int totalApplications = 0;
        int myApplications = 0;

        /*
         * Stores company database IDs for which
         * the current student has already applied.
         */
        Set<Integer> appliedCompanies = new HashSet<Integer>();


        /*
         * =========================================================
         * DATABASE
         * =========================================================
         */

        try (Connection con = DBConnection.getConnection()) {

            /*
             * TOTAL COMPANIES
             */
            try (PreparedStatement ps =
                         con.prepareStatement(
                                 "SELECT COUNT(*) FROM companies")) {

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        totalCompanies = rs.getInt(1);
                    }
                }
            }


            /*
             * TOTAL APPLICATIONS
             */
            try (PreparedStatement ps =
                         con.prepareStatement(
                                 "SELECT COUNT(*) FROM applications")) {

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        totalApplications = rs.getInt(1);
                    }
                }
            }


            /*
             * CURRENT STUDENT INFORMATION
             */
            try (PreparedStatement ps =
                         con.prepareStatement(
                                 "SELECT name FROM students WHERE id = ?")) {

                ps.setInt(1, studentId);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {

                        String name = rs.getString("name");

                        if (name != null &&
                                !name.trim().isEmpty()) {

                            studentName = name;
                        }
                    }
                }
            }


            /*
             * CURRENT STUDENT APPLICATION COUNT
             */
            try (PreparedStatement ps =
                         con.prepareStatement(
                                 "SELECT COUNT(*) " +
                                 "FROM applications " +
                                 "WHERE student_id = ?")) {

                ps.setInt(1, studentId);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {
                        myApplications = rs.getInt(1);
                    }
                }
            }


            /*
             * COMPANIES ALREADY APPLIED TO
             */
            try (PreparedStatement ps =
                         con.prepareStatement(
                                 "SELECT company_id " +
                                 "FROM applications " +
                                 "WHERE student_id = ?")) {

                ps.setInt(1, studentId);

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {

                        appliedCompanies.add(
                                rs.getInt("company_id")
                        );
                    }
                }
            }


        } catch (Exception e) {

            e.printStackTrace();

        }


        /*
         * =========================================================
         * HTML START
         * =========================================================
         */

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");

        out.println("<head>");

        out.println("<meta charset='UTF-8'>");

        out.println(
                "<meta name='viewport' " +
                "content='width=device-width, initial-scale=1.0'>"
        );

        out.println(
                "<title>Student Dashboard | Placement Portal</title>"
        );


        /*
         * =========================================================
         * CSS
         * =========================================================
         */

        out.println("<style>");

        out.println("* {");
        out.println("margin:0;");
        out.println("padding:0;");
        out.println("box-sizing:border-box;");
        out.println("}");

        out.println("body {");
        out.println("font-family:Inter,Arial,Helvetica,sans-serif;");
        out.println("background:#f4f7fb;");
        out.println("color:#263238;");
        out.println("}");

        out.println("a {");
        out.println("text-decoration:none;");
        out.println("}");

        out.println("button,input {");
        out.println("font-family:inherit;");
        out.println("}");


        /*
         * SIDEBAR
         */

        out.println(".sidebar {");
        out.println("position:fixed;");
        out.println("left:0;");
        out.println("top:0;");
        out.println("width:250px;");
        out.println("height:100vh;");
        out.println("background:linear-gradient(180deg,#101f3b,#172b4d);");
        out.println("color:white;");
        out.println("z-index:10;");
        out.println("}");

        out.println(".brand {");
        out.println("padding:28px 25px;");
        out.println("border-bottom:1px solid rgba(255,255,255,.10);");
        out.println("}");

        out.println(".brand-icon {");
        out.println("width:45px;");
        out.println("height:45px;");
        out.println("border-radius:12px;");
        out.println("background:rgba(255,255,255,.12);");
        out.println("display:flex;");
        out.println("align-items:center;");
        out.println("justify-content:center;");
        out.println("font-size:22px;");
        out.println("margin-bottom:13px;");
        out.println("}");

        out.println(".brand h2 {");
        out.println("font-size:17px;");
        out.println("letter-spacing:.7px;");
        out.println("}");

        out.println(".brand p {");
        out.println("font-size:10px;");
        out.println("color:#aeb9c8;");
        out.println("margin-top:5px;");
        out.println("}");

        out.println(".nav {");
        out.println("padding:25px 14px;");
        out.println("}");

        out.println(".nav-title {");
        out.println("font-size:9px;");
        out.println("color:#7f90a8;");
        out.println("text-transform:uppercase;");
        out.println("letter-spacing:1.3px;");
        out.println("padding:0 13px 11px;");
        out.println("}");

        out.println(".nav a {");
        out.println("display:flex;");
        out.println("align-items:center;");
        out.println("gap:12px;");
        out.println("color:#cbd5e1;");
        out.println("padding:13px 15px;");
        out.println("margin-bottom:6px;");
        out.println("border-radius:10px;");
        out.println("font-size:13px;");
        out.println("transition:.25s;");
        out.println("}");

        out.println(".nav a:hover {");
        out.println("background:rgba(255,255,255,.08);");
        out.println("color:white;");
        out.println("transform:translateX(3px);");
        out.println("}");

        out.println(".nav a.active {");
        out.println("background:linear-gradient(90deg,#28507e,#24466f);");
        out.println("color:white;");
        out.println("box-shadow:0 6px 18px rgba(0,0,0,.15);");
        out.println("}");

        out.println(".nav-icon {");
        out.println("width:22px;");
        out.println("text-align:center;");
        out.println("font-size:15px;");
        out.println("}");


        /*
         * MAIN
         */

        out.println(".main {");
        out.println("margin-left:250px;");
        out.println("min-height:100vh;");
        out.println("}");


        /*
         * TOPBAR
         */

        out.println(".topbar {");
        out.println("height:75px;");
        out.println("background:white;");
        out.println("border-bottom:1px solid #e7ebf0;");
        out.println("display:flex;");
        out.println("align-items:center;");
        out.println("justify-content:space-between;");
        out.println("padding:0 38px;");
        out.println("}");

        out.println(".breadcrumb {");
        out.println("font-size:12px;");
        out.println("color:#8793a3;");
        out.println("}");

        out.println(".breadcrumb strong {");
        out.println("color:#172b4d;");
        out.println("}");

        out.println(".user-box {");
        out.println("display:flex;");
        out.println("align-items:center;");
        out.println("gap:11px;");
        out.println("}");

        out.println(".avatar {");
        out.println("width:40px;");
        out.println("height:40px;");
        out.println("border-radius:50%;");
        out.println("background:linear-gradient(135deg,#172b4d,#315c8d);");
        out.println("color:white;");
        out.println("display:flex;");
        out.println("align-items:center;");
        out.println("justify-content:center;");
        out.println("font-size:12px;");
        out.println("font-weight:bold;");
        out.println("}");

        out.println(".user-text strong {");
        out.println("display:block;");
        out.println("font-size:12px;");
        out.println("color:#172b4d;");
        out.println("}");

        out.println(".user-text span {");
        out.println("font-size:10px;");
        out.println("color:#8b97a6;");
        out.println("}");


        /*
         * CONTENT
         */

        out.println(".content {");
        out.println("padding:34px 38px;");
        out.println("max-width:1500px;");
        out.println("margin:auto;");
        out.println("}");


        /*
         * HERO
         */

        out.println(".hero {");
        out.println("background:linear-gradient(135deg,#132846,#254e7d);");
        out.println("border-radius:18px;");
        out.println("padding:32px;");
        out.println("color:white;");
        out.println("display:flex;");
        out.println("justify-content:space-between;");
        out.println("align-items:center;");
        out.println("gap:25px;");
        out.println("margin-bottom:25px;");
        out.println("overflow:hidden;");
        out.println("position:relative;");
        out.println("box-shadow:0 12px 30px rgba(23,43,77,.16);");
        out.println("}");

        out.println(".hero:after {");
        out.println("content:'';");
        out.println("position:absolute;");
        out.println("width:230px;");
        out.println("height:230px;");
        out.println("border:35px solid rgba(255,255,255,.04);");
        out.println("border-radius:50%;");
        out.println("right:-70px;");
        out.println("top:-100px;");
        out.println("}");

        out.println(".hero-content {");
        out.println("position:relative;");
        out.println("z-index:2;");
        out.println("}");

        out.println(".hero-label {");
        out.println("font-size:10px;");
        out.println("font-weight:bold;");
        out.println("letter-spacing:1.7px;");
        out.println("text-transform:uppercase;");
        out.println("color:#d5b77e;");
        out.println("margin-bottom:8px;");
        out.println("}");

        out.println(".hero h1 {");
        out.println("font-size:29px;");
        out.println("font-weight:700;");
        out.println("margin-bottom:8px;");
        out.println("}");

        out.println(".hero p {");
        out.println("font-size:12px;");
        out.println("color:#d1dbe7;");
        out.println("max-width:620px;");
        out.println("line-height:1.7;");
        out.println("}");

        out.println(".hero-action {");
        out.println("position:relative;");
        out.println("z-index:2;");
        out.println("}");

        out.println(".view-apps-btn {");
        out.println("display:inline-block;");
        out.println("background:#d2b477;");
        out.println("color:#172b4d;");
        out.println("font-size:11px;");
        out.println("font-weight:bold;");
        out.println("padding:13px 20px;");
        out.println("border-radius:9px;");
        out.println("transition:.25s;");
        out.println("}");

        out.println(".view-apps-btn:hover {");
        out.println("transform:translateY(-2px);");
        out.println("box-shadow:0 8px 20px rgba(0,0,0,.18);");
        out.println("}");


        /*
         * STATISTICS
         */

        out.println(".stats-grid {");
        out.println("display:grid;");
        out.println("grid-template-columns:repeat(4,1fr);");
        out.println("gap:17px;");
        out.println("margin-bottom:30px;");
        out.println("}");

        out.println(".stat-card {");
        out.println("background:white;");
        out.println("border:1px solid #e6ebf1;");
        out.println("border-radius:15px;");
        out.println("padding:21px;");
        out.println("position:relative;");
        out.println("transition:.25s;");
        out.println("}");

        out.println(".stat-card:hover {");
        out.println("transform:translateY(-4px);");
        out.println("box-shadow:0 12px 25px rgba(23,43,77,.09);");
        out.println("}");

        out.println(".stat-icon {");
        out.println("width:42px;");
        out.println("height:42px;");
        out.println("border-radius:11px;");
        out.println("background:#edf3f9;");
        out.println("display:flex;");
        out.println("align-items:center;");
        out.println("justify-content:center;");
        out.println("font-size:18px;");
        out.println("margin-bottom:14px;");
        out.println("}");

        out.println(".stat-title {");
        out.println("font-size:9px;");
        out.println("font-weight:bold;");
        out.println("color:#8995a3;");
        out.println("letter-spacing:1px;");
        out.println("}");

        out.println(".stat-number {");
        out.println("font-size:30px;");
        out.println("font-weight:700;");
        out.println("color:#172b4d;");
        out.println("margin-top:6px;");
        out.println("}");

        out.println(".stat-sub {");
        out.println("font-size:10px;");
        out.println("color:#96a1ae;");
        out.println("margin-top:4px;");
        out.println("}");


        /*
         * SEARCH
         */

        out.println(".section-top {");
        out.println("display:flex;");
        out.println("justify-content:space-between;");
        out.println("align-items:flex-end;");
        out.println("gap:20px;");
        out.println("margin-bottom:18px;");
        out.println("}");

        out.println(".section-title small {");
        out.println("font-size:9px;");
        out.println("font-weight:bold;");
        out.println("letter-spacing:1.5px;");
        out.println("color:#b08d57;");
        out.println("text-transform:uppercase;");
        out.println("}");

        out.println(".section-title h2 {");
        out.println("font-size:21px;");
        out.println("color:#172b4d;");
        out.println("margin-top:5px;");
        out.println("}");

        out.println(".section-title p {");
        out.println("font-size:11px;");
        out.println("color:#8b97a6;");
        out.println("margin-top:4px;");
        out.println("}");

        out.println(".search-box {");
        out.println("width:330px;");
        out.println("position:relative;");
        out.println("}");

        out.println(".search-box input {");
        out.println("width:100%;");
        out.println("border:1px solid #dfe5eb;");
        out.println("background:white;");
        out.println("border-radius:10px;");
        out.println("padding:13px 16px 13px 40px;");
        out.println("font-size:11px;");
        out.println("outline:none;");
        out.println("transition:.2s;");
        out.println("}");

        out.println(".search-box input:focus {");
        out.println("border-color:#315c8d;");
        out.println("box-shadow:0 0 0 3px rgba(49,92,141,.08);");
        out.println("}");

        out.println(".search-icon {");
        out.println("position:absolute;");
        out.println("left:14px;");
        out.println("top:11px;");
        out.println("font-size:15px;");
        out.println("color:#8c98a7;");
        out.println("}");


        /*
         * COMPANY GRID
         */

        out.println(".company-grid {");
        out.println("display:grid;");
        out.println("grid-template-columns:repeat(2,minmax(0,1fr));");
        out.println("gap:20px;");
        out.println("}");

        out.println(".company-card {");
        out.println("background:white;");
        out.println("border:1px solid #e5eaf0;");
        out.println("border-radius:17px;");
        out.println("padding:22px;");
        out.println("transition:.25s;");
        out.println("}");

        out.println(".company-card:hover {");
        out.println("transform:translateY(-5px);");
        out.println("box-shadow:0 15px 30px rgba(23,43,77,.10);");
        out.println("}");

        out.println(".company-header {");
        out.println("display:flex;");
        out.println("justify-content:space-between;");
        out.println("align-items:flex-start;");
        out.println("margin-bottom:20px;");
        out.println("}");

        out.println(".company-name {");
        out.println("display:flex;");
        out.println("align-items:center;");
        out.println("gap:13px;");
        out.println("}");

        out.println(".company-logo {");
        out.println("width:50px;");
        out.println("height:50px;");
        out.println("border-radius:13px;");
        out.println("background:linear-gradient(135deg,#172b4d,#37628f);");
        out.println("color:white;");
        out.println("display:flex;");
        out.println("align-items:center;");
        out.println("justify-content:center;");
        out.println("font-size:13px;");
        out.println("font-weight:bold;");
        out.println("box-shadow:0 7px 16px rgba(23,43,77,.16);");
        out.println("}");

        out.println(".company-name h3 {");
        out.println("font-size:16px;");
        out.println("color:#172b4d;");
        out.println("margin-bottom:4px;");
        out.println("}");

        out.println(".company-id {");
        out.println("font-size:9px;");
        out.println("color:#8995a3;");
        out.println("}");

        out.println(".status {");
        out.println("font-size:8px;");
        out.println("font-weight:bold;");
        out.println("letter-spacing:.6px;");
        out.println("padding:6px 10px;");
        out.println("border-radius:20px;");
        out.println("background:#eaf7f0;");
        out.println("color:#24754e;");
        out.println("}");

        out.println(".applied-status {");
        out.println("background:#eef3fb;");
        out.println("color:#315c8d;");
        out.println("}");


        /*
         * DETAILS
         */

        out.println(".details {");
        out.println("display:grid;");
        out.println("grid-template-columns:1fr 1fr;");
        out.println("gap:10px;");
        out.println("margin-bottom:18px;");
        out.println("}");

        out.println(".detail {");
        out.println("background:#f7f9fb;");
        out.println("border:1px solid #edf0f4;");
        out.println("padding:12px;");
        out.println("border-radius:10px;");
        out.println("}");

        out.println(".detail-label {");
        out.println("display:block;");
        out.println("font-size:8px;");
        out.println("font-weight:bold;");
        out.println("color:#8995a3;");
        out.println("text-transform:uppercase;");
        out.println("letter-spacing:.6px;");
        out.println("margin-bottom:5px;");
        out.println("}");

        out.println(".detail-value {");
        out.println("display:block;");
        out.println("font-size:10px;");
        out.println("font-weight:600;");
        out.println("color:#37474f;");
        out.println("word-break:break-word;");
        out.println("line-height:1.4;");
        out.println("}");


        /*
         * SKILLS
         */

        out.println(".skills {");
        out.println("border-top:1px solid #edf0f3;");
        out.println("padding-top:15px;");
        out.println("margin-bottom:18px;");
        out.println("}");

        out.println(".skills-label {");
        out.println("font-size:8px;");
        out.println("font-weight:bold;");
        out.println("color:#8995a3;");
        out.println("text-transform:uppercase;");
        out.println("letter-spacing:.7px;");
        out.println("margin-bottom:7px;");
        out.println("}");

        out.println(".skills-value {");
        out.println("font-size:10px;");
        out.println("color:#455a64;");
        out.println("line-height:1.6;");
        out.println("}");


        /*
         * CARD FOOTER
         */

        out.println(".card-footer {");
        out.println("border-top:1px solid #edf0f3;");
        out.println("padding-top:16px;");
        out.println("display:flex;");
        out.println("justify-content:space-between;");
        out.println("align-items:center;");
        out.println("gap:12px;");
        out.println("}");

        out.println(".recruitment-text {");
        out.println("font-size:9px;");
        out.println("color:#8995a3;");
        out.println("}");

        out.println(".apply-btn {");
        out.println("display:inline-block;");
        out.println("background:#172b4d;");
        out.println("color:white;");
        out.println("padding:11px 18px;");
        out.println("border-radius:9px;");
        out.println("font-size:10px;");
        out.println("font-weight:bold;");
        out.println("transition:.25s;");
        out.println("}");

        out.println(".apply-btn:hover {");
        out.println("background:#315c8d;");
        out.println("transform:translateY(-2px);");
        out.println("box-shadow:0 7px 16px rgba(23,43,77,.15);");
        out.println("}");

        out.println(".already-applied {");
        out.println("display:inline-block;");
        out.println("background:#edf3f9;");
        out.println("color:#315c8d;");
        out.println("padding:11px 17px;");
        out.println("border-radius:9px;");
        out.println("font-size:10px;");
        out.println("font-weight:bold;");
        out.println("}");


        /*
         * EMPTY / ERROR
         */

        out.println(".empty {");
        out.println("grid-column:1/-1;");
        out.println("background:white;");
        out.println("border:1px dashed #d7dee7;");
        out.println("padding:60px 25px;");
        out.println("text-align:center;");
        out.println("border-radius:17px;");
        out.println("color:#8995a3;");
        out.println("}");

        out.println(".empty-icon {");
        out.println("font-size:35px;");
        out.println("margin-bottom:12px;");
        out.println("}");

        out.println(".empty h3 {");
        out.println("color:#172b4d;");
        out.println("font-size:15px;");
        out.println("margin-bottom:6px;");
        out.println("}");

        out.println(".empty p {");
        out.println("font-size:11px;");
        out.println("}");

        out.println(".error {");
        out.println("grid-column:1/-1;");
        out.println("background:#fff6f6;");
        out.println("border:1px solid #f0cccc;");
        out.println("color:#a13a3a;");
        out.println("padding:18px;");
        out.println("border-radius:12px;");
        out.println("font-size:11px;");
        out.println("}");


        /*
         * FOOTER
         */

        out.println(".footer {");
        out.println("margin-top:45px;");
        out.println("padding:22px 38px;");
        out.println("background:white;");
        out.println("border-top:1px solid #e6ebf0;");
        out.println("display:flex;");
        out.println("justify-content:space-between;");
        out.println("font-size:9px;");
        out.println("color:#8995a3;");
        out.println("}");


        /*
         * RESPONSIVE
         */

        out.println("@media(max-width:1100px) {");

        out.println(".stats-grid {");
        out.println("grid-template-columns:repeat(2,1fr);");
        out.println("}");

        out.println(".company-grid {");
        out.println("grid-template-columns:1fr;");
        out.println("}");

        out.println("}");

        out.println("@media(max-width:850px) {");

        out.println(".sidebar {");
        out.println("position:relative;");
        out.println("width:100%;");
        out.println("height:auto;");
        out.println("}");

        out.println(".main {");
        out.println("margin-left:0;");
        out.println("}");

        out.println(".nav {");
        out.println("display:flex;");
        out.println("flex-wrap:wrap;");
        out.println("gap:5px;");
        out.println("}");

        out.println(".nav-title {");
        out.println("display:none;");
        out.println("}");

        out.println(".nav a {");
        out.println("margin:0;");
        out.println("flex:1;");
        out.println("min-width:120px;");
        out.println("}");

        out.println(".hero {");
        out.println("flex-direction:column;");
        out.println("align-items:flex-start;");
        out.println("}");

        out.println("}");

        out.println("@media(max-width:650px) {");

        out.println(".topbar {");
        out.println("padding:0 18px;");
        out.println("}");

        out.println(".content {");
        out.println("padding:22px 18px;");
        out.println("}");

        out.println(".stats-grid {");
        out.println("grid-template-columns:1fr;");
        out.println("}");

        out.println(".section-top {");
        out.println("flex-direction:column;");
        out.println("align-items:stretch;");
        out.println("}");

        out.println(".search-box {");
        out.println("width:100%;");
        out.println("}");

        out.println(".details {");
        out.println("grid-template-columns:1fr;");
        out.println("}");

        out.println(".card-footer {");
        out.println("flex-direction:column;");
        out.println("align-items:stretch;");
        out.println("}");

        out.println(".apply-btn, .already-applied {");
        out.println("text-align:center;");
        out.println("}");

        out.println(".footer {");
        out.println("padding:18px;");
        out.println("flex-direction:column;");
        out.println("gap:6px;");
        out.println("}");

        out.println("}");

        out.println("</style>");

        out.println("</head>");


        /*
         * =========================================================
         * BODY
         * =========================================================
         */

        out.println("<body>");


        /*
         * SIDEBAR
         */

        out.println("<aside class='sidebar'>");

        out.println("<div class='brand'>");

        out.println("<div class='brand-icon'>🎓</div>");

        out.println("<h2>PLACEMENT PORTAL</h2>");

        out.println("<p>Student Career Management</p>");

        out.println("</div>");


        out.println("<nav class='nav'>");

        out.println("<div class='nav-title'>Main Menu</div>");

        out.println(
                "<a href='HomeServlet' class='active'>" +
                "<span class='nav-icon'>🏠</span>" +
                "<span>Dashboard</span>" +
                "</a>"
        );

        /*
         * IMPORTANT:
         * Student must use StudentCompanyServlet,
         * NOT CompanyServlet.
         */
        out.println(
                "<a href='StudentCompanyServlet'>" +
                "<span class='nav-icon'>🏢</span>" +
                "<span>Companies</span>" +
                "</a>"
        );

        out.println(
                "<a href='AppliedServlet'>" +
                "<span class='nav-icon'>📄</span>" +
                "<span>My Applications</span>" +
                "</a>"
        );

        /*
         * Student interview servlet
         */
        out.println(
                "<a href='StudentInterviewServlet'>" +
                "<span class='nav-icon'>🎤</span>" +
                "<span>Interviews</span>" +
                "</a>"
        );

        out.println(
                "<a href='ProfileServlet'>" +
                "<span class='nav-icon'>👤</span>" +
                "<span>My Profile</span>" +
                "</a>"
        );

        out.println(
                "<div class='nav-title' " +
                "style='margin-top:25px;'>Account</div>"
        );

        out.println(
                "<a href='LogoutServlet'>" +
                "<span class='nav-icon'>🚪</span>" +
                "<span>Logout</span>" +
                "</a>"
        );

        out.println("</nav>");

        out.println("</aside>");


        /*
         * MAIN
         */

        out.println("<div class='main'>");


        /*
         * TOPBAR
         */

        out.println("<header class='topbar'>");

        out.println(
                "<div class='breadcrumb'>" +
                "Placement Portal / <strong>Dashboard</strong>" +
                "</div>"
        );

        out.println("<div class='user-box'>");

        String avatarText = "ST";

        if (studentName != null &&
                !studentName.trim().isEmpty()) {

            avatarText =
                    studentName
                            .trim()
                            .substring(0, 1)
                            .toUpperCase();
        }

        out.println(
                "<div class='avatar'>" +
                escapeHtml(avatarText) +
                "</div>"
        );

        out.println("<div class='user-text'>");

        out.println(
                "<strong>" +
                escapeHtml(studentName) +
                "</strong>"
        );

        out.println("<span>Placement Candidate</span>");

        out.println("</div>");

        out.println("</div>");

        out.println("</header>");


        /*
         * CONTENT
         */

        out.println("<main class='content'>");


        /*
         * HERO
         */

        out.println("<section class='hero'>");

        out.println("<div class='hero-content'>");

        out.println(
                "<div class='hero-label'>" +
                "Student Dashboard" +
                "</div>"
        );

        out.println(
                "<h1>Welcome back, " +
                escapeHtml(studentName) +
                "! 👋</h1>"
        );

        out.println(
                "<p>" +
                "Discover exciting career opportunities, explore " +
                "recruitment drives and take the next step toward " +
                "your dream career." +
                "</p>"
        );

        out.println("</div>");

        out.println("<div class='hero-action'>");

        out.println(
                "<a href='AppliedServlet' " +
                "class='view-apps-btn'>" +
                "VIEW MY APPLICATIONS →" +
                "</a>"
        );

        out.println("</div>");

        out.println("</section>");


        /*
         * STATISTICS
         */

        out.println("<div class='stats-grid'>");


        /*
         * COMPANIES
         */

        out.println("<div class='stat-card'>");

        out.println("<div class='stat-icon'>🏢</div>");

        out.println(
                "<div class='stat-title'>" +
                "COMPANIES AVAILABLE" +
                "</div>"
        );

        out.println(
                "<div class='stat-number'>" +
                totalCompanies +
                "</div>"
        );

        out.println(
                "<div class='stat-sub'>" +
                "Recruiting companies" +
                "</div>"
        );

        out.println("</div>");


        /*
         * OPEN OPPORTUNITIES
         */

        out.println("<div class='stat-card'>");

        out.println("<div class='stat-icon'>💼</div>");

        out.println(
                "<div class='stat-title'>" +
                "OPEN OPPORTUNITIES" +
                "</div>"
        );

        out.println(
                "<div class='stat-number'>" +
                totalCompanies +
                "</div>"
        );

        out.println(
                "<div class='stat-sub'>" +
                "Current recruitment drives" +
                "</div>"
        );

        out.println("</div>");


        /*
         * TOTAL APPLICATIONS
         */

        out.println("<div class='stat-card'>");

        out.println("<div class='stat-icon'>📊</div>");

        out.println(
                "<div class='stat-title'>" +
                "TOTAL APPLICATIONS" +
                "</div>"
        );

        out.println(
                "<div class='stat-number'>" +
                totalApplications +
                "</div>"
        );

        out.println(
                "<div class='stat-sub'>" +
                "Applications received" +
                "</div>"
        );

        out.println("</div>");


        /*
         * MY APPLICATIONS
         */

        out.println("<div class='stat-card'>");

        out.println("<div class='stat-icon'>📄</div>");

        out.println(
                "<div class='stat-title'>" +
                "MY APPLICATIONS" +
                "</div>"
        );

        out.println(
                "<div class='stat-number'>" +
                myApplications +
                "</div>"
        );

        out.println(
                "<div class='stat-sub'>" +
                "Applications submitted" +
                "</div>"
        );

        out.println("</div>");

        out.println("</div>");


        /*
         * SECTION HEADER
         */

        out.println("<div class='section-top'>");

        out.println("<div class='section-title'>");

        out.println(
                "<small>Career Opportunities</small>"
        );

        out.println(
                "<h2>Available Recruitment Drives</h2>"
        );

        out.println(
                "<p>" +
                "Explore companies and find the right opportunity for you." +
                "</p>"
        );

        out.println("</div>");

        out.println("<div class='search-box'>");

        out.println(
                "<span class='search-icon'>🔍</span>"
        );

        out.println(
                "<input type='text' " +
                "id='companySearch' " +
                "placeholder='Search company, branch or skill...'>"
        );

        out.println("</div>");

        out.println("</div>");


        /*
         * COMPANY GRID
         */

        out.println(
                "<div class='company-grid' " +
                "id='companyGrid'>"
        );


        /*
         * =========================================================
         * LOAD COMPANIES
         * =========================================================
         */

        try (Connection con = DBConnection.getConnection();

             PreparedStatement ps =
                     con.prepareStatement(
                             "SELECT * " +
                             "FROM companies " +
                             "ORDER BY id DESC");

             ResultSet rs = ps.executeQuery()) {


            boolean found = false;


            while (rs.next()) {

                found = true;


                int companyDbId =
                        rs.getInt("id");

                String companyName =
                        rs.getString("name");

                String companyId =
                        rs.getString("company_id");

                String hrName =
                        rs.getString("hr_name");

                String email =
                        rs.getString("email");

                String branches =
                        rs.getString("branches");

                String branchType =
                        rs.getString("branch_type");

                String skills =
                        rs.getString("required_skills");

                double cutoff =
                        rs.getDouble("cutoff");


                /*
                 * COMPANY INITIALS
                 */

                String initials = "CO";

                if (companyName != null &&
                        !companyName.trim().isEmpty()) {

                    String[] words =
                            companyName
                                    .trim()
                                    .split("\\s+");

                    if (words.length >= 2) {

                        initials =
                                (
                                        words[0].substring(0, 1) +
                                        words[1].substring(0, 1)
                                ).toUpperCase();

                    } else {

                        initials =
                                companyName
                                        .substring(
                                                0,
                                                Math.min(
                                                        2,
                                                        companyName.length()
                                                )
                                        )
                                        .toUpperCase();
                    }
                }


                /*
                 * SEARCHABLE TEXT
                 */

                String searchable =
                        (
                                safe(companyName) + " " +
                                safe(branches) + " " +
                                safe(skills) + " " +
                                safe(branchType)
                        ).toLowerCase();


                /*
                 * COMPANY CARD
                 */

                out.println(
                        "<div class='company-card' " +
                        "data-search='" +
                        escapeHtml(searchable) +
                        "'>"
                );


                /*
                 * HEADER
                 */

                out.println(
                        "<div class='company-header'>"
                );

                out.println(
                        "<div class='company-name'>"
                );

                out.println(
                        "<div class='company-logo'>" +
                        escapeHtml(initials) +
                        "</div>"
                );

                out.println("<div>");

                out.println(
                        "<h3>" +
                        escapeHtml(companyName) +
                        "</h3>"
                );

                out.println(
                        "<div class='company-id'>" +
                        "Company ID: " +
                        escapeHtml(companyId) +
                        "</div>"
                );

                out.println("</div>");

                out.println("</div>");


                /*
                 * STATUS
                 */

                if (appliedCompanies.contains(companyDbId)) {

                    out.println(
                            "<span class='status applied-status'>" +
                            "APPLIED" +
                            "</span>"
                    );

                } else {

                    out.println(
                            "<span class='status'>" +
                            "OPEN" +
                            "</span>"
                    );
                }

                out.println("</div>");


                /*
                 * DETAILS
                 */

                out.println("<div class='details'>");


                /*
                 * HR
                 */

                out.println("<div class='detail'>");

                out.println(
                        "<span class='detail-label'>" +
                        "HR Person" +
                        "</span>"
                );

                out.println(
                        "<span class='detail-value'>" +
                        escapeHtml(hrName) +
                        "</span>"
                );

                out.println("</div>");


                /*
                 * EMAIL
                 */

                out.println("<div class='detail'>");

                out.println(
                        "<span class='detail-label'>" +
                        "Contact" +
                        "</span>"
                );

                out.println(
                        "<span class='detail-value'>" +
                        escapeHtml(email) +
                        "</span>"
                );

                out.println("</div>");


                /*
                 * BRANCH
                 */

                out.println("<div class='detail'>");

                out.println(
                        "<span class='detail-label'>" +
                        "Eligible Branches" +
                        "</span>"
                );

                out.println(
                        "<span class='detail-value'>" +
                        escapeHtml(branches) +
                        "</span>"
                );

                out.println("</div>");


                /*
                 * BRANCH TYPE
                 */

                out.println("<div class='detail'>");

                out.println(
                        "<span class='detail-label'>" +
                        "Branch Type" +
                        "</span>"
                );

                out.println(
                        "<span class='detail-value'>" +
                        escapeHtml(branchType) +
                        "</span>"
                );

                out.println("</div>");


                /*
                 * CGPA
                 */

                out.println("<div class='detail'>");

                out.println(
                        "<span class='detail-label'>" +
                        "Minimum CGPA" +
                        "</span>"
                );

                out.println(
                        "<span class='detail-value'>" +
                        String.format("%.2f", cutoff) +
                        "</span>"
                );

                out.println("</div>");

                out.println("</div>");


                /*
                 * SKILLS
                 */

                out.println("<div class='skills'>");

                out.println(
                        "<div class='skills-label'>" +
                        "Required Skills" +
                        "</div>"
                );

                out.println(
                        "<div class='skills-value'>" +
                        escapeHtml(skills) +
                        "</div>"
                );

                out.println("</div>");


                /*
                 * FOOTER
                 */

                out.println("<div class='card-footer'>");

                out.println(
                        "<div class='recruitment-text'>" +
                        "🎯 Campus recruitment opportunity" +
                        "</div>"
                );


                /*
                 * APPLY BUTTON
                 */

                if (appliedCompanies.contains(companyDbId)) {

                    out.println(
                            "<span class='already-applied'>" +
                            "✓ ALREADY APPLIED" +
                            "</span>"
                    );

                } else {

                    out.println(
                            "<a class='apply-btn' " +
                            "href='ApplyServlet?companyId=" +
                            companyDbId +
                            "'>" +
                            "APPLY NOW →" +
                            "</a>"
                    );
                }


                out.println("</div>");

                out.println("</div>");
            }


            /*
             * NO COMPANIES
             */

            if (!found) {

                out.println(
                        "<div class='empty'>" +
                        "<div class='empty-icon'>🏢</div>" +
                        "<h3>No Recruitment Drives Available</h3>" +
                        "<p>" +
                        "New opportunities will appear here " +
                        "when companies are added." +
                        "</p>" +
                        "</div>"
                );
            }


        } catch (Exception e) {

            out.println(
                    "<div class='error'>" +
                    "<strong>" +
                    "Unable to load recruitment drives." +
                    "</strong>" +
                    "<p style='margin-top:6px;'>" +
                    escapeHtml(e.getMessage()) +
                    "</p>" +
                    "</div>"
            );

            e.printStackTrace();
        }


        out.println("</div>");


        /*
         * FOOTER
         */

        out.println("<footer class='footer'>");

        out.println(
                "<span>" +
                "© 2026 Placement Management System" +
                "</span>"
        );

        out.println(
                "<span>" +
                "Placement & Training Cell" +
                "</span>"
        );

        out.println("</footer>");

        out.println("</main>");

        out.println("</div>");


        /*
         * =========================================================
         * SEARCH JAVASCRIPT
         * =========================================================
         */

        out.println("<script>");

        out.println(
                "const searchInput = " +
                "document.getElementById('companySearch');"
        );

        out.println(
                "const cards = " +
                "document.querySelectorAll('.company-card');"
        );

        out.println(
                "searchInput.addEventListener('input', function() {"
        );

        out.println(
                "const value = this.value.toLowerCase().trim();"
        );

        out.println(
                "cards.forEach(function(card) {"
        );

        out.println(
                "const text = " +
                "card.getAttribute('data-search') || '';"
        );

        out.println(
                "card.style.display = " +
                "text.includes(value) ? '' : 'none';"
        );

        out.println(
                "});"
        );

        out.println(
                "});"
        );

        out.println("</script>");


        out.println("</body>");

        out.println("</html>");
    }


    /*
     * =========================================================
     * HELPER METHODS
     * =========================================================
     */

    private static String safe(String value) {

        return value == null ? "" : value;
    }


    /*
     * Prevent HTML characters from being interpreted as HTML.
     */
    private static String escapeHtml(String value) {

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