package springboot.policy_module.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.policy_module.models.PolicyRisk;

import java.util.List;

@Repository
public interface PolicyRiskRepository extends JpaRepository<PolicyRisk, Long> {
    Page<PolicyRisk> findAllByUsername(String username, Pageable pageable);
    List<PolicyRisk> findAllByPolicyId(Long policyId);
    void deleteByPolicyIdAndRiskId(Long policyId, Long riskId);
    void deleteAllByRiskId(Long riskId);
    void deleteAllByPolicyId(Long policyId);
}
