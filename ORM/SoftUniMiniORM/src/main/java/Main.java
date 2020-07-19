import entities.User;
import orm.Connector;
import orm.EntityManager;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        Connector.createConnect("root", "12345", "users_db");

        Connection connection = Connector.getConnection();

        User user = new User(
                "username2",
                "12345",
                12,
                Date.valueOf("2019-05-06"));

        EntityManager<User> userManager = new EntityManager<User>(connection);

//        user.setId(1);
//        userManager.persist(user); // insert
//        user.setPassword("23456");
//        userManager.persist(user); // update

        User found = userManager.findFirst(User.class);

        System.out.println(found.getRegistrationDate());

        userManager.find(User.class, "username='username2'")
            .forEach(u -> System.out.println(u.getUsername()));
    }
}
