package unit.command;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jabberpoint.command.ExitCommand;
import jabberpoint.command.context.CommandContext;
import jabberpoint.presentation.Presentation;
import jabberpoint.ui.DialogService;

class ExitCommandTest {

    @Mock
    private CommandContext mockContext;
    
    @Mock
    private Presentation mockPresentation;
    
    @Mock
    private DialogService mockDialogService;

    private ExitCommand exitCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        exitCommand = new ExitCommand();
    }

    @Test
    void testExecuteWhenUserConfirmsExit() {
        // Arrange
        when(mockContext.hasReceiver(Presentation.class)).thenReturn(true);
        when(mockContext.hasReceiver(DialogService.class)).thenReturn(true);
        when(mockContext.getReceiver(Presentation.class)).thenReturn(mockPresentation);
        when(mockContext.getReceiver(DialogService.class)).thenReturn(mockDialogService);
        when(mockDialogService.confirmExit()).thenReturn(true);

        // Act
        exitCommand.execute(mockContext);

        // Assert
        verify(mockDialogService).confirmExit();
        verify(mockPresentation).exit(0);
    }
    
    @Test
    void testExecuteWhenUserCancelsExit() {
        // Arrange
        when(mockContext.hasReceiver(Presentation.class)).thenReturn(true);
        when(mockContext.hasReceiver(DialogService.class)).thenReturn(true);
        when(mockContext.getReceiver(Presentation.class)).thenReturn(mockPresentation);
        when(mockContext.getReceiver(DialogService.class)).thenReturn(mockDialogService);
        when(mockDialogService.confirmExit()).thenReturn(false);

        // Act
        exitCommand.execute(mockContext);

        // Assert
        verify(mockDialogService).confirmExit();
        verify(mockPresentation, never()).exit(anyInt());
    }
    
    @Test
    void testExecuteWithoutRequiredDependenciesInContext() {
        // Arrange - missing one or both dependencies
        when(mockContext.hasReceiver(Presentation.class)).thenReturn(false);
        when(mockContext.hasReceiver(DialogService.class)).thenReturn(true);

        // Act
        exitCommand.execute(mockContext);

        // Assert
        verify(mockDialogService, never()).confirmExit();
        verify(mockPresentation, never()).exit(anyInt());
    }
} 