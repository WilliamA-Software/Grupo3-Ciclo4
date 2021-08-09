package com.example.luma.data.model;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.example.luma.databinding.FrameProductIndividualBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Product {
    private String NameProduct,
            DescriptionProduct,
            PriceProduct,
            QuantityProduct,
            ImageProduct,
            LatitudeProduct,
            LongitudeProduct,
            TypeProduct;
//    TODO: Add (available / Out of stock) boolean in Firebase
//    private boolean isAvailable;

    public Product(String NameProduct,
                   String DescriptionProduct,
                   String PriceProduct,
                   String QuantityProduct,
                   String ImageProduct,
                   String LatitudeProduct,
                   String LongitudeProduct,
                   String TypeProduct){
        this.NameProduct = NameProduct;
        this.PriceProduct = PriceProduct;
        this.DescriptionProduct = DescriptionProduct;
        this.QuantityProduct = QuantityProduct;
        this.ImageProduct = ImageProduct;
        this.LatitudeProduct = LatitudeProduct;
        this.LongitudeProduct = LongitudeProduct;
        this.TypeProduct = TypeProduct;
    }

    public String getLatitudeProduct() {
        return LatitudeProduct;
    }

    public void setLatitudeProduct(String latitudeProduct) {
        LatitudeProduct = latitudeProduct;
    }

    public String getLongitudeProduct() {
        return LongitudeProduct;
    }

    public void setLongitudeProduct(String longitudeProduct) {
        LongitudeProduct = longitudeProduct;
    }

    public String getQuantityProduct() {
        return QuantityProduct;
    }

    public void setQuantityProduct(String quantityProduct) {
        QuantityProduct = quantityProduct;
    }

    public String getImageProduct() {
        return ImageProduct;
    }

    public void setImageProduct(String imageProduct) {
        ImageProduct = imageProduct;
    }

    public String getTypeProduct() {
        return TypeProduct;
    }

    public void setTypeProduct(String typeProduct) {
        TypeProduct = typeProduct;
    }

    public String getNameProduct() {
        return NameProduct;
    }

    public void setNameProduct(String nameProduct) {
        NameProduct = nameProduct;
    }

    public String getDescriptionProduct() {
        return DescriptionProduct;
    }

    public void setDescriptionProduct(String descriptionProduct) {
        DescriptionProduct = descriptionProduct;
    }

    public String getPriceProduct() {
        return PriceProduct;
    }

    public void setPriceProduct(String priceProduct) {
        PriceProduct = priceProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return getNameProduct().equals(product.getNameProduct()) &&
                getDescriptionProduct().equals(product.getDescriptionProduct()) &&
                getPriceProduct().equals(product.getPriceProduct()) &&
                getQuantityProduct().equals(product.getQuantityProduct()) &&
                getImageProduct().equals(product.getImageProduct()) &&
                getTypeProduct().equals(product.getTypeProduct());
    }

    public static DiffUtil.ItemCallback<Product> itemCallback = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull Product oldItem, @NonNull @NotNull Product newItem) {
            return oldItem.getNameProduct().equals(newItem.getNameProduct());
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull Product oldItem, @NonNull @NotNull Product newItem) {
            return oldItem.equals(newItem);
        }
    };

    @BindingAdapter("android:productImage")
    public static void loadImage(ImageView imageView, String imageUrl){
        Picasso.with(imageView.getContext()).load(imageUrl).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {
            }
        });
    }
}