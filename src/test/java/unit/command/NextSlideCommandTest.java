package unit.command;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jabberpoint.command.NextSlideCommand;
import jabberpoint.command.context.CommandContext;
import jabberpoint.presentation.Presentation;

class NextSlideCommandTest {

    @Mock
    private CommandContext mockContext;
    
    @Mock
    private Presentation mockPresentation;

    private NextSlideCommand nextSlideCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        nextSlideCommand = new NextSlideCommand();
    }

    @Test
    void testExecute() {
        // Arrange
        when(mockContext.hasReceiver(Presentation.class)).thenReturn(true);
        when(mockContext.getReceiver(Presentation.class)).thenReturn(mockPresentation);

        // Act
        nextSlideCommand.execute(mockContext);

        // Assert
        verify(mockPresentation).nextSlide();
    }
    
    @Test
    void testExecuteWithoutPresentationInContext() {
        // Arrange
        when(mockContext.hasReceiver(Presentation.class)).thenReturn(false);

        // Act
        nextSlideCommand.execute(mockContext);

        // Assert
        verify(mockPresentation, never()).nextSlide();
    }
} 