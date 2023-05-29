import java.sql.*;

public class BaseAuthService implements AuthService {
    private Connection connection;

    public BaseAuthService() {
        connect();
    }

    private void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:mydb.db";
            connection = DriverManager.getConnection(url);
            System.out.println("Database connection established.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getNick(String login, String pass) {
        String query = String.format("SELECT nickname FROM users WHERE login='%s' AND password='%s'", login, pass);

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getString("nickname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean login(String login, String pass) {
        String query = String.format("SELECT * FROM users WHERE login='%s' AND password='%s'", login, pass);

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean contains(String userName) {
        // TODO: Implement this method
        return false;
    }
}