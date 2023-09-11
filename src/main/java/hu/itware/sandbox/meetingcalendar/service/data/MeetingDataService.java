package hu.itware.sandbox.meetingcalendar.service.data;

import hu.itware.sandbox.meetingcalendar.db.entities.Meeting;

public interface MeetingDataService {

    public Meeting saveMeeting(Meeting meeting);
}
