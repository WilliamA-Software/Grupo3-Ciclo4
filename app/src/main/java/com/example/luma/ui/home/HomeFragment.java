package com.example.luma.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luma.data.model.Product;
import com.example.luma.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener{

    private FragmentHomeBinding binding;
    private EditText et_code,
            et_name,
            et_description,
            et_price,
            et_quantity,
            et_image;

    private String typeAux,
            setType;

    private Button btn_insert,
            btn_search,
            btn_update,
            btn_delete;

    private ProgressBar load;
    private Spinner et_type;
    private SearchView sv_product;
    private ArrayList<Product> products;
    private RecyclerView recyclerView;

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

        //recyclerView
        recyclerView = binding.rcvProduct;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false));

        //list products
        getProducts();

        //Search product
        sv_product.setOnQueryTextListener(this);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getProducts() {
        db.collection("product").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()&& !task.getResult().isEmpty()) {
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
                } else {
                    Toast.makeText(getActivity(), "Error articulo no encontrado", Toast.LENGTH_SHORT).show();
                }
                load.setVisibility(View.GONE);

                ProductAdapter adapter = new ProductAdapter(products);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        ArrayList<Product> productSearch = new ArrayList<>();
        db.collection("product").orderBy("nameProduct").startAt(query).endAt(query+'\uf8ff').get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()&& !task.getResult().isEmpty()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Product product = new Product(
                                document.get("nameProduct").toString(),
                                document.get("descriptionProduct").toString(),
                                document.get("priceProduct").toString(),
                                document.get("quantityProduct").toString(),
                                document.get("imageProduct").toString(),
                                document.get("typeProduct").toString()
                        );
                        productSearch.add(product);
                    }
                } else {
                    Toast.makeText(getActivity(), "Error articulo no encontrado", Toast.LENGTH_SHORT).show();
                    productSearch.addAll(products);
                }

                ProductAdapter adapter = new ProductAdapter(productSearch);
                recyclerView.setAdapter(adapter);
            }
        });

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}