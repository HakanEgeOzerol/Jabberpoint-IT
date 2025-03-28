package jabberpoint;

/**
 * Command to move to the next slide in the presentation
 */
public class NextSlideCommand extends PresentationCommand {
    
    /**
     * Constructor
     * @param presentation The presentation to operate on
     */
    public NextSlideCommand(Presentation presentation) {
        super(presentation);
    }
    
    /**
     * Execute the command to move to the next slide
     */
    @Override
    public void execute() {
        presentation.nextSlide();
    }
} 