package com.example.luma.ui.cart;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luma.R;
import com.example.luma.data.model.CartProduct;
import com.example.luma.data.model.Product;
import com.example.luma.databinding.FragmentShoppingcartBinding;
import com.example.luma.viewmodels.HomeViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartFragment extends Fragment implements CartAdapter.CartInterface {

    private ProgressBar load;
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    HomeViewModel homeViewModel;
    FragmentShoppingcartBinding binding;
    CartAdapter.CartInterface cart = this;

    public CartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentShoppingcartBinding.inflate(inflater, container, false);

        recyclerView = binding.rvCartProducts;
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.pbCart.setVisibility(View.GONE);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final CartAdapter cartAdapter = new CartAdapter(cart);
        binding.rvCartProducts.setAdapter(cartAdapter);

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.getCartList().observe(getViewLifecycleOwner(), new Observer<List<CartProduct>>() {
            @Override
            public void onChanged(List<CartProduct> cartProducts) {
                cartAdapter.submitList(cartProducts);
            }
        });
//define los valores del Sub-Total y el Total del carrito de compras y los actualiza en tiempo real
        homeViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer aInt) {
                binding.varTotal2pay.setText("$ " + aInt.toString());
                binding.varCarTotal.setText("$ " + aInt.toString());
            }
        });

    }

    @Override
    public void deleteProduct(CartProduct cartProduct) {
        Log.d("DElETE", "DELETE: " + cartProduct.getProduct().getNameProduct());
        homeViewModel.removeProductFromCart(cartProduct);
    }

    @Override
    public void changeQuantity(CartProduct cartProduct, int quantity) {
        homeViewModel.changeQuantity(cartProduct, quantity);
    }
}
