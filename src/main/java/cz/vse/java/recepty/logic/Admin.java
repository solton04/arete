package cz.vse.java.recepty.logic;

import cz.vse.java.recepty.enums.Role;

/**
 * Třída reprezentující administrátora v systému.
 * Dědí ze základní třídy {@link Osoba} a rozšiřuje běžného uživatele o specifická
 * administrátorská oprávnění a přístupy k systému.
 *
 *
 *
 * @author Vojtěch Soldán
 * @version 0.2 (12. 5. 2026)
 *
 */
public class Admin extends Osoba {
	/**
	 * Přiřazená role administrátora, která definuje jeho konkrétní úroveň oprávnění.
	 */
	private Role role;

	public Admin(String name, String password,Role role) {
		super(name, password);
		this.role = role;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Admin{" +
				"role=" + role +
				'}';
	}
}
