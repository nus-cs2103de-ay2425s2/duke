package task;

import java.time.format.DateTimeFormatter;

/**
 * HasStart interfact
 */
public interface HasStart {
    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");
    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mma");
    public String getStartDateTime();
}
