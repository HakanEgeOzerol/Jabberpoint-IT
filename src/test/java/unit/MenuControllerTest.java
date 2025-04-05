package unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;

import jabberpoint.Command;
import jabberpoint.Constants;
import jabberpoint.MenuController;

public class MenuControllerTest {
    private MenuController menuController;
    private Command mockCommand;
    
    @BeforeEach
    public void setUp() {
        menuController = new MenuController();
        mockCommand = mock(Command.class);
    }
    
    @Test
    public void testConstructor() {
        // Test that a new MenuController is created
        assertNotNull(menuController);
    }
    
    @Test
    public void testAddMenuItem() {
        // Add a menu item with a command
        menuController.addMenuItem("Test", mockCommand);
        
        // Create the menus
        menuController.createMenus();
        
        // Create a menu item using the added command
        // This is an indirect test as we can't directly access the map
        MenuItem menuItem = menuController.mkMenuItem("Test");
        
        assertNotNull(menuItem);
        assertEquals("Test", menuItem.getLabel());
    }
    
    @Test
    public void testMkMenuItemWithRegisteredCommand() {
        // Register a command
        menuController.addMenuItem(Constants.Commands.OPEN, mockCommand);
        
        // Create a menu item for that command
        MenuItem openItem = menuController.mkMenuItem(Constants.Commands.OPEN);
        
        assertNotNull(openItem);
        assertEquals(Constants.Commands.OPEN, openItem.getLabel());
        
        // Trigger the action to test the command execution
        ActionEvent actionEvent = new ActionEvent(openItem, ActionEvent.ACTION_PERFORMED, Constants.Commands.OPEN);
        openItem.getActionListeners()[0].actionPerformed(actionEvent);
        
        // Verify the command was executed
        verify(mockCommand, times(1)).execute();
    }
    
    @Test
    public void testMkMenuItemWithoutCommand() {
        // Create a menu item without registering a command
        MenuItem testItem = menuController.mkMenuItem("Unregistered");
        
        assertNotNull(testItem);
        assertEquals("Unregistered", testItem.getLabel());
        
        // No action listeners should be attached since no command is registered
        assertEquals(0, testItem.getActionListeners().length);
    }
    
    @Test
    public void testCreateMenus() {
        // Register commands for menu items
        menuController.addMenuItem(Constants.Commands.OPEN, mockCommand);
        menuController.addMenuItem(Constants.Commands.NEW, mockCommand);
        menuController.addMenuItem(Constants.Commands.SAVE, mockCommand);
        menuController.addMenuItem(Constants.Commands.EXIT, mockCommand);
        menuController.addMenuItem(Constants.Commands.NEXT, mockCommand);
        menuController.addMenuItem(Constants.Commands.PREV, mockCommand);
        menuController.addMenuItem(Constants.Commands.GOTO, mockCommand);
        menuController.addMenuItem(Constants.Commands.ABOUT, mockCommand);
        
        // Create the menus
        menuController.createMenus();
        
        // Check that the menus were created
        assertEquals(3, menuController.getMenuCount()); // File, View, Help menus
        
        // Verify File menu exists with correct items
        Menu fileMenu = menuController.getMenu(0);
        assertEquals(Constants.Commands.FILE, fileMenu.getLabel());
        assertEquals(5, fileMenu.getItemCount()); // OPEN, NEW, SAVE, separator, EXIT
        
        // Verify View menu exists with correct items
        Menu viewMenu = menuController.getMenu(1);
        assertEquals(Constants.Commands.VIEW, viewMenu.getLabel());
        assertEquals(3, viewMenu.getItemCount()); // NEXT, PREV, GOTO
        
        // Verify Help menu exists with correct items
        Menu helpMenu = menuController.getHelpMenu();
        assertEquals(Constants.Commands.HELP, helpMenu.getLabel());
        assertEquals(1, helpMenu.getItemCount()); // ABOUT
    }
    
    @Test
    public void testMenuShortcuts() {
        // Create a menu item and check it has a shortcut
        MenuItem testItem = menuController.mkMenuItem("Test");
        
        assertNotNull(testItem.getShortcut());
        assertEquals('T', testItem.getShortcut().getKey());
    }
} 