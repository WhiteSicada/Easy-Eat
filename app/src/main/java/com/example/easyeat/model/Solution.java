package com.example.easyeat.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class Solution implements Serializable
{
    private Category category;
    private List<SubCategory> subCategoryList;
    private List<Item> itemList;
    private Map<SubCategory, ArrayList<Item>> itemMap;

    public Solution(Category category, List<SubCategory> subCategoryList, List<Item> itemList, Map<SubCategory, ArrayList<Item>> itemMap)
    {
        this.category = category;
        this.subCategoryList = subCategoryList;
        this.itemList = itemList;
        this.itemMap = itemMap;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<SubCategory> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<SubCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }



    public Map<SubCategory, ArrayList<Item>> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<SubCategory, ArrayList<Item>> itemMap) {
        this.itemMap = itemMap;
    }
}
