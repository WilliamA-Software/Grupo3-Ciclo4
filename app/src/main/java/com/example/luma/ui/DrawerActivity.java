package com.example.luma.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.luma.R;
import com.example.luma.data.model.CartProduct;
import com.example.luma.ui.cart.CartFragment;
import com.example.luma.ui.home.HomeFragment;
import com.example.luma.ui.login.ActivityLogin;
import com.example.luma.viewmodels.HomeViewModel;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.example.luma.databinding.ActivityDrawerBinding;

import org.jetbrains.annotations.NotNull;

import java.io.StringWriter;
import java.util.List;

public class DrawerActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDrawerBinding binding;
    private NavController navController;
    HomeViewModel homeViewModel;
    private int carQuantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDrawerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        // Creacion del menu lateral y sus elementos
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_products, R.id.nav_favorites, R.id.nav_shopping_cart, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.getCartList().observe(this, new Observer<List<CartProduct>>() {
            @Override
            public void onChanged(List<CartProduct> cartProducts) {
                carQuantity = cartProducts.size();
                invalidateOptionsMenu(); // this is important to refresh de menu again
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.nav_home:
                        navController.navigate(R.id.nav_home);
                        break;
                    case R.id.nav_favorites:
                        navController.navigate(R.id.nav_favorites);
                        break;
                    case R.id.nav_products:
                        navController.navigate(R.id.nav_products);
                        break;
                    case R.id.nav_shopping_cart:
                        navController.navigate(R.id.nav_shopping_cart);
                        break;
                    case R.id.nav_logout:
                        logout();
                        return true;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void logout() {
        SharedPreferences storage = getSharedPreferences("STORAGE", MODE_PRIVATE);
        SharedPreferences.Editor editor = storage.edit();
        editor.clear();
        editor.apply();
        Intent loginIntent = new Intent(this, ActivityLogin.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        // This lines are to create dynamic badge with cart quantity
        MenuItem menuItem = menu.findItem(R.id.nav_shopping_cart);
        View actionView = menuItem.getActionView(); // this is why we use "app" instead of "android" in menu
        TextView cartBadge = actionView.findViewById(R.id.tv_cart_badge);
        cartBadge.setText(String.valueOf(carQuantity));
        cartBadge.setVisibility(carQuantity == 0 ? View.GONE : View.VISIBLE);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem); // this is to make it clickeable
            }
        });
        return true;
    }

//  Define las tareas a realizar al dar clic en los botones del Menu superior
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
//        navController = Navigation.findNavController(this, R.id.nav_shopping_cart);
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
//        navController.navigateUp();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}