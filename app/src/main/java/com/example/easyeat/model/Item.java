package com.example.easyeat.model;

import com.example.easyeat.Api.Holders.ItemHolder;

import java.io.Serializable;


public class Item implements Serializable
{
    private int id;
    private int categoryId;
    private int subCategoryId;
    private String name;
    private double unitPrice;
    private int url;

    public Item(int id, int categoryId, int subCategoryId, String name, double unitPrice,int url)
    {
        this.id = id;
        this.categoryId = categoryId;
        this.subCategoryId = subCategoryId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.url = url;
    }

    public Item(ItemHolder itemHolder, int url)
    {
        this.id = itemHolder.getId();
        this.categoryId = itemHolder.getCategoryId();
        this.subCategoryId = itemHolder.getSubCategoryId();
        this.name = itemHolder.getName();
        this.unitPrice = itemHolder.getUnitPrice();
        this.url = url;
    }

    public String getCategoryName(){
        String[] mTitleRestaurants = {"all","Molly's Dinner","STARBUCKS","MCDONALD'S","PIZZA HUT"};
        return mTitleRestaurants[this.categoryId-1];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getUrl() {
        return url;
    }

    public void setUrl(int url) {
        this.url = url;
    }
}
