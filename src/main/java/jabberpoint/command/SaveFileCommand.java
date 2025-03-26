package jabberpoint.command;

import jabberpoint.Presentation;
import jabberpoint.SlideViewerFrame;
import jabberpoint.Accessor;
import jabberpoint.XMLAccessor;

import java.io.IOException;
import javax.swing.JOptionPane;

public class SaveFileCommand extends UICommand {
	protected static final String SAVEFILE = "dump.xml";
	protected static final String IOEX = "IO Exception: ";
	protected static final String SAVEERR = "Save Error";

    public SaveFileCommand(Presentation presentation, SlideViewerFrame frame) {
        super(presentation, frame);
    }

    public void execute() {
        Accessor xmlAccessor = new XMLAccessor();
		try {
			xmlAccessor.saveFile(presentation, SAVEFILE);
		} catch (IOException exc) {
			JOptionPane.showMessageDialog(frame, IOEX + exc, 
			SAVEERR, JOptionPane.ERROR_MESSAGE);
		}
    }
}
