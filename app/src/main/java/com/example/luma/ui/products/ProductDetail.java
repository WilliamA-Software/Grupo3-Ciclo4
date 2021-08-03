package com.example.luma.ui.products;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.luma.data.model.Product;
import com.example.luma.databinding.FragmentProductDetailBinding;
import com.example.luma.ui.home.ProductAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ProductDetail extends Fragment {

    private FragmentProductDetailBinding binding;
    private ImageView img_main_product;
    private TextView tv_product_name, tv_product_price;
    private EditText et_search, et_quantity;
    private Button bt_color1, bt_color2, bt_color3, bt_sizeS, bt_sizeM, bt_sizeL, bt_sizeXL,
            bt_favorite, bt_favorite_bottom, bt_add_cart, bt_whatsapp, bt_email;
    private Product product;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        img_main_product = binding.imgMainProduct;
        tv_product_name = binding.tvProductName;
        tv_product_price = binding.tvPrice;
        //Trae los datos del producto
        product = ProductAdapter.productDetail;
        ProductAdapter.productDetail=null;
        setData(product);
        return root;
    }
    private void setData(Product product){

        tv_product_name.setText(product.getNameProduct());
        tv_product_price.setText("$ " + product.getPriceProduct());
        Picasso.with(img_main_product.getContext()).load(product.getImageProduct()).into(img_main_product, new Callback() {
            @Override
            public void onSuccess() {
            }
            @Override
            public void onError() {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
