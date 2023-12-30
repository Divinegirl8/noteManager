package africa.note.manager.service;

import africa.note.manager.data.models.NotePad;

public interface NotePadService {
    void addNote(String userId, String noteId, String noteText);
    NotePad editNote(String userId, String noteId, String newMessage, boolean appendMessage);
}
