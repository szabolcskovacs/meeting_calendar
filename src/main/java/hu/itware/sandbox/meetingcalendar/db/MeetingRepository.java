package hu.itware.sandbox.meetingcalendar.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import hu.itware.sandbox.meetingcalendar.db.entities.Meeting;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting,Long> {
}
