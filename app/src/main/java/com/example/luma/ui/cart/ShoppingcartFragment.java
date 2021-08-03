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

public class ShoppingcartFragment extends Fragment {

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

        //List products with firebase
//        getProducts();

        // List products with SharedPreferences
//        listProducts();

        return root;
    }

    private void listProducts(ArrayList<Product> cartProducts) {

        load.setVisibility(View.GONE); // Oculta el icono de carga
        cartAdapter = new CartAdapter(getView(), cartProducts);
        recyclerView.setAdapter(cartAdapter);
    }

//    private void getProducts() {
//        db.collection("product").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()&& !task.getResult().isEmpty()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Product product = new Product(
//                                document.get("nameProduct").toString(),
//                                document.get("descriptionProduct").toString(),
//                                document.get("priceProduct").toString(),
//                                document.get("quantityProduct").toString(),
//                                document.get("imageProduct").toString(),
//                                document.get("typeProduct").toString()
//                        );
//                        products.add(product);
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "Error articulo no encontrado", Toast.LENGTH_SHORT).show();
//                }
//                load.setVisibility(View.GONE);
//
//                productAdapter= new ProductAdapter(getView(), products);
//                recyclerView.setAdapter(productAdapter);
//
//
//            }
//        });
//    }
}
