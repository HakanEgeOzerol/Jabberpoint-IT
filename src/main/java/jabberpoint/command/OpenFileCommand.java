package jabberpoint.command;

import jabberpoint.Presentation;
import jabberpoint.SlideViewerFrame;
import jabberpoint.Accessor;
import jabberpoint.XMLAccessor;

import java.io.IOException;
import javax.swing.JOptionPane;

public class OpenFileCommand extends UICommand {
	protected static final String TESTFILE = "test.xml";
	protected static final String IOEX = "IO Exception: ";
	protected static final String LOADERR = "Load Error";
    
    public OpenFileCommand(Presentation presentation, SlideViewerFrame frame) {
        super(presentation, frame);
    }

    public void execute() {
        presentation.clear();
		Accessor xmlAccessor = new XMLAccessor();
	    try {
			xmlAccessor.loadFile(presentation, TESTFILE);
			presentation.setSlideNumber(0);
		} catch (IOException exc) {
			JOptionPane.showMessageDialog(frame, IOEX + exc, 
         	LOADERR, JOptionPane.ERROR_MESSAGE);
		}
		frame.repaint();
    }
}
