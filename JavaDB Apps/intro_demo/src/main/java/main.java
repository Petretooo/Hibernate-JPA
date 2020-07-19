import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class main {

    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "minions_db";

    private static Connection connection;
    private static String query;
    private static PreparedStatement statement;


    private static BufferedReader reader;

    public static void main(String[] args) throws SQLException, IOException {

        reader = new BufferedReader(new InputStreamReader(System.in));

        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "root");

        connection = DriverManager
                .getConnection(CONNECTION_STRING + DATABASE_NAME, props);
        // #.2
        getVillainsNamesAndCountOfMinion();

        //#.3
        getMinionNameEx();

        //#.4
        addMinionEx();

        //#.9
        increaseAgeStoredProcedure();


//        query = "SELECT name FROM minions";
//        statement = connection.prepareStatement(query);
//
//        ResultSet resultSet = statement.executeQuery();
//
//        while(resultSet.next()){
//            System.out.printf("%s %n", resultSet.getString("name"));
//        }


    }

    private static void increaseAgeStoredProcedure() throws IOException, SQLException {
        System.out.println("Enter minion id: ");
        int minonId = Integer.parseInt(reader.readLine());

        query = "CALL usp_get_older(?)";
        CallableStatement callableStatement = connection.prepareCall(query);
        callableStatement.setInt(1,minonId);
        callableStatement.execute();
    }

    private static void addMinionEx() throws IOException, SQLException {
        System.out.println("Enter minion parameters: ");
        String [] minonParameters = reader.readLine().split("\\s+");
        String minionName = minonParameters[0];
        int minionAge = Integer.parseInt(minonParameters[1]);
        String minionTown = minonParameters[2];

        System.out.println("Enter villain name: ");
        String villainName = reader.readLine();
        if(!checkIfEntityExistsByName(minionTown, "towns")){
            insertEntityInTown(minionTown);
        }
    }

    private static void insertEntityInTown(String minionTown) throws SQLException {
        query = "INSERT INTO towns (name, country) value(?, ?)";
        statement = connection.prepareStatement(query);
        statement.setString(1,minionTown);
        statement.setString(2,"NULL");
        statement.executeUpdate();

    }

    private static boolean checkIfEntityExistsByName(String entityName, String tableName) throws SQLException {
        query = "SELECT * FROM " + tableName + " WHERE name = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1,entityName);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();


    }

    private static void getMinionNameEx() throws IOException, SQLException {
        System.out.println("Enter villain id:");
        int villain_id = Integer.parseInt(reader.readLine());

        if(!checkIfEntityExists(villain_id, "villains")){
            System.out.printf("No villain with ID %d exists in the database", villain_id);
            return;
        }
       System.out.printf("Villain: %s%n", getEntityNameById(villain_id, "villians"));
        getMinionsAndAgeByVillainId(villain_id);
    }

    private static void getMinionsAndAgeByVillainId(int villain_id) throws SQLException {
        query = "SELECT m.name, m.age FROM minions AS m \n" +
                "JOIN minions_villains mv on m.id = mv.minion_id\n" +
                "WHERE mv.villain_id = 1;";
        statement = connection.prepareStatement(query);
        statement.setInt(1, villain_id);

        ResultSet resultSet = statement.executeQuery();
        int minionNumber = 0;

        while(resultSet.next()){
            System.out.printf("%d. %s %d%n", --minionNumber, resultSet.getString("name"), resultSet.getInt(2));
        }

    }

    private static Object getEntityNameById(int villain_id, String tableName) throws SQLException {
        query = "SELECT name FROM " + tableName + " WHERE id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, villain_id);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next() ? resultSet.getString("name") : null;
    }

    private static boolean checkIfEntityExists(int villain_id, String villains) throws SQLException {
        query = "SELECT * FROM " + villains + " WHERE id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, villain_id);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    private static void getVillainsNamesAndCountOfMinion() throws SQLException {
        query = "SELECT v.name, COUNT(mv.minion_id) AS 'count'\n" +
                "FROM villains AS v\n" +
                "JOIN minions_villains mv on v.id = mv.villain_id\n" +
                "group by v.name\n" +
                "HAVING `count` > 15\n" +
                "ORDER BY  `count` DESC;";

        statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            System.out.printf("%s %d%n", resultSet.getString("name"), resultSet.getInt(2));
        }
    }
}
