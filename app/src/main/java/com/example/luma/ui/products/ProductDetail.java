package com.example.luma.ui.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.luma.databinding.FragmentProductDetailBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProductDetail extends Fragment {

    private FragmentProductDetailBinding binding;
    private EditText et_search, et_quantity;
    private Button bt_color1, bt_color2, bt_color3, bt_sizeS, bt_sizeM, bt_sizeL, bt_sizeXL,
            bt_favorite, bt_favorite_bottom, bt_add_cart, bt_whatsapp, bt_email;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProductDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
