package cz.vse.java.recepty.logic;
/**
 * Základní třída reprezentující uživatele systému (osobu).
 * Slouží k uchování přihlašovacích údajů a základní identifikaci v aplikaci.
 * Tato třída funguje jako předek pro specifičtější typy uživatelů,
 *
 *
 *  @author Vojtěch Soldán
 *  @version 0.1 (9. 5. 2026)
 *
 */
public class Osoba {
	/**
	 * Uživatelské nebo zobrazované jméno osoby.
	 */
	private String name;
	/**
	 * Přihlašovací heslo uživatele.
	 */
	private String password;

	public Osoba(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
