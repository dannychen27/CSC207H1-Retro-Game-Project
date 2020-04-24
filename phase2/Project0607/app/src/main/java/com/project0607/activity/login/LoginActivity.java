package com.project0607.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.project0607.R;
import com.project0607.activity.MainActivity;

/**
 * LoginActivity is an implementation of LoginView where it displays contents for a login
 * screen, accepts credentials from the player, and then allows them to either login or
 * register depending on whether they enter a new username or an existing one.
 *
 * Upon successful login, the player is taken to MainActivity. Upon unsuccessful login,
 * the player receives an error message (e.g. weak password, or wrong password), prompting
 * them to log in again.
 */
public class LoginActivity extends AppCompatActivity implements LoginView {

  /** The username this player entered. */
  private EditText usernameEditText;

  /** The password this player entered. */
  private EditText passwordEditText;

  /** The login button. */
  private Button loginButton;

  /** The register button. */
  private Button registerButton;

  /** The login presenter. */
  private LoginPresenter presenter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    displayLoginForm();
    presenter = new LoginPresenter(this, new LoginModel(this));
  }

  /** Attempt to log in once the player clicks the "Login" or "Register" button. */
  private void onClick() {
    tryToLogIn();
  }

  /** Update the status of the login and register buttons based on their contents. */
  public void updateButtonStatus() {
    String username = usernameEditText.getText().toString();
    String password = passwordEditText.getText().toString();
    presenter.checkIfCredentialsExist(username, password);
  }

  /** Attempt to log in using the player's credentials. */
  public void tryToLogIn() {
    String username = usernameEditText.getText().toString();
    String password = passwordEditText.getText().toString();
    presenter.validateCredentials(username, password);
  }

  /** Make the login button visible and clickable. */
  @Override
  public void enableLogInButton() {
    loginButton.setEnabled(true);
    loginButton.setClickable(true);
  }

  /** Make the login button grayed out and unclickable. */
  @Override
  public void disableLogInButton() {
    loginButton.setEnabled(false);
    loginButton.setClickable(false);
  }

  /** Make the register button visible and clickable. */
  @Override
  public void enableRegisterButton() {
    registerButton.setEnabled(true);
    registerButton.setClickable(true);
  }

  /** Make the register button grayed out and unclickable. */
  @Override
  public void disableRegisterButton() {
    registerButton.setEnabled(false);
    registerButton.setClickable(false);
  }

  /**
   * Display an error message when the player proposes a password that doesn't satisfy at
   * least one of the following requirements:
   *
   * All new passwords should include, in any order:
   * - be at least 6 characters long.
   * - have at least one lowercase letter. (a-z)
   * - have at least one uppercase letter. (A-Z)
   * - have at least one digit. (0-9)
   * - have at least one symbol (e.g. !, @, #, etc.)
   */
  @Override
  public void showWeakPasswordError() {
    Toast.makeText(getApplicationContext(), R.string.weak_password, Toast.LENGTH_SHORT).show();
  }

  /**
   * Display an error message when the player enters an existing username, but an incorrect
   * password.
   */
  @Override
  public void showWrongPasswordError() {
    Toast.makeText(getApplicationContext(), R.string.incorrect_password, Toast.LENGTH_SHORT).show();
  }

  /** Allow the player to log in. */
  public void displayMainActivity() {
    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
    startActivity(intent);
  }

  /**
   * Display the username and password fields as well as the login button. The player can log into
   * their game account in the following ways:
   *
   * (1) Clicking the login button. OR
   * (2) Pressing the enter key.*
   *
   * For #2, I was inspired by this StackOverflow post:
   * https://stackoverflow.com/questions/1489852/android-handle-enter-in-an-edittext
   */
  private void displayLoginForm() {
    setUpCredentialFields();
    setUpListeners();
  }

  /**
   * Set up the credential fields. The login and register buttons should
   * initially be disabled.
   */
  private void setUpCredentialFields() {
    usernameEditText = findViewById(R.id.username);
    passwordEditText = findViewById(R.id.password);
    loginButton = findViewById(R.id.login);
    registerButton = findViewById(R.id.register);
  }

  /** Set up listeners. */
  private void setUpListeners() {
    setCredentialChangeListeners();
    setButtonClickListeners();
    setEnterKeyListener();
  }

  /**
   * Allows the buttons to update status (become enabled/disabled and clickable/unclickable)
   * depending on the current contents of the username and password text boxes and whether the
   * player enter an new username or an existing one.
   *
   * This code was inspired by this YouTube video:
   * https://www.youtube.com/watch?v=Vy_4sZ6JVHM
   */
  private void setCredentialChangeListeners() {
    TextWatcher watcher =
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
            updateButtonStatus();
          }

          @Override
          public void afterTextChanged(Editable s) {}
        };
    usernameEditText.addTextChangedListener(watcher);
    passwordEditText.addTextChangedListener(watcher);
  }

  /** Allows the player to log in or register by clicking the login and register buttons. */
  private void setButtonClickListeners() {
    loginButton.setOnClickListener(v -> onClick());
    registerButton.setOnClickListener(v -> onClick());
  }

  /** Allows the player to log in or register by pressing the ENTER key. */
  private void setEnterKeyListener() {
    passwordEditText.setOnKeyListener(
        (v, keyCode, event) -> {
          if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
            tryToLogIn();
            return true;
          }
          return false;
        });
  }
}
