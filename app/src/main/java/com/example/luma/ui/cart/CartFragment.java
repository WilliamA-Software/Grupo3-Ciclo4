package com.example.luma.ui.cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luma.data.model.CartProduct;
import com.example.luma.data.model.Product;
import com.example.luma.databinding.FragmentShoppingcartBinding;

import java.util.ArrayList;
import java.util.Objects;

public class CartFragment extends Fragment {

    private FragmentShoppingcartBinding binding;
    private ProgressBar load;
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShoppingcartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Progress bar
        load = binding.pbCart;

        //RecyclerView
        recyclerView = binding.rvCartProducts;
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return root;
    }

    private void listProducts(ArrayList<Product> cartProducts) {

        load.setVisibility(View.GONE); // Oculta el icono de carga
        cartAdapter = new CartAdapter(getView(), cartProducts);
        recyclerView.setAdapter(cartAdapter);
    }

}
