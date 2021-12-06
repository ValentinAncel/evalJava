package com.freestack.evaluation;

import com.freestack.evaluation.models.Booking;
import com.freestack.evaluation.models.UberDriver;
import com.freestack.evaluation.models.UberUser;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        UberUser uberUser = new UberUser("joe", "bah");
        UberApi.enrollUser(uberUser);

        UberUser uberUser2 = new UberUser("joe", "bee");
        UberApi.enrollUser(uberUser2);

        UberDriver uberDriver = new UberDriver("jane", "dee");
        UberApi.enrollDriver(uberDriver);

        Booking booking1 = UberApi.bookOneDriver(uberUser);
        if (booking1 == null) throw new AssertionError("uberDriver should be found available");

        Booking booking2 = UberApi.bookOneDriver(uberUser2);
        if (booking2 != null) throw new AssertionError("the only one driver is already booked, " +
            "we should not found any free");

        UberApi.finishBooking(booking1);
        int evaluationOfTheUser = 5;
        UberApi.evaluateDriver(booking1, evaluationOfTheUser);

        List<Booking> bookings = UberApi.listDriverBookings(uberDriver);
        if (bookings.size() != 1) throw new AssertionError(); //!= 2 changé en !=1
        if (!bookings.get(0).getEvaluation().equals(evaluationOfTheUser)) throw new AssertionError();

        Booking booking3 = UberApi.bookOneDriver(uberUser2);
        if (booking3 == null) throw new AssertionError("uberDriver should be now available");

        List<Booking> unfinishedBookings = UberApi.listUnfinishedBookings();
        if (unfinishedBookings.size() != 1) throw new AssertionError("only booking3 should be unfinished");

        float meanScore = UberApi.meanScore(uberDriver);
        if (meanScore != 5) throw new AssertionError("one eval of 5 should give a mean of 5");





        //mapping hibernate avec entités etc
        //Il faut utiliser entitymanager (avec entitymanagerfactory), pas jpaRepository (je crois) (exemple dans javaTraining, et fin du TP hibernate)
        //Boris en a mit un sur Slack
    }
}
