package com.heyx.hook.dao.log;

import com.heyx.hook.dao.BaseRepo;
import com.heyx.hook.entity.Task;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends BaseRepo<Task, String> {
}
