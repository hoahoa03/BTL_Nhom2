package com.example.btl_nhom2.models;

public class Category {
    private int ID;
    private String categoryName;

    public Category(int ID, String categoryName) {
        this.ID = ID;
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getID() {
        return ID;
    }
}
