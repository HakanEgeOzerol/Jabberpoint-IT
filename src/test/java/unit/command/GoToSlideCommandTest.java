package unit.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.junit.runner.RunWith;

import javax.swing.JOptionPane;

import jabberpoint.constants.Constants;
import jabberpoint.command.GoToSlideCommand;
import jabberpoint.presentation.Presentation;
import jabberpoint.ui.SlideViewerFrame;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JOptionPane.class)
@PowerMockIgnore({"javax.management.*", "javax.swing.*", "java.awt.*"})
public class GoToSlideCommandTest {
    private SlideViewerFrame mockFrame;
    private Presentation mockPresentation;
    private GoToSlideCommand goToSlideCommand;
    
    @BeforeEach
    public void setUp() {
        // Create mocks
        mockFrame = mock(SlideViewerFrame.class);
        mockPresentation = mock(Presentation.class);
        
        // Mock static JOptionPane
        PowerMockito.mockStatic(JOptionPane.class);
        
        // Create the command with the mocks
        goToSlideCommand = new GoToSlideCommand(mockFrame, mockPresentation);
    }
    
    @Test
    public void testConstructor() {
        // Test that the constructor properly sets the parameters
        assertNotNull(goToSlideCommand);
    }
    
    @Test
    public void testExecuteWithValidInput() {
        // Mock user input
        PowerMockito.when(JOptionPane.showInputDialog(Constants.Commands.PAGENR)).thenReturn("3");
        
        // Execute the command
        goToSlideCommand.execute();
        
        // Verify presentation.setSlideNumber was called with the correct value (0-based)
        verify(mockPresentation, times(1)).setSlideNumber(2);
    }
    
    @Test
    public void testExecuteWithInvalidInput() {
        // Mock invalid user input (non-numeric)
        PowerMockito.when(JOptionPane.showInputDialog(Constants.Commands.PAGENR)).thenReturn("not a number");
        
        // Execute the command
        goToSlideCommand.execute();
        
        // Verify presentation.setSlideNumber was never called
        verify(mockPresentation, never()).setSlideNumber(anyInt());
    }
    
    @Test
    public void testExecuteWithNullInput() {
        // Mock null input (user cancels dialog)
        PowerMockito.when(JOptionPane.showInputDialog(Constants.Commands.PAGENR)).thenReturn(null);
        
        // Execute the command
        goToSlideCommand.execute();
        
        // Verify presentation.setSlideNumber was never called
        verify(mockPresentation, never()).setSlideNumber(anyInt());
    }
    
    @Test
    public void testExecuteWithZeroPageNumber() {
        // Mock user input of "0"
        PowerMockito.when(JOptionPane.showInputDialog(Constants.Commands.PAGENR)).thenReturn("0");
        
        // Execute the command
        goToSlideCommand.execute();
        
        // Verify presentation.setSlideNumber was called with -1 (0-based)
        verify(mockPresentation, times(1)).setSlideNumber(-1);
    }
    
    @Test
    public void testExecuteWithNegativePageNumber() {
        // Mock user input of negative number
        PowerMockito.when(JOptionPane.showInputDialog(Constants.Commands.PAGENR)).thenReturn("-5");
        
        // Execute the command
        goToSlideCommand.execute();
        
        // Verify presentation.setSlideNumber was called with -6 (0-based)
        verify(mockPresentation, times(1)).setSlideNumber(-6);
    }
} 