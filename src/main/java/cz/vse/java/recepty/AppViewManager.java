package cz.vse.java.recepty;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Třída spravující zobrazení a přepínání jednotlivých obrazovek.
 *
 * @author Vojtěch Soldán
 * @version 0.2 (30. 5. 2026)
 */
public class AppViewManager {


    private static final Logger LOG = LoggerFactory.getLogger(AppViewManager.class);

    private static AppViewManager instance;
    private Stage primaryStage;

    private AppViewManager() {
        LOG.debug("Inicializace AppViewManageru.");
    }

    public static AppViewManager getInstance() {
        if (instance == null) {
            LOG.debug("Vytvářím novou instanci singletonu AppViewManager.");
            instance = new AppViewManager();
        }
        return instance;
    }

    public void setPrimaryStage(Stage primaryStage) {
        LOG.debug("Nastavuji primaryStage.");
        this.primaryStage = primaryStage;
    }

    public void changeScene(String fxmlPath) {
        LOG.info("Požadavek na změnu scény na: {}", fxmlPath);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root, 400, 800);

            primaryStage.setScene(scene);
            primaryStage.show();

            LOG.debug("Scéna {} byla úspěšně načtena a zobrazena.", fxmlPath);

        } catch (IOException e) {

            LOG.error("Kritická chyba při načítání FXML souboru: {}", fxmlPath, e);
        }
    }
}