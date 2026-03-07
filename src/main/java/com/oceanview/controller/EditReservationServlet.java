package com.oceanview.controller;

import com.oceanview.dao.ReservationDao;
import com.oceanview.model.Reservation;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/editReservation")
public class EditReservationServlet extends HttpServlet {

    private final ReservationDao dao = new ReservationDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String reservationNo = req.getParameter("reservationNo");

        if (reservationNo == null || reservationNo.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/reservations");
            return;
        }

        try {
            Reservation r = dao.findByReservationNo(reservationNo);

            if (r == null) {
                resp.sendRedirect(req.getContextPath() + "/edit-reservation.html?error=1");
                return;
            }

            // Redirect to HTML page with data in query string (fast & simple for this assessment)
            String url = req.getContextPath() + "/edit-reservation.html" +
                    "?reservationNo=" + enc(r.getReservationNo()) +
                    "&guestName=" + enc(r.getGuestName()) +
                    "&guestAddress=" + enc(r.getGuestAddress()) +
                    "&contactNo=" + enc(r.getContactNo()) +
                    "&roomType=" + enc(r.getRoomType()) +
                    "&checkIn=" + enc(r.getCheckIn().toString()) +
                    "&checkOut=" + enc(r.getCheckOut().toString());

            resp.sendRedirect(url);

        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/edit-reservation.html?error=4");
        }
    }

    private static String enc(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }
}