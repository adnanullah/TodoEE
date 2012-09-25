package net.designspace.todoEE.processors;

import java.util.Map;
import java.util.HashMap;
import java.io.Console;

import net.designspace.todoEE.items.*;
import net.designspace.todoEE.items.interfaces.*;
import net.designspace.todoEE.processors.interfaces.*;
import net.designspace.todoEE.items.enums.*;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class CommandLineProcessor implements CommandProcessor {
	
	private static Leaf currentScope = null;
	private static Map<String, String> cmdAlias;
	private static enum Command{help, exit, clear, list, select, add, remove, edit, save, reload};
	protected static enum EditCommand{description, title, duedate, favourite, note, priority, status};
	private Command choice = null;
	
	private static final String promptEnd = "> ";
	private static final String separator = "\\";
	private static final String promptURI = "TODO:";
	private static final String todoEEHeader = "\nTodoEE - Command line task manager:\n----------------------------------------------------------\n";
	private static final String defaultPrompt = promptURI+promptEnd;
	private static String prompt = defaultPrompt;
	
	public CommandLineProcessor(){
		this.setAliases();
	}
	
	protected boolean execute(Node source, String input, String[] args){
		choice = null;

		if(getAliases().containsKey(args[0].toLowerCase())) {
			try{
				choice = Command.valueOf(getAliases().get(args[0].toLowerCase()));
			}catch(Exception ex){
				choice = null;
			}
		}
		
		if (choice == null) return true;
		
		switch (choice) {
			case exit:
				return false;
			case clear:
				System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"+todoEEHeader);
				return true;
			case help:
				System.out.println(displayHelp());
				return true;
			case list:
				// LIST contents of current object, if object specified display additional information
				if(args.length < 2){
					System.out.println(source.getItemsForDisplay());
				}else {
					try{
						int item = Integer.parseInt(args[1]);
						System.out.println(source.getItem(item).toString());
					}catch (Exception ex){
						try {
							System.out.println(source.getItem(input).toString());
						}catch(Exception exex){
							System.out.println("INFO: Could not list contents of the specified item");
						}
					}
				}
				break;
			case remove:
					try{
						int item = Integer.parseInt(args[1]);
						source.removeItem(item);
					}catch (Exception ex){
						try {
							source.removeItem(input);
						}catch(Exception exex){
							System.out.println("INFO: Could not remove the specified item");
						}
					}
				break;
			case edit:
				//Check argument length
				if(args.length < 4) {
					System.out.println("WARN: Insufficient parameters supplied");
					break;
				}
            
				//Attempt to get handle via index ONLY.
				Leaf obj = null;
				try{
					int item = Integer.parseInt(args[1]);
					obj = source.getItem(item);
				}catch (Exception ex){
					System.out.println("WARN: Invalid index");
					break;
				}
            
				if(obj == null) break;
            
				EditCommand editChoice = null;
				if(getAliases().containsKey(args[2].toLowerCase())) {
					editChoice = EditCommand.valueOf(getAliases().get(args[2].toLowerCase()));
				}
            
				if(editChoice != null){				
					String cmdVal = input.substring((args[1]+args[2]).length()+2).trim();
					switch (editChoice){
						case title:
							obj.setTitle(cmdVal);
							break;
						case description:
							obj.setDescription(cmdVal);
							break;
						case note:
							obj.setNotes(cmdVal);
							break;
						case favourite:
							if("true".equalsIgnoreCase(cmdVal) || "false".toString().equalsIgnoreCase(cmdVal)){
								obj.setFavourite(Boolean.valueOf(cmdVal));
							}else{
								System.out.println("WARN: Only boolean values accepted for this command");
							}
							break;
						case status:
							try{
								StatusType stat = StatusType.valueOf(cmdVal.toLowerCase());
								obj.setStatus(stat);
							}catch(Exception exex){
								System.out.println("WARN: Unrecognised status");
							}
							break;
						case priority:
							try{
								PriorityType pri = PriorityType.valueOf(cmdVal.toLowerCase());
								obj.setPriority(pri);
							}catch(Exception exex){
								System.out.println("WARN: Unrecognised priority");
							}
							break;
						case duedate:
							try{
								if("false".equalsIgnoreCase(cmdVal)){
									obj.setDueDate(null);
								}else{
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				 					obj.setDueDate(sdf.parse(cmdVal));
				 				}
							}catch(Exception exex){
								System.out.println("INFO: Please enter a valid date in the form YYYY-MM-DD HH:MM");
							}
							break;
						default:
							System.out.println("WARN: Did not recognise command: " + editChoice.toString());
							break;
					}
				}
				break;
			default:
				break;
		}
		return true;
	}
	
	public static class ProjectManagerProcessor extends CommandLineProcessor implements Processable<ProjectManager>{
			private static enum Command{help, exit, clear, list, select, add, remove, edit, save, reload};
			
			public boolean execute(ProjectManager source, String input, String[] args){ 
					boolean returnValue = true;
					
					returnValue = super.execute(source, input, args);
					if(super.choice == null) return true;
					
					switch (super.choice) {
						case select:
							// SELECT the specified container
							try{
								int item = Integer.parseInt(args[1]);
								currentScope = source.getItem(item);
							}catch (Exception ex){
								currentScope = source.getItem(input);
								if (currentScope == null){
									if(!("..".equals(args[1]) || "/".equals(args[1]))){
										System.out.println("INFO: Project not found");
									}
								}
							}finally{
								prompt = promptURI+getPrompt(currentScope)+promptEnd;
							}
							break;
						case add:
							source.addItem(new Project(source.getSize(), input, "No description", true));
							break;
						case save:
							source.save();
							// source.read();
							break;
						case reload:
							source.read();
							break;
						default:
							break;

					}

					return returnValue;
			}
	}
	
	public static class ProjectProcessor extends CommandLineProcessor implements Processable<Project>{
			private static enum Command{help, exit, clear, list, select, add, remove, edit};
				
			public boolean execute(Project source, String input, String[] args){ 
				boolean returnValue = true;
				
				returnValue = super.execute(source, input, args);
				if(super.choice == null) return true;

				switch (super.choice) {
					case select:
						// SELECT the specified container
						Project p = (Project)currentScope;
						try{
							int item = Integer.parseInt(args[1]);
							currentScope = source.getItem(item);
						}catch (Exception ex){
							currentScope = source.getItem(input);
						}finally{
							if(currentScope == null) {
								if("..".equals(args[1]) || "/".equals(args[1])){
									prompt = defaultPrompt;
								}else{
									System.out.println("INFO: Task not found");
									currentScope = p;
								}
							}else{
								//prompt = promptURI+getPrompt(source)+separator+getPrompt(currentScope)+promptEnd;
								prompt = promptURI+getPrompt(currentScope)+promptEnd;
							}
						}
						break;
					case add:	
						source.addItem(new Task(source.getSize(), source.getKey(), input, "No description", true));
						break;
					default:
						break;
				}
				return returnValue;
			}
	}
	
	public static class TaskProcessor extends CommandLineProcessor implements Processable<Task>{
			private static enum Command{help, exit, clear, list, select, add, remove, edit};
			
			public boolean execute(Task source, String input, String[] args){ 
				boolean returnValue = true;
				
				returnValue = super.execute(source, input, args);
				if(super.choice == null) return true;	

				switch (super.choice) {
					case select:
						// SELECT the specified container
						Task t = (Task)currentScope;
						if("/".equals(args[1])) {
							currentScope = null;
							prompt = defaultPrompt;
							break;
						}
						if("..".equals(args[1])) currentScope = ProjectManager.getInstance().getItem(t.getParentKey());
						prompt = promptURI+getPrompt(currentScope)+promptEnd;
						break;
					case add:	
						source.addItem(new SubTask(source.getSize(), source.getKey(), input, "No description", true));
						break;
					default:
						break;
				}
				return returnValue;
			}
	}
	
	public static class SubTaskProcessor extends CommandLineProcessor implements Processable<SubTask>{
			public boolean execute(SubTask source, String input, String[] args){ return false; }
	}
	
	public String getPrompt(Leaf source){
		if (currentScope == null) return "";
		if (source instanceof Project) return source.getTitle();
	
		return getPrompt(ProjectManager.getInstance().getItem(source.getParentKey()))+separator+source.getTitle();
	}
	
	private String displayHelp(){
		StringBuilder sb = new StringBuilder();
		sb.append("\nexit\t- Quit TodoEE application\n\t\t(aliases: quit, bye)\n");
		sb.append("\nclear\t- Clears the current console\n\t\t(aliases: cls, clean)\n");
		sb.append("\nsave\t- Saves the current contents of the task list\n");
		sb.append("\nreload\t- Abandons all changes and reads the task list from disk\n");
		sb.append("\nhelp\t- Displays this help item\n\t\t(aliases: man, ?)\n");
		sb.append("\nselect\t- Selects a TODO item\n\t\t(aliases: cd, dir)\n\n\t\tUsage: select <index|item_name>\n");
		sb.append("\nlist\t- Lists the contents of a TODO item\n\t\t(aliases: ls, show, view)\n\n\t\tUsage: list [<index|title>]\n");
		sb.append("\nadd\t- Adds a TODO item\n\t\t(aliases: mkdir)\n\n\t\tUsage: add <title>\n");
		sb.append("\nremove\t- Removes a TODO item\n\t\t(aliases: del[ete], rm)\n\n\t\tUsage: remove <index|title>\n");
		sb.append("\nedit\t- Amends a TODO item\n\t\t(aliases: amend, set)\n\n\t\t- Sub-commands: (title, descr[iption], [due][date], [fav]ourite, note, priority, status)\n\n\t\t\tUsage: edit <index> <sub-command> <value>\n");
		return sb.toString();
	}
	
	public static void processInput(){
		
		Console con = System.console();
		if (con != null){
			con.printf(todoEEHeader);
			
			boolean useTodo = true;
			while(useTodo){
				String readIn = con.readLine(prompt).trim();
				if(readIn.equals("")) continue;
				
				String readKeywords[] = readIn.split("\\s");
				String readArguments = null;
				if(readKeywords.length > 1){
					readArguments = readIn.split(readKeywords[0])[1].trim();
				}
				
				Processable proc; 
				if(currentScope == null){
					//unchecked or unsafe operations
					proc = ProjectManager.getInstance().getCommandProcessor();
					useTodo = ((ProjectManagerProcessor)proc).execute(ProjectManager.getInstance(), readArguments, readKeywords);
				}else{
					//unchecked or unsafe operations
					proc = ((Executable)currentScope).getCommandProcessor();
					if(proc instanceof ProjectProcessor) useTodo = ((ProjectProcessor)proc).execute((Project)currentScope, readArguments, readKeywords);
					if(proc instanceof TaskProcessor) useTodo = ((TaskProcessor)proc).execute((Task)currentScope, readArguments, readKeywords);
				}	
			}		
		}else{
			System.out.println("ERROR: Could not aquire console");
		}
		
		System.out.println("\n");
	}
	
	static Map<String,String> getAliases(){return cmdAlias;}
	
	private static synchronized void setAliases(){
		if (cmdAlias != null) return;
		cmdAlias = new HashMap<String, String>();
		
		for(Command c : Command.values()){
			cmdAlias.put(c.toString(),c.toString());
		}
			
		cmdAlias.put("bye",Command.exit.toString());
		cmdAlias.put("quit",Command.exit.toString());
		cmdAlias.put("amend",Command.edit.toString());
		cmdAlias.put("set",Command.edit.toString());
		cmdAlias.put("cls",Command.clear.toString());
		cmdAlias.put("clean",Command.clear.toString());
		cmdAlias.put("delete",Command.remove.toString());
		cmdAlias.put("del",Command.remove.toString());
		cmdAlias.put("rm",Command.remove.toString());
		cmdAlias.put("view",Command.list.toString());
		cmdAlias.put("show",Command.list.toString());
		cmdAlias.put("ls",Command.list.toString());
		cmdAlias.put("cd",Command.select.toString());
		cmdAlias.put("dir",Command.select.toString());
		cmdAlias.put("?",Command.help.toString());
		cmdAlias.put("man",Command.help.toString());
		cmdAlias.put("mkdir",Command.add.toString());
		
		// Re-use cmdAlias for EDIT sub-commands
		for(EditCommand c : EditCommand.values()){
			cmdAlias.put(c.toString(),c.toString());
		}
		
		cmdAlias.put("descr",EditCommand.description.toString());
		cmdAlias.put("fav",EditCommand.favourite.toString());
		cmdAlias.put("due",EditCommand.duedate.toString());
		cmdAlias.put("date",EditCommand.duedate.toString());
		
	}
}