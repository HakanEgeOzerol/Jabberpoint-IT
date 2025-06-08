package unit.controller;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.event.KeyEvent;

import jabberpoint.command.Command;
import jabberpoint.command.NextSlideCommand;
import jabberpoint.command.PreviousSlideCommand;
import jabberpoint.command.context.CommandContext;
import jabberpoint.controller.KeyController;

class KeyControllerTest {

    @Mock
    private CommandContext mockContext;
    
    @Mock
    private KeyEvent mockKeyEvent;

    private KeyController keyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        keyController = new KeyController(mockContext);
    }

    @Test
    void testConstructor() {
        // Test that the constructor properly sets the command context
        KeyController controller = new KeyController(mockContext);
        // Verify that it was created without throwing an exception
        assert controller != null;
    }

    @Test
    void testAddBindWithValidCommand() {
        // Test adding a valid key binding
        Class<? extends Command> commandClass = NextSlideCommand.class;
        
        // This should not throw an exception
        keyController.addBind(KeyEvent.VK_RIGHT, commandClass);
    }

    @Test
    void testAddBindWithNullCommand() {
        // Test adding a null command class should throw exception
        try {
            keyController.addBind(KeyEvent.VK_RIGHT, null);
            assert false : "Expected IllegalArgumentException";
        } catch (IllegalArgumentException e) {
            // Expected behavior
            assert e.getMessage().contains("Command class cannot be null");
        }
    }

    @Test
    void testRemoveBindExistingKey() {
        // Add a binding first
        keyController.addBind(KeyEvent.VK_RIGHT, NextSlideCommand.class);
        
        // Remove the binding
        boolean result = keyController.removeBind(KeyEvent.VK_RIGHT);
        
        // Should return true indicating the binding was removed
        assert result;
    }

    @Test
    void testRemoveBindNonExistingKey() {
        // Try to remove a binding that doesn't exist
        boolean result = keyController.removeBind(KeyEvent.VK_LEFT);
        
        // Should return false indicating no binding was removed
        assert !result;
    }

    @Test
    void testKeyPressedWithBoundKey() {
        // Add a binding
        keyController.addBind(KeyEvent.VK_RIGHT, NextSlideCommand.class);
        
        // Mock key event
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_RIGHT);
        
        // Press the key
        keyController.keyPressed(mockKeyEvent);
        
        // Verify that context was used (command would be executed)
        // Note: We can't easily verify command execution without more complex setup
        verify(mockKeyEvent).getKeyCode();
    }

    @Test
    void testKeyPressedWithUnboundKey() {
        // Mock key event for unbound key
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_F1);
        
        // Press the key
        keyController.keyPressed(mockKeyEvent);
        
        // Verify key code was checked
        verify(mockKeyEvent).getKeyCode();
        // No command should be executed for unbound keys
    }

    @Test
    void testKeyPressedWithNullEvent() {
        // Test with null event - should not crash
        keyController.keyPressed(null);
        
        // Should handle gracefully without throwing exception
    }

    @Test
    void testMultipleKeyBindings() {
        // Add multiple bindings
        keyController.addBind(KeyEvent.VK_RIGHT, NextSlideCommand.class);
        keyController.addBind(KeyEvent.VK_LEFT, PreviousSlideCommand.class);
        
        // Test both keys
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_RIGHT);
        keyController.keyPressed(mockKeyEvent);
        
        when(mockKeyEvent.getKeyCode()).thenReturn(KeyEvent.VK_LEFT);
        keyController.keyPressed(mockKeyEvent);
        
        // Verify both key events were processed
        verify(mockKeyEvent, times(2)).getKeyCode();
    }
} 