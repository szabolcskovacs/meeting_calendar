package hu.itware.sandbox.meetingcalendar.rest;

import hu.itware.sandbox.meetingcalendar.service.MeetingService;
import hu.itware.sandbox.meetingcalendar.service.dto.MeetingDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping
    public MeetingDTO createMeeting(@Valid @RequestBody MeetingDTO meeting) {
        return meetingService.createMeeting(meeting);
    }
}
