package highload.lab1.repository;

import highload.lab1.model.User;
import org.springframework.context.annotation.Bean;
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

public interface UserRepository extends JpaRepository<User, UUID> {
    Page<User> findAll(Pageable pageable);

    Optional<User> findUserByUserId(UUID id);

    void deleteUserByUserId(UUID id);

    Optional<User> findByUsername(String username);


}

