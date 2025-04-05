package jabberpoint.ui;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;

import jabberpoint.command.AboutBoxCommand;
import jabberpoint.command.Command;
import jabberpoint.command.ExitCommand;
import jabberpoint.command.GoToSlideCommand;
import jabberpoint.command.NewPresentationCommand;
import jabberpoint.command.NextSlideCommand;
import jabberpoint.command.OpenFileCommand;
import jabberpoint.command.PreviousSlideCommand;
import jabberpoint.command.SaveFileCommand;
import jabberpoint.constants.Constants;
import jabberpoint.controller.KeyController;
import jabberpoint.controller.MenuController;
import jabberpoint.presentation.Presentation;

/**
 * <p>The application window for a slideviewcomponent</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 1.7 2023/03/28 Updated to match new architecture with controllers
*/

public class SlideViewerFrame extends JFrame {
	private static final long serialVersionUID = 3227L;
	
	// Using constant from Constants.UI
	// private static final String JABTITLE = "Jabberpoint 1.6 - OU";
	
	private KeyController keyController;
	private MenuController menuController;
	
	/**
	 * Constructor
	 * @param title The window title
	 * @param presentation The presentation to display
	 */
	public SlideViewerFrame(String title, Presentation presentation) {
		super(title);
		SlideViewerComponent slideViewerComponent = new SlideViewerComponent(this);
		presentation.addSubscriber(slideViewerComponent);
		setupWindow(slideViewerComponent, presentation);
	}

	/**
	 * Setup the window and controllers
	 * @param slideViewerComponent The component that displays slides
	 * @param presentation The presentation to display
	 */
	public void setupWindow(SlideViewerComponent slideViewerComponent, Presentation presentation) {
		setTitle(Constants.UI.JABTITLE);
		addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
		getContentPane().add(slideViewerComponent);
		setupControllers(presentation);
		setSize(new Dimension(Constants.UI.WIDTH, Constants.UI.HEIGHT)); // Same sizes as Slide has.
		setVisible(true);
	}
	
	/**
	 * Setup the controllers (keyboard and menu)
	 * @param presentation The presentation to control
	 */
	private void setupControllers(Presentation presentation) {
		// create commands to set
		Command nextSlideCommand = new NextSlideCommand(presentation);
		Command prevSlideCommand = new PreviousSlideCommand(presentation);
		Command exitCommand = new ExitCommand(presentation);

		Command openCommand = new OpenFileCommand(this, presentation, Constants.Commands.TESTFILE);
		Command saveCommand = new SaveFileCommand(this, presentation, Constants.Commands.SAVEFILE);
		Command goToCommand = new GoToSlideCommand(this, presentation);
		Command newCommand = new NewPresentationCommand(this, presentation);
		Command aboutCommand = new AboutBoxCommand(this, presentation);

		// Create and set the key controller
		keyController = new KeyController();

		keyController.addBind(KeyEvent.VK_PAGE_DOWN, nextSlideCommand);
		keyController.addBind(KeyEvent.VK_DOWN, nextSlideCommand);
		keyController.addBind(KeyEvent.VK_ENTER, nextSlideCommand);
		keyController.addBind((int) '+', nextSlideCommand);

		keyController.addBind(KeyEvent.VK_PAGE_UP, prevSlideCommand);
		keyController.addBind(KeyEvent.VK_UP, prevSlideCommand);
		keyController.addBind((int) '-', prevSlideCommand);

		keyController.addBind((int) 'q', exitCommand);
		keyController.addBind((int) 'Q', exitCommand);

		addKeyListener(keyController);
		
		// Create and set the menu controller
		menuController = new MenuController();

		menuController.addMenuItem(Constants.Commands.NEXT, nextSlideCommand);
		menuController.addMenuItem(Constants.Commands.PREV, prevSlideCommand);
		menuController.addMenuItem(Constants.Commands.GOTO, goToCommand);
		menuController.addMenuItem(Constants.Commands.EXIT, exitCommand);
		menuController.addMenuItem(Constants.Commands.OPEN, openCommand);
		menuController.addMenuItem(Constants.Commands.SAVE, saveCommand);
		menuController.addMenuItem(Constants.Commands.NEW, newCommand);
		menuController.addMenuItem(Constants.Commands.ABOUT, aboutCommand);

		menuController.createMenus();
		setMenuBar(menuController);
	}
	
	/**
	 * Get the key controller
	 * @return The key controller
	 */
	public KeyController getKeyController() {
		return keyController;
	}
	
	/**
	 * Get the menu controller
	 * @return The menu controller
	 */
	public MenuController getMenuController() {
		return menuController;
	}
}
