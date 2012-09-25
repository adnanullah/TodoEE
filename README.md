TodoEE
======

A simple Java-based command line todo application. Created a while back to help me study for my SCJP6 certification,
demonstrates usage of generics and some simple design pattern.

REQUIRES: Java 6
COMPILE: javac -cp src:. -d bin/ src/net/designspace/todoEE/TodoEE.java 
EXECUTE: java -cp bin net/designspace/todoEE/TodoEE

List of commands:

exit 	- Quit TodoEE application
	  (aliases: quit, bye)	

save	- Saves the current contents of the task list
	  
reload	- Abandons all changes and reads the task list from disk

help	- Displays this help item
	  (aliases: man, ?)

select	- Selects a TODO item
	  (aliases: cd, dir)

	Usage: select <index|item_name>

list	- Lists the contents of a TODO item
	  (aliases: ls, show, view)

	Usage: list [<index|title>]

add	- Adds a TODO item
	  (aliases: mkdir)

	Usage: add <title>

remove	- Removes a TODO item
	  (aliases: del[ete], rm)

	Usage: remove <index|title>

edit	- Amends a TODO item
	  (aliases: amend, set)

	- Sub-commands: (title, descr[iption], [due][date], [fav]ourite, note, priority, status) 

	Usage: edit <index> <sub-command> <value>
 
