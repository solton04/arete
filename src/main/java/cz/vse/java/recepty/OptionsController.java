package cz.vse.java.recepty;

import cz.vse.java.recepty.enums.PersonalGoal;
import cz.vse.java.recepty.enums.PhysicalActivity;
import cz.vse.java.recepty.logic.Uzivatel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class OptionsController {

    @FXML private TextField nameField;
    @FXML private TextField ageField;
    @FXML private TextField weightField;
    @FXML private TextField heightField;
    @FXML private ComboBox<PhysicalActivity> activityCombo;
    @FXML private ComboBox<PersonalGoal> goalCombo;
    @FXML private VBox targetsMatrixContainer;

    @FXML
    public void initialize() {
        activityCombo.setItems(FXCollections.observableArrayList(PhysicalActivity.values()));
        goalCombo.setItems(FXCollections.observableArrayList(PersonalGoal.values()));

        Uzivatel user = SessionManager.getInstance().getCurrentUser();
        if (user != null) {
            nameField.setText(user.getName() != null ? user.getName() : "");
            ageField.setText(String.valueOf(user.getAge()));
            weightField.setText(String.valueOf(user.getWeight()));
            heightField.setText(String.valueOf(user.getHeight()));
            activityCombo.setValue(user.getPhysicalActivity());
            goalCombo.setValue(user.getPersonalGoal());

            updateTargetsMatrix(user);
        }
    }

    private void updateTargetsMatrix(Uzivatel user) {
        targetsMatrixContainer.getChildren().clear();
        String[] values = {
            String.valueOf(user.getRecommendedKcal()),
            user.getRecommendedProteins() + "g",
            user.getRecommendedFats() + "g",
            user.getRecommendedCarbs() + "g"
        };
        String[] labels = {"kcal", "proteins", "fats", "carbs"};
        targetsMatrixContainer.getChildren().add(UIUtils.createMatrix(values, labels));
    }

    @FXML
    public void handleUpdate() {
        Uzivatel user = SessionManager.getInstance().getCurrentUser();
        if (user != null) {
            user.setName(nameField.getText());
            try { user.setAge(Integer.parseInt(ageField.getText())); } catch (Exception e) {}
            try { user.setWeight(Integer.parseInt(weightField.getText())); } catch (Exception e) {}
            try { user.setHeight(Integer.parseInt(heightField.getText())); } catch (Exception e) {}

            if (activityCombo.getValue() != null) user.getPhysicalActivity(activityCombo.getValue());
            if (goalCombo.getValue() != null) user.setPersonalGoal(goalCombo.getValue());

            cz.vse.java.recepty.logic.Vypocty vypocty = new cz.vse.java.recepty.logic.Vypocty(user);
            user.setRecommendedKcal((int) vypocty.vypocetTDEE());
            user.setRecommendedProteins((int) vypocty.vypocetProteiny());
            user.setRecommendedFats((int) vypocty.vypocetTuky());
            user.setRecommendedCarbs((int) vypocty.vypocetSacharidy());

            updateTargetsMatrix(user);
        }
    }

    @FXML
    public void handleHome() {
        AppViewManager.getInstance().changeScene("home.fxml");
    }

    @FXML
    public void handleMyRecipes() {
        AppViewManager.getInstance().changeScene("my_recipes.fxml");
    }

    @FXML
    public void handleSearch() {
        AppViewManager.getInstance().changeScene("search.fxml");
    }
}
