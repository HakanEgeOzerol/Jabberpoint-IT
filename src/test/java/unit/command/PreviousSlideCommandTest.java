package unit.command;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jabberpoint.command.PreviousSlideCommand;
import jabberpoint.command.context.CommandContext;
import jabberpoint.presentation.Presentation;

class PreviousSlideCommandTest {

    @Mock
    private CommandContext mockContext;
    
    @Mock
    private Presentation mockPresentation;

    private PreviousSlideCommand previousSlideCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        previousSlideCommand = new PreviousSlideCommand();
    }

    @Test
    void testExecute() {
        // Arrange
        when(mockContext.hasReceiver(Presentation.class)).thenReturn(true);
        when(mockContext.getReceiver(Presentation.class)).thenReturn(mockPresentation);

        // Act
        previousSlideCommand.execute(mockContext);

        // Assert
        verify(mockPresentation).prevSlide();
    }
    
    @Test
    void testExecuteWithoutPresentationInContext() {
        // Arrange
        when(mockContext.hasReceiver(Presentation.class)).thenReturn(false);

        // Act
        previousSlideCommand.execute(mockContext);

        // Assert
        verify(mockPresentation, never()).prevSlide();
    }
} 