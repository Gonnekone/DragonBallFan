package spring.app.project3.util;

import java.time.LocalDateTime;

public class MeasuremantErrorResponse {

    private String message;
    private LocalDateTime time;

    public MeasuremantErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
