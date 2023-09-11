package hu.itware.sandbox.meetingcalendar.service.data.impl;

import hu.itware.sandbox.meetingcalendar.db.UserRepository;
import hu.itware.sandbox.meetingcalendar.service.data.UserDataService;
import hu.itware.sandbox.meetingcalendar.db.entities.User;
import hu.itware.sandbox.meetingcalendar.service.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserDataServiceImpl implements UserDataService {


    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public UserDataServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public UserDTO createUser(UserDTO user) {
        var dbUser = modelMapper.map(user, User.class);
        userRepository.saveAndFlush(dbUser);
        return modelMapper.map(dbUser,UserDTO.class);
    }
}
