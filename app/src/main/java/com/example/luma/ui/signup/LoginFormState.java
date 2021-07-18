package com.example.luma.ui.signup;

import androidx.annotation.Nullable;

/**
 * Data validation state from the signup form.
 */
class LoginFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer lastnameError;
    @Nullable
    private Integer mailError;
    @Nullable
    private Integer passwordError;
    private boolean isDataValid;

    LoginFormState(@Nullable Integer usernameError, @Nullable Integer lastnameError, @Nullable Integer mailError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.lastnameError = lastnameError;
        this.mailError = mailError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }

    LoginFormState(boolean isDataValid) {
        this.usernameError = null;
        this.lastnameError = null;
        this.mailError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getLastNameError() {
        return lastnameError;
    }

    @Nullable
    Integer getMailError() {
        return mailError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}