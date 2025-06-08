package unit.command;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jabberpoint.command.GoToSlideCommand;
import jabberpoint.command.context.CommandContext;
import jabberpoint.presentation.Presentation;
import jabberpoint.ui.DialogService;
import jabberpoint.constants.Constants;

class GoToSlideCommandTest {

    @Mock
    private CommandContext mockContext;
    
    @Mock
    private Presentation mockPresentation;
    
    @Mock
    private DialogService mockDialogService;

    private GoToSlideCommand goToSlideCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        goToSlideCommand = new GoToSlideCommand();
    }

    @Test
    void testExecuteWithValidSlideNumber() {
        // Arrange
        when(mockContext.hasReceiver(DialogService.class)).thenReturn(true);
        when(mockContext.hasReceiver(Presentation.class)).thenReturn(true);
        when(mockContext.getReceiver(DialogService.class)).thenReturn(mockDialogService);
        when(mockContext.getReceiver(Presentation.class)).thenReturn(mockPresentation);
        when(mockDialogService.getUserInput(Constants.Commands.PAGENR)).thenReturn("3");

        // Act
        goToSlideCommand.execute(mockContext);

        // Assert
        verify(mockDialogService).getUserInput(Constants.Commands.PAGENR);
        verify(mockPresentation).setSlideNumber(2); // zero-based index, so 3-1=2
    }

    @Test
    void testExecuteWithInvalidSlideNumber() {
        // Arrange
        when(mockContext.hasReceiver(DialogService.class)).thenReturn(true);
        when(mockContext.hasReceiver(Presentation.class)).thenReturn(true);
        when(mockContext.getReceiver(DialogService.class)).thenReturn(mockDialogService);
        when(mockContext.getReceiver(Presentation.class)).thenReturn(mockPresentation);
        when(mockDialogService.getUserInput(Constants.Commands.PAGENR)).thenReturn("invalid");

        // Act
        goToSlideCommand.execute(mockContext);

        // Assert
        verify(mockDialogService).getUserInput(Constants.Commands.PAGENR);
        verify(mockDialogService).showErrorMessage("Please enter a valid slide number.");
        verify(mockPresentation, never()).setSlideNumber(anyInt());
    }

    @Test
    void testExecuteWithZeroSlideNumber() {
        // Arrange
        when(mockContext.hasReceiver(DialogService.class)).thenReturn(true);
        when(mockContext.hasReceiver(Presentation.class)).thenReturn(true);
        when(mockContext.getReceiver(DialogService.class)).thenReturn(mockDialogService);
        when(mockContext.getReceiver(Presentation.class)).thenReturn(mockPresentation);
        when(mockDialogService.getUserInput(Constants.Commands.PAGENR)).thenReturn("0");

        // Act
        goToSlideCommand.execute(mockContext);

        // Assert
        verify(mockDialogService).getUserInput(Constants.Commands.PAGENR);
        verify(mockPresentation).setSlideNumber(-1); // zero-based index, so 0-1=-1
    }

    @Test
    void testExecuteWithMissingDependencies() {
        // Arrange
        when(mockContext.hasReceiver(DialogService.class)).thenReturn(false);
        when(mockContext.hasReceiver(Presentation.class)).thenReturn(true);

        // Act
        goToSlideCommand.execute(mockContext);

        // Assert
        verify(mockDialogService, never()).getUserInput(anyString());
        verify(mockPresentation, never()).setSlideNumber(anyInt());
    }
} 