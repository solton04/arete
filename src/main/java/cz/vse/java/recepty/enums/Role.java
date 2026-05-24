package cz.vse.java.recepty.enums;
/**
 * Výčtový typ reprezentující úroveň oprávnění (roli) uživatele v systému.
 * Slouží k řízení přístupu k jednotlivým administračním funkcím aplikace
 * (např. správa uživatelů, schvalování nebo mazání obsahu).
 *
 * @author Vojtěch Soldán
 * @version 0.1 (9. 5. 2026)
 */
public enum Role {
    SUPER_ADMIN,
    ADMIN,
    MODERATOR
}
