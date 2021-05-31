package springboot.policy_module.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import springboot.policy_module.models.Risk;
import springboot.policy_module.models.User;
import springboot.policy_module.repositories.RiskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RiskServiceImpl implements RiskService {

    @Autowired
    private RiskRepository riskRepository;

    @Override
    public List<Risk> getAllRisks() {
        return riskRepository.findAll();
    }

    @Override
    public void saveRisk(Risk risk) {
        risk.setUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        this.riskRepository.save(risk);
    }

    @Override
    public Risk getRiskById(Long id) {
        Optional<Risk> optional = riskRepository.findById(id);
        Risk risk = null;
        if(optional.isPresent()) {
            risk = optional.get();
        } else {
            throw new RuntimeException("Risk With Id : " + id + " Not Found !!");
        }
        return risk;
    }

    @Override
    public void deleteRiskById(Long id) {
        this.riskRepository.deleteById(id);
    }

    @Override
    public Page<Risk> findPaginated(Integer pageNo, Integer pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.riskRepository.findAllByUsername(SecurityContextHolder.getContext().getAuthentication().getName(), pageable);
    }
}
