package springboot.policy_module.services;

import org.springframework.data.domain.Page;
import springboot.policy_module.models.PolicyRisk;

import java.util.List;

public interface PolicyRiskService {
    void savePolicyRiskMapping(PolicyRisk policyRisk);
    List<PolicyRisk> getAllPolicyRiskByPolicyId(Long policyId);
    Page<PolicyRisk> findPaginatedMapping(Integer pageNo, Integer pageSize, String sortField, String sortDirection);
    void deletePolicyRiskMappingById(Long policyId, Long riskId);
    void deletePolicyRiskMappingByPolicyId(Long policyId);
    void deletePolicyRiskMappingByRiskId(Long riskId);
}
