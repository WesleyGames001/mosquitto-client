package tk.wesleyramos.mosquittoclient.transcriber.types;

import tk.wesleyramos.mosquittoclient.transcriber.events.Event;

public class AuthEvent implements Event {

    private final int status;
    private final String message;

    public AuthEvent(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
