package unit.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

import java.io.IOException;

import jabberpoint.accessor.Accessor;
import jabberpoint.command.OpenFileCommand;
import jabberpoint.presentation.Presentation;
import jabberpoint.ui.SlideViewerFrame;
import jabberpoint.accessor.XMLAccessor;
import unit.BaseTest;

public class OpenFileCommandTest extends BaseTest {
    private SlideViewerFrame mockFrame;
    private Presentation mockPresentation;
    private final String testFilename = "test.xml";
    
    @BeforeEach
    public void setUp() {
        // Create mocks
        mockFrame = mock(SlideViewerFrame.class);
        mockPresentation = mock(Presentation.class);
    }
    
    @Test
    public void testConstructor() {
        // Test that the constructor properly sets the parameters
        OpenFileCommand command = new OpenFileCommand(mockFrame, mockPresentation, testFilename);
        assertNotNull(command);
    }
    
    @Test
    public void testExecuteWithFilename() throws IOException {
        // Create a spy that overrides loadFile method to avoid actual file operations
        OpenFileCommand spyCommand = spy(new OpenFileCommand(mockFrame, mockPresentation, testFilename));
        doNothing().when(spyCommand).loadFile();
        
        // Execute the command
        spyCommand.execute();
        
        // Verify presentation was cleared
        verify(mockPresentation, times(1)).clear();
        
        // Verify loadFile method was called
        verify(spyCommand, times(1)).loadFile();
        
        // Verify frame was repainted
        verify(mockFrame, times(1)).repaint();
    }
    
    @Test
    public void testLoadFileWithValidFilename() throws IOException {
        // Use mockConstruction to mock XMLAccessor
        try (var mocked = mockConstruction(XMLAccessor.class)) {
            // Create a command with a valid filename
            OpenFileCommand command = new OpenFileCommand(mockFrame, mockPresentation, testFilename);
            
            // Call loadFile directly
            command.loadFile();
            
            // Verify XML accessor was used to load the file
            XMLAccessor mockXMLAccessor = mocked.constructed().get(0);
            verify(mockXMLAccessor, times(1)).loadFile(mockPresentation, testFilename);
        }
    }
    
    @Test
    public void testLoadFileWithNullFilename() throws IOException {
        // Create a command with null filename
        OpenFileCommand nullFilenameCommand = new OpenFileCommand(mockFrame, mockPresentation, null);
        
        // Create a mock demo accessor
        Accessor mockDemoAccessor = mock(Accessor.class);
        
        // Use MockedStatic to mock static Accessor.getDemoAccessor()
        try (var mockedStatic = mockStatic(Accessor.class)) {
            mockedStatic.when(Accessor::getDemoAccessor).thenReturn(mockDemoAccessor);
            
            // Call loadFile directly
            nullFilenameCommand.loadFile();
            
            // Verify demo accessor was used
            verify(mockDemoAccessor, times(1)).loadFile(mockPresentation, "");
        }
    }
    
    @Test
    public void testExecuteWithIOException() throws IOException {
        // Create a spy that throws IOException from loadFile
        OpenFileCommand spyCommand = spy(new OpenFileCommand(mockFrame, mockPresentation, testFilename));
        IOException testException = new IOException("Test exception");
        doThrow(testException).when(spyCommand).loadFile();
        doNothing().when(spyCommand).showErrorMessage(any(IOException.class));
        
        // Execute the command
        spyCommand.execute();
        
        // Verify error method was called with correct exception
        verify(spyCommand, times(1)).showErrorMessage(testException);
        
        // Verify frame was still repainted
        verify(mockFrame, times(1)).repaint();
    }
    
    @Test
    public void testExecuteWithEmptyPresentation() throws IOException {
        // Setup an empty presentation
        when(mockPresentation.getSize()).thenReturn(0);
        
        // Create a spy that doesn't actually load files
        OpenFileCommand spyCommand = spy(new OpenFileCommand(mockFrame, mockPresentation, testFilename));
        doNothing().when(spyCommand).loadFile();
        
        // Execute the command
        spyCommand.execute();
        
        // Verify slide number was not set
        verify(mockPresentation, never()).setSlideNumber(anyInt());
    }
    
    @Test
    public void testExecuteWithNonEmptyPresentation() throws IOException {
        // Setup a non-empty presentation
        when(mockPresentation.getSize()).thenReturn(1);
        
        // Create a spy that doesn't actually load files
        OpenFileCommand spyCommand = spy(new OpenFileCommand(mockFrame, mockPresentation, testFilename));
        doNothing().when(spyCommand).loadFile();
        
        // Execute the command
        spyCommand.execute();
        
        // Verify slide number was set to 0
        verify(mockPresentation, times(1)).setSlideNumber(0);
    }
} 