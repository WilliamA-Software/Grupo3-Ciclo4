package com.example.luma.ui.products;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.luma.R;
import com.example.luma.data.model.Product;
import com.example.luma.databinding.FragmentProductDetailBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.example.luma.ui.home.HomeAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class ProductDetail extends Fragment implements OnMapReadyCallback {

    private FragmentProductDetailBinding binding;
    private ImageView img_main_product;
    private TextView tv_product_name, tv_product_price;
    private EditText et_search, et_quantity;
    private Button bt_color1, bt_color2, bt_color3, bt_sizeS, bt_sizeM, bt_sizeL, bt_sizeXL,
            bt_favorite, bt_favorite_bottom, bt_add_cart, bt_whatsapp, bt_email;
    private Product product;

    //google maps
    private GoogleMap mMap2;

    private Activity mySelf;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        img_main_product = binding.imgMainProduct;
        tv_product_name = binding.tvProductName;
        tv_product_price = binding.tvPrice;
        //latitude = binding.etLatitude;
        //longitude = binding.etLongitude;

        //Trae los datos del producto
        product = HomeAdapter.productDetail;
        HomeAdapter.productDetail=null;
        setData(product);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map2);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }


        return root;
    }
    private void setData(Product product){

        tv_product_name.setText(product.getNameProduct());
        tv_product_price.setText("$  "+product.getPriceProduct());
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

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        mMap2 = googleMap;

        // Aqui tomo los datos de latitude y longitude de firebase
        Log.e("asdasdasdddddddddddddddddddddddddddd", product.getLatitudeProduct());
        Log.e("asdasdasdddddddddddddddddddddddddddd", product.getLongitudeProduct());
        double latitude = Double.parseDouble(product.getLatitudeProduct());
        double longitude = Double.parseDouble(product.getLongitudeProduct());

        // Creo la variable marcador de tipo latitude y longitude
        LatLng marker;

        if(latitude != 0 && longitude != 0){

            // Add a marker in Sydney and move the camera
            marker = new LatLng(latitude, longitude);
            mMap2.addMarker(new MarkerOptions().position(marker).title("Marcador del producto"));
        }else {
            marker = new LatLng(5.062312, -75.493129);

        }

        // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(marker)      // Sets the center of the map to Mountain View
                .zoom(15)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder

        mMap2.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
}
