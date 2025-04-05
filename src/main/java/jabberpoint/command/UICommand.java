package jabberpoint.command;

import jabberpoint.presentation.Presentation;
import jabberpoint.ui.SlideViewerFrame;

/**
 * Abstract base class for all UI-related commands
 * Provides a reference to the SlideViewerFrame and presentation
 */
public abstract class UICommand extends PresentationCommand {
    protected SlideViewerFrame frame;
    
    /**
     * Constructor that requires both frame and presentation
     * @param frame The SlideViewerFrame
     * @param presentation The presentation to operate on
     */
    public UICommand(SlideViewerFrame frame, Presentation presentation) {
        super(presentation);
        this.frame = frame;
    }
} 