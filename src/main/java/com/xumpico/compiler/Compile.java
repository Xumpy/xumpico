/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpico.compiler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 *
 * @author nicom
 */
public class Compile {
    private static String location = "/com/xumpico/compiler/compiled/";
    
    public static Class<?> string(Class<?> interfaceName, String source, String className) throws IOException, ClassNotFoundException{
        File root = new File("/java");
        File sourceFile = new File(interfaceName.getProtectionDomain().getCodeSource().getLocation().getPath() + location + className+ ".java");
        sourceFile.getParentFile().mkdirs();
        new FileWriter(sourceFile).append("package " + getPackage() + "; " + source).close();
        
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, sourceFile.getPath());
        
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { root.toURI().toURL() });
        Class<?> cls = Class.forName(getPackage() + "." + className, true, classLoader);
        
        return cls;
    }
    
    public static String getPackage(){
        return location.substring(1, location.length() - 1).replace("/", ".");
    }
}
