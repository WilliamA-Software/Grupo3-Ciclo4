package com.example.luma.ui.products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.luma.data.model.Product;
import com.example.luma.databinding.FragmentProductsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private FragmentProductsBinding binding;

    private EditText et_code,
            et_name,
            et_description,
            et_price,
            et_quantity,
            et_image;
    private Spinner et_type;
    private String typeAux;
    private Button btn_insert,
            btn_search,
            btn_update,
            btn_delete;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //EditTexts
        et_code = binding.etCodeProduct;
        et_name = binding.etNameProduct;
        et_description = binding.etDescriptionProduct;
        et_price = binding.etPriceProduct;
        et_quantity = binding.etQuantityProduct;
        et_image = binding.etImageProduct;
        et_type = binding.etTypeProduct;
        //Buttons
        btn_insert = binding.btnInsert;
        btn_search = binding.btnSearch;
        btn_update = binding.btnUpdate;
        btn_delete = binding.btnDelete;

        List<String> types = new ArrayList<String>();
        types.add("Seleccione");
        types.add("Camisas");
        types.add("Camisetas");
        types.add("Pantalones");
        types.add("Zapatos");


        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Create(getView());
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Read(getView());
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Update(getView());
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete(getView());
            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, types);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        et_type.setAdapter(dataAdapter);
        et_type.setOnItemSelectedListener(this);
        return root;// Creating adapter for spinner
    }




    //Method to create product
    public void Create(View view){
        //local variables
        String name, description, price, quantity, image, type;
        name = et_name.getText().toString();
        description = et_description.getText().toString();
        price = et_price.getText().toString();
        quantity = et_quantity.getText().toString();
        image = et_image.getText().toString();
        type = typeAux;
        if(!name.isEmpty() && !description.isEmpty() && !price.isEmpty() && checkSpinner()){
            Product product = new Product(
                    name,
                    description,
                    price,
                    quantity,
                    image,
                    type
            );
            db.collection("product").document().set(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    // Insert Successful
                    Toast.makeText(getActivity(), "Registro Exitoso", Toast.LENGTH_SHORT).show();
                    et_name.setText("");
                    et_description.setText("");
                    et_price.setText("");
                    et_quantity.setText("");
                    et_image.setText("");
                    typeAux="";
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    // Insert Failed
                    Toast.makeText(getActivity(), "Registro Fallido", Toast.LENGTH_SHORT).show();
                }
            });
        } else{
            Toast.makeText(getActivity(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }


    //Method to read product
    public void Read(View view){
        //local variables
        String code, name;
        code = et_code.getText().toString();
        name = et_name.getText().toString();
        //Read by code
        if(!code.isEmpty()){
            db.collection("product").document(code).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful() && task.getResult().exists()) {
                        DocumentSnapshot document = task.getResult();
                        et_name.setText(document.get("nameProduct").toString());
                        et_description.setText(document.get("descriptionProduct").toString());
                        et_price.setText(document.get("priceProduct").toString());
                        et_quantity.setText(document.get("quantityProduct").toString());
                        et_image.setText(document.get("imageProduct").toString());
                        // Read Successful
                        Toast.makeText(getActivity(), "Articulo encontrado", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Error articulo no encontrado", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //Read by name
        }else if(!name.isEmpty() && code.isEmpty()){
            db.collection("product").whereEqualTo("nameProduct",name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()&& !task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            et_code.setText(document.getId());
                            et_description.setText(document.get("descriptionProduct").toString());
                            et_price.setText(document.get("priceProduct").toString());
                            et_quantity.setText(document.get("quantityProduct").toString());
                            et_image.setText(document.get("imageProduct").toString());
                            // Read Successful
                            Toast.makeText(getActivity(), "Articulo encontrado", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Error articulo no encontrado", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Toast.makeText(getActivity(), "Debes introducir el codigo o el nombre del articulo", Toast.LENGTH_SHORT).show();
        }

    }


    //Method to update a product
    public void Update(View view){
        String code, name, description, price, quantity, image, type;
        code = et_code.getText().toString();
        name = et_name.getText().toString();
        description = et_description.getText().toString();
        price = et_price.getText().toString();
        quantity = et_quantity.getText().toString();
        image = et_image.getText().toString();
        type = typeAux;

        if(!name.isEmpty() && !description.isEmpty() && !price.isEmpty() && !quantity.isEmpty() && checkSpinner()){
            Product product = new Product(
                    name,
                    description,
                    price,
                    quantity,
                    image,
                    type
            );
            db.collection("product").document(code).set(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    // Insert Successful
                    Toast.makeText(getActivity(), "Actualizacion Exitoso", Toast.LENGTH_SHORT).show();
                    et_code.setText("");
                    et_name.setText("");
                    et_description.setText("");
                    et_price.setText("");
                    et_quantity.setText("");
                    et_image.setText("");
                    typeAux="";
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    // Insert Failed
                    Toast.makeText(getActivity(), "Actualizacion Fallido", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(getActivity(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    //Method to delete a product
    public void Delete(View view){
        String code = et_code.getText().toString();

        if(!code.isEmpty()){
            db.collection("product").document(code).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    // Insert Successful
                    Toast.makeText(getActivity(), "Articulo eliminado", Toast.LENGTH_SHORT).show();
                    et_code.setText("");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    // Insert Failed
                    Toast.makeText(getActivity(), "Error al eliminar", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(getActivity(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkSpinner(){
        if (typeAux=="" || typeAux =="Seleccione"){
            return false;
        }else {
            return true;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        typeAux = item;
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}