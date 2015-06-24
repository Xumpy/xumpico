/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpico.spring.security;

import com.xumpico.core.model.XumpicoDatabase;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 *
 * @author nicom
 */
public abstract class XumpicoConfig extends AbstractAnnotationConfigDispatcherServletInitializer{

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { XumpicoDatabase.class, getSecurityBean(), AppConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        System.out.println("Init getServletConfigClasses");
        if (getServletClass() != null){
            return new Class[] { DispatcherConfig.class, getServletClass() };
        } else {
            return new Class[] { DispatcherConfig.class };
        }
    }

    @Override
    protected String[] getServletMappings() {
        System.out.println("Init getServletMappings");
        return new String[] { "/" };
    }
    
    public abstract Class<? extends SecurityBeans> getSecurityBean();
    
    public Class<?> getServletClass() {
        return Object.class;
    };
}
