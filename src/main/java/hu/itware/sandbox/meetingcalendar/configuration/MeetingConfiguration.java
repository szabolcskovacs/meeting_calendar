package hu.itware.sandbox.meetingcalendar.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Duration;
import java.time.LocalTime;

@Data
@ConfigurationProperties(prefix = "meeting")
public class MeetingConfiguration {
    @DateTimeFormat(pattern = "hh:mm a")
    private LocalTime dayStart;
    @DateTimeFormat(pattern = "hh:mm a")
    private LocalTime dayEnd;
    private Duration longestMeeting;

    private Integer unitLength;

}
