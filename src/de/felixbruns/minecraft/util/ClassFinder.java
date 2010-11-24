package de.felixbruns.minecraft.util;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
			URL  resource  = classLoader.getResource(packagePath);
			File directory = new File(resource.toURI());
			
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
		catch(URISyntaxException e){
			/* Ignore. */
		}
		
		return classes;
	}
}
