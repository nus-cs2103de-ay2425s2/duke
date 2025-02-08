// src/test/java/utils/DateTimeFormatTest.java
package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeFormatTest {
    private static final DateTimeFormatter[] FORMATTERS = {
            DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"),
    };

    public static void main(String[] args) {
        String[] testDates = {
                "12/08/2025 1200",
                "12/08/2025",
                "2025-08-12 12:00",
                "2025-08-12"
        };

        for (String testDate : testDates) {
            boolean parsed = false;
            for (DateTimeFormatter formatter : FORMATTERS) {
                try {
                    LocalDateTime dateTime = LocalDateTime.parse(testDate, formatter);
                    System.out.println("Parsed date: " + dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm")));
                    parsed = true;
                    break;
                } catch (DateTimeParseException e) {
                    // Continue to the next formatter
                }
            }
            if (!parsed) {
                System.out.println("Failed to parse date: " + testDate);
            }
        }
    }
}