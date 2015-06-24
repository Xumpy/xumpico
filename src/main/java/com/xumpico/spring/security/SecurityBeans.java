/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpico.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author nicom
 */
@Configuration
public abstract class SecurityBeans {
    @Bean
    public abstract UserAdmin userAdmin();
    
    @Bean
    public abstract LDAPInfo ldapInfo();
}
