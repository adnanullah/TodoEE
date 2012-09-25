package net.designspace.todoEE.items;

import net.designspace.todoEE.items.templates.Container;
import net.designspace.todoEE.items.enums.*;
import net.designspace.todoEE.items.interfaces.Leaf;
import net.designspace.todoEE.processors.interfaces.Processable;
import net.designspace.todoEE.processors.interfaces.Executable;

import java.util.Date;
import java.text.DateFormat;
import java.util.List;
import java.util.ArrayList;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ProjectManager extends Container<Project> implements Executable {
	
	private static ProjectManager pm;
	private static Processable proc;
	
	private static final String fileLocation = "todoItems.txt";
	private static final int permProjectCount = 4;
	
	private ProjectManager(){
			this.leafList = read();
			this.title = "Current Projects";
			
			/* Adding basic projects to get the ball rolling 
			this.addItem(new Project(this.getSize(), "Inbox", "Miscellaneous things to do...", false));
			this.addItem(new Project(this.getSize(), "Today", "Items that MUST be done today!", false));
			this.addItem(new Project(this.getSize(), "Next", "Items that need attention soon", false));
			this.addItem(new Project(this.getSize(), "Scheduled", "Items that have been planned", false));

			Project p = this.getItem(0);
			p.setPriority(PriorityType.medium);
			p.setStatus(StatusType.unscheduled);
			p.setFavourite(true);

			p = this.getItem(1);
			p.setPriority(PriorityType.urgent);
			p.setStatus(StatusType.started);
			p.setDueDate(new Date());
			p.setFavourite(true);

			p = this.getItem(2);
			p.setPriority(PriorityType.high);
			p.setStatus(StatusType.queued);
			p.setFavourite(true);

			p = this.getItem(3);
			p.setPriority(PriorityType.medium);
			p.setStatus(StatusType.scheduled);
			p.setFavourite(true);*/
			
	}

	public static ProjectManager getInstance(){
		 if(ProjectManager.pm == null) ProjectManager.pm = new ProjectManager();
		 return ProjectManager.pm; 
	}
	
	public String getItemsForDisplay(){
		String header = "\n[ "+this.title+" ]\n------------------------------------";
		StringBuffer sb = new StringBuffer();
		sb.append(header);
		int count = 0;
		
		for(Project t : leafList){
			sb.append("\n["+count+"] "+t.getTitle());
			count++;
			if (count == permProjectCount) sb.append("\n------------------------------------");
		}
		
		if (sb.toString().equals(header)) return header+"\n[-] No items currently available\n";
		return sb.append("\n").toString();
	}
	
	public void save(){saveFile(this.fileLocation, this.leafList);}
	public List<Project> read(){return readFile(this.fileLocation);}

	private void saveFile(String fileLocation, List<Project> todo){
		StringBuffer sb = new StringBuffer();
		for(Project p: getItems()){sb.append(p.save()+"\n");}
		
		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(new File(fileLocation)));
			out.write(sb.toString(),0,sb.toString().length());
			out.close();
		}catch(IOException io){		
			System.out.println("ERROR: Failed to save contents of file");
		}

	}
	private List<Project> readFile(String fileLocation){
		List<Project> lp = new ArrayList<Project>();
		
		try{
			BufferedReader in = new BufferedReader(new FileReader(new File(fileLocation)));
			Project p = null; Task t = null; SubTask st = null;
			while(in.ready()){
				String s=in.readLine();
				if (s == null) break;
				char indicator = s.charAt(0);
				String readValues[] = s.substring(1).split("~~");
				
				switch(indicator){
					case '!':
						// Add a completed project
						if(p != null){
							if (t != null) p.addItem(t);
							lp.add(p);
							p = null;
							t = null;
						}
						if(readValues.length > 0) p = (Project) setItemValues(new Project(lp.size(), readValues[0], readValues[1], Boolean.parseBoolean(readValues[7])), readValues);
						break;
					case '#':
						//Add a completed task
						if(t != null){
							if(st != null) t.addItem(st);
							p.addItem(t);
							t = null;
							st = null;
						}
						if(readValues.length > 0) t = (Task) setItemValues(new Task(p.getSize(), p.getKey(), readValues[0], readValues[1], Boolean.parseBoolean(readValues[7])), readValues);
						break;
					case '$':
						//Add a completed sub-task
						if(st != null){
							t.addItem(st);
							st = null;
						}
						if(readValues.length > 0) st = (SubTask) setItemValues(new SubTask(t.getSize(), t.getKey(), readValues[0], readValues[1], Boolean.parseBoolean(readValues[7])), readValues);
						break;
					default:
						break;
				}
			}
			if (st != null) t.addItem(st);
			if (t != null) p.addItem(t);
			if (p != null) lp.add(p);
			in.close();
		}catch(IOException io){		
			System.out.println("ERROR: Failed to read contents of file");
		}
		
		return lp;
	}
	
	private Leaf setItemValues(Leaf l, String[] values){
		l.setNotes(values[2]);
		l.setStatus(StatusType.valueOf(values[3].toLowerCase()));
		l.setPriority(PriorityType.valueOf(values[4].toLowerCase()));
		try{
			l.setDueDate(DateFormat.getInstance().parse(values[5]));
		}catch(Exception ex){
			l.setDueDate(null);
		}
		l.setFavourite(Boolean.parseBoolean(values[6]));
		l.setRepeatPeriod(Integer.parseInt(values[8]));
		
		return l;
	}
	
 	public static void setCommandProcessor(Processable p) { proc = p; }
	public Processable getCommandProcessor() { return proc; }
}