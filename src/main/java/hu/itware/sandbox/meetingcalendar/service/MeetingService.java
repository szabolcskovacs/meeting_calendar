package hu.itware.sandbox.meetingcalendar.service;

import hu.itware.sandbox.meetingcalendar.configuration.MeetingConfiguration;
import hu.itware.sandbox.meetingcalendar.db.entities.Meeting;
import hu.itware.sandbox.meetingcalendar.db.entities.MeetingUnit;
import hu.itware.sandbox.meetingcalendar.exception.MeetingCalendarBaseException;
import hu.itware.sandbox.meetingcalendar.service.data.MeetingDataService;
import hu.itware.sandbox.meetingcalendar.service.data.MeetingUnitDataService;
import hu.itware.sandbox.meetingcalendar.service.data.UserDataService;
import hu.itware.sandbox.meetingcalendar.service.dto.MeetingDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
@Slf4j
public class MeetingService {

    private final MeetingDataService meetingDataService;

    private final MeetingUnitDataService meetingUnitDataService;

    private final UserDataService userDataService;

    private final MeetingConfiguration meetingConfiguration;

    private final ModelMapper modelMapper;

    public MeetingService(MeetingDataService meetingDataService, MeetingUnitDataService meetingUnitDataService, UserDataService userDataService, MeetingConfiguration meetingConfiguration, ModelMapper modelMapper) {
        this.meetingDataService = meetingDataService;
        this.meetingUnitDataService = meetingUnitDataService;
        this.userDataService = userDataService;
        this.meetingConfiguration = meetingConfiguration;
        this.modelMapper = modelMapper;
    }
    @Transactional
    public MeetingDTO createMeeting(MeetingDTO meetingDTO) {

        if (meetingDTO.getUser() == null || meetingDTO.getUser().getId() == null) {
            throw new MeetingCalendarBaseException("No user provided",MeetingCalendarBaseException.ErrorCode.NO_USER_PROVIDED);
        }

        checkMeetingTimes(meetingDTO);

        var user = userDataService.findById(meetingDTO.getUser().getId());

        // validations here
        Meeting meeting = modelMapper.map(meetingDTO, Meeting.class);
        meeting.setUser(user);
        var meetingSaved = meetingDataService.saveMeeting(meeting);

        var units = fillWithUnits(meeting);
        try {
            meetingSaved.setMeetingUnits(meetingUnitDataService.saveMeetingUnits(units));
        }catch(DataIntegrityViolationException e) {
            throw new MeetingCalendarBaseException("Meeting time overlaps with existing meeting",MeetingCalendarBaseException.ErrorCode.OVERLAP_WITH_OTHER);
        }

        return modelMapper.map(meetingSaved,MeetingDTO.class);
    }

    private void checkMeetingTimes(MeetingDTO meetingDTO) {
        LocalTime startTime = LocalDateTime.ofInstant(meetingDTO.getMeetingStart().toInstant(), TimeZone.getDefault().toZoneId()).toLocalTime();
        LocalTime endTime = LocalDateTime.ofInstant(meetingDTO.getMeetingEnd().toInstant(),TimeZone.getDefault().toZoneId()).toLocalTime();

        if (startTime.equals(endTime))  {
            throw new MeetingCalendarBaseException("Meeting start and end times are the same",MeetingCalendarBaseException.ErrorCode.START_END_EQUALS );
        }

        if (startTime.isAfter(endTime))  {
            throw new MeetingCalendarBaseException("Meeting start is after end time",MeetingCalendarBaseException.ErrorCode.START_AFTER_END);
        }

        if (startTime.getMinute() != 30 && startTime.getMinute() != 0) {
            throw new MeetingCalendarBaseException("Meeting must start at 00 or 30 minutes of an hour",MeetingCalendarBaseException.ErrorCode.IMPROPER_START_TIME);
        }
        if (endTime.getMinute() != 30 && endTime.getMinute() != 0) {
            throw new MeetingCalendarBaseException("Meeting must end at 00 or 30 minutes of an hour",MeetingCalendarBaseException.ErrorCode.IMPROPER_END_TIME);
        }

        if (startTime.isBefore(meetingConfiguration.getDayStart())) {
            throw new MeetingCalendarBaseException(String.format("Meeting is before the first available time %s",meetingConfiguration.getDayStart().toString()),
                    MeetingCalendarBaseException.ErrorCode.START_TOO_EARLY);
        }
        if (endTime.isAfter(meetingConfiguration.getDayEnd())) {
            throw new MeetingCalendarBaseException(String.format("Meeting is after the last available time %s",meetingConfiguration.getDayEnd().toString()),
                    MeetingCalendarBaseException.ErrorCode.END_TOO_LATE);
        }

        if (meetingDTO.getMeetingStart().get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || meetingDTO.getMeetingStart().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            throw new MeetingCalendarBaseException("You can add meeting only for workdays",MeetingCalendarBaseException.ErrorCode.NOT_WORKDAY);
        }


        if (Duration.between(startTime,endTime).compareTo(meetingConfiguration.getLongestMeeting()) > 0){
            throw new MeetingCalendarBaseException(String.format("Meeting is longer that available maximum %d minutes",meetingConfiguration.getLongestMeeting().toMinutes()),
                    MeetingCalendarBaseException.ErrorCode.MEETING_TOO_LONG);
        }

    }

    private List<MeetingUnit> fillWithUnits(Meeting meeting) {

        LocalDateTime startDateTime =  LocalDateTime.ofInstant(meeting.getMeetingStart().toInstant(), TimeZone.getDefault().toZoneId());
        LocalDateTime endDateTime =  LocalDateTime.ofInstant(meeting.getMeetingEnd().toInstant(), TimeZone.getDefault().toZoneId());

        List<MeetingUnit> units = new ArrayList<>();

        while (startDateTime.isBefore(endDateTime)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(Date.from(startDateTime.atZone(TimeZone.getDefault().toZoneId()).toInstant()));
            units.add(MeetingUnit.builder().meeting(meeting).unitStart(cal).build());
            startDateTime = startDateTime.plusMinutes(meetingConfiguration.getUnitLength());
        }
        return units;
    }
}
