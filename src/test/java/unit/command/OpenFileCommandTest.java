package unit.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.junit.runner.RunWith;

import javax.swing.JOptionPane;
import java.io.IOException;

import jabberpoint.accessor.Accessor;
import jabberpoint.command.OpenFileCommand;
import jabberpoint.presentation.Presentation;
import jabberpoint.ui.SlideViewerFrame;
import jabberpoint.accessor.XMLAccessor;

@RunWith(PowerMockRunner.class)
@PrepareForTest({JOptionPane.class, XMLAccessor.class, Accessor.class})
public class OpenFileCommandTest {
    private SlideViewerFrame mockFrame;
    private Presentation mockPresentation;
    private OpenFileCommand openFileCommand;
    private XMLAccessor mockXMLAccessor;
    private final String testFilename = "test.xml";
    
    @BeforeEach
    public void setUp() throws Exception {
        // Create mocks
        mockFrame = mock(SlideViewerFrame.class);
        mockPresentation = mock(Presentation.class);
        mockXMLAccessor = PowerMockito.mock(XMLAccessor.class);
        
        // Mock static methods
        PowerMockito.mockStatic(JOptionPane.class);
        PowerMockito.mockStatic(Accessor.class);
        
        // Setup XMLAccessor mock
        PowerMockito.whenNew(XMLAccessor.class).withNoArguments().thenReturn(mockXMLAccessor);
        
        // Create the command with the mocks
        openFileCommand = new OpenFileCommand(mockFrame, mockPresentation, testFilename);
    }
    
    @Test
    public void testConstructor() {
        // Test that the constructor properly sets the parameters
        assertNotNull(openFileCommand);
    }
    
    @Test
    public void testExecuteWithFilename() throws IOException {
        // Execute the command with a valid filename
        openFileCommand.execute();
        
        // Verify presentation was cleared
        verify(mockPresentation, times(1)).clear();
        
        // Verify XML accessor was used to load the file
        verify(mockXMLAccessor, times(1)).loadFile(mockPresentation, testFilename);
        
        // Verify slide number was set if presentation has slides
        when(mockPresentation.getSize()).thenReturn(5);
        openFileCommand.execute();
        verify(mockPresentation, times(1)).setSlideNumber(0);
        
        // Verify frame was repainted
        verify(mockFrame, times(2)).repaint();
    }
    
    @Test
    public void testExecuteWithNullFilename() throws IOException {
        // Create a command with null filename
        OpenFileCommand nullFilenameCommand = new OpenFileCommand(mockFrame, mockPresentation, null);
        
        // Mock demo accessor
        Accessor mockDemoAccessor = mock(Accessor.class);
        when(Accessor.getDemoAccessor()).thenReturn(mockDemoAccessor);
        
        // Execute the command
        nullFilenameCommand.execute();
        
        // Verify presentation was cleared
        verify(mockPresentation, times(1)).clear();
        
        // Verify demo accessor was used
        verify(mockDemoAccessor, times(1)).loadFile(mockPresentation, "");
        
        // Verify frame was repainted
        verify(mockFrame, times(1)).repaint();
    }
    
    @Test
    public void testExecuteWithIOException() throws IOException {
        // Setup XML accessor to throw an IOException
        doThrow(new IOException("Test exception")).when(mockXMLAccessor).loadFile(any(Presentation.class), anyString());
        
        // Execute the command
        openFileCommand.execute();
        
        // Verify error dialog was shown
        PowerMockito.verifyStatic(JOptionPane.class);
        JOptionPane.showMessageDialog(eq(mockFrame), 
            contains("IO Exception"), 
            eq("Load Error"), 
            eq(JOptionPane.ERROR_MESSAGE));
        
        // Verify frame was still repainted
        verify(mockFrame, times(1)).repaint();
    }
    
    @Test
    public void testExecuteWithEmptyPresentation() throws IOException {
        // Setup an empty presentation
        when(mockPresentation.getSize()).thenReturn(0);
        
        // Execute the command
        openFileCommand.execute();
        
        // Verify slide number was not set
        verify(mockPresentation, never()).setSlideNumber(anyInt());
    }
} 