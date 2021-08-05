package com.example.luma.ui.home;

import android.content.SharedPreferences;
import android.util.Log;
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
import com.example.luma.databinding.FragmentFavoritesBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ProductViewHolder> {

    private ArrayList<Favorite> products;
    private LayoutInflater inflater;
    private View view;
    public static Product productDetail;

    //Firestore
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Declarar SharePreferences
    private SharedPreferences storage;

    public HomeAdapter(View view, ArrayList<Favorite> products){
        this.products=products;
        this.view = view;
        storage = view.getContext().getSharedPreferences("STORAGE", view.getContext().MODE_PRIVATE);
    }

    @NonNull
    @NotNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_product_individual,null,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductViewHolder holder, int position) {
        holder.setData(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView name, price;
        ProgressBar loading;
        ImageButton image_main, imgBtnFavorite, imgBtnShopping;

        public ProductViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            price = itemView.findViewById(R.id.tv_value);
            image_main = itemView.findViewById(R.id.img_product);
            imgBtnFavorite = itemView.findViewById(R.id.imgbtn_favorite);
            imgBtnShopping = itemView.findViewById(R.id.imgbtn_shopping);
            loading = itemView.findViewById(R.id.pb_loading_image);
        }

        public void setData(Favorite product) {
            name.setText(product.getNameProduct());
            price.setText(product.getPriceProduct());
            Picasso.with(image_main.getContext()).load(product.getImageProduct()).into(image_main, new Callback() {
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
                    Product product2 = new Product(
                            product.getNameProduct(),
                            product.getDescriptionProduct(),
                            product.getPriceProduct(),
                            product.getQuantityProduct(),
                            product.getImageProduct(),
                            product.getLatitudeProduct(),
                            product.getLongitudeProduct(),
                            product.getTypeProduct()
                    );
                    productDetail=product2;
                    Navigation.findNavController(view).navigate(R.id.nav_prod_detail);
                }
            });
            imgBtnFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String code = storage.getString("USERCODE", "");
                    Product product2 = new Product(
                            product.getNameProduct(),
                            product.getDescriptionProduct(),
                            product.getPriceProduct(),
                            product.getQuantityProduct(),
                            product.getImageProduct(),
                            product.getLatitudeProduct(),
                            product.getLongitudeProduct(),
                            product.getTypeProduct()
                    );
                    db.collection("user").document(code).collection("favorites").document(product.getIdProduct()).set(product2).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(v.getContext(), "Artucilo agregado a favoritos", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(v.getContext(), "Error articulo no agregado", Toast.LENGTH_SHORT).show();
                                }
                    });
                }
            });
        }
    }
}
