package cz.vse.java.recepty.database;

import cz.vse.java.recepty.enums.Unit;
import cz.vse.java.recepty.logic.Ingredience;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredienceRepository {
    private static final Logger LOG = LoggerFactory.getLogger(IngredienceRepository.class);
    /**
     * Uloží ingredienci a prováže ji s konkrétním receptem pomocí ID.
     */
    public void saveIngredience(Ingredience ingredience, int receptId) {
        String sql = "INSERT INTO ingredience (recept_id, name, amount, unit) VALUES (?, ?, ?, ?)";

        LOG.debug("Pokus o uložení ingredience '{}' pro recept ID: {}", ingredience.getName(), receptId);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, receptId);
            pstmt.setString(2, ingredience.getName());
            pstmt.setString(3, ingredience.getAmount());

            // Pokud má ingredience enum Unit, převedeme ho na String pro uložení do DB
            pstmt.setString(4, ingredience.getUnit() != null ? ingredience.getUnit().name() : null);

            pstmt.executeUpdate();
            LOG.info("Ingredience '{}' byla úspěšně uložena k receptu č. {}.", ingredience.getName(), receptId);

        } catch (SQLException e) {
            LOG.error("Chyba při ukládání ingredience '{}' k receptu č. {}.", ingredience.getName(), receptId, e);
        }
    }

    /**
     * Metoda pro načtení všech ingrediencí patřících ke konkrétnímu receptu.
     */
    public List<Ingredience> getIngredienceProRecept(int receptId) {
        List<Ingredience> seznamIngredienci = new ArrayList<>();
        String sql = "SELECT * FROM ingredience WHERE recept_id = ?";

        LOG.debug("Načítám ingredience pro recept ID: {}", receptId);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, receptId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String unitStr = rs.getString("unit");
                Unit unit = unitStr != null ? Unit.valueOf(unitStr) : null;

                Ingredience ingredience = new Ingredience(
                        rs.getString("name"),
                        rs.getString("amount"),
                        unit
                );

                seznamIngredienci.add(ingredience);
            }

            LOG.info("Úspěšně načteno {} ingrediencí pro recept č. {}.", seznamIngredienci.size(), receptId);

        } catch (SQLException e) {
            LOG.error("Chyba při načítání ingrediencí pro recept č. {}.", receptId, e);
        }

        return seznamIngredienci;
    }
}