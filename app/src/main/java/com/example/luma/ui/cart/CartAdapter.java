package com.example.luma.ui.cart;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
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
import com.example.luma.databinding.FragmentProductDetailBinding;
import com.example.luma.databinding.FragmentShoppingcartBinding;
import com.example.luma.databinding.FrameProductIndividualBinding;
import com.example.luma.databinding.FrameShoppingcartProdIndividualBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class CartAdapter extends ListAdapter<CartProduct, CartAdapter.CartViewHolder> {

    private CartInterface cartInterface;
    public CartAdapter(CartInterface cartInterface) {
        super(CartProduct.itemCallback);
        this.cartInterface = cartInterface;
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

        if (holder.binding.pbLoadingImage.getVisibility() == View.VISIBLE)
            holder.binding.pbLoadingImage.setVisibility(View.GONE);
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        FrameShoppingcartProdIndividualBinding binding;
        FragmentProductDetailBinding fragmentProductDetailBinding;

        public CartViewHolder(@NonNull FrameShoppingcartProdIndividualBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cartInterface.deleteProduct(getItem(getAdapterPosition()));
                }
            });
            binding.etQuantity.setOnEditorActionListener(new TextView.OnEditorActionListener(){
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        cartInterface.changeQuantity(getItem(getAdapterPosition()), Integer.parseInt(String.valueOf(binding.etQuantity.getText())));
                    }
                    return false;
                }
            });
        }
    }

    public interface CartInterface {
        void deleteProduct(CartProduct cartProduct);
        void changeQuantity(CartProduct cartProduct, int quantity);
    }


}