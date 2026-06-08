package cz.vse.java.recepty.logic;

import cz.vse.java.recepty.enums.Unit;

/**
 * Třída reprezentující jednu konkrétní ingredienci v receptu.
 * Uchovává informace o názvu suroviny, potřebném množství a příslušné měrné jednotce.
 *
 *
 * @author Vojtěch Soldán
 * @version 0.2 (12. 5. 2026)
 *
 */
public class Ingredience {
	/**
	 * Název ingredience (např. "Hladká mouka", "Mléko", "Cukr krystal").
	 */
	private String name;
	/**
	 * Požadované množství dané ingredience.
	 */
	private String amount;
	/**
	 * Měrná jednotka, ve které je množství udáváno (např. gramy, mililitry, lžíce).
	 */
	private Unit unit;

	public Ingredience(String name, String amount, Unit unit) {
		this.name = name;
		this.amount = amount;
		this.unit = unit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return "Ingredience{" +
				"name='" + name + '\'' +
				", amount=" + amount +
				", unit=" + unit +
				'}';
	}
}
