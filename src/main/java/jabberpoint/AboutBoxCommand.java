package jabberpoint;

/**
 * Command to show the about box
 */
public class AboutBoxCommand extends UICommand {
    
    /**
     * Constructor
     * @param frame The SlideViewerFrame
     * @param presentation The presentation to operate on
     */
    public AboutBoxCommand(SlideViewerFrame frame, Presentation presentation) {
        super(frame, presentation);
    }
    
    /**
     * Execute the command to show the about box
     */
    @Override
    public void execute() {
        AboutBox.show(frame);
    }
} 