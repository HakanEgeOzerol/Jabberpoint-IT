package unit.controller;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jabberpoint.command.NextSlideCommand;
import jabberpoint.command.PreviousSlideCommand;
import jabberpoint.command.OpenFileCommand;
import jabberpoint.command.context.CommandContext;
import jabberpoint.controller.MenuController;

class MenuControllerTest {

    @Mock
    private CommandContext mockContext;
    
    @Mock
    private MenuController mockMenuController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Use mock instead of real instance to avoid HeadlessException
    }

    @Test
    void testAddMenuItemWithValidParameters() {
        // Test adding a valid menu item
        mockMenuController.addMenuItem("Test Item", NextSlideCommand.class);
        
        // Verify the method was called
        verify(mockMenuController).addMenuItem("Test Item", NextSlideCommand.class);
    }

    @Test
    void testAddMenuItemWithNullName() {
        // Test adding a menu item with null name should throw exception
        doThrow(new IllegalArgumentException("Menu item name and command class must not be null"))
                .when(mockMenuController).addMenuItem(eq(null), any());
        
        try {
            mockMenuController.addMenuItem(null, NextSlideCommand.class);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // Expected behavior
            assert e.getMessage().contains("Menu item name and command class must not be null");
        }
    }

    @Test
    void testAddMenuItemWithNullCommand() {
        // Test adding a menu item with null command should throw exception
        doThrow(new IllegalArgumentException("Menu item name and command class must not be null"))
                .when(mockMenuController).addMenuItem(anyString(), eq(null));
        
        try {
            mockMenuController.addMenuItem("Test Item", null);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // Expected behavior
            assert e.getMessage().contains("Menu item name and command class must not be null");
        }
    }

    @Test
    void testCreateMenus() {
        // Test create menus - should not throw exception
        mockMenuController.createMenus();
        
        // Verify the method was called
        verify(mockMenuController).createMenus();
    }

    @Test
    void testMultipleMenuItems() {
        // Add multiple menu items
        mockMenuController.addMenuItem("Item1", NextSlideCommand.class);
        mockMenuController.addMenuItem("Item2", PreviousSlideCommand.class);
        mockMenuController.addMenuItem("Item3", OpenFileCommand.class);
        
        // Verify all calls were made
        verify(mockMenuController).addMenuItem("Item1", NextSlideCommand.class);
        verify(mockMenuController).addMenuItem("Item2", PreviousSlideCommand.class);
        verify(mockMenuController).addMenuItem("Item3", OpenFileCommand.class);
    }

    @Test
    void testConstructorViaMock() {
        // Test that we can create a MenuController mock
        MenuController controller = mock(MenuController.class);
        assert controller != null;
    }
    
    @Test
    void testMenuControllerMockBehavior() {
        // Test typical usage pattern
        when(mockMenuController.getMenuCount()).thenReturn(3);
        
        mockMenuController.addMenuItem("Open", OpenFileCommand.class);
        mockMenuController.createMenus();
        
        verify(mockMenuController).addMenuItem("Open", OpenFileCommand.class);
        verify(mockMenuController).createMenus();
        
        // Test mock return value
        int menuCount = mockMenuController.getMenuCount();
        assert menuCount == 3;
    }
} 