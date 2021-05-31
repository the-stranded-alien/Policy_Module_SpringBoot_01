package springboot.policy_module.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import springboot.policy_module.models.Policy;
import springboot.policy_module.models.Risk;
import springboot.policy_module.repositories.PolicyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    @Override
    public List<Policy> getAllPolicies() {
        return policyRepository.findAll();
    }

    @Override
    public Policy savePolicy(Policy policy) {
        policy.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return this.policyRepository.save(policy);
    }

    @Override
    public Policy getPolicyById(Long id) {
        Optional<Policy> optional = policyRepository.findById(id);
        Policy policy = null;
        if(optional.isPresent()) {
            policy = optional.get();
        } else {
            throw new RuntimeException("Policy With Id : " + id + " Not Found !");
        }
        return policy;
    }

    @Override
    public void deletePolicyById(Long id) {
        this.policyRepository.deleteById(id);
    }

    @Override
    public Page<Policy> findPaginated(Integer pageNo, Integer pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.policyRepository.findAllByUsername(SecurityContextHolder.getContext().getAuthentication().getName(), pageable);
    }

    @Override
    public List<Policy> getPolicyByUsername(String currentUserUsername) {
        return this.policyRepository.findAllByUsername(currentUserUsername);
    }
}
