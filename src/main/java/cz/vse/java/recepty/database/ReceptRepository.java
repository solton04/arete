package cz.vse.java.recepty.database;

import javafx.scene.image.Image;

import cz.vse.java.recepty.logic.Recept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReceptRepository {
    private static final Logger LOG = LoggerFactory.getLogger(ReceptRepository.class);

    public void saveRecept(Recept recept) {
        String sql = "INSERT INTO recept (name, difficulty, kcal, proteins, fats, carbs, sugars, " +
                "prepare_time, type_food, rich_in, instructions, tags, diets) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        LOG.debug("Zahajuji ukládání receptu: {}", recept.getName());
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, recept.getName());

            // Ošetření případných prázdných enumů
            pstmt.setString(2, recept.getDifficulty() != null ? recept.getDifficulty().name() : null);
            pstmt.setInt(3, recept.getKcal());
            pstmt.setInt(4, recept.getProteins());
            pstmt.setInt(5, recept.getFats());
            pstmt.setInt(6, recept.getCarbs());
            pstmt.setInt(7, recept.getSugars());
            pstmt.setInt(8, recept.getPrepareTime());

            pstmt.setString(9, recept.getTypeFood() != null ? recept.getTypeFood().name() : null);
            pstmt.setString(10, recept.getRichIn() != null ? recept.getRichIn().name() : null);

            pstmt.setString(11, recept.getInstructions() != null ? recept.getInstructions().toString() : "{}");
            pstmt.setString(12, recept.getTags() != null ? recept.getTags().toString() : "[]");
            pstmt.setString(13, recept.getDiets() != null ? recept.getDiets().toString() : "[]");

            pstmt.executeUpdate();
            LOG.info("Recept '" + recept.getName() + "' byl úspěšně uložen!");

        } catch (SQLException e) {
            LOG.error("Chyba při ukládání receptu '{}'.", recept.getName(), e);
            e.printStackTrace();
        }
    }

    /**
     * Metoda pro načtení doporučených receptů na základě osobního cíle uživatele.
     */
    public java.util.List<Recept> getReceptyByGoal(cz.vse.java.recepty.enums.PersonalGoal goal) {
        java.util.List<Recept> doporuceneRecepty = new java.util.ArrayList<>();
        String sql;

        // Jednoduchá logika pro MVP: Namapování cíle na konkrétní SQL filtr
        switch (goal) {
            case MUSCLE_GAIN:
                sql = "SELECT * FROM recept WHERE rich_in = 'PROTEIN'";
                break;
            case WEIGHT_LOSS:
                sql = "SELECT * FROM recept WHERE kcal <= 500 ORDER BY kcal ASC";
                break;
            case WEIGHT_GAIN:
                sql = "SELECT * FROM recept WHERE kcal > 500 ORDER BY kcal DESC";
                break;
            case IMPROVE_ENERGY:
                sql = "SELECT * FROM recept WHERE rich_in = 'CARBS'";
                break;
            default:
                // Pokud nemá cíl, nebo má jiný cíl, vrátíme všechny recepty
                sql = "SELECT * FROM recept";
                break;
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                // Bezpečné načtení Enumů
                String diffStr = rs.getString("difficulty");
                cz.vse.java.recepty.enums.Difficulty diff = diffStr != null ? cz.vse.java.recepty.enums.Difficulty.valueOf(diffStr) : null;

                String typeStr = rs.getString("type_food");
                cz.vse.java.recepty.enums.TypeFood type = typeStr != null ? cz.vse.java.recepty.enums.TypeFood.valueOf(typeStr) : null;

                String richStr = rs.getString("rich_in");
                cz.vse.java.recepty.enums.RichIn rich = richStr != null ? cz.vse.java.recepty.enums.RichIn.valueOf(richStr) : null;

                // Dočasné prázdné kolekce
                java.util.List<cz.vse.java.recepty.enums.RichIn> tags = new java.util.ArrayList<>();
                java.util.Map<Integer, String> instructions = new java.util.HashMap<>();
                java.util.List<String> diets = new java.util.ArrayList<>();

                // Vytvoření objektu Recept
                Recept recept = new Recept(
                        diff,
                        rs.getInt("kcal"),
                        rs.getInt("proteins"),
                        rs.getInt("fats"),
                        rs.getInt("carbs"),
                        rs.getInt("sugars"),
                        tags,
                        instructions,
                        type,
                        rs.getInt("prepare_time"),
                        rich,
                        diets,
                        rs.getString("name")
                );

                doporuceneRecepty.add(recept);
            }
            LOG.info("Pro cíl {} bylo nalezeno {} doporučených receptů.", goal, doporuceneRecepty.size());
        } catch (SQLException e) {
            LOG.error("Chyba při načítání doporučených receptů pro cíl {}.", goal, e);        }

        return doporuceneRecepty;
    }

    /**
     * Metoda pro načtení všech receptů z MySQL databáze.
     * Využívá se při startu aplikace, aby se dostupné recepty mohly zobrazit na domovské obrazovce.
     * @return Seznam (List) všech receptů načtených z databáze.
     */
    public java.util.List<Recept> getAllRecepty() {
        java.util.List<Recept> vsechnyRecepty = new java.util.ArrayList<>();
        String sql = "SELECT * FROM recept";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                try {
                    String diffStr = rs.getString("difficulty");
                    cz.vse.java.recepty.enums.Difficulty diff = (diffStr != null && !diffStr.trim().isEmpty())
                            ? cz.vse.java.recepty.enums.Difficulty.valueOf(diffStr.trim().toUpperCase()) : null;

                    String typeStr = rs.getString("type_food");
                    cz.vse.java.recepty.enums.TypeFood type = (typeStr != null && !typeStr.trim().isEmpty())
                            ? cz.vse.java.recepty.enums.TypeFood.valueOf(typeStr.trim().toUpperCase()) : null;


                    String richStr = rs.getString("rich_in");
                    cz.vse.java.recepty.enums.RichIn rich = null;
                    if (richStr != null) {
                        String rLow = richStr.toLowerCase();
                        if (rLow.contains("protein")) rich = cz.vse.java.recepty.enums.RichIn.PROTEIN;
                        else if (rLow.contains("carb")) rich = cz.vse.java.recepty.enums.RichIn.CARBS;
                        else if (rLow.contains("fat")) rich = cz.vse.java.recepty.enums.RichIn.FATS;
                    }

                    java.util.List<cz.vse.java.recepty.enums.RichIn> tags = new java.util.ArrayList<>();
                    java.util.Map<Integer, String> instructions = new java.util.HashMap<>();
                    java.util.List<String> diets = new java.util.ArrayList<>();


                    String dbInstructions = rs.getString("instructions");
                    if (dbInstructions != null && !dbInstructions.trim().isEmpty()) {
                        String[] radky = dbInstructions.split("\n");
                        int krok = 1;
                        for (String radek : radky) {
                            radek = radek.replaceFirst("^\\d+\\.\\s*", "").trim(); // Zbavíme se původního číslování
                            if (!radek.isEmpty()) {
                                instructions.put(krok++, radek);
                            }
                        }
                    }

                    Recept recept = new Recept(
                            diff, rs.getInt("kcal"), rs.getInt("proteins"), rs.getInt("fats"), rs.getInt("carbs"),
                            rs.getInt("sugars"), tags, instructions, type, rs.getInt("prepare_time"), rich, diets, rs.getString("name")
                    );

                    /*
                    // Načtení obrázku z URL (pokud by v databázi byl uložen odkaz)
                    String imageLink = rs.getString("image_link");
                    Image image = new Image(imageLink);
                    recept.setImage(image);
                    System.out.println("Obrázek pro recept ID " + rs.getInt("id") + " načten z URL: " + imageLink);
                     */

                    // Načtení obrázku podle ID receptu (zkusí .jpg i .png)
                    int receptId = rs.getInt("id");
                    String imagePathJPG = "/cz/vse/java/recepty/images/" + receptId + ".jpg";
                    String imagePathPNG = "/cz/vse/java/recepty/images/" + receptId + ".png";

                    // Zkusíme obrázek najít jako .jpg
                    java.io.InputStream imgStream = getClass().getResourceAsStream(imagePathJPG);

                    // Pokud se nenašel JPG, zkusíme .png
                    if (imgStream == null) {
                        imgStream = getClass().getResourceAsStream(imagePathPNG);
                    }

                    // Pokud se našel jeden z nich, přiřadíme ho k receptu
                    if (imgStream != null) {
                        recept.setImage(new javafx.scene.image.Image(imgStream));
                    } else {
                        // Vypíše varování do konzole, abychom přesně věděli, v čem je problém
                        System.out.println("POZOR: Obrázek 1.jpg ani 1.png pro recept ID " + receptId + " se nenašel v resources!");
                    }

                    vsechnyRecepty.add(recept);

                } catch (IllegalArgumentException ex) {
                    System.err.println("Přeskakuji chybný recept (ID " + rs.getInt("id") + "): " + ex.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("Chyba při komunikaci s databází: " + e.getMessage());
        }
        return vsechnyRecepty;
    }

        // METODY PRO OBLÍBENÉ RECEPTY

        public void saveOblibenyRecept(String email, String receptName) {
            String sql = "INSERT INTO oblibeny_recept (uzivatel_email, recept_name) VALUES (?, ?)";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                pstmt.setString(2, receptName);
                pstmt.executeUpdate();
            } catch (SQLException e) { }
        }

        public void removeOblibenyRecept(String email, String receptName) {
            String sql = "DELETE FROM oblibeny_recept WHERE uzivatel_email = ? AND recept_name = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                pstmt.setString(2, receptName);
                pstmt.executeUpdate();
            } catch (SQLException e) { System.err.println("Chyba mazání oblíbeného."); }
        }

        public java.util.List<String> getOblibeneRecepty(String email) {
            java.util.List<String> nazvy = new java.util.ArrayList<>();
            String sql = "SELECT recept_name FROM oblibeny_recept WHERE uzivatel_email = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, email);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    nazvy.add(rs.getString("recept_name"));
                }
            } catch (SQLException e) { System.err.println("Chyba načítání oblíbených."); }
            return nazvy;
        }
    }
