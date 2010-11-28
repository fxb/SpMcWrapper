package de.felixbruns.minecraft.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.felixbruns.minecraft.handlers.commands.annotations.CommandProvider;
import de.felixbruns.minecraft.util.ClassFinder;

/**
 * Finds all command handlers in the package 'de.felixbruns.minecraft.handlers.commands'
 * and provides static methods to retrieve them or check if a command is available. 
 * 
 * @author Felix Bruns <felixbruns@web.de>
 *
 */
public class CommandFinder {
	private static final List<String>                          commands;
	private static final List<Class<? extends CommandHandler>> commandHandlerClasses;
	private static final List<CommandHandler>                  commandHandlers;
	
	static {
		List<Class<?>> classes = ClassFinder.getClasses("de.felixbruns.minecraft.handlers.commands");
		
		commands              = new ArrayList<String>();
		commandHandlerClasses = new ArrayList<Class<? extends CommandHandler>>();
		commandHandlers       = new ArrayList<CommandHandler>();
		
		/* 
		 * Add command 'home', even if it is not provided by any of the command
		 * handlers, since it will be handled by the minecraft server.
		 */
		commands.add("home");
		
		/* Loop over all found classes and check the annotaion for provided commands. */
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
	
	/**
	 * Get a list of commands that are available.
	 * 
	 * @return A list of commands.
	 */
	public static List<String> getCommands(){
		return commands;
	}
	
	/**
	 * Check if a given command is available.
	 * 
	 * @param command The command to check.
	 * 
	 * @return true if it is available, false otherwise.
	 */
	public static boolean isCommandAvailable(String command){
		return commands.contains(command);
	}
	
	/**
	 * Get a list of available {@link CommandHandler} classes.
	 * 
	 * @return A list of command handler classes.
	 */
	public static List<Class<? extends CommandHandler>> getCommandHandlerClasses(){
		return commandHandlerClasses;
	}
	
	/**
	 * Get a list of available {@link CommandHandler} instances.
	 * 
	 * @return A list of command handlers.
	 */
	public static List<CommandHandler> getCommandHandlers(){
		return commandHandlers;
	}
}
