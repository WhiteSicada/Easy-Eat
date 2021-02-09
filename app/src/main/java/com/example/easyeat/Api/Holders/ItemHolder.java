package com.example.easyeat.Api.Holders;

public class ItemHolder {
    private int id;
    private int categoryId;
    private int subCategoryId;
    private String name;
    private double unitPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getSubCategoryId() {
        return subCategoryId;
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
}
