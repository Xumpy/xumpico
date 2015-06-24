/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpico.spring.security;

import com.xumpico.core.model.XumpicoDatabase;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
/**
 *
 * @author nicom
 */
@Configuration
@EnableWebSecurity
public class AppConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired XumpicoDatabase getDatabase;
    @Autowired UserAdmin userInfo;
    @Autowired LDAPInfo ldapInfo;
    
    @Override 
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/**").hasAuthority("USER")
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/login")
                .loginProcessingUrl("/j_spring_security_check")
                .defaultSuccessUrl("/index", true)
                .failureUrl("/login?error")
                .permitAll()
            .and()
                .logout()
                    .logoutUrl("/j_spring_security_logout")
                    .logoutSuccessUrl("/login");	
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        if (ldapInfo != null){
            ActiveDirectoryLdapAuthenticationProvider ldap = new ActiveDirectoryLdapAuthenticationProvider(ldapInfo.getDomain(), ldapInfo.getUrl());
            ldap.setAuthoritiesMapper(new GrantedAuthoritiesMapper() {
                public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> clctn) {
                    Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
                    
                    for(GrantedAuthority authentication : clctn){
                        for (String authority: ldapInfo.getAuthorities()){
                            if (authentication.getAuthority().equals(authority)){
                                roles.add(new GrantedAuthority() {

                                    @Override
                                    public String getAuthority() {
                                        return "USER";
                                    }
                                });
                            }
                        }
                    }
                    
                    return roles;
                }
            });
            auth.authenticationProvider(ldap);
        } else {
            auth.inMemoryAuthentication().withUser(userInfo.getUsername()).password(userInfo.getPassword()).authorities("USER");
        }
    }
}
