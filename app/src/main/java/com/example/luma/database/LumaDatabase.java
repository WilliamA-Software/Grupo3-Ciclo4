package com.example.luma.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;

import com.example.luma.database.dao.ProductDao;
import com.example.luma.database.model.Product;
import com.example.luma.database.util.Constant;

@Database(entities = {Product.class},version = 1)
public abstract class LumaDatabase extends RoomDatabase {
    public abstract ProductDao getProductDao();
    private static LumaDatabase lumaDB;

    public static LumaDatabase getInstance(Context context){
        if (lumaDB == null){
            lumaDB = buildDatabaseInstance(context);
        }
        return lumaDB;
    }

    private static LumaDatabase buildDatabaseInstance(Context context){
        //context.deleteDatabase("luma.db");
        return Room.databaseBuilder(context,
                LumaDatabase.class,
                Constant.DB_USER).allowMainThreadQueries().build();
    }
}
