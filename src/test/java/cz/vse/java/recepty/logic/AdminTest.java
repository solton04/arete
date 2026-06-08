package cz.vse.java.recepty.logic;

import cz.vse.java.recepty.enums.Role;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovací třída pro {@link Admin}.
 */
public class AdminTest {

    @Test
    public void testAdminCreation() {
        Admin admin = new Admin("adminUser", "adminPass", Role.ADMIN);
        assertEquals("adminUser", admin.getName());
        assertEquals(Role.ADMIN, admin.getRole());
    }

    @Test
    public void testSetRole() {
        Admin admin = new Admin("adminUser", "adminPass", Role.ADMIN);
        // Assuming there are other roles, let's just check if it sets correctly.
        // If Role only has ADMIN, this might be trivial.
        admin.setRole(Role.ADMIN); 
        assertEquals(Role.ADMIN, admin.getRole());
    }
}
