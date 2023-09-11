package hu.itware.sandbox.meetingcalendar.db;

import hu.itware.sandbox.meetingcalendar.db.entities.MeetingUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingUnitRepository extends JpaRepository<MeetingUnit,Long> {
}
