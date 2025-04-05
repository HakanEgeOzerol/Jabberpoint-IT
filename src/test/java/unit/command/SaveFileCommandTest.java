package unit.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.io.IOException;
import javax.swing.JOptionPane;

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
    private SaveFileCommand saveFileCommand;
    private final String testFilename = "test.xml";
    
    @BeforeEach
    public void setUp() {
        // Create mocks
        mockFrame = mock(SlideViewerFrame.class);
        mockPresentation = mock(Presentation.class);
        
        // Create the command with the mocks
        saveFileCommand = new SaveFileCommand(mockFrame, mockPresentation, testFilename);
    }
    
    @Test
    public void testConstructor() {
        // Test that the constructor properly sets the parameters
        assertNotNull(saveFileCommand);
    }
    
    @Test
    public void testExecuteWithEmptyPresentation() {
        // Mock an empty presentation
        when(mockPresentation.getSize()).thenReturn(0);
        
        // Replace JOptionPane with a mock to avoid headless issues
        try (var mockedStatic = mockStatic(JOptionPane.class)) {
            // Execute the command
            saveFileCommand.execute();
            
            // Verify the expected dialog was shown
            mockedStatic.verify(() -> 
                JOptionPane.showMessageDialog(
                    eq(mockFrame), 
                    eq("No presentation to save"), 
                    eq("Save Error"), 
                    eq(JOptionPane.WARNING_MESSAGE)
                )
            );
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
        
        // Test with mocked static JOptionPane
        try (var mockedStatic = mockStatic(JOptionPane.class)) {
            // Execute the command
            saveFileCommand.execute();
            
            // Verify the expected message was shown
            mockedStatic.verify(() -> 
                JOptionPane.showMessageDialog(
                    eq(mockFrame), 
                    eq("Presentation saved to " + testFilename), 
                    eq("Save Successful"), 
                    eq(JOptionPane.INFORMATION_MESSAGE)
                )
            );
        }
    }
    
    @Test
    public void testExecuteWithIOException() throws Exception {
        // Mock a non-empty presentation
        when(mockPresentation.getSize()).thenReturn(1);
        
        // Use a fake XMLAccessor for testing that throws an exception
        XMLAccessor mockAccessor = mock(XMLAccessor.class);
        IOException testException = new IOException("Test exception");
        doThrow(testException).when(mockAccessor).saveFile(any(Presentation.class), anyString());
        
        // Use PowerMockito to mock the XMLAccessor constructor
        try (var mockedConstructor = mockConstruction(XMLAccessor.class, (mock, context) -> {
            when(mock.toString()).thenReturn("Mocked XMLAccessor");
            // Set up the mock to throw the exception when saveFile is called
            doThrow(testException).when(mock).saveFile(any(Presentation.class), anyString());
        })) {
            // Test with mocked static JOptionPane
            try (var mockedStatic = mockStatic(JOptionPane.class)) {
                // Execute the command
                saveFileCommand.execute();
                
                // Verify the expected error message was shown
                mockedStatic.verify(() -> 
                    JOptionPane.showMessageDialog(
                        eq(mockFrame), 
                        eq("IO Exception: " + testException), 
                        eq("Save Error"), 
                        eq(JOptionPane.ERROR_MESSAGE)
                    )
                );
            }
        }
    }
} 