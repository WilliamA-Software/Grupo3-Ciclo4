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

    private EditText et_codigo, et_descripcion, et_precio, et_cantidad, et_imagen;
    private Button btn_insert, btn_search, btn_update, btn_delete;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProductsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        et_codigo = binding.txtCodigo;
        et_descripcion = binding.txtDescripcion;
        et_precio = binding.txtPrecio;
        et_cantidad = binding.txtCantidad;
        et_imagen = binding.txtImagen;
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
        String codigo, descripcion, precio, cantidad, imagen;

        codigo = et_codigo.getText().toString();
        descripcion = et_descripcion.getText().toString();
        precio = et_precio.getText().toString();
        cantidad = et_cantidad.getText().toString();
        imagen = et_imagen.getText().toString();

        if(!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();


            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);
            registro.put("cantidad", cantidad);
            registro.put("imagen", imagen);

            BaseDeDatos.insert("articulos", null, registro);
            BaseDeDatos.close();

            //Limpiamos el texto
            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");
            et_cantidad.setText("");
            et_imagen.setText("");

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
                    ("select descripcion, precio, cantidad, imagen from articulos where codigo =" + codigo, null);

            if(fila.moveToFirst()){
                et_descripcion.setText(fila.getString(0));
                et_precio.setText(fila.getString(1));
                et_cantidad.setText(fila.getString(2));
                et_imagen.setText(fila.getString(3));

                Toast.makeText(getActivity(), "Articulo encontrado", Toast.LENGTH_SHORT).show();
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
            et_cantidad.setText("");
            et_imagen.setText("");

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
        String codigo, descripcion, precio, cantidad, imagen;

        codigo = et_codigo.getText().toString();
        descripcion = et_descripcion.getText().toString();
        precio = et_precio.getText().toString();
        cantidad = et_cantidad.getText().toString();
        imagen = et_imagen.getText().toString();

        if(!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);
            registro.put("cantidad", cantidad);
            registro.put("imagen", imagen);

            int update  = BaseDatabase.update("articulos", registro, "codigo=" + codigo, null);
            //cerramos la base de datos
            BaseDatabase.close();

            if(update == 1){

                et_codigo.setText("");
                et_descripcion.setText("");
                et_precio.setText("");
                et_cantidad.setText("");
                et_imagen.setText("");

                Toast.makeText(getActivity(), "Articulo modificado correctamente", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(getActivity(), "El articulo no existe", Toast.LENGTH_SHORT).show();
            }



        }else{
            Toast.makeText(getActivity(), "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}