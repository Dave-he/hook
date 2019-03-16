package com.heyx.hook.dao.log;

import com.heyx.hook.dao.BaseRepo;
import com.heyx.hook.entity.Action;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepo extends BaseRepo<Action,String> {
}
