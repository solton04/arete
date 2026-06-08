package cz.vse.java.recepty;

import cz.vse.java.recepty.logic.Uzivatel;
import cz.vse.java.recepty.logic.Vypocty;
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

        cz.vse.java.recepty.database.UzivatelRepository repo = new cz.vse.java.recepty.database.UzivatelRepository();
        Uzivatel foundUser = repo.loginUzivatel(email, password);

        if (foundUser != null) {
            SessionManager.getInstance().setCurrentUser(foundUser);
            Uzivatel user = SessionManager.getInstance().getCurrentUser();

            Vypocty vypocty = new Vypocty(user);
            user.setRecommendedKcal((int) vypocty.vypocetTDEE());
            user.setRecommendedProteins((int) vypocty.vypocetProteiny());
            user.setRecommendedFats((int) vypocty.vypocetTuky());
            user.setRecommendedCarbs((int) vypocty.vypocetSacharidy());
            user.setRecommendedSugars(50); // dočasně pevná hodnota
            cz.vse.java.recepty.database.ReceptRepository receptRepo = new cz.vse.java.recepty.database.ReceptRepository();
            java.util.List<String> oblibene = receptRepo.getOblibeneRecepty(foundUser.getEmail());
            SessionManager.getInstance().getSavedRecipes().clear(); // vyčištění po případném předchozím loginu

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
