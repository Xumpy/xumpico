/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpico.spring.security.controllers;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author nicom
 */
@Controller
public class Pages {
    @RequestMapping(value = "login")
    public @ResponseBody String login(){
        try {
            ResponseBodyFromJSP loginJSP = new ResponseBodyFromJSP("/jsp/pages/login.jsp"); 
            return loginJSP.getResponse();
        } catch (IOException ex) {
            Logger.getLogger(Pages.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "Error on loading xumpico login page";
    }
    
    @RequestMapping(value = "resources/angular.min.js")
    public @ResponseBody String angularSpinnerMinJS(){
        try {
            ResponseBodyFromJSP loginJSP = new ResponseBodyFromJSP("/jsp/resources/angular.min.js"); 
            return loginJSP.getResponse();
        } catch (IOException ex) {
            Logger.getLogger(Pages.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "Error on loading xumpico login page";
    }
    
    @RequestMapping(value = "resources/bootstrap/css/bootstrap.min.css")
    public @ResponseBody String bootStrapMinCSS(){
        try {
            ResponseBodyFromJSP loginJSP = new ResponseBodyFromJSP("/jsp/resources/bootstrap/css/bootstrap.min.css"); 
            return loginJSP.getResponse();
        } catch (IOException ex) {
            Logger.getLogger(Pages.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "Error on loading xumpico login page";
    }
    
    @RequestMapping(value = "resources/bootstrap/css/datepicker3.css")
    public @ResponseBody String datePicker(){
        try {
            ResponseBodyFromJSP loginJSP = new ResponseBodyFromJSP("/jsp/resources/bootstrap/css/datepicker3.css"); 
            return loginJSP.getResponse();
        } catch (IOException ex) {
            Logger.getLogger(Pages.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "Error on loading xumpico login page";
    }
    
    @RequestMapping(value = "resources/bootstrap/js/bootstrap.min.js")
    public @ResponseBody String bootstrapMinJS(){
        try {
            ResponseBodyFromJSP loginJSP = new ResponseBodyFromJSP("/jsp/resources/bootstrap/js/bootstrap.min.js"); 
            return loginJSP.getResponse();
        } catch (IOException ex) {
            Logger.getLogger(Pages.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "Error on loading xumpico login page";
    }
    
    @RequestMapping(value = "resources/bootstrap/js/bootstrap-datepicker.js")
    public @ResponseBody String datePickerJS(){
        try {
            ResponseBodyFromJSP loginJSP = new ResponseBodyFromJSP("/jsp/resources/bootstrap/js/bootstrap-datepicker.js"); 
            return loginJSP.getResponse();
        } catch (IOException ex) {
            Logger.getLogger(Pages.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "Error on loading xumpico login page";
    }
    
    @RequestMapping(value = "dikke_test")
    public @ResponseBody String dikkeTest(){
        try {
            ResponseBodyFromJSP loginJSP = new ResponseBodyFromJSP("/xumpico.properties"); 
            return loginJSP.getResponse();
        } catch (IOException ex) {
            Logger.getLogger(Pages.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "Error on loading xumpico login page";
    }
    
    @RequestMapping(value = "index")
    public String indexPage(){
        return "index";
    }
}
