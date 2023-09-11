package hu.itware.sandbox.meetingcalendar.service.data.impl;

import hu.itware.sandbox.meetingcalendar.db.MeetingRepository;
import hu.itware.sandbox.meetingcalendar.db.MeetingUnitRepository;
import hu.itware.sandbox.meetingcalendar.db.UserRepository;
import hu.itware.sandbox.meetingcalendar.service.data.MeetingDataService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import hu.itware.sandbox.meetingcalendar.db.entities.Meeting;

@Service
@Slf4j
public class MeetingDataServiceImpl implements MeetingDataService {

    private final MeetingRepository meetingRepository;
    private final MeetingUnitRepository meetingUnitRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public MeetingDataServiceImpl(MeetingRepository meetingRepository, MeetingUnitRepository meetingUnitRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.meetingRepository = meetingRepository;
        this.meetingUnitRepository = meetingUnitRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Meeting saveMeeting(Meeting meeting) {
        return meetingRepository.saveAndFlush(meeting);
    }
}
