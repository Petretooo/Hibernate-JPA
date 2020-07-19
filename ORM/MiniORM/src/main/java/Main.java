import entities.User;
import orm.Connector;
import orm.EntityManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, IllegalAccessException {



        Connector.createConnection("root", "root","buhtih");
        Connection connection = Connector.getConnection();

        User user = new User("root", "root", 12,
                Date.valueOf("2019-05-06"));
        EntityManager<User> userManager = new EntityManager<User>(connection);

        user.setId(1);
        userManager.persist(user);
        //user.setPassword("root");
        //userManager.persist(user);


    }
}
