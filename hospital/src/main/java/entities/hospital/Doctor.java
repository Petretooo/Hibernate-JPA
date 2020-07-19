package entities.hospital;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "doctors")
public class Doctor extends Person {

    private Set<Patient> patients;

    public Doctor() {
    }

    @ManyToMany
    @JoinTable(name = "doctors_parients",
            joinColumns = @JoinColumn(name = "doctor_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"))
    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }
}
