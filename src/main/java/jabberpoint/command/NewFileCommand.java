package jabberpoint.command;

import jabberpoint.command.context.CommandContext;
import jabberpoint.presentation.Presentation;
import jabberpoint.ui.SlideViewerFrame;

import java.awt.*;

public class NewFileCommand implements Command {

    @Override
    public void execute(CommandContext context) {
        if (context.hasReceiver(Presentation.class) && context.hasReceiver(Frame.class)) {
            Presentation presentation = context.getReceiver(Presentation.class);
            SlideViewerFrame frame = context.getReceiver(SlideViewerFrame.class);

            presentation.clear();
            frame.repaint();
        }
    }
}
