import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {
    private static final String GRINGOTTS_PU = "gringotts";
    private static final String SALES_PU = "sales";

    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("h_test");

//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        Engine engine = new Engine(entityManager);
//        engine.run();

    }
}
