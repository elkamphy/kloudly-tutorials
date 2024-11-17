package com.kloudly.exception;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeParseExceptionDemo {
    public static void main(String[] args) {
        //Uncomment any of the method below to reproduce the DateTimeParseException
        usingLocalDate();
        //usingLocalTime();
        //usingDateTimeFormatter();
    }
    public static void usingLocalDate(){
        String invalidDateFormat = "01/05/2024";// Non ISO-8601 date format
        LocalDate date = LocalDate.parse(invalidDateFormat);// DateTimeParseException
    }

    public static void usingLocalTime(){
        String invalidTimeFormat = "04:00 PM";// Non ISO-8601 date format
        LocalTime date = LocalTime.parse(invalidTimeFormat);// DateTimeParseException
    }
    public static void usingDateTimeFormatter(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a");
        LocalDateTime date = LocalDateTime.parse("2024-05-29 03:05:00 PM",formatter);//DateTimeParseException if run on a system with a Locale that uses “AM/PM” symbols instead of “am/pm” (e.g. India)

    }

}
