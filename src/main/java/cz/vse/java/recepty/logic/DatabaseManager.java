package cz.vse.java.recepty.logic;

import cz.vse.java.recepty.enums.Difficulty;
import cz.vse.java.recepty.enums.RichIn;
import cz.vse.java.recepty.enums.TypeFood;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Třída pro správu databáze a komunikaci s ní.
 *
 *
 * @author Vojtěch Soldán
 * @version 0.1 (26. 5. 2026)
 *
 */
public class DatabaseManager {

    private static final String DB_URL = "jdbc:sqlite:recepty.db";

    public static void initDatabase() throws Exception {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS recipes (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT," +
                        "difficulty TEXT," +
                        "kcal INTEGER," +
                        "proteins INTEGER," +
                        "fats INTEGER," +
                        "carbs INTEGER," +
                        "sugars INTEGER," +
                        "typeFood TEXT," +
                        "prepareTime INTEGER," +
                        "richIn TEXT," +
                        "image BLOB" +
                        ")");
            }

            // Check if empty
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM recipes")) {
                if (rs.next() && rs.getInt(1) == 0) {
                    insertMockData(conn);
                }
            }
        }
    }

    private static void insertMockData(Connection conn) throws Exception {
        String sql = "INSERT INTO recipes (name, difficulty, kcal, proteins, fats, carbs, sugars, typeFood, prepareTime, richIn, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Recipe 1
            pstmt.setString(1, "Oatmeal");
            pstmt.setString(2, Difficulty.EASY.name());
            pstmt.setInt(3, 315);
            pstmt.setInt(4, 22);
            pstmt.setInt(5, 6);
            pstmt.setInt(6, 9);
            pstmt.setInt(7, 3);
            pstmt.setString(8, TypeFood.BREAKFAST.name());
            pstmt.setInt(9, 10);
            pstmt.setString(10, RichIn.PROTEIN.name());
            setImageFileToStatement(pstmt, 11);
            pstmt.executeUpdate();

            // Recipe 2
            pstmt.setString(1, "Chicken Salad");
            pstmt.setString(2, Difficulty.MEDIUM.name());
            pstmt.setInt(3, 450);
            pstmt.setInt(4, 40);
            pstmt.setInt(5, 15);
            pstmt.setInt(6, 20);
            pstmt.setInt(7, 5);
            pstmt.setString(8, TypeFood.LUNCH.name());
            pstmt.setInt(9, 20);
            pstmt.setString(10, RichIn.PROTEIN.name());
            setImageFileToStatement(pstmt, 11);
            pstmt.executeUpdate();

            // Recipe 3
            pstmt.setString(1, "Pasta Pomodoro");
            pstmt.setString(2, Difficulty.EASY.name());
            pstmt.setInt(3, 500);
            pstmt.setInt(4, 15);
            pstmt.setInt(5, 10);
            pstmt.setInt(6, 60);
            pstmt.setInt(7, 10);
            pstmt.setString(8, TypeFood.DINNER.name());
            pstmt.setInt(9, 15);
            pstmt.setString(10, RichIn.CARBS.name());
            setImageFileToStatement(pstmt, 11);
            pstmt.executeUpdate();
        }
    }

    private static void setImageFileToStatement(PreparedStatement pstmt, int index) throws Exception {
        File file = new File("src/main/resources/cz/vse/java/recepty/images/recipe_placeholder.jpg");
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                pstmt.setBinaryStream(index, fis, (int) file.length());
            }
        } else {
            pstmt.setBytes(index, null);
        }
    }

    public static List<Recept> loadRecipes() throws Exception {
        List<Recept> recipes = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM recipes")) {

            while (rs.next()) {
                Map<Integer, String> instructions = new HashMap<>();
                instructions.put(1, "Step 1 from DB");
                List<String> diets = new ArrayList<>();
                List<String> tags = new ArrayList<>();

                Recept r = new Recept(
                        Difficulty.valueOf(rs.getString("difficulty")),
                        rs.getInt("kcal"),
                        rs.getInt("proteins"),
                        rs.getInt("fats"),
                        rs.getInt("carbs"),
                        rs.getInt("sugars"),
                        tags,
                        instructions,
                        TypeFood.valueOf(rs.getString("typeFood")),
                        rs.getInt("prepareTime"),
                        RichIn.valueOf(rs.getString("richIn")),
                        diets,
                        rs.getString("name")
                );

                InputStream imageStream = rs.getBinaryStream("image");
                if (imageStream != null) {
                    r.setImage(new Image(imageStream));
                }
                recipes.add(r);
            }
        }
        return recipes;
    }
}
