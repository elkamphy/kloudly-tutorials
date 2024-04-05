package com.kbytes;

import java.util.List;
import java.util.function.Function;

public class SalesProcessorStatic {

    public SalesProcessorStatic(){
    }

    public int compute(List<String> sales){
        Function<String,Sale> toSale = SaleLineParserStatic::parse;
        return sales.stream()
                .map(toSale)
                .map(Sale::saleAmount)
                .reduce(Integer::sum).orElse(0);
    }
}
