package cz.vse.java.recepty;

import cz.vse.java.recepty.enums.PersonalGoal;
import cz.vse.java.recepty.logic.Uzivatel;
import cz.vse.java.recepty.logic.Vypocty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class ProfileStep4Controller {

    @FXML private ComboBox<PersonalGoal> goalCombo;

    @FXML
    public void initialize() {
        goalCombo.setItems(FXCollections.observableArrayList(PersonalGoal.values()));
    }

    @FXML
    public void handleNext() {
        Uzivatel user = SessionManager.getInstance().getCurrentUser();
        if (user != null && goalCombo.getValue() != null) {
            user.setPersonalGoal(goalCombo.getValue());

            // Do dummy calculation. In reality, would use user.getAge(), weight, height
            // Let's create a temporary Vypocty with the user's data (if the Vypocty class allowed it)
            // But Vypocty has hardcoded user creation in its constructor/fields.
            // Let's just set some calculated values based on generic formulas or Vypocty

            // Setting values for demo
            user.setRecommendedKcal(2000);
            user.setRecommendedProteins(150);
            user.setRecommendedFats(60);
            user.setRecommendedCarbs(200);
            user.setRecommendedSugars(50);
        }
        AppViewManager.getInstance().changeScene("profile_step5.fxml");
    }

    @FXML
    public void handleBack() {
        AppViewManager.getInstance().changeScene("profile_step3.fxml");
    }
}
