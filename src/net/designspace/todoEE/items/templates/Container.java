package net.designspace.todoEE.items.templates;

import java.util.List;
import java.util.ArrayList;
import net.designspace.todoEE.items.interfaces.*;

public abstract class Container<T extends Leaf> extends Item implements Node<T>{
	
	protected List<T> leafList = new ArrayList<T>();
	
	public void addItem (T node){this.leafList.add(node);}
	
	public void removeItem(int key){
		if(getItem(key).isRemovable()){	
			this.leafList.remove(key);
			rebuildIndexes();
		}else{
			System.out.println("INFO: This item cannot be removed.");
		}
	}
	public void removeItem (String value){removeItem(getItem(value).getKey());}
	
	protected void rebuildIndexes(){
		int count = 0;
		for (T t: leafList){
			t.setParentKey(count);
			count++;
		}
	}
	
	public T getItem(int key){return this.leafList.get(key);}
	public T getItem(String value){
	   	int key = -1;
		for(T t : leafList){
			if(t.getTitle().equalsIgnoreCase(value)){
	   			key = t.getKey();
	   			break;
	   		}
	   	}
	   	if(key < 0) return null;
	   	return this.leafList.get(key);
	}
	
	public int getSize() {return this.leafList.size();}
	public List<T> getItems(){return this.leafList;}
	
	public String getItemsForDisplay(){
		String header = "\n[ "+this.getTitle()+" ]\n------------------------------------";
		StringBuffer sb = new StringBuffer();
		sb.append(header);
		int count = 0;
		for(T t : leafList){
			sb.append("\n[").append(count).append("] ").append(t.getTitle());
			count++;
		}
		
		if (sb.toString().equals(header)) return header+"\n[-] No items currently available\n";
		return sb.append("\n").toString();
	}
}