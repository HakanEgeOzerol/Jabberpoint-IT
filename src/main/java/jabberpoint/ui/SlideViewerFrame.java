package jabberpoint.ui;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;

import jabberpoint.command.AboutBoxCommand;
import jabberpoint.command.ExitCommand;
import jabberpoint.command.GoToSlideCommand;
import jabberpoint.command.NewFileCommand;
import jabberpoint.command.NextSlideCommand;
import jabberpoint.command.OpenFileCommand;
import jabberpoint.command.PreviousSlideCommand;
import jabberpoint.command.SaveFileCommand;
import jabberpoint.command.context.DefaultCommandContext;
import jabberpoint.constants.Constants;
import jabberpoint.controller.KeyController;
import jabberpoint.controller.MenuController;
import jabberpoint.presentation.Presentation;

/**
 * <p>The application window for a slideviewcomponent.</p>
 *
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
     * Constructor.
     *
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
     * Setup the window and controllers.
     *
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
     * Setup the controllers (keyboard and menu).
     *
     * @param presentation The presentation to control
     */
    private void setupControllers(Presentation presentation) {
        // this application contexts should not have a need to be updated
        DefaultCommandContext context = new DefaultCommandContext(presentation, this, new DefaultDialogService(this));
        // Create and set the key controller
        keyController = new KeyController(context);

        keyController.addBind(KeyEvent.VK_PAGE_DOWN, NextSlideCommand.class);
        keyController.addBind(KeyEvent.VK_DOWN, NextSlideCommand.class);
        keyController.addBind(KeyEvent.VK_ENTER, NextSlideCommand.class);
        keyController.addBind((int) '+', NextSlideCommand.class);

        keyController.addBind(KeyEvent.VK_PAGE_UP, PreviousSlideCommand.class);
        keyController.addBind(KeyEvent.VK_UP, PreviousSlideCommand.class);
        keyController.addBind((int) '-', PreviousSlideCommand.class);

        keyController.addBind((int) 'q', ExitCommand.class);
        keyController.addBind((int) 'Q', ExitCommand.class);

        addKeyListener(keyController);
        
        // Create and set the menu controller
        menuController = new MenuController(context);

        menuController.addMenuItem(Constants.Commands.NEXT, NextSlideCommand.class);
        menuController.addMenuItem(Constants.Commands.PREV, PreviousSlideCommand.class);
        menuController.addMenuItem(Constants.Commands.GOTO, GoToSlideCommand.class);
        menuController.addMenuItem(Constants.Commands.EXIT, ExitCommand.class);
        menuController.addMenuItem(Constants.Commands.OPEN, OpenFileCommand.class);
        menuController.addMenuItem(Constants.Commands.SAVE, SaveFileCommand.class);
        menuController.addMenuItem(Constants.Commands.NEW, NewFileCommand.class);
        menuController.addMenuItem(Constants.Commands.ABOUT, AboutBoxCommand.class);

        menuController.createMenus();
        setMenuBar(menuController);
    }
    
    /**
     * Get the key controller.
     *
     * @return The key controller
     */
    public KeyController getKeyController() {
        return keyController;
    }
    
    /**
     * Get the menu controller.
     *
     * @return The menu controller
     */
    public MenuController getMenuController() {
        return menuController;
    }
}
