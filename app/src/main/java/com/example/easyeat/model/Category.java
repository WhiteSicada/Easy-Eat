package com.example.easyeat.model;

import com.example.easyeat.api.holders.CategoryHolder;

import java.io.Serializable;



public class Category implements Serializable
{
    private int id;
    private String name;
    private int resourceId;
    private double x;
    private double y;

    public Category(CategoryHolder categoryHolder, int resourceId) {
        this.id = categoryHolder.getId();
        this.name = categoryHolder.getName();
        this.resourceId = resourceId;
        this.x = categoryHolder.getX();
        this.y = categoryHolder.getY();
    }

    public Category(int id, String name, int resourceId)
    {
        this.id = id;
        this.name = name;
        this.resourceId = resourceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
