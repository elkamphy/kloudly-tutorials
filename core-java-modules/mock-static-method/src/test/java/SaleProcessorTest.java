import com.kbytes.Constants;
import com.kbytes.Sale;
import com.kbytes.SalesProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.mockito.Mockito.mockStatic;

public class SaleProcessorTest {
    private SalesProcessor bean;
    @Mock
    private Path mockPath;

    @Test
    void test_File_not_Accessible_NonExisting() throws Exception {
        //given
        String anyFile = "test.csv";
        bean = new SalesProcessor();

        try(MockedStatic<Files> mockFiles = mockStatic(Files.class);
            MockedStatic<Path> mockPath = mockStatic(Path.class)){
            mockPath.when(() -> Path.of(anyFile)).thenReturn(this.mockPath);
            mockFiles.when(() -> Files.exists(this.mockPath)).thenReturn(false);
            //when then
            Exception expectedException =  Assertions.assertThrows(IllegalArgumentException.class,() -> bean.compute(anyFile));
            Assertions.assertEquals(Constants.FILE_NOT_ACCESSIBLE, expectedException.getMessage());
        }
    }

    @Test
    void test_File_not_Accessible_NotReadable() throws Exception {
        //given
        String anyFile = "test.csv";;
        bean = new SalesProcessor();
        try(MockedStatic<Files> mockFiles = mockStatic(Files.class);
            MockedStatic<Path> mockPath = mockStatic(Path.class)){
            mockPath.when(() -> Path.of(anyFile)).thenReturn(this.mockPath);
            mockFiles.when(() -> Files.exists(this.mockPath)).thenReturn(true);
            mockFiles.when(() -> Files.isReadable(this.mockPath)).thenReturn(false);
            //when then
            Exception expectedException =  Assertions.assertThrows(IllegalArgumentException.class,() -> bean.compute(anyFile));
            Assertions.assertEquals(Constants.FILE_NOT_ACCESSIBLE, expectedException.getMessage());
        }
    }

    @Test
    void test_File_Content_Invalid() throws Exception {
        //given
        String anyFile = "test.csv";
        String invalidMimeType = "video/x-msvideo";
        bean = new SalesProcessor();
        try(MockedStatic<Files> mockFiles = mockStatic(Files.class);
            MockedStatic<Path> mockPath = mockStatic(Path.class)){
            mockPath.when(() -> Path.of(anyFile)).thenReturn(this.mockPath);
            mockFiles.when(() -> Files.exists(this.mockPath)).thenReturn(true);
            mockFiles.when(() -> Files.isReadable(this.mockPath)).thenReturn(true);
            mockFiles.when(() -> Files.probeContentType(this.mockPath)).thenReturn(invalidMimeType);
            //when then
            Exception expectedException = Assertions.assertThrows(IllegalArgumentException.class,() -> bean.compute(anyFile));
            Assertions.assertEquals(Constants.INVALID_FILE_CONTENT, expectedException.getMessage());
        }
    }

    @Test
    void test_File_With_Erroneous_Lines() throws Exception {
        //given
        String invalidLine = "P1;20;";
        Stream<String> simpleStream = Stream.of("P1;10","P2;5",invalidLine,"P1;30");
        String anyFile = "test.csv";
        String validMimeType = "text/csv";
        bean = new SalesProcessor();
        try(MockedStatic<Files> mockFiles = mockStatic(Files.class);
            MockedStatic<Path> mockPath = mockStatic(Path.class)){
            mockPath.when(() -> Path.of(anyFile)).thenReturn(this.mockPath);
            mockFiles.when(() -> Files.exists(this.mockPath)).thenReturn(true);
            mockFiles.when(() -> Files.isReadable(this.mockPath)).thenReturn(true);
            mockFiles.when(() -> Files.probeContentType(this.mockPath)).thenReturn(validMimeType);
            mockFiles.when(() -> Files.lines(this.mockPath)).thenReturn(simpleStream);
            //when then
            Exception expectedException = Assertions.assertThrows(IllegalArgumentException.class,() -> bean.compute(anyFile));
            Assertions.assertEquals(Constants.INVALID_LINE, expectedException.getMessage());
        }
    }


    @Test
    void test_Simple_File() throws Exception {
        //given
        Stream<String> simpleStream = Stream.of("P1;10","P2;5","P1;20","P1;30");
        String anyFile = "test.csv";
        String validMimeType = "text/csv";
        bean = new SalesProcessor();
        try(MockedStatic<Files> mockFiles = mockStatic(Files.class);
            MockedStatic<Path> mockPath = mockStatic(Path.class)){
            mockPath.when(() -> Path.of(anyFile)).thenReturn(this.mockPath);
            mockFiles.when(() -> Files.exists(this.mockPath)).thenReturn(true);
            mockFiles.when(() -> Files.isReadable(this.mockPath)).thenReturn(true);
            mockFiles.when(() -> Files.probeContentType(this.mockPath)).thenReturn(validMimeType);
            mockFiles.when(() -> Files.lines(this.mockPath)).thenReturn(simpleStream);
            //when
            Sale result = bean.compute(anyFile);
            // then
            Assertions.assertAll(
                    () -> Assertions.assertEquals("P1", result.productCode()),
                    () -> Assertions.assertEquals(60, result.saleAmount())
            );
        }
    }
}
