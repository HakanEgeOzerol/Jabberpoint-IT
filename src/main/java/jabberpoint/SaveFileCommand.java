package jabberpoint;

import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * Command to save a presentation to an XML file
 */
public class SaveFileCommand extends UICommand {
    private String filename;
    
    /**
     * Constructor
     * @param frame The SlideViewerFrame
     * @param presentation The presentation to operate on
     * @param filename The name of the file to save to
     */
    public SaveFileCommand(SlideViewerFrame frame, Presentation presentation, String filename) {
        super(frame, presentation);
        this.filename = filename;
    }
    
    /**
     * Execute the command to save a file
     */
    @Override
    public void execute() {
        if (presentation == null || presentation.getSize() == 0) {
            JOptionPane.showMessageDialog(frame, 
                    "No presentation to save", 
                    "Save Error", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            Accessor xmlAccessor = new XMLAccessor();
            xmlAccessor.saveFile(presentation, filename);
            JOptionPane.showMessageDialog(frame, 
                    "Presentation saved to " + filename, 
                    "Save Successful", 
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException exc) {
            JOptionPane.showMessageDialog(frame, 
                    "IO Exception: " + exc, 
                    "Save Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
} 