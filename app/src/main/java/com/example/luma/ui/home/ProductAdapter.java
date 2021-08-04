package com.example.luma.ui.home;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luma.data.model.Product;
import com.example.luma.databinding.FrameProductIndividualBinding;

public class ProductAdapter extends ListAdapter<Product, ProductAdapter.ProductViewHolder> {

    ProductInterface productInterface;

    public ProductAdapter(ProductInterface productInterface) {
        super(Product.itemCallback);
        this.productInterface = productInterface;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FrameProductIndividualBinding binding = FrameProductIndividualBinding.inflate(layoutInflater, parent, false);
        binding.setProductInterface(productInterface);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = getItem(position);
        holder.binding.setProduct(product);
        holder.binding.executePendingBindings();

        if (holder.binding.pbLoadingImage.getVisibility() == View.VISIBLE)
            holder.binding.pbLoadingImage.setVisibility(View.GONE);
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        FrameProductIndividualBinding binding;

        public ProductViewHolder(FrameProductIndividualBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ProductInterface {
        void addItem(Product product);
        void onItemClick(Product product);
        void loadBar(Product product);
    }
}
