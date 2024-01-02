package africa.note.manager.dtos.request;

import lombok.Data;

@Data
public class EditNoteRequest {
    private String userid;
    private String noteId;
    private String newNote;
    private boolean appendMessage;

}
