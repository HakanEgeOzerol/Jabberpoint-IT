package jabberpoint;

import javax.swing.JOptionPane;

import jabberpoint.command.Command;
import jabberpoint.command.OpenFileCommand;
import jabberpoint.command.context.DefaultCommandContext;
import jabberpoint.constants.Constants;
import jabberpoint.presentation.Presentation;
import jabberpoint.presentation.Style;
import jabberpoint.ui.DefaultDialogService;
import jabberpoint.ui.SlideViewerFrame;

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

	public static void main(String argv[]) {
		
		Style.createStyles();
		
		Presentation presentation = new Presentation();
		
		SlideViewerFrame viewerFrame = new SlideViewerFrame(Constants.Info.JABVERSION, presentation);

		DefaultCommandContext context = new DefaultCommandContext(presentation, viewerFrame, new DefaultDialogService(viewerFrame));

		// Load presentation data
		try {
			if (argv.length == 0) { // Load default demo presentation
				Command demoCommand = new OpenFileCommand("");
				demoCommand.execute(context);
			} else { // Load from file specified in command-line argument
				Command openCommand = new OpenFileCommand(argv[0]);
				openCommand.execute(context);
			}
			presentation.setSlideNumber(0);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null,
					Constants.ErrorMessages.IOERR + ex, Constants.ErrorMessages.JABERR,
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
