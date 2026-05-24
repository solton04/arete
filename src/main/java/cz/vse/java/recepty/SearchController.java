package cz.vse.java.recepty;

import cz.vse.java.recepty.logic.Recept;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.List;

public class SearchController {

    @FXML private TextField searchField;
    @FXML private VBox recipesContainer;

    @FXML
    public void initialize() {
        renderRecipes("");
    }

    @FXML
    public void handleSearchType() {
        renderRecipes(searchField.getText().toLowerCase());
    }

    private void renderRecipes(String query) {
        recipesContainer.getChildren().clear();
        List<Recept> allRecipes = SessionManager.getInstance().getAllRecipes();

        for (Recept r : allRecipes) {
            if (r.getName().toLowerCase().contains(query)) {
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
        }
        if (recipesContainer.getChildren().isEmpty()) {
            recipesContainer.getChildren().add(new Label("No recipes match your search."));
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
    public void handleMyRecipes() {
        AppViewManager.getInstance().changeScene("my_recipes.fxml");
    }

    @FXML
    public void handleOptions() {
        AppViewManager.getInstance().changeScene("options.fxml");
    }
}
