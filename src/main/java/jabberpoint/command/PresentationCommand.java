package jabberpoint.command;

import jabberpoint.Presentation;

public abstract class PresentationCommand {
    protected Presentation presentation;

    public PresentationCommand(Presentation presentation) {
        this.presentation = presentation;
    }

    public abstract void execute();
}