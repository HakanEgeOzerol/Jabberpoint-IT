package jabberpoint.command;

import jabberpoint.command.context.CommandContext;
import jabberpoint.presentation.Presentation;

public class NextSlideCommand implements Command {

    @Override
    public void execute(CommandContext context) {
        if (context.hasReceiver(Presentation.class)) {
            Presentation presentation = context.getReceiver(Presentation.class);
            presentation.nextSlide();
        }
    }
}
