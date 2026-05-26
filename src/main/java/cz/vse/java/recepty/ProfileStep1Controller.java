package cz.vse.java.recepty;

import cz.vse.java.recepty.logic.Uzivatel;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Label;

/**
 * Kontroler pro první krok vytváření uživatelského profilu.
 *
 *
 * @author Vojtěch Soldán
 * @version 0.1 (26. 5. 2026)
 *
 */
public class ProfileStep1Controller {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField passwordField;
    @FXML private TextField ageField;
    @FXML private RadioButton maleRadio;
    @FXML private ToggleGroup genderGroup;
    @FXML private Label errorLabel;

    @FXML
    public void initialize() {
        // Initialize an empty builder
        SessionManager.getInstance().setCurrentUser(new Uzivatel.Builder().build());
    }

    @FXML
    public void handleNext() {
        errorLabel.setText("");
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String ageStr = ageField.getText();

        if (name == null || name.trim().isEmpty() || name.length() > 16 || !name.matches("^[a-zA-Z0-9 ]+$")) {
            errorLabel.setText("Name must be up to 16 characters and contain no special characters.");
            return;
        }

        if (email == null || !email.contains("@")) {
            errorLabel.setText("Email must contain '@'.");
            return;
        }

        if (password == null || password.trim().isEmpty()) {
            errorLabel.setText("Password cannot be empty.");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageStr);
            if (age <= 0 || age >= 120) {
                errorLabel.setText("Age must be > 0 and < 120.");
                return;
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Age must be a valid number.");
            return;
        }

        Uzivatel user = SessionManager.getInstance().getCurrentUser();
        if (user != null) {
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setAge(age);
            user.setGender(maleRadio.isSelected()); // true for male
        }
        AppViewManager.getInstance().changeScene("profile_step2.fxml");
    }

    @FXML
    public void handleBack() {
        AppViewManager.getInstance().changeScene("login.fxml");
    }
}
