package com.example.easyeat.model;


import java.io.Serializable;

public class Order implements Serializable
{
    private Item item;
    private int quantity;
    private double extendedPrice = 0.0;

    public Order(Item item, int quantity)
    {
        this.item = item;
        this.quantity = quantity;
        this.extendedPrice = item.getUnitPrice() * quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getExtendedPrice() {
        return extendedPrice;
    }

    public void setExtendedPrice(double extendedPrice) {
        this.extendedPrice = extendedPrice;
    }
}