package cz.vse.java.recepty;

import cz.vse.java.recepty.enums.TypeFood;
import cz.vse.java.recepty.logic.Recept;
import cz.vse.java.recepty.logic.Uzivatel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.InputStream;

import java.util.List;

/**
 * Kontroler pro domovskou obrazovku aplikace.
 *
 *
 * @author Vojtěch Soldán
 * @version 0.1 (26. 5. 2026)
 *
 */
public class HomeController {

    @FXML private Label helloLabel;
    @FXML private Label goalLabel;
    @FXML private VBox targetsMatrixContainer;
    @FXML private VBox progressMatrixContainer;
    @FXML private VBox recipesContainer;

    @FXML private Button btnBreakfast;
    @FXML private Button btnLunch;
    @FXML private Button btnDinner;

    @FXML
    public void initialize() {
        Uzivatel user = SessionManager.getInstance().getCurrentUser();
        if (user != null) {
            helloLabel.setText("Hello " + (user.getName() != null ? user.getName() : "User") + "!");
            goalLabel.setText(user.getPersonalGoal() != null ? user.getPersonalGoal().toString().replace("_", " ") : "NO GOAL");

            // Render targets matrix
            String[] targetValues = {
                String.valueOf(user.getRecommendedKcal()),
                user.getRecommendedProteins() + "g",
                user.getRecommendedFats() + "g",
                user.getRecommendedCarbs() + "g"
            };
            String[] labels = {"kcal", "proteins", "fats", "carbs"};
            targetsMatrixContainer.getChildren().add(UIUtils.createMatrix(targetValues, labels));

            // Render progress matrix
            String[] progressValues = {
                String.valueOf(user.getActualKcal()),
                user.getActualProtein() + "g",
                user.getActualFats() + "g",
                user.getActualCarb() + "g"
            };
            progressMatrixContainer.getChildren().add(UIUtils.createMatrix(progressValues, labels));
        }

        // Default show breakfast
        showBreakfast();
    }

    private void updateTabs(Button activeBtn) {
        String activeStyle = "-fx-background-color: #2196F3; -fx-text-fill: white;";
        String inactiveStyle = "-fx-background-color: transparent; -fx-border-color: #ccc; -fx-text-fill: black;";

        btnBreakfast.setStyle(inactiveStyle);
        btnLunch.setStyle(inactiveStyle);
        btnDinner.setStyle(inactiveStyle);

        activeBtn.setStyle(activeStyle);
    }

    private void renderRecipes(TypeFood type) {
        recipesContainer.getChildren().clear();
        List<Recept> allRecipes = SessionManager.getInstance().getAllRecipes();

        for (Recept r : allRecipes) {
            if (r.getTypeFood() == type) {
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
                    // Použijeme placeholder z resources
                    InputStream is = getClass().getResourceAsStream("/cz/vse/java/recepty/images/recipe_placeholder.jpg");
                    if (is != null) {
                        imageView.setImage(new Image(is));
                    }
                }
                Label name = new Label(r.getName());
                name.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

                Label time = new Label(r.getPrepareTime() + " mins");
                time.setStyle("-fx-text-fill: gray;");

                card.getChildren().addAll(imageView, name, time);
                recipesContainer.getChildren().add(card);
            }
        }
        if (recipesContainer.getChildren().isEmpty()) {
            recipesContainer.getChildren().add(new Label("No recipes found."));
        }
    }

    private void openRecipeDetail(Recept r) {
        // We will store the selected recipe in a static variable or a service to pass it.
        // But the easiest way without modifying too many classes is passing it via a controller.
        // Let's create a temporary SelectedRecipeHolder or add to SessionManager.
        // We'll add it to SessionManager shortly.
        RecipeDetailController.selectedRecipe = r;
        AppViewManager.getInstance().changeScene("recipe_detail.fxml");
    }

    @FXML
    public void showBreakfast() {
        updateTabs(btnBreakfast);
        renderRecipes(TypeFood.BREAKFAST);
    }

    @FXML
    public void showLunch() {
        updateTabs(btnLunch);
        renderRecipes(TypeFood.LUNCH);
    }

    @FXML
    public void showDinner() {
        updateTabs(btnDinner);
        renderRecipes(TypeFood.DINNER);
    }

    @FXML
    public void handleMyRecipes() {
        AppViewManager.getInstance().changeScene("my_recipes.fxml");
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
