package africa.note.manager.exception;


public class NoteNotFound extends RuntimeException{
    public NoteNotFound(String message){
        super(message);
    }
}
