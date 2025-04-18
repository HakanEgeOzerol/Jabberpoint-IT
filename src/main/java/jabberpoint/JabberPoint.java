package jabberpoint;

import javax.swing.JOptionPane;

import jabberpoint.command.Command;
import jabberpoint.command.OpenFileCommand;
import jabberpoint.constants.Constants;
import jabberpoint.presentation.Presentation;
import jabberpoint.presentation.Style;
import jabberpoint.ui.SlideViewerFrame;

import java.io.IOException;

/** JabberPoint Main Program
 * <p>This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License).
 * Please read it. Your use of the software constitutes acceptance
 * of the terms in the COPYRIGHT.txt file.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 1.7 2023/03/28 Updated to reflect new architecture
 */

public class JabberPoint {
	// Using constants from Constants.ErrorMessages and Constants.Info
	// protected static final String IOERR = "IO Error: ";
	// protected static final String JABERR = "Jabberpoint Error ";
	// protected static final String JABVERSION = "Jabberpoint 1.7 - Updated Architecture";

	/** The Main Program */
	public static void main(String argv[]) {
		
		// Initialize the styles
		Style.createStyles();
		
		// Create the presentation model
		Presentation presentation = new Presentation();
		
		// Create the main window and connect it to the presentation
		SlideViewerFrame viewerFrame = new SlideViewerFrame(Constants.Info.JABVERSION, presentation);
		
		// Load presentation data
		try {
			if (argv.length == 0) { // Load default demo presentation
				Command demoCommand = new OpenFileCommand(viewerFrame, presentation, "");
				demoCommand.execute();
			} else { // Load from file specified in command-line argument
				Command openCommand = new OpenFileCommand(viewerFrame, presentation, argv[0]);
				openCommand.execute();
			}
			// Set starting slide
			presentation.setSlideNumber(0);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,
					Constants.ErrorMessages.IOERR + ex, Constants.ErrorMessages.JABERR,
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
