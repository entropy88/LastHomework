package com.example.entropy.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "products")
public class Product {
//    @PrimaryKey
//    private int id;

    @PrimaryKey @NonNull @ColumnInfo(name = "product_code")
    private String code;
    @ColumnInfo(name = "product_info")
    private String product_name;
    @ColumnInfo(name = "product_ingredients")
    private String ingredients_text;



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String info) {
        this.product_name = product_name;
    }

    public String getIngredients_text() {
        return ingredients_text;
    }

    public void setIngredients_text(String ingredients_text) {
        this.ingredients_text = ingredients_text;
    }
}
