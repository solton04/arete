package cz.vse.java.recepty.database;

import cz.vse.java.recepty.logic.Uzivatel;
import cz.vse.java.recepty.enums.PhysicalActivity;
import cz.vse.java.recepty.enums.PersonalGoal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UzivatelRepository {
    private static final Logger LOG = LoggerFactory.getLogger(UzivatelRepository.class);
    // Opravené ukládání (přidán email a goal)
    public void saveUzivatel(Uzivatel uzivatel) {
        String sql = "INSERT INTO uzivatel (name, password, email, age, gender, weight, height, physical_activity, personal_goal) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        LOG.debug("Zahajuji ukládání nového uživatele s e-mailem: {}", uzivatel.getEmail());
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, uzivatel.getName());
            pstmt.setString(2, uzivatel.getPassword());
            pstmt.setString(3, uzivatel.getEmail());
            pstmt.setInt(4, uzivatel.getAge());
            pstmt.setBoolean(5, uzivatel.isGender());
            pstmt.setInt(6, uzivatel.getWeight());
            pstmt.setInt(7, uzivatel.getHeight());

            pstmt.setString(8, uzivatel.getPhysicalActivity() != null ? uzivatel.getPhysicalActivity().name() : null);
            pstmt.setString(9, uzivatel.getPersonalGoal() != null ? uzivatel.getPersonalGoal().name() : null);

            pstmt.executeUpdate();
            LOG.info("Uživatel '{}' (email: {}) byl úspěšně uložen do databáze.", uzivatel.getName(), uzivatel.getEmail());

        } catch (SQLException e) {
            LOG.error("Chyba při ukládání uživatele s e-mailem {}.", uzivatel.getEmail(), e);
        }
    }

    // Přihlášení uživatele podle emailu a hesla
    public Uzivatel loginUzivatel(String email, String password) {
        String sql = "SELECT * FROM uzivatel WHERE email = ? AND password = ?";
        LOG.debug("Pokus o přihlášení uživatele s e-mailem: {}", email);
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Uzivatel uzivatel = new Uzivatel.Builder()
                        .setAge(rs.getInt("age"))
                        .setGender(rs.getBoolean("gender"))
                        .setWeight(rs.getInt("weight"))
                        .setHeight(rs.getInt("height"))
                        .build();

                uzivatel.setName(rs.getString("name"));
                uzivatel.setPassword(rs.getString("password"));
                uzivatel.setEmail(rs.getString("email"));

                String activity = rs.getString("physical_activity");
                if (activity != null) uzivatel.getPhysicalActivity(PhysicalActivity.valueOf(activity));

                String goal = rs.getString("personal_goal");
                if (goal != null) uzivatel.setPersonalGoal(PersonalGoal.valueOf(goal));

                return uzivatel;
            }
        } catch (SQLException e) {
            LOG.error("Databázová chyba při pokusu o přihlášení uživatele {}.", email, e);
        }
        return null; // Přihlášení se nezdařilo
    }

    /**
     * Aktualizuje skutečně přijaté kalorie a makroživiny u daného uživatele v databázi.
     */
    public void updateActualProgress(Uzivatel uzivatel) {
        String sql = "UPDATE uzivatel SET actual_kcal = ?, actual_protein = ?, actual_fats = ?, actual_carb = ? WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, uzivatel.getActualKcal());
            pstmt.setInt(2, uzivatel.getActualProtein());
            pstmt.setInt(3, uzivatel.getActualFats());
            pstmt.setInt(4, uzivatel.getActualCarb());
            pstmt.setString(5, uzivatel.getEmail());

            pstmt.executeUpdate();
            LOG.info("Denní progres byl úspěšně uložen do DB pro uživatele: {}", uzivatel.getEmail());
        } catch (SQLException e) {
            LOG.error("Chyba při updatu progresu pro uživatele {}.", uzivatel.getEmail(), e);
        }
    }
}