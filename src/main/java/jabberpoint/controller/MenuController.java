package jabberpoint.controller;

import java.awt.MenuBar;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import jabberpoint.command.Command;
import jabberpoint.command.context.CommandContext;
import jabberpoint.constants.Constants;

/**
 * Controller for the menu system in JabberPoint.
 * Uses command pattern with dynamic instantiation to respect SRP and OCP.
 */
public class MenuController extends MenuBar {

	private static final long serialVersionUID = 227L;

	private final CommandContext commandContext;
	private final Map<String, Class<? extends Command>> commandBindings;

	public MenuController(CommandContext commandContext) {
		this.commandContext = commandContext;
		this.commandBindings = new HashMap<>();
	}

	/**
	 * Registers a menu item label with a corresponding command class.
	 * @param name Menu item label
	 * @param commandClass Command class to instantiate on click
	 */
	public void addMenuItem(String name, Class<? extends Command> commandClass) {
		if (name == null || commandClass == null) {
			throw new IllegalArgumentException("Menu item name and command class must not be null");
		}
		commandBindings.put(name, commandClass);
	}

	/**
	 * Creates all top-level menus.
	 */
	public void createMenus() {
		createFileMenu();
		createViewMenu();
		createHelpMenu();
	}

	private void createFileMenu() {
		Menu fileMenu = new Menu(Constants.Commands.FILE);
		fileMenu.add(mkMenuItem(Constants.Commands.OPEN));
		fileMenu.add(mkMenuItem(Constants.Commands.NEW));
		fileMenu.add(mkMenuItem(Constants.Commands.SAVE));
		fileMenu.addSeparator();
		fileMenu.add(mkMenuItem(Constants.Commands.EXIT));
		add(fileMenu);
	}

	private void createViewMenu() {
		Menu viewMenu = new Menu(Constants.Commands.VIEW);
		viewMenu.add(mkMenuItem(Constants.Commands.NEXT));
		viewMenu.add(mkMenuItem(Constants.Commands.PREV));
		viewMenu.add(mkMenuItem(Constants.Commands.GOTO));
		add(viewMenu);
	}

	private void createHelpMenu() {
		Menu helpMenu = new Menu(Constants.Commands.HELP);
		helpMenu.add(mkMenuItem(Constants.Commands.ABOUT));
		setHelpMenu(helpMenu);
	}

	/**
	 * Creates a menu item with a shortcut and command action.
	 * @param name Menu item label
	 * @return MenuItem instance
	 */
	private MenuItem mkMenuItem(String name) {
		MenuItem menuItem = new MenuItem(name, new MenuShortcut(name.charAt(0)));

		Class<? extends Command> commandClass = commandBindings.get(name);
		if (commandClass != null) {
			menuItem.addActionListener(createActionListener(commandClass));
		}

		return menuItem;
	}

	/**
	 * Creates an ActionListener that instantiates and executes a new command.
	 * @param commandClass Class of the command to instantiate
	 * @return ActionListener for menu item
	 */
	private ActionListener createActionListener(Class<? extends Command> commandClass) {
		return e -> {
			try {
				Command command = commandClass.getDeclaredConstructor().newInstance();
				command.execute(commandContext);
			} catch (Exception ex) {
				ex.printStackTrace(); // Consider a DialogService for real error handling
			}
		};
	}
}
