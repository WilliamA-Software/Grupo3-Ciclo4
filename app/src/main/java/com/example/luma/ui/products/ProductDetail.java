package com.example.luma.ui.products;

import android.annotation.SuppressLint;
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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.luma.R;
import com.example.luma.data.model.Product;
import com.example.luma.databinding.FragmentProductDetailBinding;
import com.example.luma.generated.callback.OnClickListener;
import com.example.luma.ui.home.ProductAdapter;
import com.example.luma.viewmodels.HomeViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class ProductDetail extends Fragment implements OnMapReadyCallback {

    private FragmentProductDetailBinding binding;
    HomeViewModel homeViewModel;
    private NavController navController;

    //google maps
    private GoogleMap mMap2;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map2);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(view);
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        binding.setHomeViewModel(homeViewModel);
        binding.btAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeViewModel.addProduct2Cart(binding.getHomeViewModel().getProduct().getValue());
                Snackbar snackbar = Snackbar.make(requireView(), homeViewModel.getProduct().getValue().getNameProduct() + " " + getString(R.string.added2cart), Snackbar.LENGTH_LONG);
                snackbar.setActionTextColor(getResources().getColor(R.color.white));
                snackbar.setAction("Checkout", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        navController.navigate(R.id.action_nav_prod_detail_to_nav_shopping_cart);
                    }
                }).show();
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
        double latitude = Double.parseDouble(homeViewModel.getProduct().getValue().getLatitudeProduct());
        double longitude = Double.parseDouble(homeViewModel.getProduct().getValue().getLongitudeProduct());

        // Creo la variable marcador de tipo latitude y longitude
        LatLng marker;

        if(latitude != 0 && longitude != 0){
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
