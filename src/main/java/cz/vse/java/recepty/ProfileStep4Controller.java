package cz.vse.java.recepty;

import cz.vse.java.recepty.enums.PersonalGoal;
import cz.vse.java.recepty.logic.Uzivatel;
import cz.vse.java.recepty.logic.Vypocty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * Kontroler pro čtvrtý krok vytváření uživatelského profilu.
 *
 *
 * @author Vojtěch Soldán
 * @version 0.1 (26. 5. 2026)
 *
 */
public class ProfileStep4Controller {

    @FXML private ComboBox<PersonalGoal> goalCombo;
    @FXML private Label errorLabel;

    @FXML
    public void initialize() {
        goalCombo.setItems(FXCollections.observableArrayList(PersonalGoal.values()));
    }

    /**
     * Zpracovává kliknutí na tlačítko "Další" (Next) ve čtvrtém kroku registrace.
     * Ověří výběr cíle, vypočítá doporučené nutriční hodnoty pomocí třídy Vypocty
     * a následně nového uživatele trvale uloží do MySQL databáze.
     */
    @FXML
    public void handleNext() {
        errorLabel.setText("");

        /* Kontrola, zda uživatel vybral nějaký cíl */
        if (goalCombo.getValue() == null) {
            errorLabel.setText("Please select your goal.");
            return;
        }

        /* Načtení aktuálně vytvářeného uživatele z dočasné paměti */
        Uzivatel user = SessionManager.getInstance().getCurrentUser();
        if (user != null) {
            /* Uložení vybraného cíle do objektu uživatele */
            user.setPersonalGoal(goalCombo.getValue());

            /* Výpočet doporučených hodnot na základě zadaných údajů */
            Vypocty vypocty = new Vypocty(user);
            user.setRecommendedKcal((int) vypocty.vypocetTDEE());
            user.setRecommendedProteins((int) vypocty.vypocetProteiny());
            user.setRecommendedFats((int) vypocty.vypocetTuky());
            user.setRecommendedCarbs((int) vypocty.vypocetSacharidy());
            user.setRecommendedSugars(50); // Pevně daná hodnota, jelikož Vypocty cukry neřeší

            /* ULOŽENÍ DO DATABÁZE: Zápis vytvořeného uživatele do MySQL */
            cz.vse.java.recepty.database.UzivatelRepository repo = new cz.vse.java.recepty.database.UzivatelRepository();
            repo.saveUzivatel(user);

            /* Přidání uživatele do lokálního seznamu (pro jistotu, aby fungovaly i ostatní části aplikace) */
            SessionManager.getInstance().addRegisteredUser(user);
        }

        /* Přechod na závěrečnou obrazovku profilu */
        AppViewManager.getInstance().changeScene("profile_step5.fxml");
    }

    @FXML
    public void handleBack() {
        AppViewManager.getInstance().changeScene("profile_step3.fxml");
    }
}
