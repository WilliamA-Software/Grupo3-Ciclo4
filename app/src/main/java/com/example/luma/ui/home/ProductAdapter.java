package com.example.luma.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.luma.R;
import com.example.luma.data.model.Product;
import com.example.luma.databinding.FrameProductIndividualBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private ArrayList<Product> products;
    private LayoutInflater inflater;
    private View view;
    public static Product productDetail;
    public ArrayList<Product> cartProducts = null;

    public ProductAdapter(){
        super();
    }

    public ProductAdapter(View view, ArrayList<Product> products){
        this.products = products;
        this.cartProducts = products;
        this.view = view;
        //this.clickListener = clickListener;
    }

    static class HomeViewHolder extends RecyclerView.ViewHolder {

        public HomeViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        FrameProductIndividualBinding binding = FrameProductIndividualBinding.inflate(layoutInflater, parent, false);
        return new ProductViewHolder(binding);
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
        FrameProductIndividualBinding binding;

        public ProductViewHolder(FrameProductIndividualBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
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
        void addItem(Product product);
        void onItemClick(Product product);
    }
}
