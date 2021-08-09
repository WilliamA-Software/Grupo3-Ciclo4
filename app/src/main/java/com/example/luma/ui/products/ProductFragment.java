package com.example.luma.ui.products;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.luma.R;
import com.example.luma.data.model.Product;
import com.example.luma.databinding.FragmentProductsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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

public class ProductFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private FragmentProductsBinding binding;

    private EditText et_code,
            et_name,
            et_description,
            et_price,
            et_quantity,
            et_latitude,
            et_longitude,
            et_image;

    private String typeAux,
            setType;

    private Button btn_insert,
            btn_search,
            btn_update,
            btn_delete;

    //Map
    private ImageButton imgbtn_location;
    private GoogleMap mMap3;
    private LinearLayout ll_map3;
    private boolean active_map;

    private Spinner et_type;

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
        et_latitude = binding.etLatitude;
        et_longitude = binding.etLongitude;
        et_type = binding.etTypeProduct;

        //Buttons
        btn_insert = binding.btnInsert;
        btn_search = binding.btnSearch;
        btn_update = binding.btnUpdate;
        btn_delete = binding.btnDelete;

        //ImageButtons
        imgbtn_location = binding.imgbtnLocation;
        ll_map3 = binding.llMap3;

        //Visibility map
        active_map = false;

        List<String> types = new ArrayList<String>();
        types.add("Seleccione");
        types.add("Camisas");
        types.add("Camisetas");
        types.add("Pantalones");
        types.add("Zapatos");
        types.add("Vestidos");

        imgbtn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!active_map){
                    //mMap1.visibility= visible;
                    ll_map3.setVisibility(View.VISIBLE);
                    active_map = true;

                }
                else {
                    ll_map3.setVisibility(View.GONE);
                    active_map = false;


                }
            }
        });

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

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map3);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this::onMapReady);
        }

        return root;// Creating adapter for spinner
    }

    //Method to create product
    public void Create(View view){
        //local variables
        String id = "0", name, description, price, quantity, image, type, latitude, longitude;
        name = et_name.getText().toString();
        description = et_description.getText().toString();
        price = et_price.getText().toString();
        quantity = et_quantity.getText().toString();
        latitude = et_latitude.getText().toString();
        longitude = et_longitude.getText().toString();
        image = et_image.getText().toString();
        type = typeAux;
        if(!name.isEmpty() && !description.isEmpty() && !price.isEmpty() && checkSpinner()){
            Product product = new Product(
                    id,
                    name,
                    description,
                    price,
                    quantity,
                    image,
                    latitude,
                    longitude,
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
                    et_latitude.setText("");
                    et_longitude.setText("");
                    typeAux="";
                    et_type.setSelection(getItemPosition(et_type, "Seleccione"));
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
                        et_latitude.setText(document.get("latitudeProduct").toString());
                        et_longitude.setText(document.get("longitudeProduct").toString());
                        et_type.setSelection(getItemPosition(et_type, document.get("typeProduct").toString()));
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
                            et_latitude.setText(document.get("latitudeProduct").toString());
                            et_longitude.setText(document.get("longitudeProduct").toString());
                            et_type.setSelection(getItemPosition(et_type, document.get("typeProduct").toString()));
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
        String code, name, description, price, quantity, image, latitude, longitude, type;
        code = et_code.getText().toString();
        name = et_name.getText().toString();
        description = et_description.getText().toString();
        price = et_price.getText().toString();
        quantity = et_quantity.getText().toString();
        image = et_image.getText().toString();
        latitude = et_latitude.getText().toString();
        longitude = et_longitude.getText().toString();
        type = typeAux;

        if(!name.isEmpty() && !description.isEmpty() && !price.isEmpty() && !quantity.isEmpty() && checkSpinner()){
            Product product = new Product(
                    code,
                    name,
                    description,
                    price,
                    quantity,
                    image,
                    latitude,
                    longitude,
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
                    et_latitude.setText("");
                    et_longitude.setText("");
                    et_type.setSelection(getItemPosition(et_type, "Seleccione"));
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
                    et_name.setText("");
                    et_description.setText("");
                    et_price.setText("");
                    et_quantity.setText("");
                    et_image.setText("");
                    et_latitude.setText("");
                    et_longitude.setText("");
                    et_type.setSelection(getItemPosition(et_type, "Seleccione"));
                    typeAux="";
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

    public static int getItemPosition(Spinner spinner, String type) {
        //Creamos la variable posicion y lo inicializamos en 0
        int position = 0;
        //Recorre el spinner en busca del ítem que coincida con el parametro `String fruta`
        //que lo pasaremos posteriormente
        for (int i = 0; i < spinner.getCount(); i++) {
            //Almacena la posición del ítem que coincida con la búsqueda
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(type)) {
                position = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posición 0 o N, de lo contrario devuelve 0 = posición inicial)
        return position;
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

    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        mMap3 = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng marker = new LatLng(5.062312, -75.493129);
        mMap3.addMarker(new MarkerOptions().position(marker).title("Marcador del producto"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));

        // Construct a CameraPosition focusing on Mountain View and animate the camera to that position.
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(marker)      // Sets the center of the map to Mountain View
                .zoom(16)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap3.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mMap3.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull @NotNull LatLng latLng) {
                mMap3.clear();

                double latitude = latLng.latitude;
                double longitude = latLng.longitude;

                et_latitude.setText(String.valueOf(latitude));
                et_longitude.setText(String.valueOf(longitude));

                mMap3.addMarker(new MarkerOptions().position(latLng).title(et_name.getText().toString()));
            }
        });
    }
}