package net.designspace.todoEE.processors.interfaces;

import net.designspace.todoEE.items.interfaces.Leaf;

public abstract interface Processable<E extends Leaf> {
	
	public boolean execute(E source, String input, String[] args);
	public String getPrompt(Leaf source);
	
}