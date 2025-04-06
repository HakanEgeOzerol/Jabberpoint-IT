package jabberpoint.command;

import jabberpoint.presentation.Presentation;

/**
 * Command to exit the application
 */
public class ExitCommand extends PresentationCommand {
    
    /**
     * Constructor
     * @param presentation The presentation to operate on
     */
    public ExitCommand(Presentation presentation) {
        super(presentation);
    }
    
    /**
     * Execute the command to exit the application
     */
    @Override
    public void execute() {
        presentation.exit(0);
    }
} 