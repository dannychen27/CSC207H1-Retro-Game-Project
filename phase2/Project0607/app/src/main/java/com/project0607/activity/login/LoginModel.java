package com.project0607.activity.login;

import android.content.Context;
import android.util.Log;

import com.project0607.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * LoginModel handles storing and retrieving player credentials information,
 * and then reports its output to the LoginPresenter.
 */
class LoginModel {

  /** The user credentials file to write and read. */
  private static final String LOGIN_CREDENTIALS_FILE = "login_credentials.txt";

  /** A tag for logging output. */
  private static final String TAG = "Login Activity";

  /** A context for our LoginModel class. */
  private Context context;

  /**
   * Create a new login model.
   *
   * @param context The context of the login activity.
   */
  LoginModel(Context context) {
    this.context = context;
  }

  /**
   * Save this player's new username and its corresponding password into LOGIN_CREDENTIALS_FILE.
   *
   * @param username The username the player entered.
   * @param password The password the player entered.
   *
   * Precondition: password is a non-empty string.
   */
  void createNewAccount(String username, String password) {
    writeToCredentialsFile(username, password);
  }

  /**
   * Store this player's username and password when they log in with a new username.
   *
   * @param username The username this player entered.
   * @param password The password this player entered.
   *
   * Precondition: The user registers a strong password.
   */
  private void writeToCredentialsFile(String username, String password) {
    PrintWriter out;
    try {
      OutputStream outStream = context.openFileOutput(LOGIN_CREDENTIALS_FILE, Context.MODE_APPEND);
      out = new PrintWriter(outStream);
      out.println(username);
      out.println(password);
      out.close();
    } catch (FileNotFoundException e) {
      Log.e(TAG, R.string.problem_opening_file_for_writing + LOGIN_CREDENTIALS_FILE);
    }
  }

  /**
   * @return The password corresponding to the given username, or null otherwise.
   * @param username The username the player entered.
   */
  private String readFromCredentialsFile(String username) {
    try (Scanner scanner = new Scanner(context.openFileInput(LOGIN_CREDENTIALS_FILE))) {
      return searchForPassword(scanner, username);
    } catch (IOException e) {
      Log.e(TAG, R.string.problem_opening_file_for_reading + LOGIN_CREDENTIALS_FILE);
    }
    return null;
  }

  /**
   * @return The corresponding password to an existing username, or null if it doesn't exist.
   * @param scanner A scanner that reads from LOGIN_CREDENTIALS_FILE.
   * @param username The username this player typed in.
   */
  private String searchForPassword(Scanner scanner, String username) {
    int lineNum = 0;
    while (scanner.hasNextLine()) {
      // Usernames appear on even lines in LOGIN_CREDENTIALS_FILE,
      // and passwords appear on odd lines.
      String line = scanner.nextLine();
      if (!line.equals(username)) { // Not the right username.
        lineNum++;
        continue;
      }

      if (lineNum % 2 == 1) { // It's a password.
        lineNum++;
        continue;
      }

      if (scanner.hasNextLine()) { // I found the right username.
        return scanner.nextLine(); // Here's the corresponding password.
      }

      lineNum++;
    }
    return null;
  }

  /**
   * @return Whether this username exists or not.
   *
   * @param username The username the player types in.
   *
   * Precondition: password is a non-empty string.
   */
  @SuppressWarnings("BooleanMethodIsAlwaysInverted")
  boolean userExists(String username) {
    return readFromCredentialsFile(username) != null;
  }

  /**
   * @return Whether this password is strong or not.
   *
   * A strong password satisfies these requirements:
   * - has at least 6 characters.
   * - has at least one lowercase letter. (a-z)
   * - has at least one uppercase letter. (A-Z)
   * - has at least one digit. (0-9)
   * - has at least one symbol. (e.g. !, @, #, etc.)
   *
   * @param password The password this player typed in.
   *
   * Precondition: password is a non-empty string.
   */
  boolean isStrong(String password) {
    boolean hasAtLeastSixChars = Pattern.compile(".{6,}").matcher(password).find();
    boolean hasAtLeastOneLowercase = Pattern.compile("[a-z]+").matcher(password).find();
    boolean hasAtLeastOneUppercase = Pattern.compile("[A-Z]+").matcher(password).find();
    boolean hasAtLeastOneDigit = Pattern.compile("[0-9]+").matcher(password).find();
    boolean hasAtLeastOneSymbol = Pattern.compile("^[A-Za-z0-9\\s]+").matcher(password).find();
    return hasAtLeastSixChars && hasAtLeastOneLowercase && hasAtLeastOneUppercase
            && hasAtLeastOneDigit && hasAtLeastOneSymbol;
  }

  /**
   * Check whether these credentials that the player enters matches the ones found in
   * LOGIN_CREDENTIALS_FILE.
   *
   * @param username The username the player entered.
   * @param password The password the player entered.
   *
   * Precondition: password is a non-empty string.
   */
  boolean checkIfCorrect(String username, String password) {
    String correctUserPassword = readFromCredentialsFile(username);
    assert correctUserPassword != null;
    return correctUserPassword.equals(password);
  }
}
