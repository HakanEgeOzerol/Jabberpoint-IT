package jabberpoint.command;

import jabberpoint.Presentation;
import jabberpoint.SlideViewerFrame;

public class NewFileCommand extends UICommand {
    public NewFileCommand(Presentation presentation, SlideViewerFrame frame) {
        super(presentation, frame);
    }

    public void execute() {
        presentation.clear();
		frame.repaint();
    }
    
}
