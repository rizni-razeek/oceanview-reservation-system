package com.oceanview.controller;

import com.oceanview.dao.ReservationDao;
import com.oceanview.model.Reservation;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/reservationDetails")
public class ReservationDetailsServlet extends HttpServlet {

    private final ReservationDao dao = new ReservationDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String reservationNo = req.getParameter("reservationNo");

        if (reservationNo == null || reservationNo.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/view-reservation.html?error=1");
            return;
        }

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        try {
            Reservation r = dao.findByReservationNo(reservationNo.trim());

            if (r == null) {
                resp.sendRedirect(req.getContextPath() + "/view-reservation.html?error=2");
                return;
            }

            StringBuilder html = new StringBuilder();

            html.append("<html><head><title>Reservation Details</title>");
            html.append("<link rel='stylesheet' href='styles.css'>");
            html.append("</head><body>");

            html.append("<h1>Reservation Details</h1>");

            html.append("<table>");
            html.append("<tr><th>Field</th><th>Value</th></tr>");

            html.append("<tr><td>Reservation Number</td><td>").append(r.getReservationNo()).append("</td></tr>");
            html.append("<tr><td>Guest Name</td><td>").append(r.getGuestName()).append("</td></tr>");
            html.append("<tr><td>Guest Address</td><td>").append(r.getGuestAddress()).append("</td></tr>");
            html.append("<tr><td>Contact Number</td><td>").append(r.getContactNo()).append("</td></tr>");
            html.append("<tr><td>Room Type</td><td>").append(r.getRoomType()).append("</td></tr>");
            html.append("<tr><td>Check-in Date</td><td>").append(r.getCheckIn()).append("</td></tr>");
            html.append("<tr><td>Check-out Date</td><td>").append(r.getCheckOut()).append("</td></tr>");

            html.append("</table>");

            html.append("<br><p><a href='view-reservation.html'>Search Another Reservation</a></p>");
            html.append("<p><a href='dashboard-static.html'>Back to Dashboard</a></p>");

            html.append("</body></html>");

            resp.getWriter().write(html.toString());

        } catch (Exception e) {
            resp.getWriter().write("Error loading reservation details.");
        }
    }
}