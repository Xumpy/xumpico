/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpico.core.model;

import java.util.List;

/**
 *
 * @author nicom
 */
public interface Dao {
    public Object select(Integer pkId);
    public List<? extends Object> selectAll();
    public List<? extends Object> selectColumn(String name, Object object);
    
    public Object save(Object object);
    public void delete(Object object);
}
