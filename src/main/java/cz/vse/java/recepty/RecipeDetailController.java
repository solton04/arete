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
    @FXML private Button btnProgress;
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
            Map<String, Integer> consumed = SessionManager.getInstance().getConsumedRecipes();
            if (consumed.containsKey(selectedRecipe.getName())) {
                portions = consumed.get(selectedRecipe.getName());
            } else {
                portions = 1;
            }
            portionsLabel.setText(String.valueOf(portions));

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
            updateProgressButton();
        }
    }

    private void updateProgressButton() {
        if (SessionManager.getInstance().getConsumedRecipes().containsKey(selectedRecipe.getName())) {
            btnProgress.setText("Odstranit z příjmu");
            btnProgress.setStyle("-fx-background-color: #D32F2F; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10;");
        } else {
            btnProgress.setText("Přidat");
            btnProgress.setStyle("-fx-background-color: #43927D; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10;");
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

        String sql = "SELECT i.name, i.amount, i.unit FROM ingredience i JOIN recept r ON i.recept_id = r.id WHERE r.name = ?";

        try (java.sql.Connection conn = cz.vse.java.recepty.database.DatabaseConnection.getConnection();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, selectedRecipe.getName());
            java.sql.ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String amountStr = rs.getString("amount");
                String unit = rs.getString("unit");

                if (unit == null || unit.equals("nan") || unit.isEmpty()) unit = "";


                String finalAmount = amountStr;
                try {
                    if (amountStr != null && !amountStr.isEmpty()) {

                        double baseAmount = Double.parseDouble(amountStr);

                        double recalculatedAmount = baseAmount * portions;


                        if (recalculatedAmount == (long) recalculatedAmount) {
                            finalAmount = String.format("%d", (long) recalculatedAmount);
                        } else {
                            finalAmount = String.valueOf(recalculatedAmount);
                        }
                    }
                } catch (NumberFormatException e) {

                }

                String textSuroviny = finalAmount + " " + unit + " " + rs.getString("name");
                ingredientsContainer.getChildren().add(new javafx.scene.control.Label("• " + textSuroviny));
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        if (ingredientsContainer.getChildren().isEmpty()) {
            ingredientsContainer.getChildren().add(new javafx.scene.control.Label("Žádné suroviny nenalezeny."));
        }
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
            updateIngredients();
        }
    }

    @FXML
    public void increasePortions() {
        portions++;
        portionsLabel.setText(String.valueOf(portions));
        updateNutritionMatrix();
        updateIngredients();
    }

    @FXML
    public void handleBack() {
        AppViewManager.getInstance().changeScene("home.fxml");
    }

    @FXML
    public void handleSave() {
        Uzivatel user = SessionManager.getInstance().getCurrentUser();
        cz.vse.java.recepty.database.ReceptRepository repo = new cz.vse.java.recepty.database.ReceptRepository();

        if (SessionManager.getInstance().getSavedRecipes().contains(selectedRecipe)) {
            SessionManager.getInstance().removeSavedRecipe(selectedRecipe);
            if (user != null) repo.removeOblibenyRecept(user.getEmail(), selectedRecipe.getName());
        } else {
            SessionManager.getInstance().addSavedRecipe(selectedRecipe);
            if (user != null) repo.saveOblibenyRecept(user.getEmail(), selectedRecipe.getName());
        }
        updateSaveButton();
    }

    @FXML
    public void handleAddProgress() {
        Uzivatel user = SessionManager.getInstance().getCurrentUser();
        if (user != null) {
            Map<String, Integer> consumed = SessionManager.getInstance().getConsumedRecipes();
            if (consumed.containsKey(selectedRecipe.getName())) {
                int savedPortions = consumed.get(selectedRecipe.getName());
                user.setActualKcal(user.getActualKcal() - (selectedRecipe.getKcal() * savedPortions));
                user.setActualProtein(user.getActualProtein() - (selectedRecipe.getProteins() * savedPortions));
                user.setActualFats(user.getActualFats() - (selectedRecipe.getFats() * savedPortions));
                user.setActualCarb(user.getActualCarb() - (selectedRecipe.getCarbs() * savedPortions));
                SessionManager.getInstance().removeConsumedRecipe(selectedRecipe.getName());
            } else {
                user.setActualKcal(user.getActualKcal() + (selectedRecipe.getKcal() * portions));
                user.setActualProtein(user.getActualProtein() + (selectedRecipe.getProteins() * portions));
                user.setActualFats(user.getActualFats() + (selectedRecipe.getFats() * portions));
                user.setActualCarb(user.getActualCarb() + (selectedRecipe.getCarbs() * portions));
                SessionManager.getInstance().addConsumedRecipe(selectedRecipe.getName(), portions);
            }

            cz.vse.java.recepty.database.UzivatelRepository repo = new cz.vse.java.recepty.database.UzivatelRepository();
            repo.updateActualProgress(user);
            updateProgressButton();
        }
    }
}
