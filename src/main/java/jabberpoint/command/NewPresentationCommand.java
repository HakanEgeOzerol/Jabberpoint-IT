package jabberpoint.command;

import jabberpoint.presentation.Presentation;
import jabberpoint.ui.SlideViewerFrame;

/**
 * Command to create a new (empty) presentation
 */
public class NewPresentationCommand extends UICommand {
    
    /**
     * Constructor
     * @param frame The SlideViewerFrame
     * @param presentation The presentation to operate on
     */
    public NewPresentationCommand(SlideViewerFrame frame, Presentation presentation) {
        super(frame, presentation);
    }
    
    /**
     * Execute the command to create a new presentation
     */
    @Override
    public void execute() {
        presentation.clear();
        frame.repaint();
    }
} 