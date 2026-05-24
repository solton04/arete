package cz.vse.java.recepty.logic;
import java.util.List;

import cz.vse.java.recepty.enums.PersonalGoal;
import cz.vse.java.recepty.enums.PhysicalActivity;

/**
 * Třída reprezentující běžného uživatele aplikace.
 *
 *  @author Vojtěch Soldán, Lukáš Kucharík
 *   @version 0.3 (17. 5. 2026)
 *
 */
public class Uzivatel extends Osoba {
	/**
	 * Věk uživatele v letech.
	 */
	private int age;

	/**
	 * Pohlaví uživatele. - true - muž, false - žena
	 *
	 */
	private boolean gender;

	/**
	 * Tělesná hmotnost uživatele v kilogramech (kg).
	 */
	private int weight;

	/**
	 * Tělesná výška uživatele v centimetrech (cm).
	 */
	private int height;

	/**
	 * Úroveň fyzické aktivity uživatele.
	 */
	private PhysicalActivity physicalActivity;

	/**
	 * Osobní cíl uživatele (např. hubnutí, udržení váhy, nabírání svalové hmoty).
	 */
	private PersonalGoal personalGoal;

	/**
	 * Vypočítaný doporučený denní příjem energie v kilokaloriích (kcal).
	 */
	private int recommendedKcal;

	/**
	 * Vypočítaný doporučený denní příjem bílkovin v gramech.
	 */
	private int recommendedProteins;

	/**
	 * Vypočítaný doporučený denní příjem tuků v gramech.
	 */
	private int recommendedFats;

	/**
	 * Vypočítaný doporučený denní příjem sacharidů v gramech.
	 */
	private int recommendedCarbs;

	/**
	 * Vypočítaný doporučený maximální denní příjem cukrů v gramech.
	 */
	private int recommendedSugars;

	/**
	 * Aktuálně přijatá energie za daný den v kilokaloriích (kcal).
	 */
	private int actualKcal;

	/**
	 * Aktuálně přijaté množství bílkovin za daný den v gramech.
	 */
	private int actualProtein;

	/**
	 * Aktuálně přijaté množství tuků za daný den v gramech.
	 */
	private int actualFats;

	/**
	 * Aktuálně přijaté množství sacharidů za daný den v gramech.
	 */
	private int actualCarb;

	/**
	 * Aktuálně přijaté množství cukrů za daný den v gramech.
	 */
	private int actualSugars;

	/**
	 * Seznam preferovaných tagů
	 */
	private List preferencesTags;

	/**
	 * Kontaktní e-mailová adresa uživatele.
	 */
	private String email;

	/**
	 * Builder pro třídu Uzivatel, umožňující flexibilní a přehledné vytváření instancí s různými kombinacemi atributů.
	 * Využívan hlavně pro nastavení základních atributů uživatele, které jsou nezbytné pro výpočty a personalizaci doporučení.
	 */
	private Uzivatel(Builder builder) {
		super(builder.name, builder.password);
		this.age = builder.age;
		this.gender = builder.gender;
		this.weight = builder.weight;
		this.height = builder.height;
		this.physicalActivity = builder.PhysicalActivity;
	}

	public static class Builder {
		private String name;
		private String password;
		private int age;
		private boolean gender;
		private int weight;
		private int height;
		private PhysicalActivity PhysicalActivity;

		public Builder setAge(int age) {
			this.age = age;
			return this;
		}

		public Builder setGender(boolean gender) {
			this.gender = gender;
			return this;
		}

		public Builder setWeight(int weight) {
			this.weight = weight;
			return this;
		}

		public Builder setHeight(int height) {
			this.height = height;
			return this;
		}

		public Builder setPhysicalActivity(PhysicalActivity PhysicalActivity) {
			this.PhysicalActivity = PhysicalActivity;
			return this;
		}

		public Uzivatel build() {
			return new Uzivatel(this);

		}
	}

