package com.example.luma.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.luma.data.model.CartProduct;
import com.example.luma.data.model.Product;
import java.util.ArrayList;
import java.util.List;

public class CartRepo {

    private MutableLiveData<List<CartProduct>> mutableCart = new MutableLiveData<>();
    private MutableLiveData<Integer> mutableTotalPrice = new MutableLiveData<>();

    public LiveData<List<CartProduct>> getCartList(){
        if (mutableCart.getValue() == null){
            initCart();
        }
        return mutableCart;
    }

    public void initCart() {
        mutableCart.setValue(new ArrayList<CartProduct>());
        calculateCartTotal();
    }

    public boolean addItem2Cart(Product product){
        if (mutableCart.getValue() == null){
            initCart();
        }
        List<CartProduct> cartProductList = new ArrayList<>(mutableCart.getValue());
        // Recorrer la lista del carrito en busca de productos repetidos
        for (CartProduct cartProduct: cartProductList){
            if (cartProduct.getProduct().getNameProduct().equals(product.getNameProduct())){
                int index = cartProductList.indexOf(cartProduct);
                cartProduct.setQuantity(cartProduct.getQuantity() + 1);
                cartProductList.set(index, cartProduct);
                mutableCart.setValue(cartProductList);
                calculateCartTotal();
                return true;
            }
        }
        CartProduct cartProduct = new CartProduct(product, 1);
        cartProductList.add(cartProduct);
        mutableCart.setValue(cartProductList);
        calculateCartTotal();
        return true;
    }

    public void removeProductFromCart(CartProduct cartProduct) {
        if (mutableCart.getValue() == null){
            return;
        }
        List<CartProduct> cartProductList = new ArrayList<>(mutableCart.getValue());
        cartProductList.remove(cartProduct);
        mutableCart.setValue(cartProductList);
        calculateCartTotal();
    }

    public void changeQuantity(CartProduct cartProduct, int quatity) {
        if (mutableCart.getValue() == null) return;

        List<CartProduct> cartProductList= new ArrayList<>(mutableCart.getValue());

        CartProduct updateValue = new CartProduct(cartProduct.getProduct(), quatity);
        cartProductList.set(cartProductList.indexOf(cartProduct), updateValue);

        mutableCart.setValue(cartProductList);
        calculateCartTotal();
    }

    public void calculateCartTotal(){
        if (mutableCart.getValue() == null) return;
        Integer total = 0;
        List<CartProduct> cartProductList = mutableCart.getValue();
        for (CartProduct cartProduct: cartProductList){
            total += Integer.parseInt(cartProduct.getProduct().getPriceProduct()) * cartProduct.getQuantity();
        }
        mutableTotalPrice.setValue(total);
    }

    public LiveData<Integer> getTotalPrice(){
        if (mutableTotalPrice.getValue() == null){
            mutableTotalPrice.setValue(0);
        }
        return mutableTotalPrice;
    };
}
