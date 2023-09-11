package hu.itware.sandbox.meetingcalendar.db;

import hu.itware.sandbox.meetingcalendar.db.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


}
