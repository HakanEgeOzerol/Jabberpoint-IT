package jabberpoint.command;

import jabberpoint.presentation.Presentation;

/**
 * Abstract base class for all presentation-related commands
 * Provides a reference to the presentation being manipulated
 */
public abstract class PresentationCommand implements Command {
    protected Presentation presentation;
    
    /**
     * Constructor that requires a presentation
     * @param presentation The presentation to operate on
     */
    public PresentationCommand(Presentation presentation) {
        this.presentation = presentation;
    }
} 