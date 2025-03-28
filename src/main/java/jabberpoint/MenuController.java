package jabberpoint;

import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;

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
	
	private SlideViewerFrame parent; // the frame, only used as parent for the Dialogs
	private Presentation presentation; // Commands are given to the presentation
	private Map<String, Command> menuItems;
	
	private static final long serialVersionUID = 227L;
	
	protected static final String ABOUT = "About";
	protected static final String FILE = "File";
	protected static final String EXIT = "Exit";
	protected static final String GOTO = "Go to";
	protected static final String HELP = "Help";
	protected static final String NEW = "New";
	protected static final String NEXT = "Next";
	protected static final String OPEN = "Open";
	protected static final String PAGENR = "Page number?";
	protected static final String PREV = "Prev";
	protected static final String SAVE = "Save";
	protected static final String VIEW = "View";
	
	protected static final String TESTFILE = "test.xml";
	protected static final String SAVEFILE = "dump.xml";
	
	public MenuController(SlideViewerFrame frame, Presentation pres) {
		parent = frame;
		presentation = pres;
		menuItems = new HashMap<>();
		
		// Initialize commands
		Command nextSlideCommand = new NextSlideCommand(presentation);
		Command prevSlideCommand = new PreviousSlideCommand(presentation);
		Command exitCommand = new ExitCommand(presentation);
		Command openCommand = new OpenFileCommand(parent, presentation, TESTFILE);
		Command saveCommand = new SaveFileCommand(parent, presentation, SAVEFILE);
		Command newCommand = new NewPresentationCommand(parent, presentation);
		Command aboutCommand = new AboutBoxCommand(parent, presentation);
		
		// Register commands in the map
		menuItems.put(NEXT, nextSlideCommand);
		menuItems.put(PREV, prevSlideCommand);
		menuItems.put(EXIT, exitCommand);
		menuItems.put(OPEN, openCommand);
		menuItems.put(SAVE, saveCommand);
		menuItems.put(NEW, newCommand);
		menuItems.put(ABOUT, aboutCommand);
		
		createMenus();
	}
	
	/**
	 * Creates the menus and menu items
	 */
	private void createMenus() {
		Menu fileMenu = new Menu(FILE);
		fileMenu.add(mkMenuItem(OPEN));
		fileMenu.add(mkMenuItem(NEW));
		fileMenu.add(mkMenuItem(SAVE));
		fileMenu.addSeparator();
		fileMenu.add(mkMenuItem(EXIT));
		add(fileMenu);
		
		Menu viewMenu = new Menu(VIEW);
		viewMenu.add(mkMenuItem(NEXT));
		viewMenu.add(mkMenuItem(PREV));
		viewMenu.add(mkMenuItem(GOTO));
		add(viewMenu);
		
		Menu helpMenu = new Menu(HELP);
		helpMenu.add(mkMenuItem(ABOUT));
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
		} else if (GOTO.equals(name)) {
			// Special case for Go to which needs input
			menuItem.addActionListener(createGoToActionListener());
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
	 * Creates an ActionListener for the GoTo menu item
	 * @return The ActionListener
	 */
	private ActionListener createGoToActionListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				String pageNumberStr = javax.swing.JOptionPane.showInputDialog(PAGENR);
				try {
					int pageNumber = Integer.parseInt(pageNumberStr);
					// Go to page
					Command gotoCommand = new GoToSlideCommand(presentation, pageNumber - 1);
					gotoCommand.execute();
				} catch (NumberFormatException ex) {
					// Ignore if not a valid number
				}
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
