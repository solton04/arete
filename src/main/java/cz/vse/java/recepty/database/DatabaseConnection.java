package cz.vse.java.recepty.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Třída pro správu připojení k MySQL databázi.
 */
public class DatabaseConnection {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseConnection.class);

    private static final String URL = "jdbc:mysql://localhost:3306/arete_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Metoda, která naváže a vrátí spojení s databází.
     */
    public static Connection getConnection() throws SQLException {
        LOG.debug("Pokus o připojení k databázi na URL: {}", URL);

        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            LOG.info("Úspěšně připojeno k databázi 'arete_db'.");
            return connection;
        } catch (SQLException e) {
            LOG.error("Kritická chyba: Nepodařilo se připojit k databázi! Zkontrolujte, zda běží MySQL server v XAMPPu.", e);

            throw e;
        }
    }

}