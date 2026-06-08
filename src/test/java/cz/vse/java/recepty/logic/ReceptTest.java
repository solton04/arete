package cz.vse.java.recepty.logic;

import cz.vse.java.recepty.enums.Difficulty;
import cz.vse.java.recepty.enums.RichIn;
import cz.vse.java.recepty.enums.TypeFood;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída pro {@link Recept}.
 */
public class ReceptTest {

    @Test
    public void testReceptCreation() {
        List<String> tags = new ArrayList<>();
        tags.add("zdravé");
        
        Map<Integer, String> instructions = new HashMap<>();
        instructions.put(1, "Uvařte vodu.");
        
        List<String> diets = new ArrayList<>();
        diets.add("bezlepkové");

        Recept recept = new Recept(
            Difficulty.EASY, 500, 20, 15, 60, 10,
            tags, instructions, TypeFood.LUNCH, 30,
            RichIn.PROTEIN, diets, "Test Recept"
        );

        assertEquals(Difficulty.EASY, recept.getDifficulty());
        assertEquals(500, recept.getKcal());
        assertEquals(20, recept.getProteins());
        assertEquals("Test Recept", recept.getName());
        assertEquals(tags, recept.getTags());
        assertEquals(instructions, recept.getInstructions());
        assertEquals(TypeFood.LUNCH, recept.getTypeFood());
        assertEquals(RichIn.PROTEIN, recept.getRichIn());
        assertEquals(diets, recept.getDiets());
    }

    @Test
    public void testSetters() {
        Recept recept = new Recept(null, 0, 0, 0, 0, 0, null, null, null, 0, null, null, null);
        recept.setName("Nový název");
        recept.setKcal(600);
        
        assertEquals("Nový název", recept.getName());
        assertEquals(600, recept.getKcal());
    }
}
