package africa.note.manager.exception;

public class UserExistException extends RuntimeException{
    public UserExistException(String message){
        super(message);
    }
}
