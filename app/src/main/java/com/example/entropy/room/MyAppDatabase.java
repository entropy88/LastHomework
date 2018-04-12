package com.example.entropy.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Product.class},version = 2)
public abstract class MyAppDatabase extends RoomDatabase {
  public abstract ProductsDao productsDao();
}
