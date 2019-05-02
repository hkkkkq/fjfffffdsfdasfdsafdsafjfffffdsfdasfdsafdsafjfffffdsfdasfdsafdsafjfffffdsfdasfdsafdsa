package com.kt.kkos.common.util; 

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class ApplicationContextHolder implements ApplicationContextAware { 
    private static ApplicationContext appCtx;

    public ApplicationContextHolder() {
    }

    /** Spring supplied interface method for injecting app context. */
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        appCtx = applicationContext;
    }

    /** Access to spring wired beans. */
    public static ApplicationContext getContext() {
        return appCtx;
    }
}

