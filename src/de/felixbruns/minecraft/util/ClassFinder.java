package de.felixbruns.minecraft.util;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassFinder {
	/**
	 * Scans a package and returns a list of classes contained in that package.
	 * 
	 * @param packageName The package to scan for classes.
	 * 
	 * @return A list of classes.
	 */
	public static List<Class<?>> getClasses(String packageName){
		ClassLoader    classLoader = Thread.currentThread().getContextClassLoader();
		String         packagePath = packageName.replace('.', '/');
		List<Class<?>> classes     = new ArrayList<Class<?>>();
		
		try {
			URI resource = classLoader.getResource(packagePath).toURI();
			
			if(resource.getScheme().equals("jar")){
				resource = new URI(resource.getRawSchemeSpecificPart());
				
				String  jarPath = resource.getPath().replace("!/" + packagePath, "");
    			File    jarFile = new File(jarPath);
				JarFile jar     = new JarFile(jarFile);
				
				Enumeration<JarEntry> entries = jar.entries();
				
				while(entries.hasMoreElements()){
					JarEntry entry     = entries.nextElement();
					int      sepatator = entry.getName().lastIndexOf("/");
					
					if(sepatator == -1){
						continue;
					}
					
					String path = entry.getName().substring(0, sepatator);
					String name = entry.getName().substring(sepatator + 1);
					
					if(!entry.isDirectory() && path.equals(packagePath) && name.endsWith(".class")){
    					String className = name.substring(0, name.length() - 6);
    					
    					try {
    						classes.add(
    							Class.forName(packageName + "." + className)
    						);
    					}
    					catch(ClassNotFoundException e){
    						/* Ignore. */
    					}
					}
				}
			}
			else if(resource.getScheme().equals("file")){
    			File directory = new File(resource);
    			
    			if(!directory.exists()){
    				return classes;
    			}
    			
    			File[] files = directory.listFiles();
    			
    			for(File file : files){
    				if(file.getName().endsWith(".class")){
    					String fileName  = file.getName();
    					String className = fileName.substring(0, fileName.length() - 6);
    					
    					try {
    						classes.add(
    							Class.forName(packageName + "." + className)
    						);
    					}
    					catch(ClassNotFoundException e){
    						/* Ignore. */
    					}
    				}
    			}
			}
			else{
				throw new RuntimeException(
					"Can't find classes from resource: '" + resource + "'! " +
					"Only 'jar' and 'file' resources are supported!"
				);
			}
		}
		catch(URISyntaxException e){
			/* Ignore. */
		}
		catch(Exception e) {
			e.printStackTrace();
			/* Ignore. */
		}
		
		return classes;
	}
}
