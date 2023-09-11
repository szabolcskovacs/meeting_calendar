package hu.itware.sandbox.meetingcalendar;


import hu.itware.sandbox.meetingcalendar.configuration.MeetingConfiguration;
import hu.itware.sandbox.meetingcalendar.configuration.TimezoneConfiguration;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.TimeZone;

@SpringBootApplication
@ComponentScan(basePackages = {"hu.itware.sandbox.meetingcalendar"})
@EntityScan(basePackages = {"hu.itware.sandbox.meetingcalendar"})
@EnableConfigurationProperties({MeetingConfiguration.class,TimezoneConfiguration.class})
@Slf4j
public class MeetingCalendarApp {

    private final TimezoneConfiguration timezoneConfiguration;

    public MeetingCalendarApp(TimezoneConfiguration timezoneConfiguration) {
        this.timezoneConfiguration = timezoneConfiguration;
    }

    @PostConstruct
    public void init() {
        var tz = timezoneConfiguration.getDefaultTimezone();
        TimeZone.setDefault(TimeZone.getTimeZone(tz));
    }
    public static void main(String[] args) {
        SpringApplication.run(MeetingCalendarApp.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(){ return args -> log.info("App started");};
}