package hu.itware.sandbox.meetingcalendar.service.data;

import hu.itware.sandbox.meetingcalendar.db.entities.User;
import hu.itware.sandbox.meetingcalendar.service.dto.UserDTO;

public interface UserDataService {

    public User findById(Long id);
    public UserDTO createUser(UserDTO meeting);
}
