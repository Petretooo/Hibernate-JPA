import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Engine implements Runnable {
    @Override
    public void run() {
        try {
            deleteVillain();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    void deleteVillain() throws SQLException {
        Scanner in = new Scanner(System.in);
        int id = Integer.parseInt(in.nextLine());

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "root");
        String connection_URL = "jdbc:mysql://localhost:3306/minions_db";
        Connection connection = DriverManager.getConnection(connection_URL, properties);

        String nameQuery = "SELECT name " +
                            "FROM villains " +
                            "WHERE id = ?";


        PreparedStatement nameStatement = connection.prepareStatement(nameQuery);
        nameStatement.setInt(1,id);

        String nameV = "";
        int counterMinions = 0;
        ResultSet result = nameStatement.executeQuery();
        if(!result.isBeforeFirst()){
            System.out.println("No such villain was found");
        }else {
            while (result.next()){
                nameV = result.getString("name");
            }

            String mv_query = "SELECT minion_id FROM minions_villains WHERE villain_id = ?";
            PreparedStatement minions = connection.prepareStatement(mv_query);
            minions.setInt(1,id);
            ResultSet minionsResult = minions.executeQuery();

            while (minionsResult.next()){
                counterMinions++;
            }

            String deleteQuery = "DELETE FROM minions_villains\n" +
                    "WHERE villain_id = ?\n;";

            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            String deleteQueySecond = "DELETE FROM villains WHERE id = ?;";
            PreparedStatement preparedStatement1 = connection.prepareStatement(deleteQueySecond);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        }

        System.out.printf("%s was deleted", nameV).println();
        System.out.printf("%d minions released", counterMinions);

    }
}
