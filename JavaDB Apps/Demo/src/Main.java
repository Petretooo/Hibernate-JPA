import java.sql.*;

import java.util.Properties;

public class Main {
    public static void main(String[] args) throws SQLException {

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "root");

        final String connectionURL =
                "jdbc:mysql://localhost:3306/minions_db";

        Connection connection = DriverManager
                        .getConnection(connectionURL, properties);


        String query = "SELECT v.name, COUNT(mv.minion_id) AS `count`\n" +
                "FROM villains AS v\n" +
                "JOIN minions_villains mv on v.id = mv.villain_id\n" +
                "GROUP BY v.name\n" +
                "HAVING `count` > ?\n" +
                "ORDER BY `count` DESC;";

        PreparedStatement preparedStatement =
                connection.prepareStatement(query);

        preparedStatement.setInt(1, 10);


        //result -> [result1, result2, ...]
        ResultSet result = preparedStatement.executeQuery();

        while (result.next()){
            System.out.printf("%s %d",
                    result.getString("name"),
                    result.getInt("count")).println();
        }



    }
}
