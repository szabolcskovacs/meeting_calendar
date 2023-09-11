package hu.itware.sandbox.meetingcalendar.service;

import hu.itware.sandbox.meetingcalendar.service.data.UserDataService;
import hu.itware.sandbox.meetingcalendar.service.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDataService userDataService;

    public UserService(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    public UserDTO createUser(UserDTO user) {
        return userDataService.createUser(user);
    }
}
