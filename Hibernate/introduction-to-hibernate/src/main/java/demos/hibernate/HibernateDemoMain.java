package demos.hibernate;

import demos.hibernate.model.Student;
import org.hibernate.FlushMode;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;



public class HibernateDemoMain {
    public static void main(String[] args) {
        Configuration cfg = new Configuration();
        cfg.configure();

        try (SessionFactory sessionFactory = cfg.buildSessionFactory();
             Session session = sessionFactory.openSession()) {

            Student student = new Student("Ivan Petrov");
            try {
                session.beginTransaction();
                session.save(student);
                session.detach(student);
                student.setName("I. Petrov");
                session.save(student);
                // difference between save and persist is that persist throws exception when trying to persist detached entity
//                session.persist(student);
//                session.detach(student);
//                session.persist(student); //throws: org.hibernate.PersistentObjectException: detached entity passed to persist: demos.hibernate.model.Student
                session.getTransaction().commit();
            } catch (Exception e) {
                if (session.getTransaction() != null)
                    session.getTransaction().rollback();
                throw e;
            }

            session.beginTransaction();
            session.setHibernateFlushMode(FlushMode.MANUAL);
            Student s1 = session.get(Student.class, 1, LockMode.READ);
            session.getTransaction().commit();

            System.out.printf("!!! Student: %s\n", s1);

            session.beginTransaction();
//            List<Student> students =
            session.createQuery("FROM Student", Student.class)
                    .setFirstResult(1)
                    .setMaxResults(5)
                    .getResultStream()
                    .forEach(System.out::println);
            session.getTransaction().commit();

            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery criteria = builder.createQuery();
            Root<Student> r = criteria.from(Student.class);
            criteria.select(r).where(builder.like(r.get("name"),"I%"));
            List<Student> studentList = session.createQuery(criteria).getResultList();
            for (Student s : studentList) {
                System.out.println(s);
            }
            session.getTransaction().commit();

        } //session.close()
    }

}
