package cz.vse.java.recepty;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hlavní třída aplikace zodpovědná za její spuštění.
 *
 *
 * @author Vojtěch Soldán
 * @version 0.1 (26. 5. 2026)
 *
 */
public class Aplikace extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(Aplikace.class);

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("db")) {
            System.setProperty("recepty.use_db", "true");
        } else {
            System.setProperty("recepty.use_db", "false");
        }
        launch(args);
    }

    @Override
    public void init() throws Exception {
        String useDb = System.getProperty("recepty.use_db", "false");
        if ("true".equals(useDb)) {
            LOG.info("Spouštím aplikaci s databází.");
            try {
                cz.vse.java.recepty.logic.DatabaseManager.initDatabase();
                cz.vse.java.recepty.SessionManager.getInstance().loadRecipesFromDatabase();
            } catch (Exception e) {
                LOG.error("Nepodařilo se inicializovat databázi: ", e);
            }
        } else {
            LOG.info("Spouštím aplikaci bez databáze.");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LOG.info("Inicializuji JavaFX okno...");

        AppViewManager viewManager = AppViewManager.getInstance();
        viewManager.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Arete");

        // Start at login screen
        viewManager.changeScene("login.fxml");
    }
}
