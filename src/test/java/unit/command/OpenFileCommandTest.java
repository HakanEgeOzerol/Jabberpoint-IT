package unit.command;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jabberpoint.command.OpenFileCommand;
import jabberpoint.command.context.CommandContext;
import jabberpoint.presentation.Presentation;
import jabberpoint.ui.DialogService;

class OpenFileCommandTest {

    @Mock
    private CommandContext mockContext;
    
    @Mock
    private Presentation mockPresentation;
    
    @Mock
    private DialogService mockDialogService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteWithDefaultConstructor() {
        // Arrange
        OpenFileCommand command = new OpenFileCommand();
        when(mockContext.hasReceiver(Presentation.class)).thenReturn(true);
        when(mockContext.getReceiver(Presentation.class)).thenReturn(mockPresentation);
        when(mockPresentation.getSize()).thenReturn(5);

        // Act
        command.execute(mockContext);

        // Assert
        verify(mockPresentation).clear();
        verify(mockPresentation).setSlideNumber(0);
    }

    @Test
    void testExecuteWithCustomFilename() {
        // Arrange
        OpenFileCommand command = new OpenFileCommand("test.xml");
        when(mockContext.hasReceiver(Presentation.class)).thenReturn(true);
        when(mockContext.getReceiver(Presentation.class)).thenReturn(mockPresentation);
        when(mockPresentation.getSize()).thenReturn(3);

        // Act
        command.execute(mockContext);

        // Assert
        verify(mockPresentation).clear();
        verify(mockPresentation).setSlideNumber(0);
    }

    @Test
    void testExecuteWithEmptyPresentation() {
        // Arrange
        OpenFileCommand command = new OpenFileCommand("test.xml");
        when(mockContext.hasReceiver(Presentation.class)).thenReturn(true);
        when(mockContext.getReceiver(Presentation.class)).thenReturn(mockPresentation);
        when(mockPresentation.getSize()).thenReturn(0);

        // Act
        command.execute(mockContext);

        // Assert
        verify(mockPresentation).clear();
        verify(mockPresentation, never()).setSlideNumber(anyInt());
    }

    @Test
    void testExecuteWithoutPresentationInContext() {
        // Arrange
        OpenFileCommand command = new OpenFileCommand("test.xml");
        when(mockContext.hasReceiver(Presentation.class)).thenReturn(false);

        // Act
        command.execute(mockContext);

        // Assert
        verify(mockPresentation, never()).clear();
        verify(mockPresentation, never()).setSlideNumber(anyInt());
    }
} 