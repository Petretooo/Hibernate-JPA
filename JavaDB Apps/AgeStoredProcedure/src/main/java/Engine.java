import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Engine implements Runnable {

    @Override
    public void run() {
        try {
            increaseAgeProcedure();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void increaseAgeProcedure() throws SQLException {
        Scanner in = new Scanner(System.in);
        List<Integer> IDs = Arrays.stream(in.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "root");
        String connection_URL = "jdbc:mysql://localhost:3306/minions_db";
        Connection connection = DriverManager.getConnection(connection_URL, properties);

        String queryToIncrease = "CALL ups_increase_age(?);";
        String queryToLower = "CALL ups_lower_name(?);";
        String queryToShowAll = "SELECT name, age\n" +
                "FROM minions";

        for (Integer minionNUMB: IDs) {

            PreparedStatement statementForIncrease = connection.prepareStatement(queryToIncrease);
            statementForIncrease.setInt(1,minionNUMB);
            statementForIncrease.executeUpdate();

            PreparedStatement statementForLower = connection.prepareStatement(queryToLower);
            statementForLower.setInt(1,minionNUMB);
            statementForLower.executeUpdate();

        }

        PreparedStatement statementForShow = connection.prepareStatement(queryToShowAll);
        ResultSet result = statementForShow.executeQuery();
        while (result.next()){
            System.out.printf("%s %d", result.getString("name"), result.getInt("age")).println();
        }

    }

}
