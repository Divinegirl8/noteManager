package africa.note.manager.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(){
        System.out.println("Credentials is invalid");
    }
}
