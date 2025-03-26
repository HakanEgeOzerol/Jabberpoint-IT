package jabberpoint.command;

import jabberpoint.Presentation;

public class QuitCommand extends PresentationCommand {
    public QuitCommand(Presentation presentation) {
        super(presentation);
    }
    public void execute() {
        this.presentation.exit(0);
    }
}
