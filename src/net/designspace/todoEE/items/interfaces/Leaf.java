package net.designspace.todoEE.items.interfaces;

import java.util.Date;
import net.designspace.todoEE.items.enums.*;

public abstract interface Leaf {
	
	/* Setters and Getters */
	public abstract void setParentKey(int value);
	public abstract int getParentKey();
	
	public abstract int getKey();
	public abstract void setKey(int value);
	
	public abstract String getTitle();
	public abstract void setTitle(String value);
	
	public abstract String getDescription();
	public abstract void setDescription(String value);
	
	public abstract boolean isRemovable();
	
	public abstract Date getDueDate();
	public abstract void setDueDate(Date value);
	
	public abstract boolean isFavourite();
	public abstract void setFavourite(boolean value);
	
	public abstract String getNotes();
	public abstract void setNotes(String value);
	
	public abstract String getPriority();
	public abstract void setPriority(PriorityType priority);
	
	public abstract String getStatus();
	public abstract void setStatus(StatusType status);
	
	public abstract boolean isUseTime();
	public abstract void setUseTime(boolean value);
	
	public abstract int getRepeatPeriod();
	public abstract void setRepeatPeriod(int days);
}