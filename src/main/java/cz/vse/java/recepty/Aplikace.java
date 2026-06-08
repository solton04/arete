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
 * @version 0.3 (30. 5. 2026)
 *
 */
public class Aplikace extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(Aplikace.class);

   public static void main(String[] args) {

        launch(args);
    }

    /**
     * Inicializační metoda aplikace. Spouští se před startem samotného grafického rozhraní.
     * Zde testujeme, zda se nám podaří připojit k MySQL databázi. Nechceme už používat SQLite.
     */
    @Override
    public void init() throws Exception {
        LOG.info("Spouštím aplikaci a testuji připojení k databázi");

        /* Zkusíme navázat spojení pomocí našeho DatabaseConnection (MySQL) */
        try (java.sql.Connection conn = cz.vse.java.recepty.database.DatabaseConnection.getConnection()) {
            if (conn != null) {
                LOG.info("Úspěšně připojeno k databázi");
            }
        } catch (Exception e) {
            LOG.error("Nepodařilo se připojit k databázi", e);
        }

        /* Načtení receptů z databáze do globálního SessionManageru ihned po startu */
        SessionManager.getInstance().loadRecipesFromDatabase();
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
