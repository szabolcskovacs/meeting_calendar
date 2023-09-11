package hu.itware.sandbox.meetingcalendar.service.data;

import hu.itware.sandbox.meetingcalendar.db.entities.MeetingUnit;

import java.util.List;

public interface MeetingUnitDataService {

    public List<MeetingUnit> saveMeetingUnits(List<MeetingUnit> unit);
}
