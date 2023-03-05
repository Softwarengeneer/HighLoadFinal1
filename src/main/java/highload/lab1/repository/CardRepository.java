package highload.lab1.repository;

import highload.lab1.model.Card;
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
public interface CardRepository extends JpaRepository<Card, UUID> {
    Page<Card> findAll(Pageable pageable);

    Optional<Card> findCardByCardId(UUID cardId);

    void deleteCardByCardId(UUID cardId);
}
