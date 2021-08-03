package com.example.luma.data.model;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.luma.ui.home.HomeFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class CartStorage extends Fragment {
    private static final String LIST_KEY = "list_key";
    private SharedPreferences cartStorage;
    private ArrayList<Product> products;

//    public CartStorage(ArrayList<Product> list) {
//        if (cartStorage == null) {
//            cartStorage = getSharedPreferences("CART_STORAGE", MODE_PRIVATE);
//        } else {
//            Gson gson = new Gson();
//            String jsonString = gson.toJson(list);
//            SharedPreferences.Editor editor = cartStorage.edit();
//            editor.putString(LIST_KEY, jsonString);
//            editor.apply();
//        }
//    }

    // Almacenamiento local del carrito de compras
//    public void setCartStorage(ArrayList<Product> list){
//        // Pasar la lista a formato JSON
//        Gson gson = new Gson();
//        String jsonString = gson.toJson(list);
//        cartStorage = getSharedPreferences("STORAGE", MODE_PRIVATE);
//        // Obtengo los datos de usuario si ya estan almacenados previamente
//        String name = storage.getString("NAME1", "NO NAME");
//        SharedPreferences storage = PreferenceManager.getDefaultSharedPreferences();
////        String jsonString = storage.getString("list_key", "");
//    }

//    public static ArrayList<Product> getCartStorage(){
//        Gson gson = new Gson();
//        Type type = new TypeToken<ArrayList<Product>>() {}.getType();
//        list = gson.fromJson(jsonString, type);
//        return list;
//    }

//    document.get("nameProduct").toString(),
//    document.get("descriptionProduct").toString(),
//    document.get("priceProduct").toString(),
//    document.get("quantityProduct").toString(),
//    document.get("imageProduct").toString(),
//    document.get("typeProduct").toString()

//    // solo sirve con actividades y no fragmentos por requerir el CONTEXT
//    public static void setCartStorage(Context context, ArrayList<Product> list){
//        Gson gson = new Gson();
//        String jsonString = gson.toJson(list);
//
//        SharedPreferences storage = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = storage.edit();
//        editor.putString("list_key", jsonString);
//        editor.apply();
//    }
//    public static ArrayList<Product> getCartStorage(Context context){
//        SharedPreferences storage = PreferenceManager.getDefaultSharedPreferences(context);
//        String jsonString = storage.getString("list_key", "");
//        Gson gson = new Gson();
//        Type type = new TypeToken<ArrayList<Product>>() {}.getType();
//        ArrayList<Product> list = gson.fromJson(jsonString, type);
//        return list;
//    }
}
