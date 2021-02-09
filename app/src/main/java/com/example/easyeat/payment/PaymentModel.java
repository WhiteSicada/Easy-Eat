package com.example.easyeat.payment;

import java.io.Serializable;

public class PaymentModel implements Serializable {


    private int id;
    private String restaurant;
    private String adresse;
    private String customer;
    private String price;
    private String comment;
    private String status ="On going";

    public PaymentModel(String restaurant, String adresse, String customer, String price, String comment) {
        this.restaurant = restaurant;
        this.adresse = adresse;
        this.customer = customer;
        this.price = price;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
