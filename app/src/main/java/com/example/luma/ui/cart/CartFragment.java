package com.example.luma.ui.cart;

import android.os.Bundle;
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

public class CartFragment extends Fragment {

    private ProgressBar load;
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    HomeViewModel homeViewModel;
    FragmentShoppingcartBinding binding;

    public CartFragment(FragmentShoppingcartBinding binding, RecyclerView recyclerView) {
        this.binding = binding;
        this.recyclerView = recyclerView;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentShoppingcartBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
////        //Progress bar
////        load = binding.pbCart;
//
        //RecyclerView
        recyclerView = binding.rvCartProducts;
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        return root;
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CartAdapter cartAdapter = new CartAdapter();
        binding.rvCartProducts.setAdapter(cartAdapter);

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.getCart().observe(getViewLifecycleOwner(), new Observer<List<CartProduct>>() {
            @Override
            public void onChanged(List<CartProduct> cartProducts) {
                cartAdapter.submitList(cartProducts);
            }
        });
    }

    private void listProducts(ArrayList<Product> cartProducts) {
        load.setVisibility(View.GONE); // Oculta el icono de carga
    }

}
