package cz.vse.java.recepty;

import cz.vse.java.recepty.logic.Uzivatel;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class ProfileStep5Controller {

    @FXML private VBox matrixContainer;

    @FXML
    public void initialize() {
        Uzivatel user = SessionManager.getInstance().getCurrentUser();
        if (user != null) {
            String[] values = {
                String.valueOf(user.getRecommendedKcal()),
                user.getRecommendedProteins() + "g",
                user.getRecommendedFats() + "g",
                user.getRecommendedCarbs() + "g"
            };
            String[] labels = {"kcal", "proteins", "fats", "carbs"};

            matrixContainer.getChildren().add(UIUtils.createMatrix(values, labels));
        }
    }

    @FXML
    public void handleStart() {
        AppViewManager.getInstance().changeScene("home.fxml");
    }

    @FXML
    public void handleBack() {
        AppViewManager.getInstance().changeScene("profile_step4.fxml");
    }
}
