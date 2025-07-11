package spacex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE rockets (name VARCHAR PRIMARY KEY, status VARCHAR)");
            stmt.execute("INSERT INTO rockets VALUES ('Dragon 1', 'ON_GROUND')");

            ResultSet rs = stmt.executeQuery("SELECT * FROM rockets");
            while (rs.next()) {
                System.out.println(rs.getString("name") + ": " + rs.getString("status"));
            }
        }
    }
}
