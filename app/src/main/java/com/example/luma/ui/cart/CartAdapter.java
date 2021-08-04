package com.example.luma.ui.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.example.luma.R;
import com.example.luma.data.model.CartProduct;
import com.example.luma.data.model.Product;
import com.example.luma.databinding.FragmentShoppingcartBinding;
import com.example.luma.databinding.FrameProductIndividualBinding;
import com.example.luma.databinding.FrameShoppingcartProdIndividualBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class CartAdapter extends ListAdapter<CartProduct, CartAdapter.CartViewHolder> {

    public CartAdapter() {
        super(CartProduct.itemCallback);
    }

    @NonNull
    @NotNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FrameShoppingcartProdIndividualBinding binding = FrameShoppingcartProdIndividualBinding.inflate(layoutInflater, parent,false);
        return new CartViewHolder(binding);
    }

    // Fill data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull @NotNull CartViewHolder holder, int position) {
        holder.binding.setCartProduct(getItem(position));
        holder.binding.executePendingBindings();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        FrameShoppingcartProdIndividualBinding binding;

        public CartViewHolder(@NonNull FrameShoppingcartProdIndividualBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }
}