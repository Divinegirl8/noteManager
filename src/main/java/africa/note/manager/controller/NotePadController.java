package africa.note.manager.controller;

import africa.note.manager.data.models.NotePad;
import africa.note.manager.data.models.User;
import africa.note.manager.dtos.request.*;
import africa.note.manager.dtos.response.*;
import africa.note.manager.service.NoteManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NotePadController {
    @Autowired
    NoteManagerService noteManagerService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
        RegisterResponse registerResponse = new RegisterResponse();

        try {
            User user = noteManagerService.register(registerRequest);
            registerResponse.setMessage("Successful User Id is " + user);
            return  new ResponseEntity<>(new ApiResponse(true,registerResponse), HttpStatus.CREATED);
        } catch (Exception exception){
            registerResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,registerResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        LoginResponse loginResponse = new LoginResponse();

        try {
            noteManagerService.login(loginRequest);
            loginResponse.setMessage("Login successful");
            return  new ResponseEntity<>(new ApiResponse(true,loginResponse), HttpStatus.CREATED);
        }catch (Exception exception){
            loginResponse.setMessage(exception.getMessage());
            return  new ResponseEntity<>(new ApiResponse(false,loginResponse), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest logoutRequest){
        LogoutResponse logoutResponse = new LogoutResponse();
        try {
            noteManagerService.logout(logoutRequest);
            logoutResponse.setMessage("Logout successful");
            return new ResponseEntity<>(new ApiResponse(true,logoutResponse), HttpStatus.CREATED);
        }catch (Exception exception){
            logoutResponse.setMessage(exception.getMessage());
            return  new ResponseEntity<>(new ApiResponse(false,logoutResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addNote")
    public ResponseEntity<?> addNote(@RequestBody AddNoteRequest addNoteRequest){
        AddNoteResponse addNoteResponse = new AddNoteResponse();
        try {
           NotePad notePad = noteManagerService.addNote(addNoteRequest);
            addNoteResponse.setMessage("Note added successfully, Note id is " + notePad);
            return  new ResponseEntity<>(new ApiResponse(true,addNoteResponse), HttpStatus.CREATED);
        }catch (Exception exception){
            addNoteResponse.setMessage(exception.getMessage());
            return  new ResponseEntity<>(new ApiResponse(false,addNoteResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findNote")
    public ResponseEntity<?> findNote(@RequestBody FindNoteRequest findNoteRequest){
        FindNoteResponse findNoteResponse = new FindNoteResponse();
        try {
            NotePad notePad = noteManagerService.findNote(findNoteRequest);
            findNoteResponse.setMessage(notePad+"");
            return  new ResponseEntity<>(new ApiResponse(true,findNoteResponse), HttpStatus.CREATED);
        }catch (Exception exception){
            findNoteResponse.setMessage(exception.getMessage());
            return  new ResponseEntity<>(new ApiResponse(false,findNoteResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/editNote")
    public ResponseEntity<?> editNote(@RequestBody EditNoteRequest editNoteRequest){
        EditNoteResponse editNoteResponse = new EditNoteResponse();
        try {
           NotePad notePad = noteManagerService.editNote(editNoteRequest);
            editNoteResponse.setMessage(notePad+"");
            return  new ResponseEntity<>(new ApiResponse(true,editNoteResponse), HttpStatus.CREATED);
        }catch (Exception exception){
            editNoteResponse.setMessage(exception.getMessage());
            return  new ResponseEntity<>(new ApiResponse(false,editNoteResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteNote")
    public ResponseEntity<?> deleteNote(@RequestBody DeleteNoteRequest deleteNoteRequest){
        DeleteNoteResponse deleteNoteResponse = new DeleteNoteResponse();
        try {
            noteManagerService.deleteNote(deleteNoteRequest);
            deleteNoteResponse.setMessage("Note deleted successfully");
            return new ResponseEntity<>(new ApiResponse(true,deleteNoteResponse), HttpStatus.CREATED);
        }catch (Exception exception){
            deleteNoteResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,deleteNoteResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAllNote/{userId}")
    public ResponseEntity<?> findAllNote(@PathVariable("userId") String userId){
        FindAllNoteResponse findAllNoteResponse = new FindAllNoteResponse();
        try {
            List<NotePad> notePadList = noteManagerService.findAllNote(userId);
            findAllNoteResponse.setMessage(notePadList+"");
            return new ResponseEntity<>(new ApiResponse(true,findAllNoteResponse), HttpStatus.CREATED);
        }catch (Exception exception){
            findAllNoteResponse.setMessage(exception.getMessage());
            return  new ResponseEntity<>(new ApiResponse(false,findAllNoteResponse), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteAllNote")
    public ResponseEntity<?> deleteAllNote(@RequestBody DeleteNoteRequest deleteNoteRequest){
        DeleteNoteResponse deleteNoteResponse = new DeleteNoteResponse();
        try {
            noteManagerService.deleteAllNote(deleteNoteRequest);
            deleteNoteResponse.setMessage("Note deleted");
            return  new ResponseEntity<>(new ApiResponse(true,deleteNoteResponse), HttpStatus.CREATED);
        }catch (Exception exception){
            deleteNoteResponse.setMessage(exception.getMessage());
            return new ResponseEntity<>(new ApiResponse(false,deleteNoteResponse), HttpStatus.BAD_REQUEST);
        }
    }



}
