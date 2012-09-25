package net.designspace.todoEE;

import net.designspace.todoEE.items.*;
import net.designspace.todoEE.processors.CommandLineProcessor;
import net.designspace.todoEE.processors.CommandLineProcessor.*;

public class TodoEE {
	public TodoEE(){
		ProjectManager.getInstance();
		
		ProjectManager.setCommandProcessor(new ProjectManagerProcessor());
		Project.setCommandProcessor(new ProjectProcessor());
		Task.setCommandProcessor(new TaskProcessor());
		SubTask.setCommandProcessor(new SubTaskProcessor());
		
		CommandLineProcessor.processInput();
		
	}
	
	public static void main(String[] args){
		new TodoEE();
	}

}