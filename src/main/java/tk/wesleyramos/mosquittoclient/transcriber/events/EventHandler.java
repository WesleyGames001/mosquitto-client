package tk.wesleyramos.mosquittoclient.transcriber.events;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {
    byte priority() default 0;
}
