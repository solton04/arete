package cz.vse.java.recepty.logic;

import cz.vse.java.recepty.enums.PhysicalActivity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for verifying calculations in the {@link Vypocty} class.
 * It contains unit tests for BMR, TDEE, and macronutrient calculations.
 *
 * @author Ema Kostecka
 */
class VypoctyTest {
    private Vypocty vypocty;

    Uzivatel user = new Uzivatel.Builder()
            .setAge(30)
            .setGender(true) //
            .setHeight(180)
            .setWeight(75)
            .setPhysicalActivity(PhysicalActivity.MODERATE)
            .build();

    /**
     * Sets up the test environment before each test method is executed.
     * Initializes a new instance of {@link Vypocty}.
     */
    @BeforeEach
    void setUp() {
        vypocty = new Vypocty(user);
    }

    /**
     * Tests the calculation of Basal Metabolic Rate (BMR) using the Harris-Benedict equation.
     * Verifies calculations for both male and female default values.
     */
    @Test
    void vypocetBMR() {
        // Default: Male, 30y, 180cm, 75kg
        // BMR = 88.362 + (13.397 * 75) + (4.799 * 180) - (5.677 * 30) = 1786.647
        assertEquals(1786.647, vypocty.vypocetBMR(), 0.001, "BMR for Male should match Harris-Benedict equation");

        // Female case: 30y, 180cm, 75kg
        user.setGender(false); //
        // BMR = 447.593 + (9.247 * 75) + (3.098 * 180) - (4.330 * 30) = 1568.858
        assertEquals(1568.858, vypocty.vypocetBMR(), 0.001, "BMR for Female should match Harris-Benedict equation");
    }

    /**
     * Tests the calculation of Total Daily Energy Expenditure (TDEE).
     * Verifies calculations for all physical activity levels defined in {@link PhysicalActivity}.
     */
    @Test
    void vypocetTDEE() {
        // BMR (Male) = 1786.647
        
        // MODERATE (1.465) - Default
        user.setPhysicalActivity(PhysicalActivity.MODERATE);
        assertEquals(1786.647 * 1.465, vypocty.vypocetTDEE(), 0.001, "TDEE for Moderate activity");

        // SEDENTARY (1.2)
        user.setPhysicalActivity(PhysicalActivity.SEDENTARY);
        assertEquals(1786.647 * 1.2, vypocty.vypocetTDEE(), 0.001, "TDEE for Sedentary activity");

        // LIGHT (1.375)
        user.setPhysicalActivity(PhysicalActivity.LIGHT);
        assertEquals(1786.647 * 1.375, vypocty.vypocetTDEE(), 0.001, "TDEE for Light activity");

        // ACTIVE (1.55)
        user.setPhysicalActivity(PhysicalActivity.ACTIVE);
        assertEquals(1786.647 * 1.55, vypocty.vypocetTDEE(), 0.001, "TDEE for Active activity");

        // VERY_ACTIVE (1.725)
        user.setPhysicalActivity(PhysicalActivity.VERY_ACTIVE);
        assertEquals(1786.647 * 1.725, vypocty.vypocetTDEE(), 0.001, "TDEE for Very Active activity");

        // EXTRA_ACTIVE (1.9)
        user.setPhysicalActivity(PhysicalActivity.EXTRA_ACTIVE);
        assertEquals(1786.647 * 1.9, vypocty.vypocetTDEE(), 0.001, "TDEE for Extra Active activity");
    }

    /**
     * Tests the calculation of daily protein requirements.
     * Verifies that proteins are calculated as 30% of TDEE.
     */
    @Test
    void vypocetProteiny() {
        // TDEE (Default) = 2617.437855
        // Proteins = (2617.437855 * 0.3) / 4 = 196.307839125
        assertEquals(196.3078, vypocty.vypocetProteiny(), 0.001, "Proteins should be 30% of TDEE divided by 4");
    }

    /**
     * Tests the calculation of daily fat requirements.
     * Verifies that fats are calculated as 25% of TDEE.
     */
    @Test
    void vypocetTuky() {
        // TDEE (Default) = 2617.437855
        // Fats = (2617.437855 * 0.25) / 9 = 72.706607
        assertEquals(72.7066, vypocty.vypocetTuky(), 0.001, "Fats should be 25% of TDEE divided by 9");
    }

    /**
     * Tests the calculation of daily carbohydrate requirements.
     * Verifies that carbohydrates are calculated as 45% of TDEE.
     */
    @Test
    void vypocetSacharidy() {
        // TDEE (Default) = 2617.437855
        // Carbs = (2617.437855 * 0.45) / 4 = 294.461758
        assertEquals(294.4617, vypocty.vypocetSacharidy(), 0.001, "Carbs should be 45% of TDEE divided by 4");
    }
}

