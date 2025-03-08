package com.kloudly.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleClass {
    public static void main(String[] args) {
        //retrieveProperties();
        realWorldExample();
    }

    static void loadResourceBundle(){
        //First way: without a locale
        ResourceBundle propertiesWithDefaultLocale = ResourceBundle.getBundle("messages");//Search for messages.properties
        //Second way: with a specific locale
        Locale locale = Locale.FRENCH;
        ResourceBundle properties = ResourceBundle.getBundle("messages",locale);//Search for messages_fr.properties
    }

    static void retrieveProperties(){
        ResourceBundle properties = ResourceBundle.getBundle("messages");//Uses the default Locale
        String greeting = properties.getString("greeting");
        String gratitude = properties.getString("gratitude");
        String farewell = properties.getString("farewell");
        System.out.println("Greeting : "+greeting);
        System.out.println("Gratitude : "+gratitude);
        System.out.println("Farewell : "+farewell);
    }

    static void realWorldExample(){
        Locale[] supportedLocales = {Locale.getDefault(),Locale.FRENCH, Locale.of("es")};
        for(Locale locale : supportedLocales){
            retrievePropertiesForLocale(locale);
        }
    }
    static void retrievePropertiesForLocale(Locale locale){
        ResourceBundle properties = ResourceBundle.getBundle("messages", locale);
        String greeting = properties.getString("greeting");
        String gratitude = properties.getString("gratitude");
        String farewell = properties.getString("farewell");
        System.out.println("********Displaying properties for Locale : "+locale);
        System.out.println("Greeting : "+greeting);
        System.out.println("Gratitude : "+gratitude);
        System.out.println("Farewell : "+farewell);
    }
}
