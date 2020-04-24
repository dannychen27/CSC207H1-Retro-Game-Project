/*
 *
 *  * Copyright (C) 2018 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.project0607.activity.login;

/**
 * LoginView handles displaying various actions, such as enabling or disabling login and register
 * buttons, displaying various login error messages upon a unsuccessful login, and granting the
 * player access to MainActivity upon successful login.
 */
interface LoginView {

  /** Update the status of the login and register buttons based on whether they are empty or not. */
  void updateButtonStatus();

  /** Attempt to log in using the player's credentials. */
  void tryToLogIn();

  /**
   * Display an error message when the player proposes a password that doesn't satisfy at least one
   * of the following requirements:
   *
   * <p>All new passwords should include, in any order: - be at least 6 characters long. - have at
   * least one lowercase letter. (a-z) - have at least one uppercase letter. (A-Z) - have at least
   * one digit. (0-9) - have at least one symbol (e.g. !, @, #, etc.)
   */
  void showWeakPasswordError();

  /**
   * Display an error message when the player enters an existing username, but an incorrect
   * password.
   */
  void showWrongPasswordError();

  /** Allow the player to sign in. */
  void displayMainActivity();

  /** Make the login button visible and clickable. */
  void enableLogInButton();

  /** Make the login button faded and unclickable. */
  void disableLogInButton();

  /** Make the register button visible and clickable. */
  void enableRegisterButton();

  /** Make the register button faded and unclickable. */
  void disableRegisterButton();
}
