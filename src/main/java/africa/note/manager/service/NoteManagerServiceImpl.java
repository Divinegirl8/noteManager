package africa.note.manager.service;

import africa.note.manager.data.models.NotePad;
import africa.note.manager.data.models.User;
import africa.note.manager.data.repository.UserRepository;
import africa.note.manager.dtos.request.*;
import africa.note.manager.exception.InvalidCredentialsException;
import africa.note.manager.exception.UserExistException;
import africa.note.manager.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static africa.note.manager.utils.Mappers.*;

@Service
public class NoteManagerServiceImpl implements NoteManagerService {
    @Autowired
    NotePadService notePadService;
    @Autowired
    UserRepository userRepository;

    @Override
    public User register(RegisterRequest registerRequest) {
        if (userExist(registerRequest.getUsername())) throw  new UserExistException(registerRequest.getUsername() + " already exist");
        User user = map(registerRequest.getUsername(),registerRequest.getPassword(),"UID"+(userRepository.count()+1));
        userRepository.save(user);
        return user;
    }

    @Override
    public void login(LoginRequest loginRequest) {
        User user = userRepository.findUserByUsername(loginRequest.getUsername());

        if (!userExist(loginRequest.getUsername())) throw new InvalidCredentialsException();
        if (!loginRequest.getPassword().equals(user.getPassword())) throw new InvalidCredentialsException();

        user.setLogin(true);
        userRepository.save(user);

    }

    @Override
    public void logout(LogoutRequest logoutRequest) {
        User user = userRepository.findUserByUsername(logoutRequest.getUsername());

        if (!userExist(logoutRequest.getUsername())) throw new InvalidCredentialsException();
        if (!logoutRequest.getPassword().equals(user.getPassword())) throw new InvalidCredentialsException();

        user.setLogin(false);
        userRepository.save(user);
    }

    @Override
    public NotePad addNote(AddNoteRequest addNoteRequest) {
        User user = userRepository.findUserByUserId(addNoteRequest.getUserId());
        if (user == null) throw new UserNotFoundException(addNoteRequest.getUserId() + " not found");

        int noteId = user.getNoteId()+1;
        user.setNoteId(noteId);
        userRepository.save(user);

        return notePadService.addNote(addNoteRequest.getUserId(), "NID" + (user.getNoteId()), addNoteRequest.getNoteText());

    }

    private boolean userExist(String username){
        User user = userRepository.findUserByUsername(username);
        return user != null;
    }

    public User findAccountBelongingTo(String username){
        User user = userRepository.findUserByUsername(username);

        if (user == null) throw new  UserNotFoundException(username + " not found");
        return user;
    }

    @Override
    public NotePad findNote(FindNoteRequest findNoteRequest) {
        return notePadService.findNote(findNoteRequest.getUserId(), findNoteRequest.getNoteId());
    }

    @Override
    public NotePad editNote(EditNoteRequest editNoteRequest) {
        return notePadService.editNote(editNoteRequest.getUserid(), editNoteRequest.getNoteId(), editNoteRequest.getNewNote(), editNoteRequest.isAppendMessage());
    }

    @Override
    public void deleteNote(DeleteNoteRequest deleteNoteRequest) {
      notePadService.deleteNotePad(deleteNoteRequest.getUserId(), deleteNoteRequest.getNoteId());
    }

    @Override
    public List<NotePad> findAllNote(String userId) {
        return notePadService.findAllNote(userId);
    }

    @Override
    public void deleteAllNote(DeleteNoteRequest deleteNoteRequest) {
       notePadService.deleteAllNote(deleteNoteRequest.getUserId(), deleteNoteRequest.getNoteId());
    }

}
