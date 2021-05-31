package springboot.policy_module.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.policy_module.models.Risk;

@Repository
public interface RiskRepository extends JpaRepository<Risk, Long> {
    Page<Risk> findAllByUsername(String username, Pageable pageable);
}
