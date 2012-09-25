package net.designspace.todoEE.items;

import net.designspace.todoEE.items.templates.Container;
import net.designspace.todoEE.items.enums.*;
import net.designspace.todoEE.processors.interfaces.Processable;
import net.designspace.todoEE.processors.interfaces.Executable;

public class Task extends Container<SubTask> implements Executable {
	
	private static Processable proc;
	
	public Task(){}
	
	public Task(int key, int parentKey, String title, String description, boolean removable){		
		this.key = key;
		this.parentKey = parentKey;
		this.title = title;
		this.description = description;
		this.removable = removable;
		this.priority = PriorityType.none;
		this.status = StatusType.none;
		this.dueDate = null;
		this.favourite = false;
		this.notes = "";
		this.useTime = false;
		this.repeatPeriodInDays = 0;
	}
	
	public String save(){
		StringBuffer sb = new StringBuffer();
		sb.append("#"+getTitle()+"~~"+getDescription()+"~~"+getNotes()+"~~"+getStatus()+"~~"+getPriority()+"~~"+getDueDate()+"~~"+isFavourite()+"~~"+isRemovable()+"~~"+getRepeatPeriod());
		
		for(SubTask l:getItems()){
			sb.append("\n"+l.save());
		}
		return sb.toString();
	}
	
	public String toString(){
			String prjView = "\nTask: "+this.getTitle()+" ["+this.getKey()+"]"+"\nDescription: "+this.getDescription()+"\nNo. of SubTasks: "+this.getSize()+"\n---------------------------------------------------------";
			prjView += "\nStatus: "+this.getStatus()+"\nPriority: "+this.getPriority();
			if(this.getDueDate()==null) {
				prjView += "\nDue Date: None";
			} else {
				prjView += "\nDue Date: "+this.getDueDate().toString(); 
			}
			prjView += "\nFavourite: "+this.isFavourite()+"\nRemovable: "+this.isRemovable()+"\n";		
			return prjView;
	}
		
	/* Simple equality check based on title (for now) */
	public boolean equals(Object obj){return ((obj instanceof Task) && (((Task)obj).getTitle().equals(this.title)))?true:false;}
	public int hashCode(){return this.title.hashCode();}
	
	public static void setCommandProcessor(Processable p) { proc = p; }
	public Processable getCommandProcessor() { return proc; }
	
	/* Currently not used */
	public void moveTask(int projectKey, int taskKey){}
}