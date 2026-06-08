package cz.vse.java.recepty.logic;

import cz.vse.java.recepty.enums.PhysicalActivity;
import cz.vse.java.recepty.enums.PersonalGoal;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída pro {@link Uzivatel}.
 */
public class UzivatelTest {

    @Test
    public void testUzivatelBuilder() {
        Uzivatel uzivatel = new Uzivatel.Builder()
                .setAge(25)
                .setGender(true)
                .setWeight(80)
                .setHeight(185)
                .setPhysicalActivity(PhysicalActivity.ACTIVE)
                .build();
        
        uzivatel.setName("Jan");
        uzivatel.setPassword("tajneheslo");

        assertEquals(25, uzivatel.getAge());
        assertTrue(uzivatel.isGender());
        assertEquals(80, uzivatel.getWeight());
        assertEquals(185, uzivatel.getHeight());
        assertEquals(PhysicalActivity.ACTIVE, uzivatel.getPhysicalActivity());
        assertEquals("Jan", uzivatel.getName());
    }

    @Test
    public void testUzivatelFullConstructor() {
        List<String> tags = new ArrayList<>();
        tags.add("vegan");
        
        Uzivatel uzivatel = new Uzivatel(
            "Pavel", "heslo123", 30, true, 75, 180, 
            PhysicalActivity.MODERATE, PersonalGoal.WEIGHT_LOSS, 
            2000, 150, 70, 200, 30, 
            500, 40, 15, 50, 5, 
            tags, "pavel@example.com"
        );

        assertEquals("Pavel", uzivatel.getName());
        assertEquals(30, uzivatel.getAge());
        assertEquals(PersonalGoal.WEIGHT_LOSS, uzivatel.getPersonalGoal());
        assertEquals(2000, uzivatel.getRecommendedKcal());
        assertEquals(500, uzivatel.getActualKcal());
        assertEquals("pavel@example.com", uzivatel.getEmail());
        assertEquals(tags, uzivatel.getPreferencesTags());
    }

    @Test
    public void testSetters() {
        Uzivatel uzivatel = new Uzivatel.Builder().build();
        uzivatel.setAge(40);
        uzivatel.setWeight(90);
        uzivatel.setEmail("test@test.com");
        
        assertEquals(40, uzivatel.getAge());
        assertEquals(90, uzivatel.getWeight());
        assertEquals("test@test.com", uzivatel.getEmail());
    }
}
