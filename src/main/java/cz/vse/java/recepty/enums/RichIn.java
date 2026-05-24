package cz.vse.java.recepty.enums;
/**
 * Výčtový typ označující hlavní makroživinu, na kterou je daný recept
 * nebo ingredience obzvláště bohatá.
 * Slouží ke snadnému filtrování jídel podle specifických nutričních požadavků uživatele
 * (např. hledání jídel s vysokým obsahem bílkovin).
 *
 * @author Vojtěch Soldán
 * @version 0.1 (9. 5. 2026)
 */
public enum RichIn {
    PROTEIN,
    CARBS,
    FATS
}
