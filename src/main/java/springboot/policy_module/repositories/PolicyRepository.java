package springboot.policy_module.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.policy_module.models.Policy;

import java.util.List;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    Page<Policy> findAllByUsername(String username, Pageable pageable);
    List<Policy> findAllByUsername(String username);
}
