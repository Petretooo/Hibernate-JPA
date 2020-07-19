package entities.university;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "teachers")
public class Teacher extends Person {

    private String email;
    private double salaryPerHours;
    private String speciality;

    public Teacher(){

    }

    @Column(name = "speciality")
    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "salary_per_hours")
    public double getSalaryPerHours() {
        return salaryPerHours;
    }

    public void setSalaryPerHours(double salaryPerHours) {
        this.salaryPerHours = salaryPerHours;
    }
}
