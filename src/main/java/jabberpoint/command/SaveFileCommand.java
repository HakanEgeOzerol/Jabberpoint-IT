package jabberpoint.command;

import jabberpoint.accessor.Accessor;
import jabberpoint.accessor.XMLAccessor;
import jabberpoint.command.context.CommandContext;
import jabberpoint.presentation.Presentation;
import jabberpoint.ui.DialogService;
import jabberpoint.constants.Constants;

import java.io.IOException;

public class SaveFileCommand implements Command {

    public SaveFileCommand() {}

    @Override
    public void execute(CommandContext context) {
        if (!context.hasReceiver(Presentation.class)) {
            return;
        }

        Presentation presentation = context.getReceiver(Presentation.class);
        String filename = Constants.Commands.SAVEFILE;

        if (presentation == null || presentation.getSize() == 0) {
            if (context.hasReceiver(DialogService.class)) {
                context.getReceiver(DialogService.class)
                        .showErrorMessage("No presentation to save");
            }
            return;
        }

        try {
            Accessor xmlAccessor = new XMLAccessor();
            xmlAccessor.saveFile(presentation, filename);

        } catch (IOException e) {
            if (context.hasReceiver(DialogService.class)) {
                context.getReceiver(DialogService.class)
                        .showErrorMessage(Constants.ErrorMessages.IOERR + e.getMessage());
            } else {
                e.printStackTrace();
            }
        }
    }
}
