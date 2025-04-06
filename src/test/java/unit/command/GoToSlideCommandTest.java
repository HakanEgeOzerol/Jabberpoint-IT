package unit.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import org.mockito.Spy;

import jabberpoint.constants.Constants;
import jabberpoint.command.GoToSlideCommand;
import jabberpoint.presentation.Presentation;
import jabberpoint.ui.SlideViewerFrame;

public class GoToSlideCommandTest {
    private SlideViewerFrame mockFrame;
    private Presentation mockPresentation;
    
    @BeforeAll
    public static void setUpHeadlessMode() {
        // Ensure we're running in headless mode for GitHub Actions
        System.setProperty("java.awt.headless", "true");
    }
    
    @BeforeEach
    public void setUp() {
        // Create mocks
        mockFrame = mock(SlideViewerFrame.class);
        mockPresentation = mock(Presentation.class);
    }
    
    @Test
    public void testConstructor() {
        // Test that the constructor properly sets the parameters
        GoToSlideCommand command = new GoToSlideCommand(mockFrame, mockPresentation);
        assertNotNull(command);
    }
    
    @Test
    public void testExecuteWithValidInput() {
        // Create a spy of GoToSlideCommand that overrides getPageNumberInput
        GoToSlideCommand command = spy(new GoToSlideCommand(mockFrame, mockPresentation));
        doReturn("3").when(command).getPageNumberInput();
        
        // Execute the command
        command.execute();
        
        // Verify presentation.setSlideNumber was called with the correct value (0-based)
        verify(mockPresentation, times(1)).setSlideNumber(2);
    }
    
    @Test
    public void testExecuteWithInvalidInput() {
        // Create a spy of GoToSlideCommand that overrides getPageNumberInput
        GoToSlideCommand command = spy(new GoToSlideCommand(mockFrame, mockPresentation));
        doReturn("not a number").when(command).getPageNumberInput();
        
        // Execute the command
        command.execute();
        
        // Verify presentation.setSlideNumber was never called
        verify(mockPresentation, never()).setSlideNumber(anyInt());
    }
    
    @Test
    public void testExecuteWithNullInput() {
        // Create a spy of GoToSlideCommand that overrides getPageNumberInput
        GoToSlideCommand command = spy(new GoToSlideCommand(mockFrame, mockPresentation));
        doReturn(null).when(command).getPageNumberInput();
        
        // Execute the command
        command.execute();
        
        // Verify presentation.setSlideNumber was never called
        verify(mockPresentation, never()).setSlideNumber(anyInt());
    }
    
    @Test
    public void testExecuteWithZeroPageNumber() {
        // Create a spy of GoToSlideCommand that overrides getPageNumberInput
        GoToSlideCommand command = spy(new GoToSlideCommand(mockFrame, mockPresentation));
        doReturn("0").when(command).getPageNumberInput();
        
        // Execute the command
        command.execute();
        
        // Verify presentation.setSlideNumber was called with -1 (0-based)
        verify(mockPresentation, times(1)).setSlideNumber(-1);
    }
    
    @Test
    public void testExecuteWithNegativePageNumber() {
        // Create a spy of GoToSlideCommand that overrides getPageNumberInput
        GoToSlideCommand command = spy(new GoToSlideCommand(mockFrame, mockPresentation));
        doReturn("-5").when(command).getPageNumberInput();
        
        // Execute the command
        command.execute();
        
        // Verify presentation.setSlideNumber was called with -6 (0-based)
        verify(mockPresentation, times(1)).setSlideNumber(-6);
    }
    
    @Test
    public void testGoToSlideDirectly() {
        // Test the goToSlide method directly without UI interaction
        GoToSlideCommand command = new GoToSlideCommand(mockFrame, mockPresentation);
        
        // Call the method directly
        command.goToSlide("10");
        
        // Verify presentation.setSlideNumber was called with 9 (0-based)
        verify(mockPresentation, times(1)).setSlideNumber(9);
    }
} 