package spacex.db;

import java.sql.*;
import java.nio.file.*;
import java.io.*;

public class Database {
    private static final String JDBC_URL = "jdbc:h2:mem:spacex;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    static {
        try (Connection conn = getConnection()) {
            String sql = Files.readString(Paths.get("src/main/resources/init.sql"));
            for (String stmt : sql.split(";")) {
                if (!stmt.isBlank()) {
                    try (PreparedStatement ps = conn.prepareStatement(stmt.trim())) {
                        ps.execute();
                    }
                }
            }
            System.out.println("H2 database initialized with sample data.");
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }
}