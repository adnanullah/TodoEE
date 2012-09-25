package net.designspace.todoEE.items.interfaces;

import java.util.List;

public abstract interface Node<E extends Leaf> extends Leaf {
	public void addItem (E node);
	public void removeItem (int key);
	public void removeItem (String value);
	public E getItem(int key);
	public E getItem(String value);
	public List<E> getItems ();
	public String getItemsForDisplay();
	public int getSize ();
}