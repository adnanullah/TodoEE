package net.designspace.todoEE.items.templates;

import java.util.Date;
import net.designspace.todoEE.items.interfaces.Leaf;
import net.designspace.todoEE.items.enums.*;

public abstract class Item implements Leaf {
		
	protected int key;
	protected int parentKey;
	protected String title;
	protected String description;
	protected boolean removable;
	protected PriorityType priority;
	protected StatusType status;
	protected Date dueDate;
	protected boolean favourite;
	protected String notes;
	protected boolean useTime;
	protected int repeatPeriodInDays;
	
	public final int getKey(){return this.key;}
	public final void setKey(int value){this.key = value;}
	
	public final int getParentKey(){return this.parentKey;}
	public final void setParentKey(int value){this.parentKey = value;}
	
	public final String getTitle(){return this.title;}
	public final void setTitle(String value){this.title = value;}
	
	public final String getDescription(){return this.description;}
	public final void setDescription(String value){this.description = value;}

	public final boolean isRemovable(){return this.removable;}
	
	public final Date getDueDate(){
		if(this.dueDate == null) return null;
		return new Date(this.dueDate.getTime());
	}
	public final void setDueDate(Date value){
		if (value != null) {
			this.dueDate = new Date(value.getTime());
		} else {
			this.dueDate = null;
		}
	}
	
	public final boolean isFavourite(){return this.favourite;}
	public final void setFavourite(boolean value){this.favourite = value;}
	
	public final String getNotes(){return this.notes;}
	public final void setNotes(String value){this.notes = value;}
	
	public final String getPriority(){
		String s = this.priority.toString();
		return s.substring(0,1).toUpperCase()+s.substring(1);
	}
	public final void setPriority(PriorityType priority){this.priority = priority;}
	
	public final String getStatus(){
		String s = this.status.toString();
		return s.substring(0,1).toUpperCase()+s.substring(1);
	}
	public final void setStatus(StatusType status){this.status = status;}
	
	public final boolean isUseTime(){return false;}
	public final void setUseTime(boolean value){this.useTime = value;}
	
	public final int getRepeatPeriod(){return this.repeatPeriodInDays;}
	public final void setRepeatPeriod(int days){this.repeatPeriodInDays = days;}
}