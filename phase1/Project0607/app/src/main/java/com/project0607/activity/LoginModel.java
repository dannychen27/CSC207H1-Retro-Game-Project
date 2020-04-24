package com.project0607.activity;

import android.content.Context;
import android.util.Log;

import com.project0607.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Pattern;

class LoginModel {

    /** The user credentials file to write and read. */
    private static final String LOGIN_CREDENTIALS_FILE = "login_credentials.txt";

    /** A tag for logging output. */
    private static final String TAG = "Login Activity";

    /** A context for our LoginModel, which is a "non-context" activity classes. */
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
     * Record this player's username and password when they log in with a
     * new username (regardless of the password they used).
     *
     * @param username The username this player entered.
     * @param password The password this player entered.
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
     * @param username The username the player has used to sign in.
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
     * @return Whether this username exists or not.
     *
     * @param username The username the player types in.
     */
    boolean userExists(String username) {
        return readFromCredentialsFile(username) != null;
    }

    /**
     * @param password The password this player typed in.
     * @return Whether this password is strong or not.
     */
    boolean isStrong(String password) {
    // What regex satisfies this rule?
    //
    // All new passwords should include, in any order:
    //      - be at least 6 characters long.
    //      - have at least one lowercase letter. (A-Z)
    //      - have at least one uppercase letter. (a-z)
    //      - have at least one digit. (0-9)
    //      - have at least one symbol (e.g. !, @, #, etc.)
    //
    // Hint:
    // [] means accept any contents inside.
    // ^ = not (inside a [])
    // \w = word char (letters rom an alphabet)
    // \d = digits 0 - 9
    // \S = a non-whitespace character (covers every symbol).
    // + = 1 or more or something.
    //
    // must use "\\" in Java regex because \ is a special char.
    //
    // TODO: How to communicate that the only the existence of certain characters and the length matter.
    // not the order of the characters.
    //
    // e.g. return Pattern.compile("[^0-9a-zA-Z]").matcher(password).find();
    //
    // try searching "password regex":
    // https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a
    // or
    // https://www.thepolyglotdeveloper.com/2015/05/use-regex-to-test-password-strength-in-javascript/

    return Pattern.compile("[\\d\\w\\S][\\d\\w\\S][\\d\\w\\S][\\d\\w\\S][\\d\\w\\S][\\d\\w\\S]")
        .matcher(password)
        .find();
    }

    /**
     * Save this player's new username and its corresponding password, and allow the
     * user to log in.
     *
     * @param username The username the player typed in.
     * @param password The password the player typed in.
     *
     * Precondition: password != null.
     */
    void createNewAccount(String username, String password) {
        assert password != null;
        writeToCredentialsFile(username, password);
    }

    /**
     * Check whether the password the player types in matches the correct username password.
     * If not, notify the player that they have entered the wrong password.
     *
     * @param username The username the player typed in.
     * @param password The password the player typed in.
     *
     * Precondition:  password != null.
     */
    boolean checkIfCorrect(String username, String password) {
        assert password != null;
        String correctUserPassword = readFromCredentialsFile(username);
        assert correctUserPassword != null;
        return correctUserPassword.equals(password);
    }

    /**
     * @return The corresponding password to an existing username, or null if it doesn't exist.
     *
     * @param scanner A scanner that reads from LOGIN_CREDENTIALS_FILE.
     * @param username The username this player typed in.
     */
    private String searchForPassword(Scanner scanner, String username) {
        int lineNum = 0;
        while (scanner.hasNextLine()) {
            // Usernames appear on even lines in LOGIN_CREDENTIALS_FILE,
            // and passwords appear on odd lines.
            String line = scanner.nextLine();
            if (!line.equals(username)) {
                lineNum++;
                continue;
            }
            if (lineNum % 2 == 1) {
                lineNum++;
                continue;
            }
            if (scanner.hasNextLine()) {
                return scanner.nextLine();      // The corresponding password.
            }
            lineNum++;
        }
        return null;
    }
}
