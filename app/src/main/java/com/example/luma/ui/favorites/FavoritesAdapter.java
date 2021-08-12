package com.example.luma.ui.favorites;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luma.R;
import com.example.luma.data.model.Favorite;
import com.example.luma.data.model.Product;
import com.example.luma.ui.home.HomeAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    private ArrayList<Favorite> favorites;
    private View view;

    //Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Declarar SharePreferences
    private SharedPreferences storage;

    public FavoritesAdapter(View view, ArrayList<Favorite> favorites) {
        this.favorites=favorites;
        this.view = view;
        storage = view.getContext().getSharedPreferences("STORAGE", view.getContext().MODE_PRIVATE);
    }

    @NonNull
    @NotNull
    @Override
    public FavoritesAdapter.FavoritesViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_product_individual,null,false);
        return new FavoritesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FavoritesAdapter.FavoritesViewHolder holder, int position) {
        holder.setData(favorites.get(position));
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public class FavoritesViewHolder extends RecyclerView.ViewHolder{

        TextView name, price;
        ProgressBar loading;
        ImageButton image_main, imgBtnFavorite, imgBtnShopping;

        public FavoritesViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            price = itemView.findViewById(R.id.tv_value);
            image_main = itemView.findViewById(R.id.img_product);
            imgBtnFavorite = itemView.findViewById(R.id.imgbtn_favorite);
            imgBtnShopping = itemView.findViewById(R.id.imgbtn_shopping);
            loading = itemView.findViewById(R.id.pb_loading_image);
        }

        public void setData(Favorite favorite) {
            name.setText(favorite.getNameProduct());
            price.setText(favorite.getPriceProduct());
            Picasso.with(image_main.getContext()).load(favorite.getImageProduct()).into(image_main, new Callback() {
                @Override
                public void onSuccess() {
                    loading.setVisibility(View.GONE);
                }

                @Override
                public void onError() {

                }
            });
            image_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Setea el productDetail para que pueda ser accedido desde los detalles
                    Product product = new Product(favorite.getIdProduct(),
                      favorite.getNameProduct(),
                      favorite.getDescriptionProduct(),
                      favorite.getPriceProduct(),
                      favorite.getQuantityProduct(),
                      favorite.getImageProduct(),
                      favorite.getLatitudeProduct(),
                      favorite.getLongitudeProduct(),
                      favorite.getTypeProduct()
                    );
                    HomeAdapter.productDetail = product;
                    Navigation.findNavController(view).navigate(R.id.nav_prod_detail);
                }
            });
            imgBtnFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String code = storage.getString("USERCODE", "");
                    Product product2 = new Product(favorite.getIdProduct(),
                            favorite.getNameProduct(),
                            favorite.getDescriptionProduct(),
                            favorite.getPriceProduct(),
                            favorite.getQuantityProduct(),
                            favorite.getImageProduct(),
                            favorite.getLatitudeProduct(),
                            favorite.getLongitudeProduct(),
                            favorite.getTypeProduct()
                    );
                    db.collection("user").document(code).collection("favorites").document(favorite.getIdProduct()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(v.getContext(), "Artucilo eliminado de favoritos", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view).navigate(R.id.nav_favorites);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(v.getContext(), "Error al eliminar de favoritos", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

}
