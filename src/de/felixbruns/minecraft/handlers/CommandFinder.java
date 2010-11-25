package de.felixbruns.minecraft.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.felixbruns.minecraft.handlers.commands.annotations.CommandProvider;
import de.felixbruns.minecraft.util.ClassFinder;

public class CommandFinder {
	private static final List<String>                          commands;
	private static final List<Class<? extends CommandHandler>> commandHandlerClasses;
	private static final List<CommandHandler>                  commandHandlers;
	
	static {
		List<Class<?>> classes = ClassFinder.getClasses("de.felixbruns.minecraft.handlers.commands");
		
		commands              = new ArrayList<String>();
		commandHandlerClasses = new ArrayList<Class<? extends CommandHandler>>();
		commandHandlers       = new ArrayList<CommandHandler>();
		
		commands.add("home");
		
		for(Class<?> clazz : classes){
			CommandProvider commandProvider = clazz.getAnnotation(CommandProvider.class);
			
			if(commandProvider != null){
				Class<? extends CommandHandler> commandClass = clazz.asSubclass(CommandHandler.class);
				
				commands.addAll(Arrays.asList(commandProvider.commands()));
				commandHandlerClasses.add(commandClass);
				
				try {
	                commandHandlers.add(commandClass.newInstance());
                }
				catch(InstantiationException e){
					throw new RuntimeException(e);
				}
				catch(IllegalAccessException e){
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	public static List<String> getCommands(){
		return commands;
	}
	
	public static boolean isCommandAvailable(String command){
		return commands.contains(command);
	}
	
	public static List<Class<? extends CommandHandler>> getCommandHandlerClasses(){
		return commandHandlerClasses;
	}
	
	public static List<CommandHandler> getCommandHandlers(){
		return commandHandlers;
	}
}
