import java.sql.*;
import java.util.*;

public class Engine implements Runnable {
    @Override
    public void run() {
        try {
            changeCitiesNames();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void changeCitiesNames() throws SQLException {

        Scanner in = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "root");
        String connection_URL = "jdbc:mysql://localhost:3306/minions_db";
        Connection connection = DriverManager.getConnection(connection_URL,properties);

        String queryTowns = "SELECT name\n" +
                "FROM towns\n" +
                "WHERE country = ?";

        String query = "UPDATE towns\n" +
                "SET name = upper(name)\n" +
                "WHERE country = ?";

        String country = in.nextLine();

        PreparedStatement preparedStatementVisualized = connection.prepareStatement(queryTowns);
        preparedStatementVisualized.setString(1,country);
        ResultSet resultSet = preparedStatementVisualized.executeQuery();

        if(!resultSet.isBeforeFirst()){
            System.out.println("No town names were affected.");
        }else {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, country);
            preparedStatement.executeUpdate();

            ArrayList<String> cities = new ArrayList<>();
            while (resultSet.next()) {
                cities.add(resultSet.getString("name"));
            }

            System.out.printf("%d town names were affected.", cities.size()).println();
            System.out.println(Collections.singletonList(cities));
        }
    }
}
