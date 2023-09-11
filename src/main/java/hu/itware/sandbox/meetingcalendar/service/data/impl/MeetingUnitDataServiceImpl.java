package hu.itware.sandbox.meetingcalendar.service.data.impl;

import hu.itware.sandbox.meetingcalendar.db.MeetingUnitRepository;
import hu.itware.sandbox.meetingcalendar.db.entities.MeetingUnit;
import hu.itware.sandbox.meetingcalendar.service.data.MeetingUnitDataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingUnitDataServiceImpl implements MeetingUnitDataService {

    private final MeetingUnitRepository meetingUnitRepository;

    public MeetingUnitDataServiceImpl(MeetingUnitRepository meetingUnitRepository) {
        this.meetingUnitRepository = meetingUnitRepository;
    }

    @Override
    public List<MeetingUnit> saveMeetingUnits(List<MeetingUnit> units) {
        return meetingUnitRepository.saveAll(units);
    }
}
