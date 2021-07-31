package com.example.luma.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luma.R;
import com.example.luma.data.model.Product;
import com.example.luma.ui.products.ProductDetail;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private ArrayList<Product> products;
    private LayoutInflater inflater;
    private View view;
    public static Product productDetail;
    //private ItemClickListener clickListener;

    public ProductAdapter(View view, ArrayList<Product> products){
        this.products=products;
        this.view = view;
        //this.clickListener = clickListener;
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
        }
    }/*
    public interface ItemClickListener{
        public void onItemClick(Product product);
    }*/
}
