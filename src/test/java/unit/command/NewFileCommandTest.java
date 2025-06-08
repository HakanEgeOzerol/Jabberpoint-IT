package unit.command;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jabberpoint.command.NewFileCommand;
import jabberpoint.command.context.CommandContext;
import jabberpoint.presentation.Presentation;

import java.awt.Frame;

class NewFileCommandTest {

    @Mock
    private CommandContext mockContext;
    
    @Mock
    private Presentation mockPresentation;
    
    @Mock
    private Frame mockFrame;

    private NewFileCommand newFileCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        newFileCommand = new NewFileCommand();
    }

    @Test
    void testExecuteWithBothReceivers() {
        when(mockContext.hasReceiver(Presentation.class)).thenReturn(true);
        when(mockContext.hasReceiver(Frame.class)).thenReturn(true);
        when(mockContext.getReceiver(Presentation.class)).thenReturn(mockPresentation);
        when(mockContext.getReceiver(Frame.class)).thenReturn(mockFrame);

        newFileCommand.execute(mockContext);

        verify(mockPresentation).clear();
        verify(mockFrame).repaint();
    }

    @Test
    void testExecuteWithoutPresentationReceiver() {
        when(mockContext.hasReceiver(Presentation.class)).thenReturn(false);
        when(mockContext.hasReceiver(Frame.class)).thenReturn(true);

        newFileCommand.execute(mockContext);

        verify(mockPresentation, never()).clear();
        verify(mockFrame, never()).repaint();
    }

    @Test
    void testExecuteWithoutFrameReceiver() {
        when(mockContext.hasReceiver(Presentation.class)).thenReturn(true);
        when(mockContext.hasReceiver(Frame.class)).thenReturn(false);

        newFileCommand.execute(mockContext);

        verify(mockPresentation, never()).clear();
        verify(mockFrame, never()).repaint();
    }

    @Test
    void testExecuteWithoutAnyReceivers() {
        when(mockContext.hasReceiver(Presentation.class)).thenReturn(false);
        when(mockContext.hasReceiver(Frame.class)).thenReturn(false);

        newFileCommand.execute(mockContext);

        verify(mockPresentation, never()).clear();
        verify(mockFrame, never()).repaint();
    }
} 