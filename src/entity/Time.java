package entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Time {
    private LocalDateTime time;
    private DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public Time() {
        this.time = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return time.format(myFormatObj);
    }
}
