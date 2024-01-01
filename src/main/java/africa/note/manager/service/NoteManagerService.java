package africa.note.manager.service;

import africa.note.manager.data.models.User;
import africa.note.manager.dtos.request.LoginRequest;
import africa.note.manager.dtos.request.RegisterRequest;

public interface NoteManagerService {
 User register(RegisterRequest registerRequest);
 void login(LoginRequest loginRequest);

}
