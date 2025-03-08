package com.kloudly.i18n;

import java.util.*;

public class Localization {
    public static void main(String[] args) {
        createLocaleWithConstructors();
    }

    static void createLocaleWithConstructors() {
        Locale localeFromLanguage = new Locale("en");
        Locale localeFromLanguageAndCountry = new Locale("en","US");
        Locale localeFromLanguageAndCountryAndVariant = new Locale("en","US","POSIX");
    }

    static void createLocaleWithBuilder() {
        Locale locale = new Locale.Builder()
                .setLanguage("fr")   // Set language to French
                .setRegion("FR")     // Set country/region to France
                .build();            // Build the Locale object
    }

    static void createLocaleWithForLanguageTag(){
        Locale locale = Locale.forLanguageTag("en-US");
    }
    static void createLocaleFromLanguageConstants() {
        Locale localeFromLanguage = Locale.ENGLISH;
        Locale localeFromLanguage2 = Locale.FRENCH;
    }
    static void createLocaleFromCountryConstants() {
        Locale localeFromCountry = Locale.FRANCE;
        Locale localeFromCountry2 = Locale.US;
        Locale localeFromCountryAndLanguage = Locale.CANADA_FRENCH;
    }

    static void  createLocaleWithStaticMethods(){
        Locale localeFromLanguage = Locale.of("fr");
        Locale localeFromLanguageAndCountry = Locale.of("fr","FR");
        Locale localeFromLanguageAndCountryAndVariant = Locale.of("fr","FR","EURO");
    }

    static void localeUtilityMethods() {
        Locale[] availableLocales = Locale.getAvailableLocales();
        Locale defaultLocale = Locale.getDefault();
        String country = defaultLocale.getCountry();
        String language = defaultLocale.getLanguage();
    }


}

