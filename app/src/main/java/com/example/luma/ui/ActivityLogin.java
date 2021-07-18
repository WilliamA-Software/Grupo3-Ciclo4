package com.example.luma.ui;

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
import android.widget.Toast;

import com.example.luma.ui.signup.SignupActivity;

public class ActivityLogin extends AppCompatActivity {

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
        setContentView(R.layout.login_activity);

        // Referenciar las variables
        mySelf = this;
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        tv_password = findViewById(R.id.tv_password);
        tv_signup = findViewById(R.id.tv_signup);



        // --------- CLICK LOGIN ------------
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cuando el usuario de click en el boton login
                String username = et_email.getText().toString();
                String password = et_password.getText().toString();

                if (username.equals("admin@mail.com") && password.equals("123456789")){
                    Log.e("LOGIN","INICIÓ CORRECTAMENTE");
                    Intent mainActivity = new Intent(mySelf, MainActivity.class);
                    mainActivity.putExtra("username",username);
                    startActivity(mainActivity);
                } else{
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
//        tv_password.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//                //Cuando el usuario da click en el boton sign up
//                Intent activity_signup = new Intent(mySelf, ForgetActivity.class);
//                startActivity(activity_signup);
//            }
//        });


    }
}