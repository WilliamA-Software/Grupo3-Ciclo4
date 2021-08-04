package com.example.luma.viewmodels;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.luma.data.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<Product> mutableProduct = new MutableLiveData<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<Product> products;
    private MutableLiveData<List<Product>> mutableProductList;

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
                                    document.get("nameProduct").toString(),
                                    document.get("descriptionProduct").toString(),
                                    document.get("priceProduct").toString(),
                                    document.get("quantityProduct").toString(),
                                    document.get("imageProduct").toString(),
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
}
