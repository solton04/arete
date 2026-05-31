package cz.vse.java.recepty;

import cz.vse.java.recepty.enums.Difficulty;
import cz.vse.java.recepty.enums.RichIn;
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
    @FXML private Button btnFilter;
    @FXML private VBox filterMenuContainer;

    private TypeFood currentTypeFood = TypeFood.BREAKFAST;
    private String currentFilter = "None";
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
        Uzivatel user = SessionManager.getInstance().getCurrentUser();

        for (Recept r : allRecipes) {
            if (r.getTypeFood() == type) {

                // Filtrování receptů na základě MVP logiky (dle PersonalGoal)
                boolean zobrazit = true;
                if (user != null && user.getPersonalGoal() != null) {
                    switch (user.getPersonalGoal()) {
                        case MUSCLE_GAIN:
                            if (r.getRichIn() != cz.vse.java.recepty.enums.RichIn.PROTEIN) zobrazit = false;
                            break;
                        case WEIGHT_LOSS:
                            if (r.getKcal() > 500) zobrazit = false;
                            break;
                        case WEIGHT_GAIN:
                            if (r.getKcal() <= 500) zobrazit = false;
                            break;
                        case IMPROVE_ENERGY:
                            if (r.getRichIn() != cz.vse.java.recepty.enums.RichIn.CARBS) zobrazit = false;
                            break;
                        default:
                            zobrazit = true; // Zobrazí vše pro GENERAL_HEALTH apod.
                    }
                }
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
                    VBox card = new VBox(5);
                    card.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-background-radius: 5; -fx-border-color: #ddd; -fx-border-radius: 5;");
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
            recipesContainer.getChildren().add(new Label("Pro tvůj cíl jsme nenašli žádné recepty."));
        }
    }

    private void openRecipeDetail(Recept r) {
        RecipeDetailController.selectedRecipe = r;
        AppViewManager.getInstance().changeScene("recipe_detail.fxml");
    }

    @FXML
    public void showBreakfast() {
        currentTypeFood = TypeFood.BREAKFAST;

        updateTabs(btnBreakfast);
        renderRecipes(TypeFood.BREAKFAST);
    }

    @FXML
    public void showLunch() {
        currentTypeFood = TypeFood.LUNCH;

        updateTabs(btnLunch);
        renderRecipes(TypeFood.LUNCH);
    }

    @FXML
    public void showDinner() {
        currentTypeFood = TypeFood.DINNER;

        updateTabs(btnDinner);
        renderRecipes(TypeFood.DINNER);
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
        renderRecipes(currentTypeFood);
    }

    @FXML public void filterNone() { applyFilter("None"); }
    @FXML public void filterEasy() { applyFilter("Difficulty: Easy"); }
    @FXML public void filterMedium() { applyFilter("Difficulty: Medium"); }
    @FXML public void filterHard() { applyFilter("Difficulty: Hard"); }
    @FXML public void filterProtein() { applyFilter("Rich in: Protein"); }
    @FXML public void filterCarbs() { applyFilter("Rich in: Carbs"); }
    @FXML public void filterFats() { applyFilter("Rich in: Fats"); }
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
