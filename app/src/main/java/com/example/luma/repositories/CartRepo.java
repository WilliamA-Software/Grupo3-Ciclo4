package com.example.luma.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.luma.data.model.CartProduct;
import com.example.luma.data.model.Product;
import java.util.ArrayList;
import java.util.List;

public class CartRepo {

    private MutableLiveData<List<CartProduct>> mutableCart = new MutableLiveData<>();

    public LiveData<List<CartProduct>> getCartList(){
        if (mutableCart.getValue() == null){
            initCart();
        }
        return mutableCart;
    }

    public void initCart() {
        mutableCart.setValue(new ArrayList<CartProduct>());
    }

    public boolean addItem2Cart(Product product){
        if (mutableCart.getValue() == null){
            initCart();
        }
        List<CartProduct> cartProductList = new ArrayList<>(mutableCart.getValue());
        for (CartProduct cartProduct: cartProductList){
            if (cartProduct.getProduct().getNameProduct().equals(product.getNameProduct())){
                int index = cartProductList.indexOf(cartProduct);
                cartProduct.setQuantity(cartProduct.getQuantity() + 1);
                cartProductList.set(index, cartProduct);
            }
        }

        CartProduct cartProduct = new CartProduct(product, 1);
        cartProductList.add(cartProduct);
        mutableCart.setValue(cartProductList);
        return true;
    }
}
