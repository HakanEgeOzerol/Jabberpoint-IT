package jabberpoint;

/**
 * Command to move to the previous slide in the presentation
 */
public class PreviousSlideCommand extends PresentationCommand {
    
    /**
     * Constructor
     * @param presentation The presentation to operate on
     */
    public PreviousSlideCommand(Presentation presentation) {
        super(presentation);
    }
    
    /**
     * Execute the command to move to the previous slide
     */
    @Override
    public void execute() {
        presentation.prevSlide();
    }
} 