package com.example.luma.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.luma.R;
import com.example.luma.ui.DrawerActivity;
import com.example.luma.ui.signup.SignupActivity;

public class ActivityLogin extends AppCompatActivity {

    // Declarar las variables
    private EditText et_email;
    private EditText et_password;
    private Button btn_login;
    private Activity mySelf;
    private TextView tv_password;
    private TextView tv_signup;

    // Declarar SharePreferences
    private SharedPreferences storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // intanciar las variables
        mySelf = this;
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        tv_password = findViewById(R.id.tv_password);
        tv_signup = findViewById(R.id.tv_signup);

        String email_et = et_email.getText().toString();
        String password_et = et_password.getText().toString();

        // referenciar el preference
        storage = getSharedPreferences("STORAGE", MODE_PRIVATE);

        // creo las variables las cuales tienen el string con el get
        // para los datos guardados
        String email = storage.getString("EMAIL", "");
        String password = storage.getString("PASSWORD", "");

        if(email.equals("admin") || password.equals("123")){
            Intent drawerActivity = new Intent(mySelf, DrawerActivity.class);
            startActivity(drawerActivity);
            finish();
        } else {
            // Almacenamiento local del primer usuario administrador de pruebas
            SharedPreferences.Editor editor = storage.edit();
            editor.putString("EMAIL", "admin");
            editor.putString("PASSWORD", "123");
            editor.apply();
        }

        // --------- CLICK LOGIN ------------
        btn_login.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                //Cuando el usuario de click en el boton login
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                String email_storage = storage.getString("EMAIL", "NO HAY USUARIO");
                String password_storage = storage.getString("PASSWORD", "NO HAY USUARIO");

                Log.e("EMAIL del edit",email);
                Log.e("PASSWORD del edit",password);

                Log.e("EMAIL del storage",email_storage);
                Log.e("PASSWORD del storage",password_storage);

                if (email.equals(email_storage) && password.equals(password_storage)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(mySelf);
                    builder.setCancelable(false);
                    builder.setTitle(R.string.login);
                    builder.setMessage(R.string.txt_success_login);
//                    builder.setNegativeButton(R.string.txt_cancel, null);
                    builder.setPositiveButton(R.string.txt_accept, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //creo redirecciono
                            Intent drawerActivity = new Intent(mySelf, DrawerActivity.class);
                            startActivity(drawerActivity);
//                            finish();
                        }
                    });
                    AlertDialog dialog = builder.create();
//                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.dark3_gray);
                    dialog.show();
                } else{
                    Log.e("LOGIN","ERROR, FALLÓ DE SESION");
                    AlertDialog.Builder builder = new AlertDialog.Builder(mySelf);
                    builder.setTitle(R.string.login);
                    builder.setMessage(R.string.txt_error_login);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        // --------- CLICK SIGN UP ------------
        tv_signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Cuando el usuario da click en el boton sign up
                Intent activity_signup = new Intent(mySelf, SignupActivity.class);
                startActivity(activity_signup);
            }
        });

        // --------- CLICK FORGET PASSWORD ------------
        tv_password.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Cuando el usuario da click en el boton sign up
                Intent activity_signup = new Intent(mySelf, ForgetPassword.class);
                startActivity(activity_signup);
            }
        });


    }
}