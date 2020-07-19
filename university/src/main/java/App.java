import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayDeque;
import java.util.Queue;

public class App {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("university");
    }
}
