package africa.note.manager.service;

import africa.note.manager.data.models.NotePad;
import africa.note.manager.data.models.User;
import africa.note.manager.data.repository.NotePadRepository;
import africa.note.manager.data.repository.UserRepository;
import africa.note.manager.exception.NoteNotFound;
import africa.note.manager.exception.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotePadServiceImpl implements NotePadService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    NotePadRepository notePadRepository;
    @Override
    public void addNote(String userId, String noteId, String noteText) {
        User user = userRepository.findUserByUserId(userId);

        if (user == null) throw new UserNotFound(userId + " not found");

        NotePad notePad = new NotePad();

        notePad.setUserId(user.getUserId());
        notePad.setNoteId(noteId);
        notePad.setNoteText(noteText);
        notePadRepository.save(notePad);
    }

    @Override
    public NotePad editNote(String userId, String noteId,String newMessage,boolean appendMessage) {
        User user = userRepository.findUserByUserId(userId);
        NotePad notePad = notePadRepository.findNotePadsByNoteId(noteId);

        if (user == null) throw new UserNotFound(userId + " not found");
        if (notePad == null) throw new NoteNotFound(noteId + " not found");

        if (user.getNoteId().equals(notePad.getNoteId())) {
            if (appendMessage){
                String newTaskMessage = notePad.getNoteText() + " " + newMessage;
                notePad.setNoteText(newTaskMessage);
            } else {
                notePad.setNoteText(newMessage);
            }
        }
        notePadRepository.save(notePad);
       return  notePad;
    }




}
