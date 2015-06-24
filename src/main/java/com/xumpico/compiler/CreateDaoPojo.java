/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpico.compiler;

import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author nicom
 */
public class CreateDaoPojo {
    
    String tableName;
    String className;
    String interfaceName;
    String interfaceVariable;
    
    Map<String, Method> variables;

    public String getClassName(){
        return className;
    }
    
    public String getInterfaceName(){
        return interfaceName;
    }
    
    public Map<String, Method> getMethods(){
        return variables;
    }
    
    public String getTableName(){
        return tableName;
    }
    
    public String getDaoPojo(){
        String sourceCode = "@javax.persistence.Entity @javax.persistence.Table(name=\"" + tableName + "\") public class " + className + " implements " + interfaceName + " { \n";
        
        sourceCode = sourceCode + createVariables();
        sourceCode = sourceCode + createGetterMethods();
        sourceCode = sourceCode + createConstructor();
        
        sourceCode = sourceCode + "}";
        
        
        return sourceCode;
    }
    
    private String createGetterMethods(){
        String sourceCode = new String();
        
        for (Entry<String, Method> entry: variables.entrySet()){
            sourceCode = sourceCode + "@Override public " 
                    + entry.getValue().getReturnType().getName() 
                    + " " + entry.getValue().getName() 
                    + "(){ return this." + entry.getKey() + ";} \n";
        }
        
        return sourceCode;
    }
    
    private String createConstructor(){
        String sourceCode = new String();
        
        sourceCode = sourceCode + "public " + className + "(" + interfaceName + " " + interfaceVariable + "){";
        
        for (Entry<String, Method> entry: variables.entrySet()){
            Boolean isInterface = false;
            
            for (Annotation annotation: entry.getValue().getAnnotations()){
                if (annotation.annotationType().getName().equals("javax.persistence.JoinColumn")){
                    isInterface = true;
                }
            }
            if (!isInterface){
                sourceCode = sourceCode + "this." + entry.getKey() + " = " + interfaceVariable + "." + entry.getValue().getName() + "();";
            } else {
                sourceCode = sourceCode + "this." + entry.getKey() + " = new " + Compile.getPackage() + "." + entry.getValue().getReturnType().getSimpleName() + "DaoPojo(" + interfaceVariable + "." + entry.getValue().getName() + "());";
            }
        }
        
        sourceCode = sourceCode + "}\n";
        
        sourceCode = sourceCode + "public " + className + "(){ }";
        
        return sourceCode;
    }
    
    private String getNameAnnotation(Annotation annotation){
        String[] annotationValues = annotation.toString().substring(annotation.toString().indexOf("(") + 1, annotation.toString().length() - 1).split(",");
        
        for (String annotationValue: annotationValues){
            if (annotationValue.trim().startsWith("name")){
                return annotationValue.trim().substring(5);
            }
        }
        return null;
    }
    
    private String createVariables(){
        String sourceCode = new String();
        for (Entry<String, Method> entry: variables.entrySet()){
            Boolean isInterface = false;
        
            for (Annotation annotation: entry.getValue().getAnnotations()){
                if (annotation.annotationType().getName().equals("javax.persistence.Column")){
                    sourceCode = sourceCode + "@javax.persistence.Column(name=\"" + getNameAnnotation(annotation) + "\") ";
                }
                if (annotation.annotationType().getName().equals("javax.persistence.Id")){
                    sourceCode = sourceCode + "@javax.persistence.Id ";
                }
                if (annotation.annotationType().getName().equals("javax.persistence.GeneratedValue")){
                    sourceCode = sourceCode + "@javax.persistence.GeneratedValue(strategy=javax.persistence.GenerationType.AUTO) ";
                }
                
                if (annotation.annotationType().getName().equals("javax.persistence.JoinColumn")){
                    isInterface = true;
                    sourceCode = sourceCode + "@javax.persistence.ManyToOne ";
                    sourceCode = sourceCode + "@javax.persistence.JoinColumn(name=\"" + getNameAnnotation(annotation) + "\") ";
                }
            }
            
            if (!isInterface){
                sourceCode = sourceCode + " private " + entry.getValue().getReturnType().getName() + " " + entry.getKey() + "; \n";
            } else {
                sourceCode = sourceCode + " private " + Compile.getPackage() + "." + entry.getValue().getReturnType().getSimpleName() + "DaoPojo " + entry.getKey() + "; \n";
            }
        }
        
        return sourceCode;
    }
    
    public <T> CreateDaoPojo(Class<T> interfaceClass){
        
        String[] interfaceClasses = interfaceClass.getName().split("\\.");
        
        className = interfaceClasses[interfaceClasses.length - 1] + "DaoPojo";
        interfaceName = interfaceClass.getName();
        
        String[] interfaceVars = interfaceName.split("\\.");
        interfaceVariable = Introspector.decapitalize(interfaceVars[interfaceClasses.length - 1]);
        
        for (Annotation annotation: interfaceClass.getAnnotations()){
            if (annotation.annotationType().getName().equals("javax.persistence.Table")){
                tableName = getNameAnnotation(annotation);
            }
        }
        
        variables = new HashMap<String, Method>();
        
        for(Method method: interfaceClass.getMethods()){
            variables.put(Introspector.decapitalize(method.getName().substring(3)), method);
        }
    }
}
