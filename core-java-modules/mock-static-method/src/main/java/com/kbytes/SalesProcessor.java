package com.kbytes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SalesProcessor {

    private final SaleLineParser lineParser;

    public SalesProcessor(){
        this.lineParser = new SaleLineParser();
    }

    public Sale compute(String filePath) throws Exception {
        Stream<String> sales = this.readFile(filePath);
        Function<String,Sale> toSale = this.lineParser::parse;
        Map<String,List<Sale>> salesByProduct = sales
                .map(toSale)
                .collect(Collectors.groupingBy(Sale::productCode));
        return computeMaxSales(salesByProduct);
    }

    private Sale computeMaxSales(Map<String, List<Sale>> salesByProduct) {
        int salesMax = Integer.MIN_VALUE;
        String maxSaleProductCode = "";
        for(Map.Entry<String,List<Sale>> entry: salesByProduct.entrySet()){
            String productCode = entry.getKey();
            List<Sale> productSales = entry.getValue();
            int totalSales = productSales.stream().map(Sale::saleAmount).reduce(Integer::sum).orElse(0);
            if(totalSales > salesMax){
                maxSaleProductCode = productCode;
                salesMax = totalSales;
            }
        }
        return new Sale(maxSaleProductCode,salesMax);
    }

    private Stream<String> readFile(String filePath) throws Exception {
        Path path = Path.of(filePath);
        String error = this.checkValidity(path);
        if(error.isEmpty()) {
            return Files.lines(path);
        }
        throw new IllegalArgumentException(error);
    }

    private String checkValidity(Path path) throws IOException {
        if(isFileNotAccessible(path)) {
            return Constants.FILE_NOT_ACCESSIBLE;
        }
        if(isContentInvalid(path)){
            return Constants.INVALID_FILE_CONTENT;
        }
        return "";
    }

    private boolean isFileNotAccessible(Path path){
        return !Files.exists(path) || !Files.isReadable(path);
    }

    private boolean isContentInvalid(Path path) throws IOException {
        String mimeType = Files.probeContentType(path);
        for(TextFileMimeType type : TextFileMimeType.values()){
            if(type.name.equals(mimeType))
                return false;
        }
        return true;
    }
}
