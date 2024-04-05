import com.kbytes.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;

import static org.mockito.Mockito.mockStatic;

public class SaleProcessorStaticTest {

    @Test
    void test_simple_compute(){
      //given
        List<String> sales = List.of("P1;10","P2;20");
        Sale sale1 = new Sale("P1",50);
        Sale sale2 = new Sale("P2",70);
        SaleLineParser lineParser = new SaleLineParser();
        int expectedTotal = 120;
        try(MockedStatic<SaleLineParserStatic> mockSaleLineParser = mockStatic(SaleLineParserStatic.class)){
            mockSaleLineParser.when(() -> SaleLineParserStatic.parse("P1;10")).thenReturn(sale1);
            mockSaleLineParser.when(() -> SaleLineParserStatic.parse("P2;20")).thenReturn(sale2);
            //when
            SalesProcessorStatic salesProcessor = new SalesProcessorStatic();
            int salesTotal = salesProcessor.compute(sales);
            //then
            Assertions.assertEquals(expectedTotal,salesTotal);
        }
    }

    @Test
    void test_throws_Exception(){
        //given
        List<String> sales = List.of("P1;10","P2;20");
        Sale sale1 = new Sale("P1",10);
        Sale sale2 = new Sale("P2",20);
        SaleLineParser lineParser = new SaleLineParser();
        int expectedTotal = 30;
        try(MockedStatic<SaleLineParserStatic> mockSaleLineParser = mockStatic(SaleLineParserStatic.class)){
            mockSaleLineParser.when(() -> SaleLineParserStatic.parse("P1;10")).thenReturn(sale1);
            mockSaleLineParser.when(() -> SaleLineParserStatic.parse("P2;20")).thenReturn(sale2);
            //when
            SalesProcessorStatic salesProcessor = new SalesProcessorStatic();
            int salesTotal = salesProcessor.compute(sales);
            //then
            Assertions.assertEquals(expectedTotal,salesTotal);
        }

    }
}
