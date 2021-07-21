package com.example.luma.ui.products;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.luma.R;
import com.example.luma.database.LumaDatabase;
import com.example.luma.database.model.Product;

import java.lang.ref.WeakReference;
import java.util.List;

public class ActivityProducts extends AppCompatActivity {

    private EditText et_codigo, et_descripcion, et_precio, et_name;
    private Activity mySelf;
    private LumaDatabase lumaDatabase;
    private List<Product> productRegister;
    private Product productSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_products);
        mySelf =this;

        et_codigo = (EditText)findViewById(R.id.txt_codigo);
        //et_name = (EditText)findViewById(R.id.txt_name);
        et_descripcion = (EditText)findViewById(R.id.txt_descripcion);
        et_precio = (EditText)findViewById(R.id.txt_precio);


    }





    //Login
    private static class SearchProduct extends AsyncTask<Void, Void, Product> {

        private WeakReference<ActivityProducts> activityProductsWeakReference;

        SearchProduct(ActivityProducts context) {
            this.activityProductsWeakReference = new WeakReference<>(context);
        }

        @Override
        protected Product doInBackground(Void... voids) {
            if (activityProductsWeakReference.get() != null) {
                String name = activityProductsWeakReference.get().et_name.getText().toString();
                String id = activityProductsWeakReference.get().et_codigo.getText().toString();
                String descripcion = activityProductsWeakReference.get().et_descripcion.getText().toString();
                String precio = activityProductsWeakReference.get().et_precio.getText().toString();
                Product product = activityProductsWeakReference.get().lumaDatabase.getProductDao().GetProduct(id,name);

                return product;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Product product) {
            if (product != null) {
                activityProductsWeakReference.get().loginOk();
            } else {
                activityProductsWeakReference.get().loginError();
            }
            super.onPostExecute(product);
        }
    }


    //Login successful message
    public void loginOk() {
        Toast.makeText(mySelf, "Producto", Toast.LENGTH_SHORT).show();
    }

    //Login error message
    public void loginError() {
        Toast.makeText(mySelf, "Usuario o contraseña erronea", Toast.LENGTH_SHORT).show();
    }

    public void Registrar(View view){

        String name = et_name.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();
        if(!name.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){

            Product product =  new Product(name,descripcion,precio);
            long s = lumaDatabase.getProductDao().insertProduct(product);


            //Limpiamos el texto
            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");

            // Informamos del registro exitoso
            Toast.makeText(this, "Registro Exitoso "+ s, Toast.LENGTH_SHORT).show();



        } else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
    /*
    //Metodo para dar el alta un producto
    public void Registrar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
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
            Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();



        } else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para consultar un Articulo o producto
    public void Buscar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
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

                Toast.makeText(this, "No existe el articulo", Toast.LENGTH_SHORT).show();
                //Cerramos la base de datos
                BaseDeDatabase.close();

            }

        }else {
            Toast.makeText(this, "Debes introducir el codigo del articulo", Toast.LENGTH_SHORT).show();
        }

    }

    //Metodo para eliminar un producto o articulo

    public void Eliminar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

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
                Toast.makeText(this, "Articulo Eliminado Exitosamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Debes introducir el codigo del articulo", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para modificiar un articulo o producto
    public void Modificar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
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

                Toast.makeText(this, "Articulo modificado correctamente", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
            }



        }else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
*/
}