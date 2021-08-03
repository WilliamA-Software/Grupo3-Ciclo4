package com.example.luma.data.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Product {
    private String NameProduct,
            DescriptionProduct,
            PriceProduct,
            QuantityProduct,
            ImageProduct,
            TypeProduct;

    public Product(String NameProduct,
                   String DescriptionProduct,
                   String PriceProduct,
                   String QuantityProduct,
                   String ImageProduct,
                   String TypeProduct){
        this.NameProduct = NameProduct;
        this.PriceProduct = PriceProduct;
        this.DescriptionProduct = DescriptionProduct;
        this.QuantityProduct = QuantityProduct;
        this.ImageProduct = ImageProduct;
        this.TypeProduct = TypeProduct;
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
}