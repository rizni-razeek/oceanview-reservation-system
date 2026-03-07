package com.oceanview.controller;

import com.oceanview.dao.ReservationDao;
import com.oceanview.model.Reservation;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;

@WebServlet("/addReservation")
public class AddReservationServlet extends HttpServlet {

    private final ReservationDao dao = new ReservationDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String guestName = req.getParameter("guestName");
        String guestAddress = req.getParameter("guestAddress");
        String contactNo = req.getParameter("contactNo");
        String roomType = req.getParameter("roomType");
        String checkInStr = req.getParameter("checkIn");
        String checkOutStr = req.getParameter("checkOut");

        if (isBlank(guestName) || isBlank(guestAddress) || isBlank(contactNo)
                || isBlank(roomType) || isBlank(checkInStr) || isBlank(checkOutStr)) {
            resp.sendRedirect(req.getContextPath() + "/add-reservation.html?error=1");
            return;
        }

        try {
            LocalDate checkIn = LocalDate.parse(checkInStr);
            LocalDate checkOut = LocalDate.parse(checkOutStr);

            if (!checkOut.isAfter(checkIn)) {
                resp.sendRedirect(req.getContextPath() + "/add-reservation.html?error=2");
                return;
            }

            String reservationNo = "RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            Reservation r = new Reservation(
                    reservationNo,
                    guestName.trim(),
                    guestAddress.trim(),
                    contactNo.trim(),
                    roomType.trim(),
                    checkIn,
                    checkOut
            );

            dao.add(r);
            resp.sendRedirect(req.getContextPath() + "/add-reservation.html?ok=1");

        } catch (DateTimeParseException e) {
            resp.sendRedirect(req.getContextPath() + "/add-reservation.html?error=1");
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/add-reservation.html?error=3");
        }
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}