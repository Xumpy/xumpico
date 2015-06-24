/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpico.core;

import com.xumpico.core.model.XumpicoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.web.WebApplicationInitializer;

/**
 *
 * @author nicom
 */
public interface XumpicoSpringConfig extends WebApplicationInitializer{
    @Bean
    public XumpicoDatabase database();
}
