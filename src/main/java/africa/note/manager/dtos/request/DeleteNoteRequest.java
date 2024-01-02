package africa.note.manager.dtos.request;

import lombok.Data;

@Data
public class DeleteNoteRequest {
    private String userId;
    private String noteId;
}
