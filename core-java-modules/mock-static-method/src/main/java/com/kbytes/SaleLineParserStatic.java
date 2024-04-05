package com.kbytes;

public class SaleLineParserStatic {
    private static final int COLUMN_COUNT = 2;

    public static Sale parse(String line){
        return parse(line,";");
    }
    public static Sale parse(String line, String delimiter){
        String[] saleData = line.split(delimiter,-1);

        if(isInvalidColumnCount(saleData))
            throw new IllegalArgumentException("The file must have "+COLUMN_COUNT+" columns!");

        if(isInvalidField(saleData))
            throw new IllegalArgumentException("The file data format is invalid!");

        return new Sale(saleData[0],Integer.parseInt(saleData[1]));
    }

    private static boolean isInvalidField(String[] saleData) {
        boolean isSaleInvalid = true;
        try{
            Double.parseDouble(saleData[1]);
            isSaleInvalid = false;
        }catch (NumberFormatException ex){
            ex.printStackTrace();
        }
        return saleData[0] == null || saleData[0].isEmpty() || isSaleInvalid;
    }

    private static boolean isInvalidColumnCount(String[] cityData) {
        return cityData != null && (cityData.length != COLUMN_COUNT);
    }
}
