package jabberpoint.command;

import java.io.IOException;
import javax.swing.JOptionPane;

import jabberpoint.accessor.Accessor;
import jabberpoint.accessor.XMLAccessor;
import jabberpoint.presentation.Presentation;
import jabberpoint.ui.SlideViewerFrame;

/**
 * Command to open an XML file and load its contents into the presentation
 */
public class OpenFileCommand extends UICommand {
    private String filename;
    
    /**
     * Constructor
     * @param frame The SlideViewerFrame
     * @param presentation The presentation to operate on
     * @param filename The name of the file to open
     */
    public OpenFileCommand(SlideViewerFrame frame, Presentation presentation, String filename) {
        super(frame, presentation);
        this.filename = filename;
    }
    
    /**
     * Execute the command to open and load a file
     */
    @Override
    public void execute() {
        presentation.clear();
        
        try {
            if (filename == null || filename.isEmpty()) {
                // Load demo presentation if no filename provided
                Accessor.getDemoAccessor().loadFile(presentation, "");
            } else {
                // Load specified XML file
                Accessor xmlAccessor = new XMLAccessor();
                xmlAccessor.loadFile(presentation, filename);
            }
            
            // Set initial slide if presentation is not empty
            if (presentation.getSize() > 0) {
                presentation.setSlideNumber(0);
            }
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(frame, 
                    "IO Exception: " + exc, 
                    "Load Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
        
        frame.repaint();
    }
} 