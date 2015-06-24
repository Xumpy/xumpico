/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpico.core;

import com.xumpico.compiler.Compile;
import com.xumpico.compiler.CreateDaoPojo;
import com.xumpico.core.model.Dao;
import com.xumpico.core.model.ProxyDao;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author nicom
 */
public class Xumpico {
    
    public static SessionFactory sessionFactory;
    public static Transaction transaction;
    private static ServiceRegistry serviceRegistry;
    private static final Configuration config = new Configuration();
    
    public static <T> Dao Dao(Class<T> interfaceName){
        try {
            CreateDaoPojo daoPojo = new CreateDaoPojo(interfaceName);

            config.addAnnotatedClass(Compile.string(interfaceName, daoPojo.getDaoPojo(), daoPojo.getClassName()));
            
            InvocationHandler proxyDao = new ProxyDao(daoPojo);
            
            Object dao = Proxy.newProxyInstance(Xumpico.class.getClassLoader(), new Class[]{Dao.class}, proxyDao);
            
            return (Dao)dao;
        } catch (Exception ex) {
            Logger.getLogger(Xumpico.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static void start(){
        config.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        config.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        config.setProperty("hibernate.connection.url", "jdbc:h2:mem:test'");
        config.setProperty("hibernate.current_session_context_class", "thread");   
        config.setProperty("hibernate.hbm2ddl.auto", "create");
        
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        sessionFactory = config.buildSessionFactory(serviceRegistry);
        
        transaction = sessionFactory.getCurrentSession().beginTransaction();
        ProxyDao.sessionFactory = sessionFactory;
    }
    
    public static void start(Properties properties){
        config.setProperties(properties);
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        sessionFactory = config.buildSessionFactory(serviceRegistry);
        
        transaction = sessionFactory.getCurrentSession().beginTransaction();
        ProxyDao.sessionFactory = sessionFactory;
    }
    
    public static void stop(){
        transaction.commit();
        sessionFactory.close();
    }
}
