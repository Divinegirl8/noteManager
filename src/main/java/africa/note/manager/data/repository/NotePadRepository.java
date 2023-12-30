package africa.note.manager.data.repository;

import africa.note.manager.data.models.NotePad;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotePadRepository extends MongoRepository<NotePad,String> {
    NotePad findNotePadsByNoteId(String noteId);
}
