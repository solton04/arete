package cz.vse.java.recepty;

import cz.vse.java.recepty.logic.Recept;
import cz.vse.java.recepty.logic.Uzivatel;
import cz.vse.java.recepty.enums.Difficulty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída pro {@link SessionManager}.
 */
public class SessionManagerTest {

    private SessionManager sessionManager;

    @BeforeEach
    public void setUp() {
        sessionManager = SessionManager.getInstance();
        // Vzhledem k tomu, že jde o Singleton, musíme ručně "resetovat" stav pro každý test, 
        // pokud to třída umožňuje. V tomto případě budeme muset počítat s tím, 
        // že instance je sdílená, nebo (pokud bychom mohli) upravit SessionManager.
        sessionManager.setCurrentUser(null);
        sessionManager.getSavedRecipes().clear();
    }

    @Test
    public void testSingleton() {
        SessionManager anotherInstance = SessionManager.getInstance();
        assertSame(sessionManager, anotherInstance);
    }

    @Test
    public void testSetCurrentUser() {
        Uzivatel user = new Uzivatel.Builder().setAge(20).build();
        user.setEmail("test@test.cz");
        sessionManager.setCurrentUser(user);
        assertEquals(user, sessionManager.getCurrentUser());
        assertEquals("test@test.cz", sessionManager.getCurrentUser().getEmail());
    }

    @Test
    public void testAddRemoveSavedRecipe() {
        Recept recept = new Recept(Difficulty.EASY, 100, 10, 5, 20, 5, new ArrayList<>(), new HashMap<>(), null, 10, null, new ArrayList<>(), "Test");
        
        sessionManager.addSavedRecipe(recept);
        assertTrue(sessionManager.getSavedRecipes().contains(recept));
        assertEquals(1, sessionManager.getSavedRecipes().size());
        
        sessionManager.removeSavedRecipe(recept);
        assertFalse(sessionManager.getSavedRecipes().contains(recept));
        assertEquals(0, sessionManager.getSavedRecipes().size());
    }
}
