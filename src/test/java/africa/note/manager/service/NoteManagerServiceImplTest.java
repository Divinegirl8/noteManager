package africa.note.manager.service;

import africa.note.manager.data.repository.NotePadRepository;
import africa.note.manager.data.repository.UserRepository;
import africa.note.manager.dtos.request.*;
import africa.note.manager.exception.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NoteManagerServiceImplTest {
    @Autowired
    NoteManagerService noteManagerService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    NotePadRepository notePadRepository;

    @AfterEach
    void  cleanUp(){
        userRepository.deleteAll();
        notePadRepository.deleteAll();
    }

    @Test void test_That_User_Can_Register(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        noteManagerService.register(registerRequest);
        assertEquals(1,userRepository.count());
    }


    @Test void test_That_User_Cannot_Register_With_Same_Username(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        noteManagerService.register(registerRequest);
        assertThrows(UserExistException.class,()->noteManagerService.register(registerRequest));
    }

    @Test void test_That_User_Can_Register_And_Login(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        noteManagerService.register(registerRequest);

        assertFalse(noteManagerService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        noteManagerService.login(loginRequest);

        assertTrue(noteManagerService.findAccountBelongingTo("username").isLogin());
    }

    @Test void test_That_User_Can_Register_And_If_Login_WIth_Wrong_Username_It_Throws_Error(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        noteManagerService.register(registerRequest);

        assertFalse(noteManagerService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("name");
        loginRequest.setPassword("password");


       assertThrows(InvalidCredentialsException.class,()->  noteManagerService.login(loginRequest));

    }


    @Test void test_That_User_Can_Register_And_If_Login_WIth_Wrong_Password_It_Throws_Error(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        noteManagerService.register(registerRequest);

        assertFalse(noteManagerService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("word");


        assertThrows(InvalidCredentialsException.class,()->  noteManagerService.login(loginRequest));

    }

    @Test void test_That_User_Can_Register_Login_And_Logout(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        noteManagerService.register(registerRequest);

        assertFalse(noteManagerService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        noteManagerService.login(loginRequest);

        assertTrue(noteManagerService.findAccountBelongingTo("username").isLogin());

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username");
        logoutRequest.setPassword("password");
        noteManagerService.logout(logoutRequest);
        assertFalse(noteManagerService.findAccountBelongingTo("username").isLogin());
    }
    @Test void test_That_User_Can_Register_Login_And_Logout_With_Wrong_Password_Throws_Exception(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        noteManagerService.register(registerRequest);

        assertFalse(noteManagerService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        noteManagerService.login(loginRequest);

        assertTrue(noteManagerService.findAccountBelongingTo("username").isLogin());

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username");
        logoutRequest.setPassword("word");
        assertThrows(InvalidCredentialsException.class,()->noteManagerService.logout(logoutRequest));
    }

    @Test void test_That_User_Can_Register_Login_And_Logout_With_Wrong_Username_Throws_Exception(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        noteManagerService.register(registerRequest);

        assertFalse(noteManagerService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        noteManagerService.login(loginRequest);

        assertTrue(noteManagerService.findAccountBelongingTo("username").isLogin());

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("name");
        logoutRequest.setPassword("password");
        assertThrows(InvalidCredentialsException.class,()->noteManagerService.logout(logoutRequest));
    }

    @Test void test_That_User_Can_Register_Login_AddNote_And_Logout(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        noteManagerService.register(registerRequest);

        assertFalse(noteManagerService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        noteManagerService.login(loginRequest);

        assertTrue(noteManagerService.findAccountBelongingTo("username").isLogin());
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setUserId("UID1");
        addNoteRequest.setNoteText("Note");
        noteManagerService.addNote(addNoteRequest);

        FindNoteRequest findNoteRequest = new FindNoteRequest();
        findNoteRequest.setUserId("UID1");
        findNoteRequest.setNoteId("NID1");
        assertNotNull(noteManagerService.findNote(findNoteRequest));

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username");
        logoutRequest.setPassword("password");
        noteManagerService.logout(logoutRequest);
        assertFalse(noteManagerService.findAccountBelongingTo("username").isLogin());
    }


    @Test void test_That_User_Can_Register_Login_AddNote_WithWrongUsername_Throws_Exception(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        noteManagerService.register(registerRequest);

        assertFalse(noteManagerService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        noteManagerService.login(loginRequest);

        assertTrue(noteManagerService.findAccountBelongingTo("username").isLogin());
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setUserId("UID2");
        addNoteRequest.setNoteText("Note");
        assertThrows(UserNotFoundException.class,()->noteManagerService.addNote(addNoteRequest));

    }



    @Test void test_That_User_Can_Register_Login_AddNote_EditNote_And_Logout(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        noteManagerService.register(registerRequest);

        assertFalse(noteManagerService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        noteManagerService.login(loginRequest);

        assertTrue(noteManagerService.findAccountBelongingTo("username").isLogin());
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setUserId("UID1");
        addNoteRequest.setNoteText("Note");
        noteManagerService.addNote(addNoteRequest);

        EditNoteRequest editNoteRequest = new EditNoteRequest();
        editNoteRequest.setUserid("UID1");
        editNoteRequest.setNoteId("NID1");
        editNoteRequest.setAppendMessage(true);
        editNoteRequest.setNewNote("pad");

        assertNotNull(noteManagerService.editNote(editNoteRequest));
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username");
        logoutRequest.setPassword("password");
        noteManagerService.logout(logoutRequest);
        assertFalse(noteManagerService.findAccountBelongingTo("username").isLogin());
    }

    @Test void test_That_User_Can_Register_Login_AddNote_EditNote_By_Clearing_The_Previous_Note_And_Logout(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        noteManagerService.register(registerRequest);

        assertFalse(noteManagerService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        noteManagerService.login(loginRequest);

        assertTrue(noteManagerService.findAccountBelongingTo("username").isLogin());
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setUserId("UID1");
        addNoteRequest.setNoteText("Note");
        noteManagerService.addNote(addNoteRequest);

        EditNoteRequest editNoteRequest = new EditNoteRequest();
        editNoteRequest.setUserid("UID1");
        editNoteRequest.setNoteId("NID1");
        editNoteRequest.setAppendMessage(false);
        editNoteRequest.setNewNote("pad");
        assertNotNull(noteManagerService.editNote(editNoteRequest));

        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username");
        logoutRequest.setPassword("password");
        noteManagerService.logout(logoutRequest);
        assertFalse(noteManagerService.findAccountBelongingTo("username").isLogin());
    }


    @Test void test_That_User_Can_Register_Login_AddNote_EditNote_With_Wrong_Username_Throws_Exception(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        noteManagerService.register(registerRequest);

        assertFalse(noteManagerService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        noteManagerService.login(loginRequest);

        assertTrue(noteManagerService.findAccountBelongingTo("username").isLogin());
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setUserId("UID1");
        addNoteRequest.setNoteText("Note");
        noteManagerService.addNote(addNoteRequest);

        EditNoteRequest editNoteRequest = new EditNoteRequest();
        editNoteRequest.setUserid("UID16");
        editNoteRequest.setNoteId("NID1");
        editNoteRequest.setAppendMessage(false);
        editNoteRequest.setNewNote("pad");
        assertThrows(UserNotFoundException.class,()->noteManagerService.editNote(editNoteRequest));

    }


    @Test void test_That_User_Can_Register_Login_AddNote_EditNote_With_Wrong_Password_Throws_Exception(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        noteManagerService.register(registerRequest);

        assertFalse(noteManagerService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        noteManagerService.login(loginRequest);

        assertTrue(noteManagerService.findAccountBelongingTo("username").isLogin());
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setUserId("UID1");
        addNoteRequest.setNoteText("Note");
        noteManagerService.addNote(addNoteRequest);

        EditNoteRequest editNoteRequest = new EditNoteRequest();
        editNoteRequest.setUserid("UID1");
        editNoteRequest.setNoteId("NID7");
        editNoteRequest.setAppendMessage(false);
        editNoteRequest.setNewNote("pad");
        assertThrows(InvalidDetailsException.class,()->noteManagerService.editNote(editNoteRequest));
    }

    @Test void test_That_User_Can_Register_Login_AddNote_DeleteNote(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        noteManagerService.register(registerRequest);

        assertFalse(noteManagerService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        noteManagerService.login(loginRequest);

        assertTrue(noteManagerService.findAccountBelongingTo("username").isLogin());
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setUserId("UID1");
        addNoteRequest.setNoteText("Note");
        noteManagerService.addNote(addNoteRequest);

         DeleteNoteRequest deleteNoteRequest = new DeleteNoteRequest();
         deleteNoteRequest.setUserId("UID1");
         deleteNoteRequest.setNoteId("NID1");
         noteManagerService.deleteNote(deleteNoteRequest);

         FindNoteRequest findNoteRequest = new FindNoteRequest();
         findNoteRequest.setUserId("UID1");
         findNoteRequest.setNoteId("NID1");
         assertThrows(NoteNotFoundException.class,()->noteManagerService.findNote(findNoteRequest));

    }



    @Test void test_That_User_Can_Register_Login_AddNote_And_FindAllNote(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        noteManagerService.register(registerRequest);

        assertFalse(noteManagerService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        noteManagerService.login(loginRequest);

        assertTrue(noteManagerService.findAccountBelongingTo("username").isLogin());
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setUserId("UID1");
        addNoteRequest.setNoteText("Note");
        noteManagerService.addNote(addNoteRequest);
        noteManagerService.addNote(addNoteRequest);

        assertNotNull(noteManagerService.findAllNote("UID1"));


        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setUsername("username");
        logoutRequest.setPassword("password");
        noteManagerService.logout(logoutRequest);
        assertFalse(noteManagerService.findAccountBelongingTo("username").isLogin());
    }


    @Test void test_That_User_Can_Register_Login_AddNote_DeleteAllNote(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("username");
        registerRequest.setPassword("password");
        noteManagerService.register(registerRequest);

        assertFalse(noteManagerService.findAccountBelongingTo("username").isLogin());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");
        noteManagerService.login(loginRequest);

        assertTrue(noteManagerService.findAccountBelongingTo("username").isLogin());
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setUserId("UID1");
        addNoteRequest.setNoteText("Note");
        noteManagerService.addNote(addNoteRequest);

        DeleteNoteRequest deleteNoteRequest = new DeleteNoteRequest();
        deleteNoteRequest.setUserId("UID1");
        deleteNoteRequest.setNoteId("NID1");
        noteManagerService.deleteAllNote(deleteNoteRequest);

        FindNoteRequest findNoteRequest = new FindNoteRequest();
        findNoteRequest.setUserId("UID1");
        findNoteRequest.setNoteId("NID1");
        assertThrows(NoteNotFoundException.class,()->noteManagerService.findNote(findNoteRequest));

    }



}