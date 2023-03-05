package highload.lab1.repository;

import highload.lab1.model.Market;
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
public interface MarketRepository extends JpaRepository<Market, UUID> {
    Page<Market> findAll(Pageable pageable);

    void deleteMarketByMarketId(UUID marketId);

    Optional<Market> findById(UUID uuid);
}
