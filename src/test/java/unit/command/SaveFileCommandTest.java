package unit.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.io.IOException;

import jabberpoint.accessor.Accessor;
import jabberpoint.presentation.Presentation;
import jabberpoint.command.SaveFileCommand;
import jabberpoint.ui.SlideViewerFrame;
import jabberpoint.accessor.XMLAccessor;

import java.util.ArrayList;
import java.util.Vector;

public class SaveFileCommandTest {
    private SlideViewerFrame mockFrame;
    private Presentation mockPresentation;
    private final String testFilename = "test.xml";
    
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
        SaveFileCommand command = new SaveFileCommand(mockFrame, mockPresentation, testFilename);
        assertNotNull(command);
    }
    
    @Test
    public void testExecuteWithEmptyPresentation() {
        // Mock an empty presentation
        when(mockPresentation.getSize()).thenReturn(0);
        
        // Create a spy that overrides the showNoContentWarning method
        SaveFileCommand spyCommand = spy(new SaveFileCommand(mockFrame, mockPresentation, testFilename));
        doNothing().when(spyCommand).showNoContentWarning();
        
        // Execute the command
        spyCommand.execute();
        
        // Verify the warning dialog was shown
        verify(spyCommand, times(1)).showNoContentWarning();
        
        // Verify that savePresentation was not called
        try {
            verify(spyCommand, never()).savePresentation();
        } catch (IOException e) {
            fail("IOException should not occur in this test");
        }
    }
    
    @Test
    public void testExecuteWithPresentation() throws Exception {
        // Mock a non-empty presentation with a slide
        when(mockPresentation.getSize()).thenReturn(1);
        
        // Add a slide to the presentation
        jabberpoint.presentation.Slide mockSlide = mock(jabberpoint.presentation.Slide.class);
        when(mockSlide.getTitle()).thenReturn("Test Slide");
        when(mockSlide.getSlideItems()).thenReturn(new Vector<>());
        when(mockPresentation.getSlide(0)).thenReturn(mockSlide);
        
        // Create a spy that overrides the dialog methods
        SaveFileCommand spyCommand = spy(new SaveFileCommand(mockFrame, mockPresentation, testFilename));
        doNothing().when(spyCommand).showSaveSuccessMessage();
        
        // Execute the command
        spyCommand.execute();
        
        // Verify savePresentation was called
        verify(spyCommand, times(1)).savePresentation();
        
        // Verify the success message was shown
        verify(spyCommand, times(1)).showSaveSuccessMessage();
    }
    
    @Test
    public void testExecuteWithIOException() throws Exception {
        // Mock a non-empty presentation
        when(mockPresentation.getSize()).thenReturn(1);
        
        // Create a spy that throws an IOException from savePresentation
        SaveFileCommand spyCommand = spy(new SaveFileCommand(mockFrame, mockPresentation, testFilename));
        IOException testException = new IOException("Test exception");
        doThrow(testException).when(spyCommand).savePresentation();
        doNothing().when(spyCommand).showErrorMessage(any(IOException.class));
        
        // Execute the command
        spyCommand.execute();
        
        // Verify the error message was shown with the correct exception
        verify(spyCommand, times(1)).showErrorMessage(testException);
    }
    
    @Test
    public void testSavePresentation() throws IOException {
        // This test directly tests the savePresentation method
        // Use a mockConstruction to create a mock XMLAccessor whenever the code creates one
        try (var mockedConstructor = mockConstruction(XMLAccessor.class)) {
            // Create the command
            SaveFileCommand command = new SaveFileCommand(mockFrame, mockPresentation, testFilename);
            
            // Call savePresentation directly
            command.savePresentation();
            
            // Verify the XMLAccessor was created and saveFile was called with right arguments
            XMLAccessor mockAccessor = mockedConstructor.constructed().get(0);
            verify(mockAccessor).saveFile(mockPresentation, testFilename);
        }
    }
} 