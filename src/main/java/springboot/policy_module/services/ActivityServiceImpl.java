package springboot.policy_module.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import springboot.policy_module.models.Activity;
import springboot.policy_module.repositories.ActivityRepository;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public void saveActivity(Activity activity) {
        activityRepository.save(activity);
    }

    @Override
    public List<Activity> getAllActivity() {
        return this.activityRepository.findAll();
    }

    @Override
    public Page<Activity> findPaginated(String username, Integer pageNo, Integer pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.activityRepository.findAllByUsername(username, pageable);
    }

}
