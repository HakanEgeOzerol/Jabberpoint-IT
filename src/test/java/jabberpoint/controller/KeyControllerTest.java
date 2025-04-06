package jabberpoint.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import java.awt.event.KeyEvent;

import jabberpoint.command.Command;
import jabberpoint.controller.KeyController;
import jabberpoint.BaseTest;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore({"javax.management.*", "javax.swing.*", "java.awt.*"})
public class KeyControllerTest extends BaseTest {
    private KeyController keyController;
    private Command mockCommand;
    private KeyEvent mockKeyEvent;
    
    @BeforeEach
    public void setUp() {
        keyController = new KeyController();
        mockCommand = mock(Command.class);
        mockKeyEvent = mock(KeyEvent.class);
    }
    
    @Test
    public void testConstructor() {
        // Test that a new KeyController is created without exceptions
        assertNotNull(keyController);
    }
    
    @Test
    public void testAddBind() {
        // Add a command binding
        keyController.addBind(KeyEvent.VK_RIGHT, mockCommand);
        
        // Simulate pressing the key
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_RIGHT);
        keyController.keyPressed(mockKeyEvent);
        
        // Verify the command was executed
        verify(mockCommand, times(1)).execute();
    }
    
    @Test
    public void testAddBindThrowsExceptionForNullCommand() {
        // Test that adding a null command throws an exception
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            keyController.addBind(KeyEvent.VK_ENTER, null);
        });
        
        assertEquals("Command cannot be null", exception.getMessage());
    }
    
    @Test
    public void testRemoveBind() {
        // Add a command binding
        keyController.addBind(KeyEvent.VK_RIGHT, mockCommand);
        
        // Remove the binding
        boolean removed = keyController.removeBind(KeyEvent.VK_RIGHT);
        assertTrue(removed);
        
        // Simulate pressing the key
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_RIGHT);
        keyController.keyPressed(mockKeyEvent);
        
        // Verify the command was not executed
        verify(mockCommand, never()).execute();
    }
    
    @Test
    public void testRemoveBindNonExistent() {
        // Try to remove a binding that doesn't exist
        boolean removed = keyController.removeBind(KeyEvent.VK_F1);
        assertFalse(removed);
    }
    
    @Test
    public void testExecuteCommand() {
        // Test the executeCommand method
        keyController.executeCommand(mockCommand);
        
        // Verify the command was executed
        verify(mockCommand, times(1)).execute();
    }
    
    @Test
    public void testExecuteCommandNull() {
        // Test that executing a null command doesn't throw an exception
        assertDoesNotThrow(() -> {
            keyController.executeCommand(null);
        });
    }
    
    @Test
    public void testKeyPressedWithNullEvent() {
        // Test handling a null key event
        assertDoesNotThrow(() -> {
            keyController.keyPressed(null);
        });
        
        // Verify no commands were executed
        verify(mockCommand, never()).execute();
    }
    
    @Test
    public void testKeyPressedWithUnboundKey() {
        // Add a binding for a different key
        keyController.addBind(KeyEvent.VK_RIGHT, mockCommand);
        
        // Simulate pressing an unbound key
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_LEFT);
        keyController.keyPressed(mockKeyEvent);
        
        // Verify the command was not executed
        verify(mockCommand, never()).execute();
    }
    
    @Test
    public void testMultipleKeyBindings() {
        // Create a second mock command
        Command mockCommand2 = mock(Command.class);
        
        // Add multiple bindings
        keyController.addBind(KeyEvent.VK_RIGHT, mockCommand);
        keyController.addBind(KeyEvent.VK_LEFT, mockCommand2);
        
        // Simulate pressing both keys
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_RIGHT);
        keyController.keyPressed(mockKeyEvent);
        
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_LEFT);
        keyController.keyPressed(mockKeyEvent);
        
        // Verify both commands were executed
        verify(mockCommand, times(1)).execute();
        verify(mockCommand2, times(1)).execute();
    }
} 