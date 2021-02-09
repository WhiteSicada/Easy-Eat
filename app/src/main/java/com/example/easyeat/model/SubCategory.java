package com.example.easyeat.model;

import com.example.easyeat.Api.Holders.SubCategoryHolder;

import java.io.Serializable;



public class SubCategory implements Serializable
{
    private int id;
    private int categoryId;
    private String name;
    private boolean isSelected = false;
    private boolean isExpanded = true;

    public SubCategory(SubCategory subCategory)
    {
        this.id = subCategory.id;
        this.categoryId = subCategory.categoryId;
        this.name = subCategory.name;
        this.isSelected = subCategory.isSelected;
    }

    public SubCategory(SubCategoryHolder subCategoryHolder)
    {
        this.id = subCategoryHolder.getId();
        this.categoryId = subCategoryHolder.getCategoryId();
        this.name = subCategoryHolder.getName();
    }

    public SubCategory(int id, int categoryId, String name)
    {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public void setIsExpanded()
    {
        isExpanded= (!isExpanded);
    }
}
