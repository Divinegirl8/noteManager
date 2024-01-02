package africa.note.manager.service;

import africa.note.manager.data.models.NotePad;
import africa.note.manager.data.models.User;
import africa.note.manager.dtos.request.*;

import java.util.List;

public interface NoteManagerService {
 User register(RegisterRequest registerRequest);
 void login(LoginRequest loginRequest);
void logout(LogoutRequest logoutRequest);
 NotePad addNote(AddNoteRequest addNoteRequest);
 User findAccountBelongingTo(String username);
 NotePad findNote(FindNoteRequest findNoteRequest);

 NotePad editNote(EditNoteRequest editNoteRequest);
 void deleteNote(DeleteNoteRequest deleteNoteRequest);
 List<NotePad> findAllNote(String userId);

 void deleteAllNote(DeleteNoteRequest deleteNoteRequest);
}
