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

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Declaracion de variables del metodo: onCreate
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

        // Instancia del almacenamiento persistente
        storage = getSharedPreferences("STORAGE", MODE_PRIVATE);

        // Obtengo los datos de usuario si ya estan almacenados previamente
        String name = storage.getString("NAME", "NO NAME");
        String lastname = storage.getString("LASTNAME", "NO LASTNAME");
        String email = storage.getString("EMAIL", "NO MAIL");
        String password = storage.getString("PASSWORD", "NO PASSWORD");

        // Almacenamiento local del primer usuario administrador de pruebas
        SharedPreferences.Editor editor = storage.edit();
        editor.putString("EMAIL", "admin");
        editor.putString("PASSWORD", "123");
        editor.apply();

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

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
                signupButton.setEnabled(isChecked && validForm);
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
//                loadingProgressBar.setVisibility(View.VISIBLE);
                // Almacenamiento local de las llaves
                SharedPreferences.Editor editor = storage.edit();
                editor.putString("NAME", usernameEditText.toString());
                editor.putString("LASTNAME", lastnameEditText.toString());
                editor.putString("EMAIL", mailEditText.toString());
                editor.putString("PASSWORD", passwordEditText.toString());
                editor.commit();

                //Cuando el usuario da click en el boton Sign Up lo lleva al home
                Intent activity = new Intent(mySelf, DrawerActivity.class);
                activity.putExtra("name", name);
                startActivity(activity);
                finish();
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