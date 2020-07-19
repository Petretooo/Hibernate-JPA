package entities.hospital;

import com.mysql.cj.jdbc.Blob;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "patients")
public class Patient extends Person {
    private String address;
    private String email;
    private LocalDateTime dateOfBirth;
    private File picture;
    private boolean isMedicalInsurance;

    private Set<Visitation> visitations;
    private Set<Medicament> medicaments;
    private Set<Diagnose> diagnoses;
    private Set<Doctor> doctors;

    public Patient() {
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "date_of_birth")
    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Column(name = "pircture")
    public File getPicture() {
        return picture;
    }

    public void setPicture(File picture) {
        this.picture = picture;
    }

    @Column(name = "is_medical_insurance")
    public boolean isMedicalInsurance() {
        return isMedicalInsurance;
    }

    public void setMedicalInsurance(boolean medicalInsurance) {
        isMedicalInsurance = medicalInsurance;
    }

    @OneToMany(mappedBy = "patient", targetEntity = Visitation.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Visitation> getVisitations() {
        return visitations;
    }

    public void setVisitations(Set<Visitation> visitations) {
        this.visitations = visitations;
    }

    @OneToMany(mappedBy = "patient", targetEntity = Medicament.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Medicament> getMedicaments() {
        return medicaments;
    }

    public void setMedicaments(Set<Medicament> medicaments) {
        this.medicaments = medicaments;
    }

    @OneToMany(mappedBy = "patient", targetEntity = Diagnose.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public Set<Diagnose> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(Set<Diagnose> diagnoses) {
        this.diagnoses = diagnoses;
    }

    @ManyToMany(mappedBy = "patients", targetEntity = Doctor.class)
    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }
}
