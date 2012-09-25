package net.designspace.todoEE.items;

import net.designspace.todoEE.items.templates.Item;
import net.designspace.todoEE.items.enums.*;
import net.designspace.todoEE.processors.interfaces.Processable;
import net.designspace.todoEE.processors.interfaces.Executable;

public class SubTask extends Item implements Executable {
	
	private static Processable proc;
	
	public SubTask(int key, int parentKey, String title, String description, boolean removable){
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
		return "$"+getTitle()+"~~"+getDescription()+"~~"+getNotes()+"~~"+getStatus()+"~~"+getPriority()+"~~"+getDueDate()+"~~"+isFavourite()+"~~"+isRemovable()+"~~"+getRepeatPeriod();
		
	}
	public String toString(){
		String prjView = "\nSubTask: "+this.getTitle()+" ["+this.getKey()+"]"+"\nDescription: "+this.getDescription()+"\n---------------------------------------------------------";
		prjView += "\nStatus: "+this.getStatus()+"\nPriority: "+this.getPriority();
		if(this.getDueDate()==null) {
			prjView += "\nDue Date: None";
		} else {
			prjView += "\nDue Date: "+this.getDueDate().toString(); 
		}
		prjView += "\nFavourite: "+this.isFavourite()+"\nRemovable: "+this.isRemovable()+"\n";		
		return prjView;
	}
	
	public void moveSubTask(int projectKey, int taskKey, int subtaskKey){}
	
	/* Simple equality check based on title (for now) */
	public boolean equals(Object object){return ((object instanceof SubTask) && (((SubTask)object).getTitle().equals(this.title)))?true:false;}
	public int hashCode(){return this.title.hashCode();}
	
	public static void setCommandProcessor(Processable p) { proc = p; }
	public Processable getCommandProcessor() { return proc; }	
	
}