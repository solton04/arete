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

    /**
     * Načtení informací o uživateli pro výpočet denního kalorického příjmu a makroživin.
     */
    Uzivatel user = new Uzivatel.Builder()
            .setAge(30)
            .setGender(true) //
            .setHeight(180)
            .setWeight(75)
            .setPhysicalActivity(PhysicalActivity.MODERATE)
            .build();

    int Age = user.getAge();
    boolean gender = user.isGender();
    int Height = user.getHeight();
    int Weight = user.getWeight();
    PhysicalActivity physicalActivity = user.getPhysicalActivity();

    /**
     * Výpočet bazálního metabolického výdeje (BMR) pomocí Harris-Benedictovy rovnice.
     */
    public double vypocetBMR() {
        if (gender) {       // Male
            return 88.362 + (13.397 * Weight) + (4.799 * Height) - (5.677 * Age);
        } else {            // Female
            return 447.593 + (9.247 * Weight) + (3.098 * Height) - (4.330 * Age);
        }
    }

    /**
     * Výpočet celkového denního energetického výdeje (TDEE) pomocí Harris-Benedictovy rovnice.
     */
    public double vypocetTDEE() {
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
