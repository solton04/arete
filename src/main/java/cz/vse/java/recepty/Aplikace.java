package cz.vse.java.recepty;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hlavní třída aplikace.
 * Slouží jako hlavní vstupní bod pro spuštění celého programu.
 * @author Vojtěch Soldán
 * @version 0.2 (12. 5. 2026)
 */
public class Aplikace extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(Aplikace.class);

    public static void main(String[] args) {

        if (args.length > 0 && args[0].equals("text")) {
            LOG.info("Spouštím aplikaci v textovém režimu.");

            Platform.exit();
            System.exit(0);

        } else {

            launch(args);
        }
    }
    /**
     * Inicializační metoda životního cyklu JavaFX.
     * Slouží k načtení definice uživatelského rozhraní ze souboru home.fxml,
     * vytvoření hlavní scény a zobrazení okna aplikace s názvem Arete.
     *
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        LOG.info("Inicializuji JavaFX okno...");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("home.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Arete");
        primaryStage.show();
    }
}