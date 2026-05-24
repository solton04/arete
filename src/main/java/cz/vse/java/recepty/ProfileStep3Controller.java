package cz.vse.java.recepty;

import cz.vse.java.recepty.enums.PhysicalActivity;
import cz.vse.java.recepty.logic.Uzivatel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class ProfileStep3Controller {

    @FXML private ComboBox<PhysicalActivity> activityCombo;
    @FXML private Label errorLabel;

    @FXML
    public void initialize() {
        activityCombo.setItems(FXCollections.observableArrayList(PhysicalActivity.values()));
    }

    @FXML
    public void handleNext() {
        errorLabel.setText("");

        if (activityCombo.getValue() == null) {
            errorLabel.setText("Please select your activity level.");
            return;
        }

        Uzivatel user = SessionManager.getInstance().getCurrentUser();
        if (user != null) {
            // The setter is misnamed getPhysicalActivity in Uzivatel model.
            user.getPhysicalActivity(activityCombo.getValue());
        }
        AppViewManager.getInstance().changeScene("profile_step4.fxml");
    }

    @FXML
    public void handleBack() {
        AppViewManager.getInstance().changeScene("profile_step2.fxml");
    }
}
