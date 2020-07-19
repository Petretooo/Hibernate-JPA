package entities.hospital;

import entities.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "diagnose")
public class Diagnose extends BaseEntity {
    private String name;
    private String diagnoses;

    private Patient patient;

    public Diagnose() {
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "diagnoses")
    public String getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(String diagnoses) {
        this.diagnoses = diagnoses;
    }

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
