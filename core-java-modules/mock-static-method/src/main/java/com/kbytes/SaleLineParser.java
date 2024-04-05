package com.kbytes;


public class SaleLineParser {
    private static final int COLUMN_COUNT = 2;

    public SaleLineParser(){
    }

    public Sale parse(String line){
        String delimiter = ";";
        String[] saleData = line.split(delimiter,-1);

        if(isInvalidColumnCount(saleData))
            throw new IllegalArgumentException(Constants.INVALID_LINE);

        if(isInvalidField(saleData))
            throw new IllegalArgumentException(Constants.INVALID_FIELD);

        return new Sale(saleData[0],Integer.parseInt(saleData[1]));
    }

    private boolean isInvalidField(String[] saleData) {
        boolean isSaleInvalid = true;
        try{
            Integer.parseInt(saleData[1]);
            isSaleInvalid = false;
        }catch (NumberFormatException ex){
            ex.printStackTrace();
        }
        return saleData[0] == null || saleData[0].isEmpty() || isSaleInvalid;
    }

    private static boolean isInvalidColumnCount(String[] saleData) {
        return saleData != null && (saleData.length != COLUMN_COUNT);
    }
}
