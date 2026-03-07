package com.oceanview.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // prevent browser caching
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setDateHeader("Expires", 0);

        HttpSession session = req.getSession(false);
        String user = (session != null) ? (String) session.getAttribute("user") : null;

        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        resp.getWriter().write("""
                <!DOCTYPE html>
                <html lang="en">
                <head>
                  <meta charset="UTF-8"/>
                  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                  <title>Dashboard</title>
                </head>
                <body>
                  <h1>Dashboard</h1>
                  <p>Welcome, %s</p>
                  <p><a href="logout">Logout</a></p>
                </body>
                </html>
                """.formatted(user == null ? "User" : user));
    }
}