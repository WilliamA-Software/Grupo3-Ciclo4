package com.example.luma.ui.cart;

import android.os.Binder;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.luma.data.model.Product;
import com.example.luma.databinding.FragmentShoppingcartBinding;
import com.example.luma.ui.home.ProductAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class ShoppingcartFragment extends Fragment {

    private FragmentShoppingcartBinding binding;
    private ProgressBar load;
    private ArrayList<Product> products;
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShoppingcartBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Progress bar
        load = binding.pbCart;

        //Array list products
        products = new ArrayList<>();

        //RecyclerView
        recyclerView = binding.rvProducts;
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //List products with firebase
//        getProducts();

        // List products with SharedPreferences
        listProducts();

        return root;
    }

    private void listProducts() {


        load.setVisibility(View.GONE); // Oculta el icono de carga
        cartAdapter = new CartAdapter(getView(), products);
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
