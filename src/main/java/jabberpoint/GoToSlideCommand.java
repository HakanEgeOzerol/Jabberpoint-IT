package jabberpoint;

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
        String pageNumberStr = javax.swing.JOptionPane.showInputDialog(Constants.Commands.PAGENR);
		try {
			int pageNumber = Integer.parseInt(pageNumberStr);
			// Presentation methods expect 0-based slide numbers
            presentation.setSlideNumber(pageNumber - 1);
		} catch (NumberFormatException ex) {
			// Ignore if not a valid number
		}
        
    }
} 