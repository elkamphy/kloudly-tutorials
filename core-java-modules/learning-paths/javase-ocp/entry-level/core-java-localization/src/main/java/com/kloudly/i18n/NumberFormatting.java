package com.kloudly.i18n;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class NumberFormatting {
    public static void main(String[] args) throws ParseException {
        basicFormatting();
        currencyFormatting();
        customizeCurrencyFormatting();
        percentageFormatting();
        customizePercentageFormatting();
        compactNumberFormatting();
        parseStringToNumber();
        parseStringToBigDecimal();
    }

    static void createNumberFormatters(){
        NumberFormat numberFormatter = NumberFormat.getNumberInstance();//Use the default locale
        NumberFormat numberFormatterWithLocale = NumberFormat.getNumberInstance(Locale.FRANCE);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        NumberFormat currencyFormatterWithLocale = NumberFormat.getCurrencyInstance(Locale.FRANCE);
        NumberFormat percentageFormatter = NumberFormat.getPercentInstance();
        NumberFormat percentageFormatterWithLocale = NumberFormat.getPercentInstance(Locale.FRANCE);
        NumberFormat compactNumberFormatter = NumberFormat.getCompactNumberInstance();
        NumberFormat compactNumberFormatterWithLocale = NumberFormat.getCompactNumberInstance(Locale.FRANCE,NumberFormat.Style.LONG);
    }

    static void basicFormatting(){
        NumberFormat numberFormatter = NumberFormat.getInstance(Locale.GERMANY);
        double number = 1234567.89;
        String formattedNumber = numberFormatter.format(number);
        System.out.println(formattedNumber); // Output: 1.234.567,89
    }
    static void currencyFormatting(){
        double amount = 1234.56;

        NumberFormat usFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        NumberFormat franceFormatter = NumberFormat.getCurrencyInstance(Locale.FRANCE);

        String usFormattedNumber = usFormatter.format(amount);
        String franceFormattedNumber = franceFormatter.format(amount);

        System.out.println(usFormattedNumber);   // Output: $1,234.56
        System.out.println(franceFormattedNumber); // Output: 1 234,56 €
    }

    static void customizeCurrencyFormatting(){
        double amount = 1234.56;

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setCurrencySymbol("USD$");
        NumberFormat numberFormatter = new DecimalFormat("¤#,##0.00", symbols);

        System.out.println(numberFormatter.format(amount)); // Output: USD$1,234.56
    }
    static void percentageFormatting(){
        NumberFormat percentFormatter = NumberFormat.getPercentInstance();
        double value = 0.7563;
        String percentFormatted = percentFormatter.format(value);

        System.out.println(percentFormatted); // Output: 76%
    }

    static void customizePercentageFormatting(){
        NumberFormat percentFormatter = NumberFormat.getPercentInstance();
        percentFormatter.setMinimumFractionDigits(2);
        double value = 0.7563;
        String percentFormatted = percentFormatter.format(value);

        System.out.println(percentFormatted); // Output: 75.63%
    }

    static void compactNumberFormatting(){
        NumberFormat shortFormatter = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
        NumberFormat longFormatter = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.LONG);

        String compactNumberShortFormat = shortFormatter.format(1_000_000);
        String compactNumberLongFormat = longFormatter.format(1_000_000) ;

        System.out.println(compactNumberShortFormat);  // Output: 1M
        System.out.println(compactNumberLongFormat);   // Output: 1 million
    }
    static void parseStringToNumber() throws ParseException {
        NumberFormat numberFormatter = NumberFormat.getInstance();
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        NumberFormat percentFormatter = NumberFormat.getPercentInstance();

        String numStr = "1,234.56";
        String currencyStr = "$1,234.56";
        String percentStr = "75%";

        Number parsedNumber = numberFormatter.parse(numStr);
        Number parsedCurrency = currencyFormatter.parse(currencyStr);
        Number parsedPercentage = percentFormatter.parse(percentStr);

        System.out.println(parsedNumber); // Output: 1234.56
        System.out.println(parsedCurrency); // Output: 1234.56
        System.out.println(parsedPercentage); // Output: 0.75
    }
    static void parseStringToBigDecimal() throws ParseException {
        String numStr = "1,234.56";
        DecimalFormat decimalFormatter = new DecimalFormat("#,###.##");
        decimalFormatter.setParseBigDecimal(true);

        BigDecimal bigDecimal = (BigDecimal) decimalFormatter.parse(numStr);
        System.out.println(bigDecimal); // Output: 1234.56
    }
}
