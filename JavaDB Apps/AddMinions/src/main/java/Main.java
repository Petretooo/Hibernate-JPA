import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/minions_db";

    public static void main(String[] args) throws SQLException {

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "root");

        String connectionURL = "jdbc:mysql://localhost:3306/minions_db";

        Connection connection = DriverManager
                .getConnection(CONNECTION_URL,properties);


        Engine engine = new Engine(connection);
        engine.run();

    }
}
