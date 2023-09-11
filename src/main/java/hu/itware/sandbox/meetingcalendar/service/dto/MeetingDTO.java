package hu.itware.sandbox.meetingcalendar.service.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Calendar;

@Data
public class MeetingDTO {

    private Long id;
    @NotNull
    private UserDTO user;
    @NotNull
    @Future
    private Calendar meetingStart;
    @NotNull
    @Future
    private Calendar meetingEnd;


}
