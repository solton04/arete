package cz.vse.java.recepty;

import cz.vse.java.recepty.logic.Uzivatel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ProfileStep2Controller {

    @FXML private TextField weightField;
    @FXML private TextField heightField;

    @FXML
    public void handleNext() {
        Uzivatel user = SessionManager.getInstance().getCurrentUser();
        if (user != null) {
            try {
                user.setWeight(Integer.parseInt(weightField.getText()));
            } catch (NumberFormatException e) {
                user.setWeight(0);
            }
            try {
                user.setHeight(Integer.parseInt(heightField.getText()));
            } catch (NumberFormatException e) {
                user.setHeight(0);
            }
        }
        AppViewManager.getInstance().changeScene("profile_step3.fxml");
    }

    @FXML
    public void handleBack() {
        AppViewManager.getInstance().changeScene("profile_step1.fxml");
    }
}
