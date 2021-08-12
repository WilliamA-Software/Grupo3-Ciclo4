package com.example.luma.views;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import com.example.luma.R;
import com.example.luma.databinding.ActivityDrawerBinding;

public class LogOut extends AppCompatActivity {

    private SharedPreferences storage;
    private NavController navController;
    private ActivityDrawerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        storage = getSharedPreferences("STORAGE", MODE_PRIVATE);
        SharedPreferences.Editor editor = storage.edit();
        editor.clear();
    }
}