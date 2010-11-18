package de.felixbruns.minecraft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class SpMcServer extends Thread {
	private BufferedReader           stdout;
	private BufferedReader           stderr;
	private PrintWriter              stdin;
	private List<SpMcConsoleHandler> handlers;
	private boolean                  running;
	private int                      memory;
	
	public SpMcServer(int memory){
		this.handlers = new ArrayList<SpMcConsoleHandler>();
		this.running  = true;
		this.memory   = memory;
	}
	
	public void addHandler(SpMcConsoleHandler handler){
		this.handlers.add(handler);
	}
	
	public void command(String command){
		this.stdin.println(command);
	}
	
	private void handleMessage(String message){
		for(SpMcConsoleHandler handler : this.handlers){
			handler.handleConsole(message);
		}
	}
	
	public void terminate(){
	    this.command("stop");
	    
		this.running = false;
	}
	
    public void run(){
		try {
			/* Start the server. */
			Process process = Runtime.getRuntime().exec(
				"java -Xmx" + this.memory + "M -Xms" + this.memory + "M -jar minecraft_server.jar nogui"
			);
			
			this.stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
			this.stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			this.stdin  = new PrintWriter(
				new OutputStreamWriter(process.getOutputStream()), true
			);
			
			while(this.running){
    			while(this.stdout.ready()){
    				this.handleMessage(this.stdout.readLine());
    			}
    			
    			while(this.stderr.ready()){
    				this.handleMessage(this.stderr.readLine());
    			}
    			
    			Thread.sleep(100);
			}
			
	        process.waitFor();
        }
        catch(IOException e){
	        e.printStackTrace();
        }
        catch(InterruptedException e){
	        e.printStackTrace();	        
        }
    }
}
