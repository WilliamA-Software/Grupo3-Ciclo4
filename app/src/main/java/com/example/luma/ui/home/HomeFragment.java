package com.example.luma.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.example.luma.R;
import com.example.luma.data.model.Product;
import com.example.luma.databinding.ActivityDrawerBinding;
import com.example.luma.databinding.FragmentHomeBinding;
import com.example.luma.viewmodels.HomeViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.List;

//public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener, ProductAdapter.HomeInterface{
public class HomeFragment extends Fragment implements ProductAdapter.ProductInterface{

    private FragmentHomeBinding binding;
    private ProductAdapter productAdapter;
    private ProgressBar load;
    private SearchView sv_product;
    private HomeViewModel homeViewModel;
    private NavController navController;
    private SharedPreferences storage;
    private ActivityDrawerBinding activityDrawerBinding;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Progress bar
        load = binding.pbProduct;

//        //Search View
//        sv_product = binding.svProduct;

        //Search product
//        sv_product.setOnQueryTextListener(this);

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
        storage = view.getContext().getSharedPreferences("STORAGE", view.getContext().MODE_PRIVATE);
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

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
        boolean isAdded = homeViewModel.addProduct2Cart(product);
        if (isAdded) {
            Snackbar snackbar = Snackbar.make(requireView(), product.getNameProduct() + " " + getString(R.string.added2cart), Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(getResources().getColor(R.color.white));
            snackbar.setAction("Checkout", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            navController.navigate(R.id.action_nav_home_to_nav_shopping_cart);
                        }
                    }).show();
        }
    }

    @Override
    public void addFav(Product product) {
        String code = storage.getString("USERCODE", "");
        boolean isAdded = homeViewModel.addProduct2Fav(code, product);
        if (isAdded) {
            Snackbar snackbar = Snackbar.make(requireView(), product.getNameProduct() + " " + getString(R.string.added2fav), Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(getResources().getColor(R.color.white));
            snackbar.show();
        }
    }

    @Override
    public void onItemClick(Product product) {
        homeViewModel.setProduct(product);
        navController.navigate(R.id.action_nav_home_to_nav_prod_detail);
    }
}