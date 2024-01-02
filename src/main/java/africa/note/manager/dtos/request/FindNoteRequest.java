package africa.note.manager.dtos.request;

import lombok.Data;

@Data
public class FindNoteRequest {
    private String userId;
    private String noteId;
}
