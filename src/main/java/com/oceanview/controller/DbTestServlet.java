package com.oceanview.controller;

import com.oceanview.util.DbConnection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/api/dbtest")
public class DbTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        response.setContentType("text/plain");

        try (Connection connection = DbConnection.getConnection()) {
            response.getWriter().write("DB CONNECTED: " + (connection != null));
        } catch (Exception e) {
            response.setStatus(500);
            response.getWriter().write("DB ERROR: " + e.getMessage());
        }
    }
}