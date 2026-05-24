package cz.vse.java.recepty;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class UIUtils {

    /**
     * Creates a 2-row matrix UI for targets or nutritional info.
     * First row: Values (e.g., "1900", "170g", "50g", "165g")
     * Second row: Labels (e.g., "kcal", "proteins", "fats", "carbs")
     */
    public static GridPane createMatrix(String[] values, String[] labels) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(5);

        for (int i = 0; i < values.length; i++) {
            VBox col = new VBox();
            col.setAlignment(Pos.CENTER);

            Label valLabel = new Label(values[i]);
            valLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

            Label nameLabel = new Label(labels[i]);
            nameLabel.setStyle("-fx-text-fill: gray; -fx-font-size: 12px;");

            col.getChildren().addAll(valLabel, nameLabel);
            grid.add(col, i, 0);
        }

        return grid;
    }
}
