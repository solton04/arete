package cz.vse.java.recepty.logic;

import cz.vse.java.recepty.enums.Difficulty;
import cz.vse.java.recepty.enums.RichIn;
import cz.vse.java.recepty.enums.TypeFood;

import java.util.List;
import java.util.Map;
import javafx.scene.image.Image;
/**
 * Třída reprezentující konkrétní recept v systému.
 * Uchovává veškeré informace o jídle, včetně názvu, nutričních hodnot,
 * postupu přípravy, štítků a dietních omezení.
 *  @author Vojtěch Soldán
 *   @version 0.2 (12. 5. 2026)
 *
  */
public class Recept {
	/**
	 * Obtížnost přípravy receptu (např. snadná, střední, těžká).
	 */
	private Difficulty difficulty;
	/**
	 * Celková energetická hodnota porce v kilokaloriích (kcal).
	 */
	private int kcal;
	/**
	 * Množství bílkovin v gramech.
	 */
	private int proteins;
	/**
	 * Množství tuků v gramech.
	 */
	private int fats;
	/**
	 * Množství sacharidů v gramech.
	 */
	private int carbs;
	/**
	 * Množství cukrů v gramech (z celkového počtu sacharidů).
	 */
	private int sugars;
	/**
	 * Seznam štítků (tagů) asociovaných s receptem.
	 */
	private List tags;
	/**
	 * Postup přípravy receptu.
	 * Očekává se mapa, kde klíčem je číslo kroku a hodnotou je textová instrukce.
	 */
	private Map instructions;
	/**
	 * Typ chodu
	 */
	private TypeFood typeFood;

	/**
	 * Odhadovaný čas přípravy v minutách.
	 */
	private int prepareTime;
	/**
	 * Hlavní mikroživiny nebo benefity, na které je recept bohatý (např. vláknina, vitamín C).
	 */
	private RichIn richIn;
	/**
	 * Dieta, pro kterou je recept vhodný (např. vegan, vegetarián, bezlepková).
	 */
	private List diets;
	/**
	 * Název receptu (např. "Špagety Carbonara").
	 */
	private String name;

	private Image image;

	public Recept(Difficulty difficulty, int kcal, int proteins, int fats, int carbs, int sugars, List tags, Map instructions, TypeFood typeFood, int prepareTime, RichIn richIn, List diets, String name) {
		this.difficulty = difficulty;
		this.kcal = kcal;
		this.proteins = proteins;
		this.fats = fats;
		this.carbs = carbs;
		this.sugars = sugars;
		this.tags = tags;
		this.instructions = instructions;
		this.typeFood = typeFood;
		this.prepareTime = prepareTime;
		this.richIn = richIn;
		this.diets = diets;
		this.name = name;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public int getKcal() {
		return kcal;
	}

	public void setKcal(int kcal) {
		this.kcal = kcal;
	}

	public int getProteins() {
		return proteins;
	}

	public void setProteins(int proteins) {
		this.proteins = proteins;
	}

	public int getFats() {
		return fats;
	}

	public void setFats(int fats) {
		this.fats = fats;
	}

	public int getCarbs() {
		return carbs;
	}

	public void setCarbs(int carbs) {
		this.carbs = carbs;
	}

	public int getSugars() {
		return sugars;
	}

	public void setSugars(int sugars) {
		this.sugars = sugars;
	}

	public List getTags() {
		return tags;
	}

	public void setTags(List tags) {
		this.tags = tags;
	}

	public Map getInstructions() {
		return instructions;
	}

	public void setInstructions(Map instructions) {
		this.instructions = instructions;
	}

	public TypeFood getTypeFood() {
		return typeFood;
	}

	public void setTypeFood(TypeFood typeFood) {
		this.typeFood = typeFood;
	}

	public int getPrepareTime() {
		return prepareTime;
	}

	public void setPrepareTime(int prepareTime) {
		this.prepareTime = prepareTime;
	}

	public RichIn getRichIn() {
		return richIn;
	}

	public void setRichIn(RichIn richIn) {
		this.richIn = richIn;
	}

	public List getDiets() {
		return diets;
	}

	public void setDiets(List diets) {
		this.diets = diets;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Recept{" +
				"difficulty=" + difficulty +
				", kcal=" + kcal +
				", proteins=" + proteins +
				", fats=" + fats +
				", carbs=" + carbs +
				", sugars=" + sugars +
				", tags=" + tags +
				", instructions=" + instructions +
				", typeFood=" + typeFood +
				", prepareTime=" + prepareTime +
				", richIn=" + richIn +
				", diets=" + diets +
				", name='" + name + '\'' +
				'}';
	}
}
