package cz.vse.java.recepty.logic;

import cz.vse.java.recepty.enums.PhysicalActivity;

/**
 * Třída Vypocty obsahuje metody pro výpočet bazálního metabolického výdeje (BMR),
 * celkového denního energetického výdeje (TDEE) a doporučeného rozdělení makroživin.
 *
 * @author Lukáš Kucharík
 * @version 0.1 (17. 5. 2026)
 */
public class Vypocty {

    private Uzivatel user;

    public Vypocty(Uzivatel user) {
        this.user = user;
    }

    /**
     * Výpočet bazálního metabolického výdeje (BMR) pomocí Harris-Benedictovy rovnice.
     */
    public double vypocetBMR() {
        int age = user.getAge();
        int height = user.getHeight();
        int weight = user.getWeight();

        if (user.isGender()) {       // Male
            return 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {            // Female
            return 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }
    }

    /**
     * Výpočet celkového denního energetického výdeje (TDEE) pomocí Harris-Benedictovy rovnice.
     */
    public double vypocetTDEE() {
        PhysicalActivity physicalActivity = user.getPhysicalActivity();
        if (physicalActivity == null) return vypocetBMR() * 1.2;

        if (physicalActivity == PhysicalActivity.SEDENTARY) {
            return vypocetBMR() * 1.2;
        } else if (physicalActivity == PhysicalActivity.LIGHT) {
            return vypocetBMR() * 1.375;
        } else if (physicalActivity == PhysicalActivity.MODERATE) {
            return vypocetBMR() * 1.465;
        } else if (physicalActivity == PhysicalActivity.ACTIVE) {
            return vypocetBMR() * 1.55;
        } else if (physicalActivity == PhysicalActivity.VERY_ACTIVE) {
            return vypocetBMR() * 1.725;
        } else {                                    // EXTRA_ACTIVE
            return vypocetBMR() * 1.9;
        }
    }

    /**
     * Výpočet celkového denního přijmu proteinu v gramech.
     */
    public double vypocetProteiny() {
        double tdee = vypocetTDEE();
        return (tdee * 0.3) / 4;    // 30% kalorií z bílkovin, 1g bílkovin = 4 kalorie
    }

    /**
     * Výpočet celkového denního přijmu tuků v gramech.
     */
    public double vypocetTuky() {
        double tdee = vypocetTDEE();
        return (tdee * 0.25) / 9;   // 25% kalorií z tuků, 1g tuků = 9 kalorie
    }

    /**
     * Výpočet celkového denního přijmu sacharidů v gramech.
     */
    public double vypocetSacharidy() {
        double tdee = vypocetTDEE();
        return (tdee * 0.45) / 4;    // 45% kalorií ze sacharidů, 1g sacharidů = 4 kalorie
    }
}
