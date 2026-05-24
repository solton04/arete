package cz.vse.java.recepty;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
