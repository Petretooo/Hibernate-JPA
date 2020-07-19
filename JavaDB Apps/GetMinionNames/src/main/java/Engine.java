import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Engine implements Runnable {


    public Engine() {

    }

    public void run() {
        try {
            showMinions();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    void showMinions() throws SQLException {

        Scanner in = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "root");

        String connection_url = "jdbc:mysql://localhost:3306/minions_db";
        Connection connection = DriverManager.getConnection(connection_url, properties);


        String query = "SELECT m.name, m.age\n" +
                "FROM villains v\n" +
                "INNER JOIN minions_villains mv\n" +
                "on v.id = mv.villain_id\n" +
                "INNER JOIN minions m\n" +
                "ON mv.minion_id = m.id\n" +
                "WHERE v.id = ?\n" +
                "ORDER BY v.id;";


        String queryVilllainName = "SELECT name\n" +
                "FROM villains\n" +
                "WHERE id = ?";

        int villainID = Integer.parseInt(in.nextLine());

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, villainID);
        ResultSet result = preparedStatement.executeQuery();


        PreparedStatement preparedStatementForVillain = connection.prepareStatement(queryVilllainName);
        preparedStatementForVillain.setInt(1, villainID);

        ResultSet resultVillainName = preparedStatementForVillain.executeQuery();


        if (!resultVillainName.next()) {
            System.out.printf("No villain with ID %d exists in the database.", villainID).println();
        } else {
            while (resultVillainName.next()) {
                System.out.printf("Villain: %s", resultVillainName.getString("name")).println();
            }
        }

        int counter = 0;
        while (result.next()) {
            System.out.printf("%d: %s %d", ++counter,
                    result.getString("name"), result.getInt("age")).println();
        }

    }
}
