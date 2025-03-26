package jabberpoint.command;

import jabberpoint.Presentation;
import jabberpoint.SlideViewerFrame;
import jabberpoint.AboutBox;

public class AboutBoxCommand extends UICommand {
    public AboutBoxCommand(Presentation presentation, SlideViewerFrame frame) {
        super(presentation, frame);
    }

    public void execute() {
        AboutBox.show(frame);
    }
}
