import java.sql.*;
import java.util.Properties;

public class Engine implements Runnable {
    public Engine() {
    }

    @Override
    public void run() {
        try {
            printMethodAllMinions();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void printMethodAllMinions() throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "root");
        String connection_URL = "jdbc:mysql://localhost:3306/minions_db";
        Connection connection = DriverManager.getConnection(connection_URL,properties);

        String query = "SELECT name FROM minions";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet result = preparedStatement.executeQuery();
        while (result.next()){
            System.out.printf("%s", result.getString("name")).println();
        }
    }
}
