package ru.bespalyy.server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import ru.bespalyy.server.model.DocumentModel;

import java.util.List;

@Repository
public interface DocumentRepository extends MongoRepository<DocumentModel, String> {

    @Query("{'author': {$regex: ?0, $options: 'i'}}")
    List<DocumentModel> findAllByAuthorContaining(String keyword);

    @Query("{'title': {$regex: ?0, $options: 'i'}}")
    List<DocumentModel> findAllByTitleContaining(String keyword);
}
