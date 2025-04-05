package jabberpoint.controller;

import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

import jabberpoint.command.Command;
import jabberpoint.constants.Constants;
import jabberpoint.constants.Constants.Commands;

/** <p>The controller for the menu</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 1.7 2023/03/28 Updated to use Command pattern
 */
public class MenuController extends MenuBar {
	
	private Map<String, Command> menuItems;
	
	private static final long serialVersionUID = 227L;
	
	// Using constants from Constants.Commands
	// protected static final String ABOUT = "About";
	// protected static final String FILE = "File";
	// protected static final String EXIT = "Exit";
	// protected static final String GOTO = "Go to";
	// protected static final String HELP = "Help";
	// protected static final String NEW = "New";
	// protected static final String NEXT = "Next";
	// protected static final String OPEN = "Open";
	// protected static final String PAGENR = "Page number?";
	// protected static final String PREV = "Prev";
	// protected static final String SAVE = "Save";
	// protected static final String VIEW = "View";
	
	// protected static final String TESTFILE = "test.xml";
	// protected static final String SAVEFILE = "dump.xml";
	
	public MenuController() {
		menuItems = new HashMap<>();
		
		// Register commands in the map
	}
	
	/**
	 * Creates the menus and menu items
	 */
	public void createMenus() {
		Menu fileMenu = new Menu(Constants.Commands.FILE);
		fileMenu.add(mkMenuItem(Constants.Commands.OPEN));
		fileMenu.add(mkMenuItem(Constants.Commands.NEW));
		fileMenu.add(mkMenuItem(Constants.Commands.SAVE));
		fileMenu.addSeparator();
		fileMenu.add(mkMenuItem(Constants.Commands.EXIT));
		add(fileMenu);
		
		Menu viewMenu = new Menu(Constants.Commands.VIEW);
		viewMenu.add(mkMenuItem(Constants.Commands.NEXT));
		viewMenu.add(mkMenuItem(Constants.Commands.PREV));
		viewMenu.add(mkMenuItem(Constants.Commands.GOTO));
		add(viewMenu);
		
		Menu helpMenu = new Menu(Constants.Commands.HELP);
		helpMenu.add(mkMenuItem(Constants.Commands.ABOUT));
		setHelpMenu(helpMenu);		// needed for portability (Motif, etc.).
	}

	/**
	 * Create a menu item with a command action
	 * @param name The name of the menu item
	 * @return The created MenuItem
	 */
	public MenuItem mkMenuItem(String name) {
		MenuItem menuItem = new MenuItem(name, new MenuShortcut(name.charAt(0)));
		
		// If the menu item has a command, add an action listener
		Command command = menuItems.get(name);
		if (command != null) {
			menuItem.addActionListener(createActionListener(command));
		}
		
		return menuItem;
	}
	
	/**
	 * Creates an ActionListener for the given command
	 * @param command The command to execute
	 * @return The ActionListener
	 */
	private ActionListener createActionListener(Command command) {
		return new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				command.execute();
			}
		};
	}
	
	/**
	 * Add a menu item with a command
	 * @param name The name of the menu item
	 * @param command The command to execute
	 */
	public void addMenuItem(String name, Command command) {
		menuItems.put(name, command);
	}
}
