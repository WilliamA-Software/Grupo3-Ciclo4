package com.example.luma.ui.signup;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.luma.R;
import com.example.luma.data.LoginRepository;
import com.example.luma.data.Result;
import com.example.luma.data.model.LoggedInUser;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void signup(String name, String lastname, String mail, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(mail, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getName(), data.getLastName(), data.getMail(), data.getPassword())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginDataChanged(String name, String lastname, String mail, String password) {
        if (!isNameValid(name)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_name, null, null, null));
        } else if (!isNameValid(lastname)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_name, null, null));
        } else if (!isMailValid(mail)) {
            loginFormState.setValue(new LoginFormState(null, null, R.string.invalid_mail, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, null, null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A name and lastname validation check
    private boolean isNameValid(String s) {
        if (s == null) {
            return false;
        }
        //return (!username.trim().isEmpty() || !lastname.trim().isEmpty());
        return s.matches("[A-Za-z. _-]+");
    }

    // A mail structure validation check
    private boolean isMailValid(String mail) {
        if (mail == null) {
            return false;
        }
        if (mail.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(mail).matches();
        } else {
            return false;
        }
    }

    // A password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 6;
    }
}