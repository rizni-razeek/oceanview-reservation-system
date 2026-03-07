package com.oceanview.dao;

import com.oceanview.model.Reservation;
import com.oceanview.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReservationDao {

    // Add new reservation
    public void add(Reservation r) throws Exception {

        String sql = "INSERT INTO reservations " +
                "(reservation_no, guest_name, guest_address, contact_no, room_type, check_in, check_out) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, r.getReservationNo());
            ps.setString(2, r.getGuestName());
            ps.setString(3, r.getGuestAddress());
            ps.setString(4, r.getContactNo());
            ps.setString(5, r.getRoomType());
            ps.setDate(6, java.sql.Date.valueOf(r.getCheckIn()));
            ps.setDate(7, java.sql.Date.valueOf(r.getCheckOut()));

            ps.executeUpdate();
        }
    }

    // Get all reservations
    public List<Reservation> findAll() throws Exception {

        List<Reservation> list = new ArrayList<>();

        String sql = "SELECT reservation_no, guest_name, guest_address, contact_no, room_type, check_in, check_out " +
                "FROM reservations ORDER BY created_at DESC";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Reservation r = new Reservation(
                        rs.getString("reservation_no"),
                        rs.getString("guest_name"),
                        rs.getString("guest_address"),
                        rs.getString("contact_no"),
                        rs.getString("room_type"),
                        rs.getDate("check_in").toLocalDate(),
                        rs.getDate("check_out").toLocalDate()
                );

                list.add(r);
            }
        }

        return list;
    }

    // Search reservation by res number or guest name
    public List<Reservation> search(String keyword) throws Exception {

        List<Reservation> list = new ArrayList<>();

        String sql = "SELECT reservation_no, guest_name, guest_address, contact_no, room_type, check_in, check_out " +
                "FROM reservations " +
                "WHERE reservation_no LIKE ? OR guest_name LIKE ? " +
                "ORDER BY created_at DESC";

        String pattern = "%" + keyword + "%";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pattern);
            ps.setString(2, pattern);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Reservation r = new Reservation(
                            rs.getString("reservation_no"),
                            rs.getString("guest_name"),
                            rs.getString("guest_address"),
                            rs.getString("contact_no"),
                            rs.getString("room_type"),
                            rs.getDate("check_in").toLocalDate(),
                            rs.getDate("check_out").toLocalDate()
                    );

                    list.add(r);
                }
            }
        }

        return list;
    }

    // Delete reservation
    public void deleteByReservationNo(String reservationNo) throws Exception {

        String sql = "DELETE FROM reservations WHERE reservation_no = ?";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, reservationNo);
            ps.executeUpdate();
        }
    }

    // Find reservation by reservation number (used for editing)
    public Reservation findByReservationNo(String reservationNo) throws Exception {

        String sql = "SELECT reservation_no, guest_name, guest_address, contact_no, room_type, check_in, check_out " +
                "FROM reservations WHERE reservation_no = ?";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, reservationNo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new Reservation(
                            rs.getString("reservation_no"),
                            rs.getString("guest_name"),
                            rs.getString("guest_address"),
                            rs.getString("contact_no"),
                            rs.getString("room_type"),
                            rs.getDate("check_in").toLocalDate(),
                            rs.getDate("check_out").toLocalDate()
                    );
                }
            }
        }

        return null;
    }

    // Update reservation
    public void update(Reservation r) throws Exception {

        String sql = "UPDATE reservations SET " +
                "guest_name=?, guest_address=?, contact_no=?, room_type=?, check_in=?, check_out=? " +
                "WHERE reservation_no=?";

        try (Connection con = DbConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, r.getGuestName());
            ps.setString(2, r.getGuestAddress());
            ps.setString(3, r.getContactNo());
            ps.setString(4, r.getRoomType());
            ps.setDate(5, java.sql.Date.valueOf(r.getCheckIn()));
            ps.setDate(6, java.sql.Date.valueOf(r.getCheckOut()));
            ps.setString(7, r.getReservationNo());

            ps.executeUpdate();
        }
    }
}