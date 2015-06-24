/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpico.spring.security.controllers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author nicom
 */
public class ResponseBodyFromJSP {
    private String response;
    
    public ResponseBodyFromJSP(String resourcePath) throws FileNotFoundException, IOException{
        InputStream inputstream = getClass().getResourceAsStream(resourcePath);
        
        BufferedReader br = new BufferedReader(new InputStreamReader(inputstream));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            response = sb.toString();
        } finally {
            br.close();
        }
    }
    
    public String getResponse(){
        return response;
    }
}
