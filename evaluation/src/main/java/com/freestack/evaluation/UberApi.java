package com.freestack.evaluation;

import com.freestack.evaluation.models.UberUser;
import com.freestack.evaluation.models.Booking;
import com.freestack.evaluation.models.UberDriver;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public class UberApi {

    public static void enrollUser(UberUser uberUser){
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().createEntityManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(uberUser);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public static void enrollDriver(UberDriver uberDriver){
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().createEntityManager();
        try{
            entityManager.getTransaction().begin();
            uberDriver.setAvailable(true); //Quand quelqu'un s'inscrit il n'est pas encore booké et on est donc disponible
            entityManager.persist(uberDriver);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public static Booking bookOneDriver(UberUser uberuser) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().createEntityManager();
        Query queryUberdriverAvailableInList = entityManager.createQuery("select u from UberDriver u where u.available=true");
        List<UberDriver> UberdriverAvailableInList = (List<UberDriver>) queryUberdriverAvailableInList.getResultList();

        if (UberdriverAvailableInList.size() > 0) {
            try {
                entityManager.getTransaction().begin();
                Query queryUberdriverAvailable = entityManager.createQuery("select u from UberDriver u where u.available=true");
                UberDriver queryAvailable = (UberDriver) queryUberdriverAvailable.setMaxResults(1).getSingleResult();

                Booking booking = new Booking(uberuser);
                booking.setUberuser(uberuser);
                booking.setStart_of_the_booking(new Date());
                booking.setUberdriver(queryAvailable);
                queryAvailable.setAvailable(false);
                entityManager.persist(booking);

                entityManager.getTransaction().commit();
                return booking;
            } finally {
                entityManager.close();
            }
        }
        return null;
    }

    public static Booking finishBooking(Booking booking){
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            Query queryBooking = entityManager.createQuery("select b from Booking b where b.id = :id");
            queryBooking.setParameter("id", booking.getId());
            Booking booking1 = (Booking) queryBooking.getSingleResult();//Je force le type Booking car sinon j'obtiens un objet
            booking1.getUberdriver().setAvailable(true);
            booking1.setEnd_of_the_booking(new Date());//Une date de fin est générée pour le booking une fois la méthode utilisée
            entityManager.getTransaction().commit();
            return booking;
        } finally {
            entityManager.close();
        }
    }

    public static void evaluateDriver(Booking booking, int evaluationOfTheUser){
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().createEntityManager();
        entityManager.getTransaction().begin();
        Query queryBooking = entityManager.createQuery("select b from Booking b where b.id = :id");
        queryBooking.setParameter("id", booking.getId());
        Booking booking1 = (Booking) queryBooking.getSingleResult();
        try {
            booking1.setEvaluation(evaluationOfTheUser); //La note en paramètre est attribuée à ce booking
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    public static List<Booking> listDriverBookings(UberDriver uberdriver) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().createEntityManager();
        entityManager.getTransaction().begin();
        Query queryBookList = entityManager.createQuery("select b from Booking b where b.uberdriver = :uberdriver");
        queryBookList.setParameter("uberdriver", uberdriver);
        List<Booking> bookList = (List<Booking>) queryBookList.getResultList();

        if (bookList.size() > 0) {
            try {
                entityManager.getTransaction().commit(); //On obtient les courses effectuées par le conducteur en paramètre
                return bookList;
            } finally {
                entityManager.close();
            }
        }
        entityManager.close();
        return null;
    }

    public static List<Booking> listUnfinishedBookings() {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().createEntityManager();
        entityManager.getTransaction().begin();
        Query queryUnFinishedBookList = entityManager.createQuery("select b from Booking b where end_of_the_booking is null");
        List<Booking> UnFinishedBookList = (List<Booking>) queryUnFinishedBookList.getResultList();

        if (UnFinishedBookList.size() > 0) {
            try {
                entityManager.getTransaction().commit(); //On obtient les courses non-terminé du conducteur en paramètre
                return UnFinishedBookList;
            } finally {
                entityManager.close();
            }
        }
        entityManager.close();
        return null;
    }

    public static Float meanScore(UberDriver uberdriver) {
        EntityManager entityManager = EntityManagerFactorySingleton.getInstance().createEntityManager();
        entityManager.getTransaction().begin();
        Query queryFinishedBooking = entityManager.createQuery("select b from Booking b where b.end_of_the_booking is not null and b" + ".uberdriver = :uberdriver");
        queryFinishedBooking.setParameter("uberdriver", uberdriver);
        List<Booking> FinishedBook = (List<Booking>) queryFinishedBooking.getResultList();

        if (FinishedBook.size() > 0) {
            Query queryAvgScore = entityManager.createQuery("select avg(b.evaluation) from Booking b where b.end_of_the_booking is not null and b" + ".uberdriver = :uberdriver");
            queryAvgScore.setParameter("uberdriver", uberdriver);
            Double avgScore = (Double) queryAvgScore.getSingleResult();
            try {
                return avgScore.floatValue();
            } finally {
                entityManager.close();
            }
        }
        entityManager.close();
        return null;
    }
}
