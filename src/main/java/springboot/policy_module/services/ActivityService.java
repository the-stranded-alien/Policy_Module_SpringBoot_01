package springboot.policy_module.services;

import org.springframework.data.domain.Page;
import springboot.policy_module.models.Activity;

import java.util.List;

public interface ActivityService {
    void saveActivity(Activity activity);
    List<Activity> getAllActivity();
    Page<Activity> findPaginated(String username, Integer pageNo, Integer pageSize, String sortField, String sortDir);
}
