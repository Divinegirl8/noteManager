package africa.note.manager.service;

import africa.note.manager.data.models.NotePad;

import java.util.List;

public interface NotePadService {
    void addNote(String userId, String noteId, String noteText);
    NotePad editNote(String userId, String noteId, String newMessage, boolean appendMessage);

    NotePad findNote(String userId, String noteId);

    void  deleteNotePad(String userId, String noteId);

    List<NotePad> findAllNote(String userId);

    void deleteAllNote(String userId, String noteId);


}
