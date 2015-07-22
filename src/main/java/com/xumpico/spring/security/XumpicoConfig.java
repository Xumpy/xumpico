/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpico.spring.security;

import com.xumpico.core.model.XumpicoDatabase;
import com.xumpico.pages.GeneratePages;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 *
 * @author nicom
 */
public abstract class XumpicoConfig extends AbstractAnnotationConfigDispatcherServletInitializer{

    @Override
    protected void registerDispatcherServlet(ServletContext servletContext) {
        super.registerDispatcherServlet(servletContext);

        try {
            GeneratePages.generate(servletContext, getPageBean());
        } catch (IOException ex) {
            Logger.getLogger(XumpicoConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        servletContext.addListener(new HttpSessionEventPublisher());

    }
    
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { XumpicoDatabase.class, getSecurityBean(), AppConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
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
    public abstract Class<?> getPageBean();
    
    public Class<?> getServletClass() {
        return Object.class;
    };
}
