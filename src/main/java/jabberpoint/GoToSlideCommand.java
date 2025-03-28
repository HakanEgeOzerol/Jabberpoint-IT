package jabberpoint;

/**
 * Command to navigate to a specific slide in the presentation
 */
public class GoToSlideCommand extends PresentationCommand {
    private int slideNumber;
    
    /**
     * Constructor
     * @param presentation The presentation to operate on
     * @param slideNumber The slide number to navigate to (0-based)
     */
    public GoToSlideCommand(Presentation presentation, int slideNumber) {
        super(presentation);
        this.slideNumber = slideNumber;
    }
    
    /**
     * Execute the command to go to a specific slide
     */
    @Override
    public void execute() {
        // Presentation methods expect 0-based slide numbers
        presentation.setSlideNumber(slideNumber);
    }
} 