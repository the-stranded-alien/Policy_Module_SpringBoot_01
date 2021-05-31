package springboot.policy_module.services;

import org.springframework.data.domain.Page;
import springboot.policy_module.models.Risk;

import java.util.List;

public interface RiskService {
    
    List<Risk> getAllRisks();
    void saveRisk(Risk risk);
    Risk getRiskById(Long id);
    void deleteRiskById(Long id);
    Page<Risk> findPaginated(Integer pageNo, Integer pageSize, String sortField, String sortDirection);
}
