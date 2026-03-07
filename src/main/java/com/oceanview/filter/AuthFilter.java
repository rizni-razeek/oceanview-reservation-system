package com.oceanview.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {
        "/dashboard-static.html",
        "/reservations",
        "/add-reservation.html",
        "/edit-reservation.html",
        "/view-reservation.html",
        "/bill.html",
        "/help.html",
        "/addReservation",
        "/editReservation",
        "/updateReservation",
        "/deleteReservation",
        "/reservationDetails",
        "/bill"
})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("user") != null);

        if (!loggedIn) {
            resp.sendRedirect(req.getContextPath() + "/login.html?error=3");
            return;
        }

        chain.doFilter(request, response);
    }
}