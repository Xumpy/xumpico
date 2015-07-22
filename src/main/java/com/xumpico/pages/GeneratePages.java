/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpico.pages;

import com.xumpico.spring.security.controllers.ResponseBodyFromJSP;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;

/**
 *
 * @author nicom
 */
public class GeneratePages {
    static Logger log = Logger.getLogger(GeneratePages.class.getName());
    
    private static List<String> getSourceLocation(String url){
        String[] splittedUrl = url.split("/");
        int targetLocation = 0;
        
        for (int i=1; i<splittedUrl.length; i++){
            if (splittedUrl[i].equals("target")){
                targetLocation = i;
            }
        }
        
        String basePath = "";
        
        if (targetLocation != 0){
            for(int i=1;i<targetLocation;i++){
                basePath = basePath.concat("/").concat(splittedUrl[i]);
            }
        }
        
        String sourceBasePath = basePath + "/src/main/webapp";
        
        List<String> returnString = new ArrayList<String>();
        
        returnString.add(sourceBasePath);
        
        String sourcePath = sourceBasePath;
        
        for(int i=targetLocation + 2; i<splittedUrl.length; i++){
            sourcePath = sourcePath.concat("/").concat(splittedUrl[i]);
        }
        
        returnString.add(sourcePath);

        return returnString;
    }
    
    private static void createFolder(String url){
        File file = new File(url);
        file.mkdirs();
        
        List<String> sourceLocations = getSourceLocation(url);

        if (new File(sourceLocations.get(0)).isDirectory()){
            File sourceFolder = new File(sourceLocations.get(1));
            sourceFolder.mkdir();
        }
    }
    
    private static void createFile(String url, String content) throws FileNotFoundException{
        PrintWriter out = new PrintWriter(url);
        out.print(content);
        
        out.close();
        
        List<String> sourceLocations = getSourceLocation(url);
        
        if (new File(sourceLocations.get(0)).isDirectory()){
            PrintWriter out2 = new PrintWriter(sourceLocations.get(1));
            out2.print(content);
            out2.close();
        }
    }
    
    public static void createFolderFromFileURL(String url){
        String folder = url.substring(0, url.lastIndexOf("/"));
        
        if (!new File(folder).isDirectory()){
            createFolder(folder);
        }
    }
    
    public static void generate(ServletContext servletContex, Class<?> pageBeanClass) throws FileNotFoundException, IOException{
        String url = servletContex.getRealPath("resources");
        
        if( !new File(url).isDirectory()){
            log.info("Resource folder does not exists! Creating it.");
            createFolder(url);
        }
        
        url = servletContex.getRealPath("WEB-INF/jsp");
        if( !new File(url).isDirectory()){
            log.info("WEB-INF/jsp folder does not exists! Creating it.");
            createFolder(url);
        }
        
        url = servletContex.getRealPath("WEB-INF/jsp/login.jsp");
        if( !new File(url).isFile()){
            log.info("WEB-INF/jsp/login.jsp does not exists! Creating it.");
            createFile(url, new ResponseBodyFromJSP("/jsp/pages/login.jsp").getResponse());
        }
        
        log.debug("---- scanning for new pages in page class: " + pageBeanClass.getName());
        
        Field[] fields = pageBeanClass.getFields();
        
        for(Field field: fields){
            Annotation[] annotations = field.getAnnotations();
            log.debug(" ------ Found field name in page class: " + field.getName());
            for (Annotation annotation: annotations){
                log.debug(annotation.annotationType().toString());
                if (annotation.annotationType().equals(Page.class)){
                    Page page = (Page) annotation;
                    
                    url = servletContex.getRealPath("WEB-INF/jsp/" + page.pageName());
                    
                    createFolderFromFileURL(url);
                    
                    if( !new File(url).isFile()){
                        log.info(page.pageName() + " file does not exists! Creating it.");
                        createFile(url, "Hello Nico");
                    }
                }
            }
        }
    }
}
