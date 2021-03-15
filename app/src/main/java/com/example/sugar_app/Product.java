package com.example.sugar_app;

public class Product {

    String ProductName;
    String Sugar;
    String BarCode;
    String Kcal;

    public Product(){}

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getSugar() {
        return Sugar;
    }

    public void setSugar(String sugar) {
        Sugar = sugar;
    }

    public String getBarCode() {
        return BarCode;
    }

    public void setBarCode(String barCode) {
        BarCode = barCode;
    }

    public String getKcal() { return Kcal; }

    public void setKcal(String kcal) { Kcal = kcal; }
}
