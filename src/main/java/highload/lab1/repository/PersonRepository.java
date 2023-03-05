package highload.lab1.repository;

import highload.lab1.model.Person;
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
public interface PersonRepository extends JpaRepository<Person, UUID> {
    Page<Person> findAll(Pageable pageable);

    Optional<Person> findPersonByPersonId(UUID personId);

    void deletePersonByPersonId(UUID personId);
}
