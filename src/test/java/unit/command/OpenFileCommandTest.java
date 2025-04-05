package unit.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import javax.swing.JOptionPane;
import java.io.IOException;

import jabberpoint.accessor.Accessor;
import jabberpoint.command.OpenFileCommand;
import jabberpoint.presentation.Presentation;
import jabberpoint.ui.SlideViewerFrame;
import jabberpoint.accessor.XMLAccessor;

public class OpenFileCommandTest {
    private SlideViewerFrame mockFrame;
    private Presentation mockPresentation;
    private OpenFileCommand openFileCommand;
    private final String testFilename = "test.xml";
    
    @BeforeEach
    public void setUp() {
        // Create mocks
        mockFrame = mock(SlideViewerFrame.class);
        mockPresentation = mock(Presentation.class);
        
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
        // Use mockConstruction to mock XMLAccessor
        try (var mocked = mockConstruction(XMLAccessor.class)) {
            // Execute the command with a valid filename
            openFileCommand.execute();
            
            // Verify presentation was cleared
            verify(mockPresentation, times(1)).clear();
            
            // Verify XML accessor was used to load the file
            XMLAccessor mockXMLAccessor = mocked.constructed().get(0);
            verify(mockXMLAccessor, times(1)).loadFile(mockPresentation, testFilename);
            
            // Verify frame was repainted
            verify(mockFrame, times(1)).repaint();
        }
    }
    
    @Test
    public void testExecuteWithNullFilename() throws IOException {
        // Create a command with null filename
        OpenFileCommand nullFilenameCommand = new OpenFileCommand(mockFrame, mockPresentation, null);
        
        // Create a mock demo accessor
        Accessor mockDemoAccessor = mock(Accessor.class);
        
        // Use MockedStatic to mock static Accessor.getDemoAccessor()
        try (var mockedStatic = mockStatic(Accessor.class)) {
            mockedStatic.when(Accessor::getDemoAccessor).thenReturn(mockDemoAccessor);
            
            // Execute the command
            nullFilenameCommand.execute();
            
            // Verify presentation was cleared
            verify(mockPresentation, times(1)).clear();
            
            // Verify demo accessor was used
            verify(mockDemoAccessor, times(1)).loadFile(mockPresentation, "");
            
            // Verify frame was repainted
            verify(mockFrame, times(1)).repaint();
        }
    }
    
    @Test
    public void testExecuteWithIOException() throws IOException {
        // Setup an IOException to be thrown
        IOException testException = new IOException("Test exception");
        
        // Use mockConstruction and mockStatic together
        try (var mocked = mockConstruction(XMLAccessor.class, (mock, context) -> {
            // Set up the mock to throw the exception when loadFile is called
            doThrow(testException).when(mock).loadFile(any(Presentation.class), anyString());
        })) {
            try (var mockedStatic = mockStatic(JOptionPane.class)) {
                // Execute the command
                openFileCommand.execute();
                
                // Verify error dialog was shown
                mockedStatic.verify(() -> JOptionPane.showMessageDialog(
                    eq(mockFrame), 
                    contains("IO Exception"), 
                    eq("Load Error"), 
                    eq(JOptionPane.ERROR_MESSAGE)));
                
                // Verify frame was still repainted
                verify(mockFrame, times(1)).repaint();
            }
        }
    }
    
    @Test
    public void testExecuteWithEmptyPresentation() throws IOException {
        // Setup an empty presentation
        when(mockPresentation.getSize()).thenReturn(0);
        
        // Use mockConstruction
        try (var mocked = mockConstruction(XMLAccessor.class)) {
            // Execute the command
            openFileCommand.execute();
            
            // Verify slide number was not set
            verify(mockPresentation, never()).setSlideNumber(anyInt());
        }
    }
} 