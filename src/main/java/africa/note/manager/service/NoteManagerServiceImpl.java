package africa.note.manager.service;

import africa.note.manager.data.models.User;
import africa.note.manager.data.repository.UserRepository;
import africa.note.manager.dtos.request.LoginRequest;
import africa.note.manager.dtos.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static africa.note.manager.utils.Mappers.*;

@Service
public class NoteManagerServiceImpl implements NoteManagerService {
    @Autowired
    NotePadService notePadService;
    @Autowired
    UserRepository userRepository;

    @Override
    public User register(RegisterRequest registerRequest) {
        User user = map(registerRequest.getUsername(),registerRequest.getPassword(),"UID"+(userRepository.count()+1));
        userRepository.save(user);
        return user;
    }

    @Override
    public void login(LoginRequest loginRequest) {

    }

}
