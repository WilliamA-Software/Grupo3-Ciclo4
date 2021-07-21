package com.example.luma.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.luma.database.model.Product;
import com.example.luma.database.util.Constant;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM "+ Constant.TABLE_NAME_USER)
    List<Product> GetProducts();

    @Query("SELECT * FROM " + Constant.TABLE_NAME_USER + " WHERE IdProduct = :idProduct or NameProduct = :nameProduct")
    Product GetProduct(String idProduct, String nameProduct);

    @Insert
    long insertProduct(Product product);

    @Update
    int updateProduct(Product product);

    @Delete
    int deleteProduct(Product product);
}
