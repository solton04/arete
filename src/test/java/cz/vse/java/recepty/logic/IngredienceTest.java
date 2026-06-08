package cz.vse.java.recepty.logic;

import cz.vse.java.recepty.enums.Unit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída pro {@link Ingredience}.
 */
public class IngredienceTest {

    @Test
    public void testIngredienceCreation() {
        Ingredience ingredience = new Ingredience("Mouka", "500", Unit.G);
        assertEquals("Mouka", ingredience.getName());
        assertEquals("500", ingredience.getAmount());
        assertEquals(Unit.G, ingredience.getUnit());
    }

    @Test
    public void testSetters() {
        Ingredience ingredience = new Ingredience("Voda", "1", Unit.L);
        ingredience.setName("Mléko");
        ingredience.setAmount("250");
        ingredience.setUnit(Unit.ML);
        
        assertEquals("Mléko", ingredience.getName());
        assertEquals("250", ingredience.getAmount());
        assertEquals(Unit.ML, ingredience.getUnit());
    }
}