	public Uzivatel(String name, String password, int age, boolean gender, int weight, int height, PhysicalActivity physicalActivity, PersonalGoal personalGoal, int recommendedKcal, int recommendedProteins, int recommendedFats, int recommendedCarbs, int recommendedSugars, int actualKcal, int actualProtein, int actualFats, int actualCarb, int actualSugars, List preferencesTags, String email) {
		super(name, password);
		this.age = age;
		this.gender = gender;
		this.weight = weight;
		this.height = height;
		this.physicalActivity = physicalActivity;
		this.personalGoal = personalGoal;
		this.recommendedKcal = recommendedKcal;
		this.recommendedProteins = recommendedProteins;
		this.recommendedFats = recommendedFats;
		this.recommendedCarbs = recommendedCarbs;
		this.recommendedSugars = recommendedSugars;
		this.actualKcal = actualKcal;
		this.actualProtein = actualProtein;
		this.actualFats = actualFats;
		this.actualCarb = actualCarb;
		this.actualSugars = actualSugars;
		this.preferencesTags = preferencesTags;
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public PhysicalActivity getPhysicalActivity() {
		return physicalActivity;
	}

	public void getPhysicalActivity(PhysicalActivity physicalActivity) {
		this.physicalActivity = physicalActivity;
	}

	public PersonalGoal getPersonalGoal() {
		return personalGoal;
	}

	public void setPersonalGoal(PersonalGoal personalGoal) {
		this.personalGoal = personalGoal;
	}

	public int getRecommendedKcal() {
		return recommendedKcal;
	}

	public void setRecommendedKcal(int recommendedKcal) {
		this.recommendedKcal = recommendedKcal;
	}

	public int getRecommendedProteins() {
		return recommendedProteins;
	}

	public void setRecommendedProteins(int recommendedProteins) {
		this.recommendedProteins = recommendedProteins;
	}

	public int getRecommendedFats() {
		return recommendedFats;
	}

	public void setRecommendedFats(int recommendedFats) {
		this.recommendedFats = recommendedFats;
	}

	public int getRecommendedCarbs() {
		return recommendedCarbs;
	}

	public void setRecommendedCarbs(int recommendedCarbs) {
		this.recommendedCarbs = recommendedCarbs;
	}

	public int getRecommendedSugars() {
		return recommendedSugars;
	}

	public void setRecommendedSugars(int recommendedSugars) {
		this.recommendedSugars = recommendedSugars;
	}

	public int getActualKcal() {
		return actualKcal;
	}

	public void setActualKcal(int actualKcal) {
		this.actualKcal = actualKcal;
	}

	public int getActualProtein() {
		return actualProtein;
	}

	public void setActualProtein(int actualProtein) {
		this.actualProtein = actualProtein;
	}

	public int getActualFats() {
		return actualFats;
	}

	public void setActualFats(int actualFats) {
		this.actualFats = actualFats;
	}

	public int getActualCarb() {
		return actualCarb;
	}

	public void setActualCarb(int actualCarb) {
		this.actualCarb = actualCarb;
	}

	public int getActualSugars() {
		return actualSugars;
	}

	public void setActualSugars(int actualSugars) {
		this.actualSugars = actualSugars;
	}

	public List getPreferencesTags() {
		return preferencesTags;
	}

	public void setPreferencesTags(List preferencesTags) {
		this.preferencesTags = preferencesTags;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Uzivatel{" +
				"age=" + age +
				", gender=" + gender +
				", weight=" + weight +
				", height=" + height +
				", physicalActivity=" + physicalActivity +
				", personalGoal=" + personalGoal +
				", recommendedKcal=" + recommendedKcal +
				", recommendedProteins=" + recommendedProteins +
				", recommendedFats=" + recommendedFats +
				", recommendedCarbs=" + recommendedCarbs +
				", recommendedSugars=" + recommendedSugars +
				", actualKcal=" + actualKcal +
				", actualProtein=" + actualProtein +
				", actualFats=" + actualFats +
				", actualCarb=" + actualCarb +
				", actualSugars=" + actualSugars +
				", preferencesTags=" + preferencesTags +
				", email='" + email + '\'' +
				'}';
	}
}
