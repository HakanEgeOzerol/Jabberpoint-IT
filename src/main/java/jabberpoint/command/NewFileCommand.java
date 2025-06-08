package jabberpoint.command;

import jabberpoint.command.context.CommandContext;
import jabberpoint.presentation.Presentation;
import jabberpoint.ui.SlideViewerFrame;

public class NewFileCommand implements Command {

    @Override
    public void execute(CommandContext context) {
        if (context.hasReceiver(Presentation.class) && context.hasReceiver(SlideViewerFrame.class)) {
            Presentation presentation = context.getReceiver(Presentation.class);
            SlideViewerFrame frame = context.getReceiver(SlideViewerFrame.class);

            presentation.clear();
            frame.repaint();
        }
    }
}
