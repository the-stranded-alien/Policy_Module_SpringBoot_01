package springboot.policy_module.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import springboot.policy_module.models.PolicyRisk;
import springboot.policy_module.repositories.PolicyRiskRepository;

import java.util.List;

@Service
public class PolicyRiskServiceImpl implements PolicyRiskService {

    @Autowired
    private PolicyRiskRepository policyRiskRepository;

    @Override
    public void savePolicyRiskMapping(PolicyRisk policyRisk) {
        policyRisk.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        policyRiskRepository.save(policyRisk);
    }

    @Override
    public List<PolicyRisk> getAllPolicyRiskByPolicyId(Long policyId) {
        return this.policyRiskRepository.findAllByPolicyId(policyId);
    }

    @Override
    public void deletePolicyRiskMappingById(Long policyId, Long riskId) {
        this.policyRiskRepository.deleteByPolicyIdAndRiskId(policyId, riskId);
    }

    @Override
    public void deletePolicyRiskMappingByRiskId(Long riskId) {
        this.policyRiskRepository.deleteAllByRiskId(riskId);
    }

    @Override
    public void deletePolicyRiskMappingByPolicyId(Long policyId) {
        this.policyRiskRepository.deleteAllByPolicyId(policyId);
    }


    @Override
    public Page<PolicyRisk> findPaginatedMapping(Integer pageNo, Integer pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.policyRiskRepository.findAllByUsername(SecurityContextHolder.getContext().getAuthentication().getName(), pageable);
    }
}
