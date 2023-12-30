package africa.note.manager.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class NotePad {
    @Id
    private String noteId;
    private String noteText;
    private String userId;
}
