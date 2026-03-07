package com.oceanview.model;

import java.time.LocalDate;

public class Reservation {
    private final String reservationNo;
    private final String guestName;
    private final String guestAddress;
    private final String contactNo;
    private final String roomType;
    private final LocalDate checkIn;
    private final LocalDate checkOut;

    public Reservation(String reservationNo, String guestName, String guestAddress,
                       String contactNo, String roomType, LocalDate checkIn, LocalDate checkOut) {
        this.reservationNo = reservationNo;
        this.guestName = guestName;
        this.guestAddress = guestAddress;
        this.contactNo = contactNo;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public String getReservationNo() { return reservationNo; }
    public String getGuestName() { return guestName; }
    public String getGuestAddress() { return guestAddress; }
    public String getContactNo() { return contactNo; }
    public String getRoomType() { return roomType; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }
}