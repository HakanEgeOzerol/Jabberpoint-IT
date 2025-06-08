package jabberpoint.command;

import jabberpoint.accessor.Accessor;
import jabberpoint.accessor.XMLAccessor;
import jabberpoint.command.context.CommandContext;
import jabberpoint.presentation.Presentation;
import jabberpoint.ui.DialogService;
import jabberpoint.constants.Constants;

import java.io.IOException;

public class OpenFileCommand implements Command {
    private String filename;

    public OpenFileCommand() {
        // no external file name in this implementation
        filename = Constants.Commands.TESTFILE;
    }

    public OpenFileCommand(String filename) {
        this.filename = filename;
    }

    @Override
    public void execute(CommandContext context) {
        if (!context.hasReceiver(Presentation.class)) {
            return;
        }

        Presentation presentation = context.getReceiver(Presentation.class);

        presentation.clear();

        try {
            if (filename.isEmpty()) {
                Accessor.getDemoAccessor().loadFile(presentation, "");
            } else {
                new XMLAccessor().loadFile(presentation, filename);
            }

            if (presentation.getSize() > 0) {
                presentation.setSlideNumber(0);
            }
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
