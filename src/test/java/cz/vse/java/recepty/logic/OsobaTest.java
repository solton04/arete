package cz.vse.java.recepty.logic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída pro {@link Osoba}.
 */
public class OsobaTest {

    @Test
    public void testOsobaCreation() {
        Osoba osoba = new Osoba("testUser", "testPass");
        assertEquals("testUser", osoba.getName());
        assertEquals("testPass", osoba.getPassword());
    }

    @Test
    public void testSetters() {
        Osoba osoba = new Osoba("initial", "initial");
        osoba.setName("newName");
        osoba.setPassword("newPass");
        assertEquals("newName", osoba.getName());
        assertEquals("newPass", osoba.getPassword());
    }
}
