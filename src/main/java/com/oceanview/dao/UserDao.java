package com.oceanview.dao;

import com.oceanview.model.User;
import com.oceanview.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {

    public User findByCredentials(String username, String password) throws Exception {
        String sql = "SELECT id, username, role FROM users WHERE username = ? AND password_hash = ?";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("role")
                    );
                }
            }
        }
        return null;
    }
}