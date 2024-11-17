package com.kloudly.exception;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;

public class DateTimeExceptionDemo {
    public static void main(String[] args) {
        //Uncomment any of the method below to reproduce the DateTimeException
        usingLocalDate();
        //usingLocalTime();
        //usingInstant();
        //usingPeriod();
    }

    public static void usingLocalDate(){
        LocalDate wrongDate = LocalDate.of(2024,13,1); //DateTimeException
    }

    public static void usingLocalTime(){

        LocalTime wrongTime = LocalTime.of(25,1);//DateTimeException
    }

    public static void usingInstant(){
        Instant tooBigInstant = Instant.ofEpochSecond(31556889864403199L + 1);//DateTimeException
    }

    public static void usingPeriod(){
        Period period = Period.ofYears(1);
        TemporalAmount secondsAmount = Duration.of(5000, ChronoUnit.SECONDS);//Invalid TemporalAmount
        Period addedPeriod = period.plus(secondsAmount);//DateTimeException
    }
}
