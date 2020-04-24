package com.project0607.activity.login;

/**
 * LoginPresenter serves as the mediator between LoginActivity and LoginModel by querying
 * information from the LoginModel to control what happens in LoginActivity.
 */
class LoginPresenter {

  /** The login view. */
  private LoginView loginView;

  /** The login model. */
  private LoginModel loginModel;

  /**
   * Create a new login presenter object.
   *
   * @param loginView The login view.
   * @param loginModel The login model.
   */
  LoginPresenter(LoginView loginView, LoginModel loginModel) {
    this.loginView = loginView;
    this.loginModel = loginModel;
  }

  /**
   * Verify whether the player's credentials exist and is correct.
   *
   * @param username The username the player entered.
   * @param password The password the player entered.
   */
  void validateCredentials(String username, String password) {
    if (!loginModel.userExists(username)) {
      if (loginModel.isStrong(password)) {
        loginModel.createNewAccount(username, password);
        loginView.displayMainActivity();
      } else {
        loginView.showWeakPasswordError();
      }
    } else if (!loginModel.checkIfCorrect(username, password)) {
      loginView.showWrongPasswordError();
    } else {
      loginView.displayMainActivity();
    }
  }

  /**
   * Verify that these credentials exist. Then update the login and register buttons accordingly.
   *
   * @param username The username this player entered.
   * @param password The password this player entered.
   */
  void checkIfCredentialsExist(String username, String password) {
    if (isEmpty(username) || isEmpty(password)) {
      loginView.disableLogInButton();
      loginView.disableRegisterButton();
    } else if (!loginModel.userExists(username)) {
      loginView.disableLogInButton();
      loginView.enableRegisterButton();
    } else {
      loginView.enableLogInButton();
      loginView.disableRegisterButton();
    }
  }

  /**
   * @return Whether the credential (without surrounding whitespace) is blank.
   *
   * @param credential The credential the player enters.
   */
  private boolean isEmpty(String credential) {
    return credential.trim().equals("");
  }
}
