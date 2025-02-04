package io;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class InputValidator {
    public static boolean isValidDate(String stringDate, boolean requiresFuture) {
        DateTimeFormatter dayMonthYearFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        DateTimeFormatter dayMonthFormatter = DateTimeFormatter.ofPattern("d/M");

        List<DateTimeFormatter> dateFormatters = new ArrayList<>();
        dateFormatters.add(dayMonthYearFormatter);
        dateFormatters.add(dayMonthFormatter);

        // parse information without a year
        for (DateTimeFormatter formatter : dateFormatters) {
            try {
                LocalDate date;
                if (formatter.equals(dayMonthFormatter)) {
                    date = LocalDate.parse(stringDate + "/" + Year.now(), dayMonthYearFormatter);
                }
                else {
                    date = LocalDate.parse(stringDate, formatter);
                }

                if (requiresFuture) {
                    return date.isAfter(LocalDate.now());
                }
                return true;
            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    /**
     * Method to check if the provided string follows the dd/MM or dd/MM H:m format
     *
     * @param stringDateTime String to be checked
     * @param requiresFuture Indicates if stringDateTime needs to be in the future
     * @return boolean that indicates if it is a valid time format
     */
    public static boolean isValidDateTime(String stringDateTime, boolean requiresFuture) {
        DateTimeFormatter dayMonthYearTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy H:m");
        DateTimeFormatter dayMonthTimeFormatter = DateTimeFormatter.ofPattern("dd/MM H:m");

        List<DateTimeFormatter> dateYearFormatters = new ArrayList<>();
        dateYearFormatters.add(dayMonthYearTimeFormatter);
        dateYearFormatters.add(dayMonthTimeFormatter);

        List<String> stringDateList = new ArrayList<>(List.of(stringDateTime.split(" ")));

        for (DateTimeFormatter formatter : dateYearFormatters) {
            try {
                LocalDateTime dateTime;
                if (formatter.equals(dayMonthTimeFormatter)) {
                    String date = stringDateList.removeFirst();
                    stringDateList.addFirst(date + "/" + Year.now());
                    dateTime = LocalDateTime.parse(String.join(" ", stringDateList), dayMonthYearTimeFormatter);
                }
                else {
                    dateTime = LocalDateTime.parse(stringDateTime, formatter);
                }

                if (requiresFuture) {
                    return dateTime.isAfter(LocalDateTime.now());
                }
                return true;
            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

}
