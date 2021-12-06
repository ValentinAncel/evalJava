package com.freestack.evaluation.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class UberUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    @OneToMany(mappedBy = "uberuser")
    private List<Booking> bookings;

    //Besoin d'un constructeur par défaut
    public UberUser(){

    }

    public UberUser(String firstname, String lastname ){
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
