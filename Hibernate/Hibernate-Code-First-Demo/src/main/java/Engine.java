import entities.gringotts.WizardDeposit;

import javax.persistence.EntityManager;

public class Engine implements Runnable {
    private EntityManager entityManager;

    public Engine(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public void run() {
//        WizardDeposit wd = new WizardDeposit();
//        wd.setLast_name("Pesho");
//        wd.setAge(10);
//
//        this.entityManager.getTransaction().begin();
//        this.entityManager.persist(wd);
//        this.entityManager.getTransaction().commit();
    }
}
