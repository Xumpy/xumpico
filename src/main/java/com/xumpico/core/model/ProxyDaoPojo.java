/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpico.core.model;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.hibernate.SessionFactory;

/**
 *
 * @author nicom
 */
public class ProxyDaoPojo implements InvocationHandler{

    private SessionFactory sessionFactory;
    
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("getNaam")){
            return "Martens";
        }
        
        if (method.getName().equals("getVoornaam")){
            return "Nico";
        }
        
        return null;
    }
    
    public ProxyDaoPojo(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    
}
