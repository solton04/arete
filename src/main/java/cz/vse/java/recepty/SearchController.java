package cz.vse.java.recepty;

import cz.vse.java.recepty.enums.Difficulty;
import cz.vse.java.recepty.enums.RichIn;
import cz.vse.java.recepty.logic.Recept;
import cz.vse.java.recepty.logic.Uzivatel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.InputStream;

import java.util.List;

/**
 * Kontroler pro obrazovku vyhledávání.
 *
 *
 * @author Vojtěch Soldán
 * @version 0.1 (26. 5. 2026)
 *
 */
public class SearchController {

    @FXML private TextField searchField;
    @FXML private VBox recipesContainer;
    @FXML private VBox filterMenuContainer;

    private String currentFilter = "None";

    @FXML
    public void initialize() {
        renderRecipes("");
    }

    @FXML
    public void handleSearchType() {
        renderRecipes(searchField.getText().toLowerCase());
    }

    @FXML
    public void handleFilter() {
        boolean isVisible = !filterMenuContainer.isVisible();
        filterMenuContainer.setVisible(isVisible);
        filterMenuContainer.setManaged(isVisible);
    }

    private void applyFilter(String filterName) {
        currentFilter = filterName;
        filterMenuContainer.setVisible(false);
        filterMenuContainer.setManaged(false);
        renderRecipes(searchField.getText().toLowerCase());
    }

    @FXML public void filterNone() { applyFilter("None"); }
    @FXML public void filterEasy() { applyFilter("Difficulty: Easy"); }
    @FXML public void filterMedium() { applyFilter("Difficulty: Medium"); }
    @FXML public void filterHard() { applyFilter("Difficulty: Hard"); }
    @FXML public void filterProtein() { applyFilter("Rich in: Protein"); }
    @FXML public void filterCarbs() { applyFilter("Rich in: Carbs"); }
    @FXML public void filterFats() { applyFilter("Rich in: Fats"); }

    private void renderRecipes(String query) {
        recipesContainer.getChildren().clear();
        List<Recept> allRecipes = SessionManager.getInstance().getAllRecipes();
        Uzivatel user = SessionManager.getInstance().getCurrentUser();

        for (Recept r : allRecipes) {
            if (r.getName().toLowerCase().contains(query)) {

                boolean zobrazit = true;

                boolean match = false;
                switch (currentFilter) {
                    case "None":
                        match = true;
                        break;
                    case "Difficulty: Easy":
                        match = (r.getDifficulty() == Difficulty.EASY);
                        break;
                    case "Difficulty: Medium":
                        match = (r.getDifficulty() == Difficulty.MEDIUM);
                        break;
                    case "Difficulty: Hard":
                        match = (r.getDifficulty() == Difficulty.HARD);
                        break;
                    case "Rich in: Protein":
                        match = (r.getRichIn() == RichIn.PROTEIN);
                        break;
                    case "Rich in: Carbs":
                        match = (r.getRichIn() == RichIn.CARBS);
                        break;
                    case "Rich in: Fats":
                        match = (r.getRichIn() == RichIn.FATS);
                        break;
                    default:
                        match = true;
                }

                if (!match) {
                    continue;
                }

                // Vykreslení karty receptu, pokud prošel filtrem
                if (zobrazit) {
                    // Create a recipe card
                    VBox card = new VBox(5);
                    card.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-background-radius: 5; -fx-border-color: #ddd; -fx-border-radius: 5;");

                    // Clicking the card opens recipe detail
                    card.setOnMouseClicked(e -> openRecipeDetail(r));

                    ImageView imageView = new ImageView();
                    imageView.setFitHeight(100);
                    imageView.setFitWidth(100);
                    imageView.setPreserveRatio(true);

                    if (r.getImage() != null) {
                        imageView.setImage(r.getImage());
                    } else {
                        InputStream is = getClass().getResourceAsStream("/cz/vse/java/recepty/images/recipe_placeholder.jpg");
                        if (is != null) imageView.setImage(new Image(is));
                    }

                    Label name = new Label(r.getName());
                    name.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

                    Label time = new Label(r.getPrepareTime() + " mins | " + r.getKcal() + " kcal");
                    time.setStyle("-fx-text-fill: gray;");

                    card.getChildren().addAll(imageView, name, time);
                    recipesContainer.getChildren().add(card);
                }
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