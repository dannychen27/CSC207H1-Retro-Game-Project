package com.project0607.activity;

class LoginPresenter implements LoginRequestObserver {

    /** The login view. */
    private LoginView loginView;    // loginView has an addObserver method, so it's an Observable.

    /** The login interactor. */
    private LoginModel loginModel;  // loginPresenter has to know about loginModel to interact with it.

    /**
     * Create a new login presenter object.
     *
     * @param loginView The login view (That's the VIEW in MVP).
     * @param loginModel The login interactor (That's the MODEL in MVP).
     */
    LoginPresenter(LoginView loginView, LoginModel loginModel) {
        this.loginView = loginView;
        this.loginModel = loginModel;
        loginView.addLoginRequestObserver(this); // presenter cares when the login button is pressed.
    }

    /**
     * Verify whether the player can login in or not.
     *  @param username The username the player typed in.
     * @param password The password the player typed in.
     */
    public void validateCredentials(String username, String password) {
        if (username.trim().equals("")) {
            loginView.showMissingUsernameError();
        } else if (password.trim().equals("")) {
            loginView.showMissingPasswordError();
        } else if (!loginModel.userExists(username)) {
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
}
