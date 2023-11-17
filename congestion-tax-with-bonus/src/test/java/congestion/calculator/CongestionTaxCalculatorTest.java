package congestion.calculator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CongestionTaxCalculatorTest {

    @ParameterizedTest
    @CsvSource({
            "2023-01-05 06:15:00, true",  // Test case for not 2013
            "2013-01-05 06:15:00, true",  // Test case for Saturday
            "2013-01-06 08:15:00, true", //  Test case for Sunday
            "2013-12-31 08:15:00, true", //  Test case for Holiday
            "2013-01-08 06:15:00, false",  // Test case for 06:00–06:29
            "2013-01-08 08:15:00, false", // Test case for 08:00–08:29
            "2013-01-08 15:15:00, false", // Test case for 15:00–15:29
            "2013-01-08 18:15:00, false",  // Test case for 18:00–18:29
            "2013-01-05 02:15:00, true",   // Test case for 18:30–05:59
            "2013-01-08 08:45:00, false",  // Test case for 08:30–14:59
            "2013-01-08 16:45:00, false"  // Test case for 15:30–16:59
    })

    public void testIsTollFreeDateFix(String dateString, Boolean bExpected) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormat.parse(dateString);

        assertEquals(bExpected, CongestionTaxCalculator.isTollFreeDate(date), "Incorrect toll free check" + dateString);
    }
}