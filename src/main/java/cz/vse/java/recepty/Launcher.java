package cz.vse.java.recepty;

/**
 * Falešná spouštěcí třída nutná pro správný build do spustitelného JAR souboru.
 * Obchází problém chybějících JavaFX komponent při startu.
 */
public class Launcher {

    public static void main(String[] args) {
        // Zde pouze zavoláme main metodu z naší skutečné JavaFX třídy
        Aplikace.main(args);
    }
}