package jabberpoint.command;

import jabberpoint.constants.Constants;
import jabberpoint.presentation.Presentation;
import jabberpoint.ui.SlideViewerFrame;

/**
 * Command to navigate to a specific slide in the presentation
 */
public class GoToSlideCommand extends UICommand {
    
    /**
     * Constructor
     * @param presentation The presentation to operate on
     * @param slideNumber The slide number to navigate to (0-based)
     */
    public GoToSlideCommand(SlideViewerFrame frame, Presentation presentation) {
        super(frame, presentation);
    }
    
    /**
     * Execute the command to go to a specific slide
     */
    @Override
    public void execute() {
        String pageNumberStr = getPageNumberInput();
        goToSlide(pageNumberStr);
    }
    
    /**
     * Shows the input dialog to get the page number from user
     * @return The page number as a string, or null if canceled
     */
    public String getPageNumberInput() {
        return javax.swing.JOptionPane.showInputDialog(Constants.Commands.PAGENR);
    }
    
    /**
     * Process the page number input and navigate to the slide if valid
     * @param pageNumberStr The page number as a string
     */
    public void goToSlide(String pageNumberStr) {
        try {
            int pageNumber = Integer.parseInt(pageNumberStr);
            // Presentation methods expect 0-based slide numbers
            presentation.setSlideNumber(pageNumber - 1);
        } catch (NumberFormatException ex) {
            // Ignore if not a valid number
        }
    }
} 