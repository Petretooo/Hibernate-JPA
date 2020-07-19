package entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {

    private String id;

    public BaseEntity(){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
//    @GeneratedValue(generator = "uuid-string")
//    @GenericGenerator(name = "uuid-string",
//        strategy = "org.hibernate.id.UUIDGenerator")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
