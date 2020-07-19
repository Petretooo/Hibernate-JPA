import entities.Address;
import entities.Employee;
import entities.Project;
import entities.Town;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Engine implements Runnable {

    private final EntityManager entityManager;
    private final BufferedReader reader;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        //Ex 2
        //this.removeObjectEx();

        //Еx 3
//        try {
//            this.containsEmployeeEx();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //Ex 4
        //this.employeeWithSalaryOver50000();

        //Ex 5
        //this.emplyeesFromDepartment();

        //Ex 6
//        try {
//            this.addingNewAddressAndAddItToEmp();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //Ex 7
        //this.addressesWithEmployees();

        //Ex 8 TODO NOT READY
//        try {
//            this.getEmployeeWithProject();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        
        //Ex 9
        //this.geteLatestStartedProjects();
        
        //Ex 10
        //this.increaseSalaryOfDepartment();

        //Ex 11
//        try {
//            this.deleteTownByName();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //Ex 12
//        try {
//            this.findEmployeesByPaternByFirstName();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //Ex 13
        this.employeesMaxSalaries();
    }

    private void employeesMaxSalaries() {
        List<Object[]> employees = this.entityManager.createNativeQuery("SELECT d.name, MAX(e.salary) as M\n" +
                "FROM employees e\n" +
                "INNER JOIN departments d on e.department_id = d.department_id\n" +
                "GROUP BY e.department_id\n" +
                "HAVING M NOT BETWEEN 30000 AND 70000\n" +
                "ORDER BY M DESC;\n").getResultList();


        for (Object[] employee : employees) {
            System.out.printf("%s %.2f%n", employee[0], employee[1]);
        }

//        this.entityManager.createQuery("SELECT e.department.name, MAX(e.salary) FROM Employee AS e " +
//                "INNER JOIN e.department.id " +
//                "GROUP BY e.department.id " +
//                "HAVING MAX(e.salary) NOT BETWEEN 30000 AND 70000 " +
//                "ORDER BY MAX(e.salary) DESC").getResultStream().forEach(System.out::println);

        System.out.println();
    }

    private void findEmployeesByPaternByFirstName() throws IOException {
        String pattern = reader.readLine();
        List<Object[]> employees =
                this.entityManager
                        .createQuery("SELECT CONCAT(e.firstName, ' ', e.lastName), e.jobTitle, e.salary FROM Employee as e" +
                                             " WHERE e.firstName LIKE :pattern").setParameter("pattern", pattern + "%").getResultList();

        employees.stream().forEach(e -> System.out.printf("%s %s %.2f%n", e[0], e[1], e[2]));
    }

    private void deleteTownByName() throws IOException {
        String nameTown = reader.readLine();

        this.entityManager.getTransaction().begin();
        this.entityManager.createQuery("SELECT a.text FROM Address as a " +
                "INNER JOIN a.town t" +
                " WHERE a.town.name = :name").setParameter("name", nameTown).getResultStream().forEach(System.out::println);
        this.entityManager.createQuery("DELETE FROM Town as t WHERE t.name = :name").setParameter("name",nameTown).executeUpdate();
        this.entityManager.getTransaction().commit();

    }

    private void increaseSalaryOfDepartment() {
        this.entityManager.getTransaction().begin();
        this.entityManager
                .createQuery("UPDATE Employee as e SET e.salary = e.salary * 1.12 WHERE e.department.id IN (1,2,4,11)")
                .executeUpdate();
        this.entityManager.getTransaction().commit();


        List<Object[]> employees = this.entityManager
                .createQuery("SELECT e.salary, e.department.id from Employee as e WHERE e.department.id IN (1,2,4,11)")
                .getResultList();


        employees.stream().forEach(e -> System.out.printf("%.2f %d%n", e[0], e[1]));

    }

    private void geteLatestStartedProjects() {
        List<Object[]> projects = this.entityManager.createQuery("SELECT p.name, p.description, p.startDate, p.endDate FROM Project AS p ORDER BY p.startDate DESC")
                .setMaxResults(10).getResultList();

        for (Object[] project : projects) {
            System.out.println(project[0] + " " + project[1] + " " + project[2] + " " + project[3]);
        }
    }

    private void getEmployeeWithProject() throws IOException {
        int id = Integer.parseInt(this.reader.readLine());

        int employee = this.entityManager
                .createNativeQuery("SELECT e.employee_id, CONCAT(e.first_name, ' ', e.last_name), d.name, p.name\n" +
                        "FROM employees AS e\n" +
                        "INNER JOIN employees_projects ep\n" +
                        "on e.employee_id = ep.employee_id\n" +
                        "INNER JOIN projects p\n" +
                        "on ep.project_id = p.project_id\n" +
                        "INNER JOIN departments d\n" +
                        "on e.department_id = d.department_id\n" +
                        "WHERE e.employee_id = ?1\n" +
                        "GROUP BY p.name\n" +
                        "ORDER BY p.name asc;", Employee.class)
                .setParameter(1,id)
                .getFirstResult();



        System.out.println();

    }

    private void addressesWithEmployees() {

               List<Object[]> employees = this.entityManager
                       .createQuery("SELECT e.address.text, e.address.town.name, COUNT(e.address.id)" +
               "FROM Employee as e " +
               "INNER JOIN e.address a " +
               "INNER JOIN e.address.town t " +
               "GROUP BY e.address.id " +
               "ORDER BY COUNT(e.address.id) DESC")
               .setMaxResults(10)
               .getResultList();

        employees.stream().forEach(e -> System.out.printf("%s %s - %d employees%n",e[0],e[1],e[2]));


               //forEach(e -> System.out.printf("%s %s %d - Employees%n", e.getAddress().getText(), e.getAddress().getTown().getName(), AggregationFunction.COUNT));

        System.out.println();



//       this.entityManager.createNativeQuery("SELECT a.address_text, t.name, COUNT(e.address_id) as counter\n" +
//                                               "FROM addresses as a\n" +
//                                               "INNER JOIN employees as e on a.address_id = e.address_id\n" +
//                                               "INNER JOIN towns as t on a.town_id = t.town_id\n" +
//                                               "GROUP BY e.address_id\n" +
//                                               "ORDER BY counter DESC", Employee.class);

    }

    private void addingNewAddressAndAddItToEmp() throws IOException {
        System.out.println("Enter emp last name:");
        String lastName = this.reader.readLine();

        Employee employee = this.entityManager
                .createQuery("SELECT e FROM Employee AS e " +
                        "WHERE e.lastName = :name", Employee.class)
                .setParameter("name", lastName).getSingleResult();

        Address address = this.createNewAddress("Vitoshka 15");

        this.entityManager.getTransaction().begin();
        this.entityManager.detach(employee);
        employee.setAddress(address);
        this.entityManager.merge(employee);
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();

        System.out.println();


    }

    private Address createNewAddress(String textContent) {
        Address address = new Address();
        address.setText(textContent);

        this.entityManager.getTransaction().begin();
        this.entityManager.persist(address);
        this.entityManager.getTransaction().commit();

        return address;
    }

    private void emplyeesFromDepartment() {
        List<Employee> employees = this.entityManager
                .createQuery("SELECT e FROM Employee  AS e " +
                        "WHERE e.department.name = 'Research and Development' " +
                        "ORDER BY e.salary, e.id DESC", Employee.class)
                .getResultList();


    }

    private void employeeWithSalaryOver50000() {
        this.entityManager
                .createQuery("SELECT e FROM Employee AS e " +
                        "WHERE e.salary > 50000", Employee.class).getResultStream()
                .forEach(e -> System.out.printf("%s%n", e.getFirstName()));

    }

    private void containsEmployeeEx() throws IOException {
        System.out.println("Enter employee full name:");
        String fullName = this.reader.readLine();

        try {
            Employee employee = this.entityManager
                    .createQuery("SELECT e FROM Employee AS e " +
                            "WHERE concat(e.firstName, ' ', e.lastName) = :name", Employee.class)
                    .setParameter("name", fullName).getSingleResult();
            System.out.println("Yes");
        }catch (NoResultException nre){
            System.out.println("No");
        }

        System.out.println();

    }

    private void removeObjectEx(){
        List<Town> towns = this.entityManager
                .createQuery("SELECT t from Town AS t " + "WHERE length(t.name) > 5", Town.class)
                .getResultList();

        this.entityManager.getTransaction().begin();
        towns.forEach(this.entityManager::detach);

        for (Town town : towns) {
            town.setName(town.getName().toLowerCase());
        }

        towns.forEach(this.entityManager::merge);
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();


        System.out.println();
    }
}
