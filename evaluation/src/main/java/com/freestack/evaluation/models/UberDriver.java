package com.freestack.evaluation.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class UberDriver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "available")
    private Boolean available;
    private String firstname;
    private String lastname;
    @OneToMany(mappedBy = "uberdriver", orphanRemoval = true)
    private List<Booking> bookings;

    //Besoin d'un constructeur par défaut
    public UberDriver() {
    }

    public UberDriver(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
