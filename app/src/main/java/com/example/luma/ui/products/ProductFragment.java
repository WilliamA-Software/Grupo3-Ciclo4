package com.example.luma.ui.products;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.luma.R;
import com.example.luma.databinding.FragmentProductsBinding;
import com.example.luma.ui.DrawerActivity;

public class ProductFragment extends Fragment {

    private FragmentProductsBinding binding;

    private EditText et_codigo, et_descripcion, et_precio;
    private Button btn_insert, btn_search, btn_update, btn_delete;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        et_codigo = binding.txtCodigo;
        et_descripcion = binding.txtDescripcion;
        et_precio = binding.txtPrecio;
        btn_insert = binding.btnInsert;
        btn_search = binding.btnSearch;
        btn_update = binding.btnUpdate;
        btn_delete = binding.btnDelete;

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registrar(getView());
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Buscar(getView());
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Eliminar(getView());
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Modificar(getView());
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    //Metodo para dar el alta un producto
    public void Registrar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();

        if(!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();


            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);


            BaseDeDatos.insert("articulos", null, registro);
            BaseDeDatos.close();

            //Limpiamos el texto
            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");

            // Informamos del registro exitoso
            Toast.makeText(getActivity(), "Registro Exitoso", Toast.LENGTH_SHORT).show();



        } else{
            Toast.makeText(getActivity(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para consultar un Articulo o producto
    public void Buscar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "administracion", null, 1);
        SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();

        if(!codigo.isEmpty()){

            Cursor fila = BaseDeDatabase.rawQuery
                    ("select descripcion, precio from articulos where codigo =" + codigo, null);

            if(fila.moveToFirst()){
                et_descripcion.setText(fila.getString(0));
                et_precio.setText(fila.getString(1));

                //Cerramos la base de datos para evitar bucles
                BaseDeDatabase.close();
            } else {

                Toast.makeText(getActivity(), "No existe el articulo", Toast.LENGTH_SHORT).show();
                //Cerramos la base de datos
                BaseDeDatabase.close();

            }

        }else {
            Toast.makeText(getActivity(), "Debes introducir el codigo del articulo", Toast.LENGTH_SHORT).show();
        }

    }

    //Metodo para eliminar un producto o articulo

    public void Eliminar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "administracion", null, 1);

        SQLiteDatabase BaseDatabase = admin.getWritableDatabase();

        String  codigo = et_codigo.getText().toString();

        if(!codigo.isEmpty()){

            int cantidad  = BaseDatabase.delete("articulos", "codigo=" + codigo, null );
            //Cerramos la base de datos
            BaseDatabase.close();

            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");

            if(cantidad ==1 ){
                Toast.makeText(getActivity(), "Articulo Eliminado Exitosamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "El articulo no existe", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getActivity(), "Debes introducir el codigo del articulo", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para modificiar un articulo o producto
    public void Modificar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getActivity(), "administracion", null, 1);
        SQLiteDatabase BaseDatabase = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();

        if(!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            int cantidad  = BaseDatabase.update("articulos", registro, "codigo=" + codigo, null);
            //cerramos la base de datos
            BaseDatabase.close();

            if(cantidad == 1){

                Toast.makeText(getActivity(), "Articulo modificado correctamente", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(getActivity(), "El articulo no existe", Toast.LENGTH_SHORT).show();
            }



        }else{
            Toast.makeText(getActivity(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}