package unit.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import javax.swing.JOptionPane;

import jabberpoint.accessor.Accessor;
import jabberpoint.presentation.Presentation;
import jabberpoint.command.SaveFileCommand;
import jabberpoint.ui.SlideViewerFrame;
import jabberpoint.accessor.XMLAccessor;

@RunWith(PowerMockRunner.class)
@PrepareForTest({JOptionPane.class, XMLAccessor.class})
@PowerMockIgnore({"javax.management.*", "javax.swing.*", "java.awt.*"})
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
        
        // Setup PowerMockito
        PowerMockito.mockStatic(JOptionPane.class);
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
        
        // Execute the command
        saveFileCommand.execute();
        
        // Verify that showMessageDialog was called with the warning message
        PowerMockito.verifyStatic(JOptionPane.class);
        JOptionPane.showMessageDialog(eq(mockFrame), 
            eq("No presentation to save"), 
            eq("Save Error"), 
            eq(JOptionPane.WARNING_MESSAGE));
    }
    
    @Test
    public void testExecuteWithPresentation() throws Exception {
        // Mock a non-empty presentation
        when(mockPresentation.getSize()).thenReturn(1);
        
        // Create and configure mock XMLAccessor
        XMLAccessor mockXMLAccessor = PowerMockito.mock(XMLAccessor.class);
        PowerMockito.whenNew(XMLAccessor.class).withNoArguments().thenReturn(mockXMLAccessor);
        
        // Execute the command
        saveFileCommand.execute();
        
        // Verify that saveFile was called with the right parameters
        verify(mockXMLAccessor).saveFile(mockPresentation, testFilename);
        
        // Verify that showMessageDialog was called with the success message
        PowerMockito.verifyStatic(JOptionPane.class);
        JOptionPane.showMessageDialog(eq(mockFrame), 
            eq("Presentation saved to " + testFilename), 
            eq("Save Successful"), 
            eq(JOptionPane.INFORMATION_MESSAGE));
    }
    
    @Test
    public void testExecuteWithIOException() throws Exception {
        // Mock a non-empty presentation
        when(mockPresentation.getSize()).thenReturn(1);
        
        // Create and configure mock XMLAccessor to throw an exception
        XMLAccessor mockXMLAccessor = PowerMockito.mock(XMLAccessor.class);
        PowerMockito.whenNew(XMLAccessor.class).withNoArguments().thenReturn(mockXMLAccessor);
        
        IOException testException = new IOException("Test exception");
        doThrow(testException).when(mockXMLAccessor).saveFile(any(Presentation.class), anyString());
        
        // Execute the command
        saveFileCommand.execute();
        
        // Verify that saveFile was called
        verify(mockXMLAccessor).saveFile(mockPresentation, testFilename);
        
        // Verify that showMessageDialog was called with the error message
        PowerMockito.verifyStatic(JOptionPane.class);
        JOptionPane.showMessageDialog(eq(mockFrame), 
            eq("IO Exception: " + testException), 
            eq("Save Error"), 
            eq(JOptionPane.ERROR_MESSAGE));
    }
} 