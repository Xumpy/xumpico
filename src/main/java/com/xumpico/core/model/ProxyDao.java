/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpico.core.model;

import com.xumpico.compiler.Compile;
import com.xumpico.compiler.CreateDaoPojo;
import com.xumpico.core.domain.Domain;
import com.xumpico.exceptions.XumpicoNotStartedException;
import java.beans.Introspector;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

/**
 *
 * @author nicom
 */
public class ProxyDao implements InvocationHandler{

    public static SessionFactory sessionFactory;
    CreateDaoPojo tableObject;
    
    public Object invoke(Object proxy, Method method, Object[] args) throws XumpicoNotStartedException, Throwable {
        try{
            Class<?> interfaceType = null;
            if (args != null){
                Class<?> interfaceTypes[] = args[0].getClass().getInterfaces();
        
                for(Class<?> interfaceTypeSplit: interfaceTypes){
                    if (Domain.class.isAssignableFrom(interfaceTypeSplit)){
                        interfaceType = interfaceTypeSplit;
                    }
                }
            }
        
            if (method.getName().equals("select")){
                Integer pkId = (Integer) args[0];

                Query query = sessionFactory.getCurrentSession().createQuery("from " + tableObject.getClassName() + " where pk_id = :pk_id");
                query.setInteger("pk_id", pkId);
            
                return query.list().get(0);
            }
        
            if (method.getName().equals("selectAll")){
                return sessionFactory.getCurrentSession().createQuery("from " + tableObject.getClassName()).list();
            }

            if (method.getName().equals("selectColumn")){
                Query query = sessionFactory.getCurrentSession().createQuery("from " + tableObject.getClassName() + " where " + args[0] + " = :object");
                
                query.setParameter("object", args[1]);
                
                return query.list();
            }
            
            if (method.getName().equals("save")){
                Domain newDomainClass = (Domain) Compile.string(proxy.getClass(), tableObject.getDaoPojo(), tableObject.getClassName()).getDeclaredConstructor(interfaceType).newInstance(args[0]);
                return sessionFactory.getCurrentSession().merge(newDomainClass);
            }
        
            if (method.getName().equals("delete")){
                Domain newDomainClass = (Domain) Compile.string(proxy.getClass(), tableObject.getDaoPojo(), tableObject.getClassName()).getDeclaredConstructor(interfaceType).newInstance(args[0]);
                sessionFactory.getCurrentSession().delete(newDomainClass);
                return null;
            }
        } catch(NullPointerException ex){
            try{
                throw new XumpicoNotStartedException(ex.getMessage());
            } catch(XumpicoNotStartedException exc){
                exc.printStackTrace();
            }
        }
        
        return null;
    }
    
    public ProxyDao(CreateDaoPojo tableObject){
        this.tableObject = tableObject;
    }
}
