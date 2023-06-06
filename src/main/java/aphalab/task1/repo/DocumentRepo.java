package aphalab.task1.repo;

import aphalab.task1.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepo extends JpaRepository<Document, Long> {

    List<Document> findByNumberAndActive(String number, Boolean active);
}
