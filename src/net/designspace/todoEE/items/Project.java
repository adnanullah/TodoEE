package net.designspace.todoEE.items;

import net.designspace.todoEE.items.templates.Container;
import net.designspace.todoEE.items.enums.*;
import net.designspace.todoEE.processors.interfaces.Processable;
import net.designspace.todoEE.processors.interfaces.Executable;

public class Project extends Container<Task> implements Executable {
		
		private static Processable proc;
			
		public Project(int key, String title, String description, boolean removable){
			this.key = key;
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
			sb.append("!"+getTitle()+"~~"+getDescription()+"~~"+getNotes()+"~~"+getStatus()+"~~"+getPriority()+"~~"+getDueDate()+"~~"+isFavourite()+"~~"+isRemovable()+"~~"+getRepeatPeriod());
			for(Task l:getItems()){
				sb.append("\n"+l.save());
			}
			return sb.toString();
		}

		public String toString(){
			String prjView = "\nProject: "+this.getTitle()+" ["+this.getKey()+"]"+"\nDescription: "+this.getDescription()+"\nNo. of Tasks: "+this.leafList.size()+"\n---------------------------------------------------------";
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
		public boolean equals(Object object){return ((object instanceof Project) && (((Project)object).getTitle().equals(this.title)))?true:false;}
		public int hashCode(){return this.title.hashCode();}

		public static void setCommandProcessor(Processable p) { proc = p; }
		public Processable getCommandProcessor() { return proc; }
}