package com.example.luma.viewmodels;

import android.content.SharedPreferences;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.luma.data.model.CartProduct;
import com.example.luma.data.model.Product;
import com.example.luma.repositories.CartRepo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<Product> mutableProduct = new MutableLiveData<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<Product> products;
    private MutableLiveData<List<Product>> mutableProductList;
    private SharedPreferences storage;
    private Boolean add = false;
    CartRepo cartRepo = new CartRepo();

    public LiveData<List<Product>> getProducts() {
        if (mutableProductList == null) {
            mutableProductList = new MutableLiveData<>();
            products = new ArrayList<>();
            db.collection("product").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Product product = new Product(
                                    document.getId(),
                                    document.get("nameProduct").toString(),
                                    document.get("descriptionProduct").toString(),
                                    document.get("priceProduct").toString(),
                                    document.get("quantityProduct").toString(),
                                    document.get("imageProduct").toString(),
                                    document.get("latitudeProduct").toString(),
                                    document.get("longitudeProduct").toString(),
                                    document.get("typeProduct").toString()
                            );
                            products.add(product);
                        }
                        mutableProductList.setValue(products);
                    }
                }
            });
        }
        return mutableProductList;
    }

    public void setProduct(Product product) {
        mutableProduct.setValue(product);
    }

    public LiveData<Product> getProduct(){
        return mutableProduct;
    }

    public LiveData<List<CartProduct>> getCartList(){
        return cartRepo.getCartList();
    }

    public boolean addProduct2Cart(Product product){
        return cartRepo.addItem2Cart(product);
    }

    public boolean addProduct2Fav(String code, @NotNull Product product) {

        Product product2 = new Product(
                product.getIdProduct(),
                product.getNameProduct(),
                product.getDescriptionProduct(),
                product.getPriceProduct(),
                product.getQuantityProduct(),
                product.getImageProduct(),
                product.getLatitudeProduct(),
                product.getLongitudeProduct(),
                product.getTypeProduct()
        );
        db.collection("user").document(code).collection("favorites").document(product.getIdProduct()).set(product2).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                add = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                add = false;            }
        });
        return add;
    }

    public void removeProductFromCart(CartProduct cartProduct) {
        cartRepo.removeProductFromCart(cartProduct);
    }

    public void changeQuantity(CartProduct cartProduct, int quatity){
        cartRepo.changeQuantity(cartProduct, quatity);
    }

    public LiveData<Integer> getTotalPrice(){
        return cartRepo.getTotalPrice();
    };

    public void resetCart(){
        cartRepo.initCart();
    }

}
