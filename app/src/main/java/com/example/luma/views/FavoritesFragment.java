package com.example.luma.views;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luma.adapters.FavoritesAdapter;
import com.example.luma.data.model.Product;
import com.example.luma.databinding.FragmentFavoritesBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;

    private RecyclerView recyclerView;
    private ProgressBar load;
    private ArrayList<Product> favorites;
    private FavoritesAdapter favoritesAdapter;
    private NavController navController;
    private String code;

    //Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Declarar SharePreferences
    private SharedPreferences storage;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Progress bar
        load = binding.pbFavorites;

        //Array list products
        favorites = new ArrayList<>();

        //RecyclerView
        recyclerView = binding.rcvFavorites;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false));
        storage = getActivity().getSharedPreferences("STORAGE", getContext().MODE_PRIVATE);
        code = storage.getString("USERCODE", "");


        return root;
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favoritesAdapter = new FavoritesAdapter(getView(), favorites);
        binding.rcvFavorites.setAdapter(favoritesAdapter);

        getFavorites();

        navController = Navigation.findNavController(view);

    }

    private void getFavorites() {
//        String code = storage.getString("USERCODE", "");
        db.collection("user").document(code).collection("favorites").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()&& !task.getResult().isEmpty()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        db.collection("product").document(document.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document2 = task.getResult();
                                    if (document.exists()) {
                                        Product favorite = new Product(
                                                document2.getId(),
                                                document2.get("nameProduct").toString(),
                                                document2.get("descriptionProduct").toString(),
                                                document2.get("priceProduct").toString(),
                                                document2.get("quantityProduct").toString(),
                                                document2.get("imageProduct").toString(),
                                                document2.get("latitudeProduct").toString(),
                                                document2.get("longitudeProduct").toString(),
                                                document2.get("typeProduct").toString()
                                        );
                                        favorites.add(favorite);
                                    }
                                    load.setVisibility(View.GONE);
//                                    favoritesAdapter = new FavoritesAdapter(getView(), favorites);
                                    recyclerView.setAdapter(favoritesAdapter);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}