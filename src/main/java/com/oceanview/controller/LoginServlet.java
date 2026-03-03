package com.oceanview.controller;

import com.oceanview.dao.UserDao;
import com.oceanview.model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserDao userDao = new UserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/login.html?error=1");
            return;
        }

        try {
            User user = userDao.findByCredentials(username.trim(), password);

            if (user == null) {
                resp.sendRedirect(req.getContextPath() + "/login.html?error=1");
                return;
            }

            HttpSession session = req.getSession(true);
            session.setAttribute("user", user.getUsername());
            session.setAttribute("role", user.getRole());

            // Always redirect with context path (works even if context changes)
            resp.sendRedirect(req.getContextPath() + "/dashboard-static.html");

        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/login.html?error=2");
        }
    }
}