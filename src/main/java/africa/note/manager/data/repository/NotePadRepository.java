package africa.note.manager.data.repository;

import africa.note.manager.data.models.NotePad;
import africa.note.manager.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotePadRepository extends MongoRepository<NotePad,String> {
    NotePad findNotePadsByNoteId(String noteId);
    List<NotePad> findNotePadsByUserId(String userId);
}
