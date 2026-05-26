/**
 * Hlavní modul aplikace pro doporučování receptů.
 * Definuje závislosti na externích knihovnách (JavaFX pro grafické rozhraní, SLF4J pro logování)
 * a zpřístupňuje aplikační balíčky frameworku pro správné načítání a provázání FXML souborů.
 *
 * @author Vojtěch Soldán
 * @version 0.1 (12. 5. 2026)
 */
module cz.vse.java.recepty {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires java.sql;

    opens cz.vse.java.recepty to javafx.fxml;
    exports cz.vse.java.recepty;
}