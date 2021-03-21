package com.catfish.ums.mapstruct;

import org.mapstruct.TargetType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author chenyj
 * @version 1.0
 * @date 2021/1/10$ - 23:52$
 */
@Component
public class EntityObjFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) {
        this.applicationContext = context;
    }

    public <T> T createEntity(@TargetType Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
