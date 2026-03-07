package com.oceanview.controller;

import com.oceanview.dao.ReservationDao;
import com.oceanview.model.Reservation;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.temporal.ChronoUnit;

@WebServlet("/bill")
public class BillServlet extends HttpServlet {

    private final ReservationDao dao = new ReservationDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String reservationNo = req.getParameter("reservationNo");

        if (reservationNo == null || reservationNo.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/bill.html?error=1");
            return;
        }

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        try {
            Reservation r = dao.findByReservationNo(reservationNo.trim());

            if (r == null) {
                resp.sendRedirect(req.getContextPath() + "/bill.html?error=2");
                return;
            }

            long nights = ChronoUnit.DAYS.between(r.getCheckIn(), r.getCheckOut());
            double roomRate = getRoomRate(r.getRoomType());
            double total = nights * roomRate;

            StringBuilder html = new StringBuilder();

            html.append("<html><head><title>Reservation Bill</title>");
            html.append("<link rel='stylesheet' href='styles.css'>");
            html.append("</head><body>");

            html.append("<h1>Reservation Bill</h1>");

            html.append("<table>");
            html.append("<tr><th>Field</th><th>Value</th></tr>");
            html.append("<tr><td>Reservation Number</td><td>").append(r.getReservationNo()).append("</td></tr>");
            html.append("<tr><td>Guest Name</td><td>").append(r.getGuestName()).append("</td></tr>");
            html.append("<tr><td>Room Type</td><td>").append(r.getRoomType()).append("</td></tr>");
            html.append("<tr><td>Check-in Date</td><td>").append(r.getCheckIn()).append("</td></tr>");
            html.append("<tr><td>Check-out Date</td><td>").append(r.getCheckOut()).append("</td></tr>");
            html.append("<tr><td>Number of Nights</td><td>").append(nights).append("</td></tr>");
            html.append("<tr><td>Rate per Night</td><td>").append(String.format("%.2f", roomRate)).append("</td></tr>");
            html.append("<tr><td><strong>Total Bill</strong></td><td><strong>")
                    .append(String.format("%.2f", total))
                    .append("</strong></td></tr>");
            html.append("</table>");

            html.append("<br><button onclick='window.print()'>Print Bill</button>");
            html.append("<p><a href='bill.html'>Generate Another Bill</a></p>");
            html.append("<p><a href='dashboard-static.html'>Back to Dashboard</a></p>");

            html.append("</body></html>");

            resp.getWriter().write(html.toString());

        } catch (Exception e) {
            resp.getWriter().write("Error generating bill.");
        }
    }

    private double getRoomRate(String roomType) {
        if ("Single".equalsIgnoreCase(roomType)) {
            return 100.00;
        }
        if ("Double".equalsIgnoreCase(roomType)) {
            return 180.00;
        }
        if ("Suite".equalsIgnoreCase(roomType)) {
            return 300.00;
        }
        return 0.0;
    }
}