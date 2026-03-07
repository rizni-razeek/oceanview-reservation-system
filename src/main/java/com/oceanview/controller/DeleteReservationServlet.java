package com.oceanview.controller;

import com.oceanview.dao.ReservationDao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteReservation")
public class DeleteReservationServlet extends HttpServlet {

    private final ReservationDao dao = new ReservationDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String reservationNo = req.getParameter("reservationNo");

        if (reservationNo == null || reservationNo.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/reservations");
            return;
        }

        try {
            dao.deleteByReservationNo(reservationNo);
        } catch (Exception e) {
            // ignore for now
        }

        resp.sendRedirect(req.getContextPath() + "/reservations");
    }
}