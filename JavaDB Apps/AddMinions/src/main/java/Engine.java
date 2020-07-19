import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Engine implements Runnable {
    
    private Connection connection;

    public Engine(Connection connection) {
        this.connection = connection;
    }

    public void run() {
        try {
            //this.getMinionNames();
            this.addMinion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addMinion() throws SQLException {
        Scanner in = new Scanner(System.in);

        String[] minionsData = in.nextLine().split("\\s+");
        String minionName = minionsData[1];
        int minionAge = Integer.parseInt(minionsData[2]);
        String minionTown = minionsData[3];

        String villianName = in.nextLine().split("\\s+")[1];

        String townQuery = "SELECT name FROM towns WHERE name = ?;";
        PreparedStatement statement = connection.prepareStatement(townQuery);
        statement.setString(1,minionTown);

        ResultSet townResult = statement.executeQuery();
        if(!townResult.next()){
            String insertQuery = "INSERT INTO towns(name, country) VALUES(?,'TomorrowLand')";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            insertStatement.setString(1,minionTown);

            statement.executeQuery();
            System.out.printf("Town %s was added to the database.", minionTown).println();
        }

        int townId = 0;


        String villainQuery = "SELECT name FROM villains WHERE name = ?;)";
        PreparedStatement villainStatement = connection.prepareStatement(villainQuery);
        villainStatement.setString(1,minionTown);

        ResultSet villainResult = villainStatement.executeQuery();

        if(!villainResult.next()){
            String insertQuery = "INSERT INTO villains(name, evilness_factor) VALUES(?,'evil')";
            PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            villainStatement.setString(1,villianName);

            statement.executeQuery();
            System.out.printf("Villain %s was added to the database.", minionTown).println();
        }

        String minionInsertQuery = "INSERT INTO minions(name, age, town_id) VALUES(?,?,?)";
        PreparedStatement minionInsertStatement = connection.prepareStatement(minionInsertQuery);
        minionInsertStatement.setString(1,minionName);
        minionInsertStatement.setInt(2,minionAge);
        minionInsertStatement.setInt(3, townId);

    }

    private void getMinionNames() throws SQLException{

    }

    private void getVilliansNames() throws SQLException {
        String query = "SELECT v.name, COUNT(mv.minion_id) AS `count`\n" +
                "FROM villains AS v\n" +
                "JOIN minions_villains mv on v.id = mv.villain_id\n" +
                "GROUP BY v.name\n" +
                "HAVING `count` > ?\n" +
                "ORDER BY `count` DESC;";

        PreparedStatement preparedStatement = connection
                .prepareStatement(query);
        preparedStatement.setInt(1,15);
        ResultSet result = preparedStatement.executeQuery();

        while (result.next()){
            System.out.printf("%s %d",
                    result.getString("name"),
                    result.getInt("count")).println();
        }

    }
}
