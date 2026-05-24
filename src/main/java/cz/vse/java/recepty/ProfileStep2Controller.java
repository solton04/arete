package cz.vse.java.recepty;

import cz.vse.java.recepty.logic.Uzivatel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

public class ProfileStep2Controller {

    @FXML private TextField weightField;
    @FXML private TextField heightField;
    @FXML private Label errorLabel;

    @FXML
    public void handleNext() {
        errorLabel.setText("");
        String weightStr = weightField.getText();
        String heightStr = heightField.getText();

        int weight;
        try {
            weight = Integer.parseInt(weightStr);
            if (weight < 20 || weight > 400) {
                errorLabel.setText("Weight must be between 20 kg and 400 kg.");
                return;
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Weight must be a valid number.");
            return;
        }

        int height;
        try {
            height = Integer.parseInt(heightStr);
            if (height < 80 || height > 250) {
                errorLabel.setText("Height must be between 80 cm and 250 cm.");
                return;
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Height must be a valid number.");
            return;
        }

        Uzivatel user = SessionManager.getInstance().getCurrentUser();
        if (user != null) {
            user.setWeight(weight);
            user.setHeight(height);
        }
        AppViewManager.getInstance().changeScene("profile_step3.fxml");
    }

    @FXML
    public void handleBack() {
        AppViewManager.getInstance().changeScene("profile_step1.fxml");
    }
}
