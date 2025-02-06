package task;

import java.time.format.DateTimeFormatter;

/**
 * HasDeadline Interface
 */
public interface HasDeadline {
    static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");
    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mma");

    public String getDeadLine();
}
