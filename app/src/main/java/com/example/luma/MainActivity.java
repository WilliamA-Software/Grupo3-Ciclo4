package com.example.luma;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Declarar las variables
    private EditText et_email;
    private EditText et_password;
    private Button btn_login;
    private Activity mySelf;
    private TextView tv_password;
    private TextView tv_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referenciar las variables
        mySelf = this;
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        tv_password = findViewById(R.id.tv_password);
        tv_signup = findViewById(R.id.tv_signup);



        // --------- CLICK LOG IN ------------
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cuando el usuario de click en el boton login
                String username = et_email.getText().toString();
                String password = et_password.getText().toString();

                Log.e("USERNAME",username);
                Log.e("PASSWORD",password);

                if (username.equals("admin") && password.equals("123")){
                    Log.e("LOGIN","INICIÓ CORRECTAMENTE");
                    AlertDialog.Builder builder = new AlertDialog.Builder(mySelf);
                    builder.setTitle("Bienvenido");
                    builder.setMessage("Entras");
                    builder.setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent activity2 = new Intent(mySelf, MainActivity2.class);
                            activity2.putExtra("username",username);
                            activity2.putExtra("password",password);
                            startActivity(activity2);
                        }
                    });

                }else{
                    Log.e("LOGIN","ERROR, FALLÓ SESION");
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
                Intent activity_signup = new Intent(mySelf, ForgetActivity.class);
                startActivity(activity_signup);
            }
        });


    }
}