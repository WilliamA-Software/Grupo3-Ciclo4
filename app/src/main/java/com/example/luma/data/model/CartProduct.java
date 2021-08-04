package com.example.luma.data.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import org.jetbrains.annotations.NotNull;


public class CartProduct {
    private Product product;
    private int quantity;

    public CartProduct(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @NotNull
    @Override
    public String toString() {
        return "CartProduct{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartProduct that = (CartProduct) o;
        return getQuantity() == that.getQuantity() &&
                getProduct().equals(that.getProduct());
    }

    public static DiffUtil.ItemCallback<CartProduct> itemCallback = new DiffUtil.ItemCallback<CartProduct>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull CartProduct oldItem, @NonNull @NotNull CartProduct newItem) {
            return oldItem.getProduct().equals(newItem.getProduct());
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull CartProduct oldItem, @NonNull @NotNull CartProduct newItem) {
            return oldItem.equals(newItem);
        }
    };

    //    public CartProduct(ArrayList<Product> list) {
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
