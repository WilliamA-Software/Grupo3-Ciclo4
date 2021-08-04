package com.example.luma.data.model;

public class Favorite {
    private String IdProduct,
            NameProduct,
            DescriptionProduct,
            PriceProduct,
            QuantityProduct,
            ImageProduct,
            LatitudeProduct,
            LongitudeProduct,
            TypeProduct;

    public Favorite(String IdProduct,
                    String NameProduct,
                    String DescriptionProduct,
                    String PriceProduct,
                    String QuantityProduct,
                    String ImageProduct,
                    String LatitudeProduct,
                    String LongitudeProduct,
                    String TypeProduct){
        this.IdProduct = IdProduct;
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

    public String getIdProduct() {
        return IdProduct;
    }

    public void setIdProduct(String idProduct) {
        IdProduct = idProduct;
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

}