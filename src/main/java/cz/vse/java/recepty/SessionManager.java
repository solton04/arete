package cz.vse.java.recepty;

import cz.vse.java.recepty.database.ReceptRepository;
import cz.vse.java.recepty.logic.Recept;
import cz.vse.java.recepty.logic.Uzivatel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Třída pro správu relace (session) a uchovávání stavových dat běžící aplikace.
 * Implementuje návrhový vzor Singleton, aby byla data dostupná napříč celým UI.
 * Nyní plně napojeno na MySQL databázi.
 *
 * @author Vojtěch Soldán
 * @version 0.2 (30.5.2026)
 */
public class SessionManager {

    private static final Logger LOG = LoggerFactory.getLogger(SessionManager.class);

    private static SessionManager instance;

    private Uzivatel currentUser;
    private List<Recept> allRecipes;
    private List<Recept> savedRecipes;
    private List<Uzivatel> registeredUsers;
    private Map<String, Integer> consumedRecipes;

    /**
     * Privátní konstruktor pro Singleton vzor.
     * Inicializuje prázdné seznamy.
     */
    private SessionManager() {
        LOG.debug("Inicializace SessionManageru.");
        this.allRecipes = new ArrayList<>();
        this.savedRecipes = new ArrayList<>();
        this.registeredUsers = new ArrayList<>();
        this.consumedRecipes = new HashMap<>();
    }

    /**
     * Vrátí (případně vytvoří) jedinou instanci této třídy.
     *
     * @return Globální instance SessionManageru.
     */
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    /**
     * Načte všechny recepty z MySQL databáze pomocí repozitáře
     * a uloží je do paměti aplikace (kolekce allRecipes).
     */
    public void loadRecipesFromDatabase() {
        LOG.debug("Zahajuji načítání všech receptů z databáze do paměti aplikace.");
        try {
            ReceptRepository repo = new ReceptRepository();
            this.allRecipes = repo.getAllRecepty();
            LOG.info("Úspěšně načteno {} receptů z MySQL databáze do SessionManageru.", allRecipes.size());
        } catch (Exception e) {
            LOG.error("Kritická chyba při načítání receptů do SessionManageru.", e);
        }
    }

    /**
     * Vrátí aktuálně přihlášeného uživatele.
     *
     * @return Objekt přihlášeného uživatele nebo null, pokud nikdo není přihlášen.
     */
    public Uzivatel getCurrentUser() {
        return currentUser;
    }

    /**
     * Nastaví aktuálně přihlášeného uživatele do relace.
     *
     * @param currentUser Objekt uživatele po úspěšném přihlášení.
     */
    public void setCurrentUser(Uzivatel currentUser) {
        this.currentUser = currentUser;
        if (currentUser != null) {
            LOG.info("Do SessionManageru byl nastaven přihlášený uživatel: {}", currentUser.getEmail());
        } else {
            LOG.info("Uživatel byl odhlášen (currentUser nastaven na null).");
        }
    }

    /**
     * Vrátí seznam všech načtených receptů v paměti aplikace.
     */
    public List<Recept> getAllRecipes() {
        return allRecipes;
    }

    /**
     * Vrátí lokální seznam oblíbených (uložených) receptů aktuálního uživatele.
     */
    public List<Recept> getSavedRecipes() {
        return savedRecipes;
    }

    /**
     * Přidá recept do lokálního seznamu oblíbených receptů (pokud tam ještě není).
     *
     * @param recept Recept k uložení do oblíbených.
     */
    public void addSavedRecipe(Recept recept) {
        if (!savedRecipes.contains(recept)) {
            savedRecipes.add(recept);
            LOG.debug("Recept '{}' přidán do lokálního seznamu oblíbených v SessionManageru.", recept.getName());
        }
    }

    /**
     * Odebere recept z lokálního seznamu oblíbených receptů.
     *
     * @param recept Recept k odebrání.
     */
    public void removeSavedRecipe(Recept recept) {
        if (savedRecipes.remove(recept)) {
            LOG.debug("Recept '{}' odebrán z lokálního seznamu oblíbených v SessionManageru.", recept.getName());
        }
    }

    /**
     * Přidá nového uživatele do lokálního seznamu (pro účely mezipaměti/registrace).
     *
     * @param user Uživatel k přidání.
     */
    public void addRegisteredUser(Uzivatel user) {
        registeredUsers.add(user);
        LOG.debug("Uživatel '{}' přidán do lokálního seznamu registrovaných uživatelů.", user.getEmail());
    }

    public Map<String, Integer> getConsumedRecipes() {
        return consumedRecipes;
    }

    public void addConsumedRecipe(String name, int portions) {
        consumedRecipes.put(name, portions);
    }

    public void removeConsumedRecipe(String name) {
        consumedRecipes.remove(name);
    }
}