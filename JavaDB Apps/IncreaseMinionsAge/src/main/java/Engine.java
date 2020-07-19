import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class Engine implements Runnable {
    public Engine() {
    }

    @Override
    public void run() {
        try {
            increaseAge();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void increaseAge() throws SQLException {
        Scanner in = new Scanner(System.in);
        List<Integer> IDs = Arrays.stream(in.nextLine().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "root");
        String connection_URL = "jdbc:mysql://localhost:3306/minions_db";
        Connection connection = DriverManager.getConnection(connection_URL,properties);

        String queryToIncrease = "UPDATE minions\n" +
                "SET age = age + 1\n" +
                "WHERE id = ?;\n";
        String queryToLower = "UPDATE minions\n" +
                "SET name = LOWER(name)\n" +
                "WHERE id = ?;\n";
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
