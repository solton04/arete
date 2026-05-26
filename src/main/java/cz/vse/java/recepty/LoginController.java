package cz.vse.java.recepty;

import cz.vse.java.recepty.logic.Uzivatel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import java.util.List;

/**
 * Kontroler pro přihlašovací obrazovku.
 *
 *
 * @author Vojtěch Soldán
 * @version 0.1 (26. 5. 2026)
 *
 */
public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    public void handleSignIn() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email == null || !email.contains("@")) {
            errorLabel.setText("Invalid email format.");
            return;
        }

        if (password == null || password.isEmpty()) {
            errorLabel.setText("Password cannot be empty.");
            return;
        }

        List<Uzivatel> users = SessionManager.getInstance().getRegisteredUsers();
        Uzivatel foundUser = null;
        for (Uzivatel u : users) {
            if (u.getEmail() != null && u.getEmail().equals(email) && u.getPassword() != null && u.getPassword().equals(password)) {
                foundUser = u;
                break;
            }
        }

        if (foundUser != null) {
            SessionManager.getInstance().setCurrentUser(foundUser);
            AppViewManager.getInstance().changeScene("home.fxml");
        } else {
            errorLabel.setText("Invalid email or password.");
        }
    }

    @FXML
    public void handleCreateAccount() {
        AppViewManager.getInstance().changeScene("profile_step1.fxml");
    }
}
