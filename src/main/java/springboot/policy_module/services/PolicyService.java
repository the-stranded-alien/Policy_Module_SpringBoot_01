package springboot.policy_module.services;

import org.springframework.data.domain.Page;
import springboot.policy_module.models.Policy;

import java.util.List;

public interface PolicyService {
    List<Policy> getAllPolicies();
    Policy savePolicy(Policy policy);
    Policy getPolicyById(Long id);
    void deletePolicyById(Long id);
    Page<Policy> findPaginated(Integer pageNo, Integer pageSize, String sortField, String sortDirection);
    List<Policy> getPolicyByUsername(String currentUserUsername);
}
