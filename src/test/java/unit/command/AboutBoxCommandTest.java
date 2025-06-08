package unit.command;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jabberpoint.command.AboutBoxCommand;
import jabberpoint.command.context.CommandContext;
import jabberpoint.command.context.DefaultCommandContext;
import jabberpoint.ui.DialogService;

class AboutBoxCommandTest {

    @Mock
    private DefaultCommandContext mockContext;
    
    @Mock
    private DialogService mockDialogService;

    private AboutBoxCommand aboutBoxCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        aboutBoxCommand = new AboutBoxCommand();
    }

    @Test
    void testExecute() {
        when(mockContext.getDialogs()).thenReturn(mockDialogService);

        aboutBoxCommand.execute(mockContext);

        verify(mockDialogService).showAboutBox();
    }
} 