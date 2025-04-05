package unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jabberpoint.PreviousSlideCommand;
import jabberpoint.Presentation;

public class PreviousSlideCommandTest {
    private Presentation mockPresentation;
    private PreviousSlideCommand previousSlideCommand;

    @BeforeEach
    public void setUp() {
        // Create a mock Presentation
        mockPresentation = mock(Presentation.class);
        
        // Create the command with the mock presentation
        previousSlideCommand = new PreviousSlideCommand(mockPresentation);
    }

    @Test
    public void testConstructor() {
        // Test that the constructor properly sets the presentation
        assertNotNull(previousSlideCommand);
    }

    @Test
    public void testExecute() {
        // Execute the command
        previousSlideCommand.execute();
        
        // Verify that prevSlide was called exactly once on the presentation
        verify(mockPresentation, times(1)).prevSlide();
    }
} 