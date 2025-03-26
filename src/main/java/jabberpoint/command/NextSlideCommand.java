package jabberpoint.command;

import jabberpoint.Presentation;

public class NextSlideCommand extends PresentationCommand {
    public NextSlideCommand(Presentation presentation) {
        super(presentation);
    }

    public void execute() {
        this.presentation.nextSlide();
    }
}
