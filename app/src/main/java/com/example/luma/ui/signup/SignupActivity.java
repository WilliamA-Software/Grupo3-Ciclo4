package com.example.luma.ui.signup;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.luma.R;
import com.example.luma.databinding.ActivitySignupBinding;
import com.example.luma.ui.DrawerActivity;
import com.example.luma.ui.login.ActivityLogin;
import com.example.luma.ui.login.ForgetPassword;
import com.example.luma.ui.login.TermsConditions;

public class SignupActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivitySignupBinding binding;
    private boolean validForm;

    // Persistencia con Shared Preference
    private SharedPreferences storage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//// This 2 lines remove the Title Bar from the app
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().hide(); // This line removes the Action Bar from each activity, instead is better to remove it from the themes.xml file

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Instancia del almacenamiento persistente
        storage = getSharedPreferences("STORAGE", MODE_PRIVATE);
        // Obtengo los datos de usuario almacenados
        String name = storage.getString("NAME", "NO NAME");
        String lastname = storage.getString("LASTNAME", "NO LASTNAME");
        String mail = storage.getString("MAIL", "NO MAIL");
        String password = storage.getString("PASSWORD", "NO PASSWORD");
        if (mail.equals("admin@mail.com") && password.equals("123456789")){
//            Intent drawer = new Intent(this, DrawerActivity.class);
//            starActivity(drawer);
            Toast.makeText(this, "DATA SAVED", Toast.LENGTH_LONG);
        }

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.name;
        final EditText lastnameEditText = binding.lastname;
        final EditText mailEditText = binding.mail;
        final EditText passwordEditText = binding.password;
        final CheckBox checkBox = binding.cbPtc;
        final Button signupButton = binding.btSignup;
        signupButton.setEnabled(false);
        final TextView loginTextView = binding.tvLogin;
        final TextView policiestc = binding.tvPtc;
        final ProgressBar loadingProgressBar = binding.loading;
        final Activity mySelf;
        mySelf = this;

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                validForm = loginFormState.isDataValid();

            // Genera los mensajes de error en cada uno de los campos del registro
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getLastNameError() != null) {
                    lastnameEditText.setError(getString(loginFormState.getLastNameError()));
                }
                if (loginFormState.getMailError() != null) {
                    mailEditText.setError(getString(loginFormState.getMailError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(usernameEditText.getText().toString());
                }
                setResult(Activity.RESULT_OK);

            //Complete and destroy signup activity once successful
//                finish();
            }
        });
//    Verifica el estado del formulario
        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        lastnameEditText.getText().toString(),
                        mailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        lastnameEditText.addTextChangedListener(afterTextChangedListener);
        mailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
//    Verifica el estado del password y lanza el proceso de registro
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.signup(usernameEditText.getText().toString(),
                            lastnameEditText.getText().toString(),
                            mailEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });
//    Valida el checkBox y cambia el estado del boton si el formulario es valido tambien
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && validForm){
                    signupButton.setEnabled(true);
                } else
                    signupButton.setEnabled(false);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);

//          Almacenamiento local de los datos de usuario
                SharedPreferences.Editor editor = storage.edit();
                editor.putString("NAME", usernameEditText.toString());
                editor.putString("LASTNAME", lastnameEditText.toString());
                editor.putString("MAIL", mailEditText.toString());
                editor.putString("PASSWORD", passwordEditText.toString());
                editor.commit();

//                loginViewModel.signup(usernameEditText.getText().toString(),
//                        lastnameEditText.getText().toString(),
//                        mailEditText.getText().toString(),
//                        passwordEditText.getText().toString());

            }
        });

        // --------- CLICK POLITICAS TERMINOS Y CONDICIONES ------------
        policiestc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Cuando el usuario da click en el texto de Politicas, Terminos y Condiciones
                Intent activity = new Intent(mySelf, TermsConditions.class);
                startActivity(activity);
            }
        });

        // --------- CLICK SIGN UP ------------
        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Cuando el usuario da click en el boton Sign Up lo lleva al home
                Intent activity = new Intent(mySelf, DrawerActivity.class);
                activity.putExtra("name", name);
                startActivity(activity);
            }
        });

        // --------- CLICK LOGIN ------------
        loginTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Cuando el usuario da click en el texto de Login
                Intent activity = new Intent(mySelf, ActivityLogin.class);
                startActivity(activity);
            }
        });
    }
    private void updateUiWithUser(String s) {
        String welcome = getString(R.string.welcome) + " " + s;
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }
//    private void updateUiWithUser(LoggedInUserView model) {
//        String welcome = getString(R.string.welcome) + model.getDisplayName();
//        // TODO : initiate successful logged in experience
//        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
//    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}