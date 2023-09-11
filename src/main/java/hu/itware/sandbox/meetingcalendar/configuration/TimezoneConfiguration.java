package hu.itware.sandbox.meetingcalendar.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "timezone")
@Data
public class TimezoneConfiguration {
    private String defaultTimezone;
}
