package com.oceanview.controller;

import com.oceanview.dao.ReservationDao;
import com.oceanview.model.Reservation;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/reservations")
public class ViewReservationsServlet extends HttpServlet {

    private final ReservationDao dao = new ReservationDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        String q = req.getParameter("q");

        try {

            List<Reservation> list;

            if (q != null && !q.trim().isEmpty()) {
                list = dao.search(q.trim());
            } else {
                list = dao.findAll();
            }

            StringBuilder html = new StringBuilder();

            html.append("<html><head><title>Reservations</title>");
            html.append("<link rel='stylesheet' href='styles.css'>");
            html.append("</head><body>");
            html.append("<h1>All Reservations</h1>");

            // Search form
            html.append("<form method='get' action='reservations'>");
            html.append("Search: <input name='q' value='")
                    .append(q == null ? "" : q)
                    .append("' /> ");
            html.append("<button type='submit'>Search</button> ");
            html.append("<a href='reservations'>Clear</a>");
            html.append("</form><br>");

            html.append("<table border='1'>");
            html.append("<tr>");
            html.append("<th>Reservation No</th>");
            html.append("<th>Guest Name</th>");
            html.append("<th>Contact</th>");
            html.append("<th>Room</th>");
            html.append("<th>Check In</th>");
            html.append("<th>Check Out</th>");
            html.append("<th>Action</th>");
            html.append("</tr>");

            for (Reservation r : list) {

                html.append("<tr>");
                html.append("<td>").append(r.getReservationNo()).append("</td>");
                html.append("<td>").append(r.getGuestName()).append("</td>");
                html.append("<td>").append(r.getContactNo()).append("</td>");
                html.append("<td>").append(r.getRoomType()).append("</td>");
                html.append("<td>").append(r.getCheckIn()).append("</td>");
                html.append("<td>").append(r.getCheckOut()).append("</td>");

                // Action column: Edit + Delete
                html.append("<td>");

                html.append("<a href='editReservation?reservationNo=")
                        .append(r.getReservationNo())
                        .append("'>Edit</a>");

                html.append(" | ");

                html.append("<a href='deleteReservation?reservationNo=")
                        .append(r.getReservationNo())
                        .append("'>Delete</a>");

                html.append("</td>");

                html.append("</tr>");
            }

            html.append("</table>");

            html.append("<br><a href='dashboard-static.html'>Back to Dashboard</a>");

            html.append("</body></html>");

            resp.getWriter().write(html.toString());

        } catch (Exception e) {
            resp.getWriter().write("Error loading reservations");
        }
    }
}