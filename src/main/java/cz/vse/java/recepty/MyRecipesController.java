package cz.vse.java.recepty;

import cz.vse.java.recepty.logic.Recept;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Kontroler pro zobrazení mých receptů.
 *
 *
 * @author Vojtěch Soldán
 * @version 0.1 (26. 5. 2026)
 *
 */
public class MyRecipesController {

    @FXML private VBox recipesContainer;

    @FXML
    public void initialize() {
        renderRecipes();
    }

    private void renderRecipes() {
        recipesContainer.getChildren().clear();
        List<Recept> savedRecipes = SessionManager.getInstance().getSavedRecipes();

        for (Recept r : savedRecipes) {
            // Create a recipe card
            VBox card = new VBox(5);
            card.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-background-radius: 5; -fx-border-color: #ddd; -fx-border-radius: 5;");

            // Clicking the card opens recipe detail
            card.setOnMouseClicked(e -> openRecipeDetail(r));

            Label name = new Label(r.getName());
            name.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

            Label time = new Label(r.getPrepareTime() + " mins");
            time.setStyle("-fx-text-fill: gray;");

            card.getChildren().addAll(name, time);
            recipesContainer.getChildren().add(card);
        }
        if (recipesContainer.getChildren().isEmpty()) {
            recipesContainer.getChildren().add(new Label("No saved recipes found."));
        }
    }

    private void openRecipeDetail(Recept r) {
        RecipeDetailController.selectedRecipe = r;
        AppViewManager.getInstance().changeScene("recipe_detail.fxml");
    }

    @FXML
    public void handleHome() {
        AppViewManager.getInstance().changeScene("home.fxml");
    }

    @FXML
    public void handleSearch() {
        AppViewManager.getInstance().changeScene("search.fxml");
    }

    @FXML
    public void handleOptions() {
        AppViewManager.getInstance().changeScene("options.fxml");
    }
}
