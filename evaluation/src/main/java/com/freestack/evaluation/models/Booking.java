package com.freestack.evaluation.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date end_of_the_booking;
    private Integer evaluation;
    private Date start_of_the_booking;

    @ManyToOne
    @JoinColumn(name="UBERUSER_ID")
    private UberUser uberuser;

    @ManyToOne
    @JoinColumn(name="UBERDRIVER_ID")
    private UberDriver uberdriver;

    //Besoin d'un constructeur par défaut
    public Booking() {
    }

    public Booking(UberUser uberuser) {
        this.uberuser = uberuser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEnd_of_the_booking() {
        return end_of_the_booking;
    }

    public void setEnd_of_the_booking(Date end_of_the_booking) {
        this.end_of_the_booking = end_of_the_booking;
    }

    public Integer getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Integer evaluation) {
        this.evaluation = evaluation;
    }

    public Date getStart_of_the_booking() {
        return start_of_the_booking;
    }

    public void setStart_of_the_booking(Date start_of_the_booking) {
        this.start_of_the_booking = start_of_the_booking;
    }

    public UberUser getUberuser() {
        return uberuser;
    }

    public void setUberuser(UberUser uberuser) {
        this.uberuser = uberuser;
    }

    public UberDriver getUberdriver() {
        return uberdriver;
    }

    public void setUberdriver(UberDriver uberdriver) {
        this.uberdriver = uberdriver;
    }
}
