package highload.lab1.repository;

import highload.lab1.model.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Repository
@Component
public interface ContentRepository extends JpaRepository<Content, UUID> {
    Page<Content> findAll(Pageable pageable);

    Optional<Content> findContentByContentId(UUID contentId);

    void deleteContentByContentId(UUID contentId);
}
