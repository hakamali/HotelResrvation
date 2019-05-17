package com.app.reservation.hotel.hotelreservationproject.Model;


public class BookingModel {

    String uid, username, phone, cnic, mealplan, email, bid, noOfRooms, checkinDate, Checkout;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getMealplan() {
        return mealplan;
    }

    public void setMealplan(String mealplan) {
        this.mealplan = mealplan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(String noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    public String getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    public String getCheckOutDate() {
        return Checkout;
    }

    public void setCheckOutDate(String checkOutDate) {
        Checkout = checkOutDate;
    }
}
