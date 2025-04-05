package unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jabberpoint.ExitCommand;
import jabberpoint.Presentation;

public class ExitCommandTest {
    private Presentation mockPresentation;
    private ExitCommand exitCommand;

    @BeforeEach
    public void setUp() {
        // Create a mock Presentation
        mockPresentation = mock(Presentation.class);
        
        // Create the command with the mock presentation
        exitCommand = new ExitCommand(mockPresentation);
    }

    @Test
    public void testConstructor() {
        // Test that the constructor properly sets the presentation
        assertNotNull(exitCommand);
    }

    @Test
    public void testExecute() {
        // Execute the command
        exitCommand.execute();
        
        // Verify that exit was called exactly once on the presentation with status code 0
        verify(mockPresentation, times(1)).exit(0);
    }
} 