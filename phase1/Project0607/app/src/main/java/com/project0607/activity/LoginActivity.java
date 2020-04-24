package com.project0607.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project0607.R;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements LoginView {

    /** The username this player entered. */
    private EditText usernameEditText;

    /** The password this player entered. */
    private EditText passwordEditText;

    /** A list of observers observing the login button. */
    private List<LoginRequestObserver> loginRequestObservers = new ArrayList<>();

    // A good rule of thumb:
    //  (1) the view and model should not contain any if-statements, unless         DONE!
    //      it's checking the type of a key event (accepting and processing
    //      user input) -- it's an extension to handling user input, not logic.
    //      If you use if-statements to control logic, delegate the logic to the
    //      presenter:
    //          e.g. "Hey view, can you tell me when the user wants to log in?"
    //                  and "Hey model, does this user exist?"
    //                  and "Hey model, create this user."
    //  (2) the model should not use observer, observable. It should just have      DONE!
    //     small methods that return stuff.
    //      So observer-observable should just be presenter and view.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        displayLoginForm();

        // Q: How would I create the presenter if there's no main method?
        // A: onCreate is like a main method. Therefore, it needs some implementation
        // details to use dependency injection. The main method creates the dependencies
        // and injects them where needed. This violates the dependency inversion principle,
        // but this method requires some information about LoginPresenter and
        // LoginModel (the fact that it exists).
        new LoginPresenter(this, new LoginModel(this));
    }

    /** Attempt to log in once the player clicks the "Login/Register" button. */
    public void onClick() {
        notifyLoginRequestObservers();
    }

    @Override
    public void addLoginRequestObserver(LoginRequestObserver loginRequestObserver) {
        loginRequestObservers.add(loginRequestObserver);
    }

    private void notifyLoginRequestObservers() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        for (LoginRequestObserver loginRequestObserver : loginRequestObservers) {
            loginRequestObserver.validateCredentials(username, password);
        }
    }

    /**
     * Display an error message when the player leaves the username field blank when they try
     * to log in.
     */
    public void showMissingUsernameError() {
        Toast.makeText(getApplicationContext(), R.string.missing_username, Toast.LENGTH_SHORT).show();
    }

    /**
     * Display an error message when the player leaves the password field blank when they try
     * to log in.
     */
    public void showMissingPasswordError() {
        Toast.makeText(getApplicationContext(), R.string.missing_password, Toast.LENGTH_SHORT).show();
    }

    /**
     * Display an error message when the player proposes a password that doesn't satisfy at
     * least one of the following requirements:
     *
     * All new passwords should include, in any order:
     *    - be at least 6 characters long.
     *    - have at least one lowercase letter. (A-Z)
     *    - have at least one uppercase letter. (a-z)
     *    - have at least one digit. (0-9)
     *    - have at least one symbol (e.g. !, @, #, etc.)
     */
    @Override
    public void showWeakPasswordError() {
        Toast.makeText(getApplicationContext(), R.string.weak_password, Toast.LENGTH_SHORT).show();
    }

    /**
     * Display an error message when the player enters an existing username, but an incorrect
     * password.
     */
    public void showWrongPasswordError() {
        Toast.makeText(getApplicationContext(), R.string.incorrect_password, Toast.LENGTH_SHORT).show();
    }

    /** Allow the player to sign in. */
    public void displayMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

  /**
   * Display the username and password fields and login button. The player can log into
   * their game account in the following ways:
   *
   *    (1) Clicking the login button.
   *    (2) Pressing the enter key.*
   *
   *    * For #2, I was inspired by this StackOverflow post:
   *    https://stackoverflow.com/questions/1489852/android-handle-enter-in-an-edittext
   */
  private void displayLoginForm() {
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);

        // Enables login by clicking on the "login" button.
        loginButton.setOnClickListener(v -> onClick());

        // Enables login by pressing the enter key.
        passwordEditText.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                notifyLoginRequestObservers();
                return true;
            }
            return false;
        });
    }
}