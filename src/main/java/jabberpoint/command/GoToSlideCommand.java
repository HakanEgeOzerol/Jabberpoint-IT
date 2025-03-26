package jabberpoint.command;

import javax.swing.JOptionPane;
import jabberpoint.Presentation;
import jabberpoint.SlideViewerFrame;

public class GoToSlideCommand extends UICommand {
	protected static final String PAGENR = "Page number?";
    
    public GoToSlideCommand(Presentation presentation, SlideViewerFrame frame) {
        super(presentation, frame);
    }

    public void execute() {
        String pageNumberStr = JOptionPane.showInputDialog((Object)PAGENR);
		int pageNumber = Integer.parseInt(pageNumberStr);
		presentation.setSlideNumber(pageNumber - 1);
    }
    
}
