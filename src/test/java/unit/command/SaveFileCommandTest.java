package unit.command;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jabberpoint.command.SaveFileCommand;
import jabberpoint.command.context.CommandContext;
import jabberpoint.presentation.Presentation;
import jabberpoint.ui.DialogService;

class SaveFileCommandTest {

    @Mock
    private CommandContext mockContext;
    
    @Mock
    private Presentation mockPresentation;
    
    @Mock
    private DialogService mockDialogService;

    private SaveFileCommand saveFileCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        saveFileCommand = new SaveFileCommand();
    }

    @Test
    void testExecuteWithEmptyPresentation() {
        // Arrange
        when(mockContext.hasReceiver(Presentation.class)).thenReturn(true);
        when(mockContext.hasReceiver(DialogService.class)).thenReturn(true);
        when(mockContext.getReceiver(Presentation.class)).thenReturn(mockPresentation);
        when(mockContext.getReceiver(DialogService.class)).thenReturn(mockDialogService);
        when(mockPresentation.getSize()).thenReturn(0);

        // Act
        saveFileCommand.execute(mockContext);

        // Assert
        verify(mockDialogService).showErrorMessage("No presentation to save");
    }

    @Test
    void testExecuteWithNullPresentation() {
        // Arrange
        when(mockContext.hasReceiver(Presentation.class)).thenReturn(true);
        when(mockContext.hasReceiver(DialogService.class)).thenReturn(true);
        when(mockContext.getReceiver(Presentation.class)).thenReturn(null);
        when(mockContext.getReceiver(DialogService.class)).thenReturn(mockDialogService);

        // Act
        saveFileCommand.execute(mockContext);

        // Assert
        verify(mockDialogService).showErrorMessage("No presentation to save");
    }

    @Test
    void testExecuteWithoutPresentationInContext() {
        // Arrange
        when(mockContext.hasReceiver(Presentation.class)).thenReturn(false);

        // Act
        saveFileCommand.execute(mockContext);

        // Assert
        verify(mockPresentation, never()).getSize();
        verify(mockDialogService, never()).showErrorMessage(anyString());
    }
} 