package africa.note.manager.dtos.request;

import lombok.Data;

@Data
public class AddNoteRequest {
    private String userId;
    private String noteText;
}
