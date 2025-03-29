package com.kloudly.i18n;

import java.text.ChoiceFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.text.MessageFormat;

public class MessageFormatting {
    public static void main(String[] args) {
        formatCompoundMessage();
        formatCompoundMessageCustomType();
        conditionalFormatting();
    }

    static void formatCompoundMessage(){
        Locale locale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("compound_messages", locale);//Search for messages.properties
        String orderStatusMessage = bundle.getString("order.status.message");
        String customerName = "John Doe";
        String orderNumber = "12345";
        LocalDate orderDate = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", locale);
        String formatedOrderDate = dateFormatter.format(orderDate);
        String status = "shipped";
        String translatedStatus = bundle.getString("status." + status);

        String message = MessageFormat.format(orderStatusMessage, customerName, orderNumber, formatedOrderDate, translatedStatus);
        System.out.println(message);
    }

    static void formatCompoundMessageCustomType(){
        Locale locale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("compound_messages", locale);//Search for messages.properties
        String balanceMessage = bundle.getString("balance.message");
        String customerName = "John Doe";
        double balance = 200.62;

        String message = MessageFormat.format(balanceMessage, customerName, balance);
        System.out.println(message);
    }

    static void conditionalFormatting(){
        Locale locale = Locale.getDefault();
        ResourceBundle bundle = ResourceBundle.getBundle("conditional_messages", locale);
        String pattern = bundle.getString("cart.message");
        MessageFormat messageFormat = new MessageFormat(pattern);
        messageFormat.setLocale(locale);
        double[] cartLimits = {0,1,2};

        String [] cartStrings = {
                bundle.getString("noItems"),
                bundle.getString("oneItem"),
                bundle.getString("multipleItems")
        };
        ChoiceFormat choiceFormat = new ChoiceFormat(cartLimits, cartStrings);
        Format[] formats = {choiceFormat, null, NumberFormat.getInstance()};
        messageFormat.setFormats(formats);

        Object[] messageArgumentsZero = {0};
        Object[] messageArgumentsOne = {1};
        Object[] messageArgumentsMultiple = {10};
        String resultZero = messageFormat.format(messageArgumentsZero);
        String resultOne = messageFormat.format(messageArgumentsOne);
        String resultMultiple = messageFormat.format(messageArgumentsMultiple);

        System.out.println(resultZero);
        System.out.println(resultOne);
        System.out.println(resultMultiple);
    }

}
