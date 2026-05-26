package cz.vse.java.recepty;

import cz.vse.java.recepty.enums.PersonalGoal;
import cz.vse.java.recepty.logic.Uzivatel;
import cz.vse.java.recepty.logic.Vypocty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * Kontroler pro čtvrtý krok vytváření uživatelského profilu.
 *
 *
 * @author Vojtěch Soldán
 * @version 0.1 (26. 5. 2026)
 *
 */
public class ProfileStep4Controller {

    @FXML private ComboBox<PersonalGoal> goalCombo;
    @FXML private Label errorLabel;

    @FXML
    public void initialize() {
        goalCombo.setItems(FXCollections.observableArrayList(PersonalGoal.values()));
    }

    @FXML
    public void handleNext() {
        errorLabel.setText("");

        if (goalCombo.getValue() == null) {
            errorLabel.setText("Please select your goal.");
            return;
        }

        Uzivatel user = SessionManager.getInstance().getCurrentUser();
        if (user != null) {
            user.setPersonalGoal(goalCombo.getValue());

            Vypocty vypocty = new Vypocty(user);

            // Setting calculated values based on Vypocty
            user.setRecommendedKcal((int) vypocty.vypocetTDEE());
            user.setRecommendedProteins((int) vypocty.vypocetProteiny());
            user.setRecommendedFats((int) vypocty.vypocetTuky());
            user.setRecommendedCarbs((int) vypocty.vypocetSacharidy());
            user.setRecommendedSugars(50); // Hardcoded as Vypocty doesn't compute sugars

            // Add user to registered users list after completing profile setup
            SessionManager.getInstance().addRegisteredUser(user);
        }
        AppViewManager.getInstance().changeScene("profile_step5.fxml");
    }

    @FXML
    public void handleBack() {
        AppViewManager.getInstance().changeScene("profile_step3.fxml");
    }
}
