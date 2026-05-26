package cz.vse.java.recepty;

import cz.vse.java.recepty.logic.Ingredience;
import cz.vse.java.recepty.logic.Recept;
import cz.vse.java.recepty.logic.Uzivatel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Map;
import java.net.URL;

/**
 * Kontroler pro obrazovku detailu receptu.
 *
 *
 * @author Vojtěch Soldán
 * @version 0.1 (26. 5. 2026)
 *
 */
public class RecipeDetailController {

    public static Recept selectedRecipe;

    @FXML private Button btnSave;
    @FXML private ImageView recipeImage;
    @FXML private Label nameLabel;
    @FXML private Label tagsLabel;
    @FXML private Label timeLabel;
    @FXML private Label difficultyLabel;
    @FXML private Label portionsLabel;
    @FXML private VBox nutritionMatrixContainer;
    @FXML private VBox ingredientsContainer;
    @FXML private VBox instructionsContainer;

    private int portions = 1;

    @FXML
    public void initialize() {
        if (selectedRecipe != null) {
            // Load placeholder image
            URL imageUrl = getClass().getResource("/cz/vse/java/recepty/images/recipe_placeholder.jpg");
            if (selectedRecipe.getImage() != null) {
                recipeImage.setImage(selectedRecipe.getImage());
            } else if (imageUrl != null) {
                recipeImage.setImage(new Image(imageUrl.toString()));
            }

            nameLabel.setText(selectedRecipe.getName());
            // Build tags string
            StringBuilder tags = new StringBuilder();
            tags.append(selectedRecipe.getTypeFood().toString().toLowerCase()).append(", ");
            if (selectedRecipe.getTags() != null) {
                for (Object tag : selectedRecipe.getTags()) {
                    tags.append(tag.toString()).append(", ");
                }
            }
            if (tags.length() > 0) tags.setLength(tags.length() - 2);
            tagsLabel.setText(tags.toString());

            timeLabel.setText(selectedRecipe.getPrepareTime() + " min");
            difficultyLabel.setText(selectedRecipe.getDifficulty().toString());

            updateNutritionMatrix();
            updateIngredients();

            // Render instructions with checkboxes
            if (selectedRecipe.getInstructions() != null) {
                for (Map.Entry<Integer, String> entry : ((Map<Integer, String>) selectedRecipe.getInstructions()).entrySet()) {
                    CheckBox cb = new CheckBox(entry.getKey() + ". " + entry.getValue());
                    cb.setWrapText(true);
                    instructionsContainer.getChildren().add(cb);
                }
            }

            updateSaveButton();
        }
    }

    private void updateNutritionMatrix() {
        nutritionMatrixContainer.getChildren().clear();
        String[] values = {
            (selectedRecipe.getKcal() * portions) + "",
            (selectedRecipe.getProteins() * portions) + "g",
            (selectedRecipe.getFats() * portions) + "g",
            (selectedRecipe.getCarbs() * portions) + "g"
        };
        String[] labels = {"kcal", "proteins", "fats", "carbs"};
        nutritionMatrixContainer.getChildren().add(UIUtils.createMatrix(values, labels));
    }

    private void updateIngredients() {
        ingredientsContainer.getChildren().clear();
        // The mock recipe doesn't have ingredients list in constructor currently,
        // assuming it could be added. We'll leave it empty or add a dummy one.
        ingredientsContainer.getChildren().add(new Label("Ingredients list not populated in mock."));
    }

    private void updateSaveButton() {
        if (SessionManager.getInstance().getSavedRecipes().contains(selectedRecipe)) {
            btnSave.setText("Unsave");
        } else {
            btnSave.setText("Save");
        }
    }

    @FXML
    public void decreasePortions() {
        if (portions > 1) {
            portions--;
            portionsLabel.setText(String.valueOf(portions));
            updateNutritionMatrix();
        }
    }

    @FXML
    public void increasePortions() {
        portions++;
        portionsLabel.setText(String.valueOf(portions));
        updateNutritionMatrix();
    }

    @FXML
    public void handleBack() {
        AppViewManager.getInstance().changeScene("home.fxml");
    }

    @FXML
    public void handleSave() {
        if (SessionManager.getInstance().getSavedRecipes().contains(selectedRecipe)) {
            SessionManager.getInstance().removeSavedRecipe(selectedRecipe);
        } else {
            SessionManager.getInstance().addSavedRecipe(selectedRecipe);
        }
        updateSaveButton();
    }

    @FXML
    public void handleAddProgress() {
        Uzivatel user = SessionManager.getInstance().getCurrentUser();
        if (user != null) {
            user.setActualKcal(user.getActualKcal() + (selectedRecipe.getKcal() * portions));
            user.setActualProtein(user.getActualProtein() + (selectedRecipe.getProteins() * portions));
            user.setActualFats(user.getActualFats() + (selectedRecipe.getFats() * portions));
            user.setActualCarb(user.getActualCarb() + (selectedRecipe.getCarbs() * portions));
        }
        AppViewManager.getInstance().changeScene("home.fxml");
    }
}
