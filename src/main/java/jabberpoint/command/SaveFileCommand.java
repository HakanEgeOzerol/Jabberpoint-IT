package jabberpoint.command;

import java.io.IOException;
import javax.swing.JOptionPane;

import jabberpoint.accessor.Accessor;
import jabberpoint.accessor.XMLAccessor;
import jabberpoint.presentation.Presentation;
import jabberpoint.ui.SlideViewerFrame;

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
            showNoContentWarning();
            return;
        }
        
        try {
            savePresentation();
            showSaveSuccessMessage();
        } catch (IOException exc) {
            showErrorMessage(exc);
        }
    }
    
    /**
     * Save the presentation to a file
     * @throws IOException if there's an error saving the file
     */
    public void savePresentation() throws IOException {
        Accessor xmlAccessor = new XMLAccessor();
        xmlAccessor.saveFile(presentation, filename);
    }
    
    /**
     * Show a warning when there's no content to save
     */
    public void showNoContentWarning() {
        JOptionPane.showMessageDialog(frame, 
                "No presentation to save", 
                "Save Error", 
                JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Show a success message after saving
     */
    public void showSaveSuccessMessage() {
        JOptionPane.showMessageDialog(frame, 
                "Presentation saved to " + filename, 
                "Save Successful", 
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Show an error message if saving fails
     * @param exc The exception that occurred
     */
    public void showErrorMessage(IOException exc) {
        JOptionPane.showMessageDialog(frame, 
                "IO Exception: " + exc, 
                "Save Error", 
                JOptionPane.ERROR_MESSAGE);
    }
} 