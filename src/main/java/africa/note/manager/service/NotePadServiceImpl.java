package africa.note.manager.service;

import africa.note.manager.data.models.NotePad;
import africa.note.manager.data.models.User;
import africa.note.manager.data.repository.NotePadRepository;
import africa.note.manager.data.repository.UserRepository;
import africa.note.manager.exception.InvalidDetailsException;
import africa.note.manager.exception.NoteNotFoundException;
import africa.note.manager.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotePadServiceImpl implements NotePadService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    NotePadRepository notePadRepository;
    @Override
    public NotePad addNote(String userId, String noteId, String noteText) {
        NotePad notePad = new NotePad();

        notePad.setUserId(userId);
        notePad.setNoteId(noteId);
        notePad.setNoteText(noteText);
        notePadRepository.save(notePad);
        return notePad;
    }

    @Override
    public NotePad editNote(String userId, String noteId,String newMessage,boolean appendMessage) {
        User user = userRepository.findUserByUserId(userId);

        if (user == null) throw new UserNotFoundException(userId + " not found");


        NotePad notePad = notePadRepository.findNotePadByNoteIdAndUserId(noteId, userId);
        if (notePad != null) {
            if (appendMessage){
                String newTaskMessage = notePad.getNoteText() + " " + newMessage;
                notePad.setNoteText(newTaskMessage);
            } else {
                notePad.setNoteText(newMessage);
            }
        } else {
            throw new InvalidDetailsException("The details is invalid");
        }
        notePadRepository.save(notePad);
       return  notePad;
    }

    @Override
    public NotePad findNote(String userId, String noteId) {
        User user = userRepository.findUserByUserId(userId);

        if (user == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }

        NotePad notePad = notePadRepository.findNotePadByNoteIdAndUserId(noteId, userId);

        if (notePad == null) {
            throw new NoteNotFoundException("Note with ID " + noteId + " not found for user with ID " + userId);
        }

        return notePad;
    }

    @Override
    public void deleteNotePad(String userId, String noteId) {
        User user = userRepository.findUserByUserId(userId);


        if (user == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }
        NotePad notePad = notePadRepository.findNotePadByNoteIdAndUserId(noteId, userId);


        if (notePad == null) {
            throw new NoteNotFoundException("Note with ID " + noteId + " not found");
        }
        notePadRepository.delete(notePad);
    }

    @Override
    public List<NotePad> findAllNote(String userId) {
        User user = userRepository.findUserByUserId(userId);

        if (user == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }

        return new ArrayList<>(notePadRepository.findNotePadsByUserId(user.getUserId()));
    }

    @Override
    public void deleteAllNote(String userId, String noteId) {
        User user = userRepository.findUserByUserId(userId);


        if (user == null) {
            throw new UserNotFoundException("User with ID " + userId + " not found");
        }

        NotePad notePad = notePadRepository.findNotePadByNoteIdAndUserId(noteId,userId);

        if (notePad == null) {
            throw new NoteNotFoundException("Note with ID " + noteId + " not found");

        }

        List<NotePad> notePadList = findAllNote(user.getUserId());
        notePadRepository.deleteAll(notePadList);
    }


}
