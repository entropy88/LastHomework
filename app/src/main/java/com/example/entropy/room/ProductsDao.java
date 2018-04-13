package com.example.entropy.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ProductsDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    public void addProduct(Product product);

    @Query("select * from products")
    public List<Product> getProducts();

    @Query("select* from products where product_code==:code")
    Product findProductByCode(String code);
}
