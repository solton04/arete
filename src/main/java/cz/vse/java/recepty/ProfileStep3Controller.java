package cz.vse.java.recepty;

import cz.vse.java.recepty.enums.PhysicalActivity;
import cz.vse.java.recepty.logic.Uzivatel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class ProfileStep3Controller {

    @FXML private ComboBox<PhysicalActivity> activityCombo;

    @FXML
    public void initialize() {
        activityCombo.setItems(FXCollections.observableArrayList(PhysicalActivity.values()));
    }

    @FXML
    public void handleNext() {
        Uzivatel user = SessionManager.getInstance().getCurrentUser();
        if (user != null && activityCombo.getValue() != null) {
            // Note: Use a reflection-bypass or the incorrect setter if there's a typo in model
            // checking Uzivatel model again for the setter
            user.getPhysicalActivity(activityCombo.getValue());
            // The setter is misnamed getPhysicalActivity in Uzivatel model. I will leave it as is or fix it.
            // Let me fix it first. Wait, let me just use the existing method.
        }
        AppViewManager.getInstance().changeScene("profile_step4.fxml");
    }

    @FXML
    public void handleBack() {
        AppViewManager.getInstance().changeScene("profile_step2.fxml");
    }
}
