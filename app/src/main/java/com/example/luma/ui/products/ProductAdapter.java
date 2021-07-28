package com.example.luma.ui.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luma.R;
import com.example.luma.data.model.Product;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    ArrayList<Product> products;
    LayoutInflater inflater;
    //private ItemClickListener clickListener;

    public ProductAdapter(ArrayList<Product> products){
        this.products=products;
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

        TextView name,price;
        ImageView image;
        ProgressBar loading;

        public ProductViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            price = itemView.findViewById(R.id.tv_value);
            image = itemView.findViewById(R.id.img_product);
            loading = itemView.findViewById(R.id.pb_loading_image);
        }

        public void setData(Product product) {
            name.setText(product.getNameProduct());
            price.setText(product.getPriceProduct());
            Picasso.with(image.getContext()).load(product.getImageProduct()).into(image, new Callback() {
                @Override
                public void onSuccess() {
                    loading.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                }
            });
        }
    }/*
    public interface ItemClickListener{
        public void onItemClick(Product product);
    }*/
}
