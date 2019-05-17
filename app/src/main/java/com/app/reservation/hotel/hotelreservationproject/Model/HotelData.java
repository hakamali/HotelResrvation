package com.app.reservation.hotel.hotelreservationproject.Model;


public class HotelData
{

    String id;
    String hotelname;
    String img;
    String roomcharges;
    String mealcharges;
    String roomavailable;
    String city;
    String address;
    String contact;
    String longitude;
    String latitude;

    public HotelData() {

    }

    public HotelData(String id, String hotelname, String img,
                     String roomcharges, String mealcharges,
                     String roomavailable, String city, String address,
                     String contact, String longitude, String latitude,
                     String services) {
        this.id = id;
        this.hotelname = hotelname;
        this.img = img;
        this.roomcharges = roomcharges;
        this.mealcharges = mealcharges;
        this.roomavailable = roomavailable;
        this.city = city;
        this.address = address;
        this.contact = contact;
        this.longitude = longitude;
        this.latitude = latitude;
        this.services = services;
    }

    public String getServices() {

        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    String services;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHotelname() {
        return hotelname;
    }

    public void setHotelname(String hotelname) {
        this.hotelname = hotelname;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getRoomcharges() {
        return roomcharges;
    }

    public void setRoomcharges(String roomcharges) {
        this.roomcharges = roomcharges;
    }

    public String getMealcharges() {
        return mealcharges;
    }

    public void setMealcharges(String mealcharges) {
        this.mealcharges = mealcharges;
    }

    public String getRoomavailable() {
        return roomavailable;
    }

    public void setRoomavailable(String roomavailable) {
        this.roomavailable = roomavailable;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
