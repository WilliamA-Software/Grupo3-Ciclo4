package com.example.luma.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.luma.data.model.Product;
import com.example.luma.databinding.FragmentHomeBinding;
import com.example.luma.viewmodels.HomeViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

//public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener, ProductAdapter.HomeInterface{
public class HomeFragment extends Fragment implements ProductAdapter.ProductInterface{

    private FragmentHomeBinding binding;
    private ProductAdapter productAdapter;
    private ProgressBar load;
    private SearchView sv_product;
    private ArrayList<Product> products;
    private RecyclerView recyclerView;
    public ArrayList<Product> cartProducts;
    private HomeViewModel homeViewModel;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Progress bar
        load = binding.pbProduct;

        //Search View
        sv_product = binding.svProduct;

        //Array list products
        products = new ArrayList<>();
        cartProducts = new ArrayList<>();

        //RecyclerView
//        recyclerView = binding.rcvProduct;
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false));

        //List products
//        getProducts();

        //Search product
//        sv_product.setOnQueryTextListener(this);

        // Start sharedpreference Storage
//        CartProduct.setCartStorage(cartProducts);


        return root;
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productAdapter = new ProductAdapter(this);
        binding.rcvProduct.setAdapter(productAdapter);

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.getProducts().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                productAdapter.submitList(products);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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
////                recyclerView.setAdapter(productAdapter);
//
//
//            }
//        });
//    }


//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        ArrayList<Product> productSearch = new ArrayList<>();
//        db.collection("product").orderBy("nameProduct").startAt(query).endAt(query+'\uf8ff').get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
//                        productSearch.add(product);
//                    }
//                } else {
//                    Toast.makeText(getActivity(), "Error articulo no encontrado", Toast.LENGTH_SHORT).show();
//                    productSearch.addAll(products);
//                }
//
//                productAdapter = new ProductAdapter(getView(),productSearch);
//                recyclerView.setAdapter(productAdapter);
//            }
//        });
//
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//        return false;
//    }

    @Override
    public void addItem(Product product) {
        Log.d("CART DEBUGGER ---->", "addItem: " + product.getNameProduct() + product.getQuantityProduct());
    }

    @Override
    public void onItemClick(Product product) {
        homeViewModel.setProduct(product);
    }

    @Override
    public void loadBar(Product product) {
        load.setVisibility(View.GONE);
    }
}