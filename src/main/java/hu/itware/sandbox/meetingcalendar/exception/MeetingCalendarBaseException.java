package hu.itware.sandbox.meetingcalendar.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class MeetingCalendarBaseException extends RuntimeException{

    private String error;

    private ErrorCode code;

    public enum ErrorCode {
        NO_USER_PROVIDED,
        START_END_EQUALS,
        START_AFTER_END,
        IMPROPER_START_TIME,
        IMPROPER_END_TIME,
        START_TOO_EARLY,
        END_TOO_LATE,
        NOT_WORKDAY,
        MEETING_TOO_LONG,
        OVERLAP_WITH_OTHER
    };

}
