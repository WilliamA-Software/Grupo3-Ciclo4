package com.example.luma.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.luma.data.model.CartProduct;
import com.example.luma.data.model.Product;
import com.example.luma.repositories.CartRepo;
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

    public LiveData<List<CartProduct>> getCartList(){
        return cartRepo.getCartList();
    }

    public boolean addProduct2Cart(Product product){
        return cartRepo.addItem2Cart(product);
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
