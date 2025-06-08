package jabberpoint.command;

import jabberpoint.command.context.CommandContext;
import jabberpoint.presentation.Presentation;

/**
 * Command to move to the previous slide in the presentation.
 */
public class PreviousSlideCommand implements Command {

    @Override
    public void execute(CommandContext context) {
        if (context.hasReceiver(Presentation.class)) {
            Presentation presentation = context.getReceiver(Presentation.class);
            presentation.prevSlide();
        }
    }
}
