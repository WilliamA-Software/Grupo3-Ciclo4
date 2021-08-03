package com.example.luma.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luma.R;
import com.example.luma.data.model.Product;
import com.example.luma.ui.products.ProductDetail;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

//    protected ProductAdapter(){
//        super();
//    }

    private ArrayList<Product> products;
    private LayoutInflater inflater;
    private View view;
    public static Product productDetail;
    public ArrayList<Product> cartProducts = null;

    public ProductAdapter(View view, ArrayList<Product> products){
        this.products = products;
        this.cartProducts = products;
        this.view = view;
        //this.clickListener = clickListener;
    }

    @NonNull
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

        public void setData(Product product) {
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
                    productDetail=product;
                    Navigation.findNavController(view).navigate(R.id.nav_prod_detail);
                }
            });

            // Agrega el producto al carrito de compras
            imgBtnShopping.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartProducts.add(product);

                }
            });
        }
    }

    public interface HomeInterface {
        public void addItem(Product product);
        void onItemClick(Product product);
    }
}
