package com.example.luma.ui.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.luma.R;
import com.example.luma.data.model.Product;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ProductViewHolder> {

    private ArrayList<Product> products;
    private LayoutInflater inflater;
    private View view;
    public static Product productDetail;

    public CartAdapter(View view, ArrayList<Product> products){
//    public CartAdapter(ArrayList<Product> products){
        this.products = products;
        this.view = view;
    }

    @NonNull
    @NotNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_shoppingcart,parent,false);
        return new ProductViewHolder(view);
    }

    // Fill data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull @NotNull ProductViewHolder holder, int position) {
        holder.setData(products.get(position));
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{
        TextView name, code, color, size, value, subtotal;
        EditText quantity;
        ImageButton image_main, delete;
        ProgressBar loading;

        public ProductViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_product_name);
            code = itemView.findViewById(R.id.tv_product_code);
            color = itemView.findViewById(R.id.tv_color);
            size = itemView.findViewById(R.id.tv_size);
            quantity = itemView.findViewById(R.id.et_quantity);
            value = itemView.findViewById(R.id.tv_value);
            subtotal = itemView.findViewById(R.id.tv_subtotal);
            image_main = itemView.findViewById(R.id.img_product);
            delete = itemView.findViewById(R.id.img_delete);
            loading = itemView.findViewById(R.id.pb_loading_image);
        }

        //TODO: Cambiar el MODELO del Producto agregando los atributos de: Color y Talla y mostrandolos en los recyclerview del carrito
        public void setData(Product product) {
            name.setText(product.getNameProduct());
            quantity.setText(product.getQuantityProduct());
            value.setText(product.getPriceProduct());
            double total2pay = Integer.getInteger(product.getQuantityProduct()) * Integer.getInteger(product.getPriceProduct());
            subtotal.setText(String.valueOf(total2pay));
            Picasso.with(image_main.getContext()).load(product.getImageProduct()).into(image_main, new Callback() {
                @Override
                public void onSuccess() {
                    loading.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                }
            });

            // Abre el detalle del producto al dar clic en la imagen
            image_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Setea el productDetail para que pueda ser accedido desde los detalles
                    productDetail = product;
                    Navigation.findNavController(view).navigate(R.id.nav_prod_detail);
                }
            });
            // Borra el producto de la lista al dar clic en la "X"
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    products.remove(product);
                }
            });
        }
    }/*
    public interface ItemClickListener{
        public void onItemClick(Product product);
    }*/
}
