package cz.vse.java.recepty;

import cz.vse.java.recepty.enums.Difficulty;
import cz.vse.java.recepty.enums.PersonalGoal;
import cz.vse.java.recepty.enums.PhysicalActivity;
import cz.vse.java.recepty.enums.RichIn;
import cz.vse.java.recepty.enums.TypeFood;
import cz.vse.java.recepty.enums.Unit;
import cz.vse.java.recepty.logic.Ingredience;
import cz.vse.java.recepty.logic.Recept;
import cz.vse.java.recepty.logic.Uzivatel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionManager {
    private static SessionManager instance;

    private Uzivatel currentUser;
    private List<Recept> allRecipes;
    private List<Recept> savedRecipes;

    // A list of registered users
    private List<Uzivatel> registeredUsers;

    private SessionManager() {
        allRecipes = new ArrayList<>();
        savedRecipes = new ArrayList<>();
        registeredUsers = new ArrayList<>();
        initMockData();
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public Uzivatel getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Uzivatel currentUser) {
        this.currentUser = currentUser;
    }

    public List<Recept> getAllRecipes() {
        return allRecipes;
    }

    public List<Recept> getSavedRecipes() {
        return savedRecipes;
    }

    public void addSavedRecipe(Recept recept) {
        if (!savedRecipes.contains(recept)) {
            savedRecipes.add(recept);
        }
    }

    public void removeSavedRecipe(Recept recept) {
        savedRecipes.remove(recept);
    }

    public List<Uzivatel> getRegisteredUsers() {
        return registeredUsers;
    }

    public void addRegisteredUser(Uzivatel user) {
        registeredUsers.add(user);
    }

    private void initMockData() {
        // Add a dummy user
        Uzivatel dummyUser = new Uzivatel.Builder()
            .setAge(25)
            .setGender(true)
            .setWeight(80)
            .setHeight(185)
            .setPhysicalActivity(PhysicalActivity.MODERATE)
            .build();
        dummyUser.setName("Test User");
        dummyUser.setEmail("test@test.com");
        dummyUser.setPassword("password");
        dummyUser.setPersonalGoal(PersonalGoal.GENERAL_HEALTH);
        registeredUsers.add(dummyUser);

        // Breakfast mock recipe
        Map<Integer, String> inst1 = new HashMap<>();
        inst1.put(1, "Mix oats and milk");
        inst1.put(2, "Cook for 5 minutes");

        List<String> diets1 = new ArrayList<>();
        diets1.add("Vegetarian");

        Recept r1 = new Recept(Difficulty.EASY, 315, 22, 6, 9, 3, new ArrayList<>(), inst1, TypeFood.BREAKFAST, 10, RichIn.PROTEIN, diets1, "Oatmeal");
        allRecipes.add(r1);

        // Lunch mock recipe
        Map<Integer, String> inst2 = new HashMap<>();
        inst2.put(1, "Grill chicken");
        inst2.put(2, "Mix with salad");

        Recept r2 = new Recept(Difficulty.MEDIUM, 450, 40, 15, 20, 5, new ArrayList<>(), inst2, TypeFood.LUNCH, 20, RichIn.PROTEIN, new ArrayList<>(), "Chicken Salad");
        allRecipes.add(r2);

        // Dinner mock recipe
        Map<Integer, String> inst3 = new HashMap<>();
        inst3.put(1, "Boil pasta");
        inst3.put(2, "Add tomato sauce");

        Recept r3 = new Recept(Difficulty.EASY, 500, 15, 10, 60, 10, new ArrayList<>(), inst3, TypeFood.DINNER, 15, RichIn.CARBS, new ArrayList<>(), "Pasta Pomodoro");
        allRecipes.add(r3);
    }
}
