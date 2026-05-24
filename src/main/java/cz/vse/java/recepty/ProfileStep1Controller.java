package cz.vse.java.recepty;

import cz.vse.java.recepty.logic.Uzivatel;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class ProfileStep1Controller {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField passwordField;
    @FXML private TextField ageField;
    @FXML private RadioButton maleRadio;
    @FXML private ToggleGroup genderGroup;

    @FXML
    public void initialize() {
        // Initialize an empty builder
        SessionManager.getInstance().setCurrentUser(new Uzivatel.Builder().build());
    }

    @FXML
    public void handleNext() {
        Uzivatel user = SessionManager.getInstance().getCurrentUser();
        if (user != null) {
            user.setName(nameField.getText());
            user.setEmail(emailField.getText());
            user.setPassword(passwordField.getText());
            try {
                user.setAge(Integer.parseInt(ageField.getText()));
            } catch (NumberFormatException e) {
                user.setAge(0);
            }
            user.setGender(maleRadio.isSelected()); // true for male
        }
        AppViewManager.getInstance().changeScene("profile_step2.fxml");
    }

    @FXML
    public void handleBack() {
        AppViewManager.getInstance().changeScene("login.fxml");
    }
}
