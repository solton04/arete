package cz.vse.java.recepty;

import cz.vse.java.recepty.logic.Uzivatel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML
    public void handleSignIn() {
        // Mock sign in - simply load the first user and go to home
        Uzivatel mockUser = new Uzivatel.Builder()
            .setAge(25)
            .setGender(true)
            .setWeight(80)
            .setHeight(185)
            .setPhysicalActivity(cz.vse.java.recepty.enums.PhysicalActivity.MODERATE)
            .build();
        mockUser.setName("John Doe");
        SessionManager.getInstance().setCurrentUser(mockUser);
        AppViewManager.getInstance().changeScene("home.fxml");
    }

    @FXML
    public void handleCreateAccount() {
        AppViewManager.getInstance().changeScene("profile_step1.fxml");
    }
}
