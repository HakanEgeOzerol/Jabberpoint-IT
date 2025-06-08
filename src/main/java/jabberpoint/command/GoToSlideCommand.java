package jabberpoint.command;

import jabberpoint.command.context.CommandContext;
import jabberpoint.constants.Constants;
import jabberpoint.presentation.Presentation;
import jabberpoint.ui.DialogService;

public class GoToSlideCommand implements Command {

    @Override
    public void execute(CommandContext context) {
        if (context.hasReceiver(DialogService.class) && context.hasReceiver(Presentation.class)) {
            DialogService dialogService = context.getReceiver(DialogService.class);
            Presentation presentation = context.getReceiver(Presentation.class);

            String pageNumberStr = dialogService.getUserInput(Constants.Commands.PAGENR);
            goToSlide(presentation, pageNumberStr, dialogService);
        }
    }

    private void goToSlide(Presentation presentation, String pageNumberStr, DialogService dialogService) {
        try {
            int pageNumber = Integer.parseInt(pageNumberStr);
            presentation.setSlideNumber(pageNumber - 1); // zero-based index
        } catch (NumberFormatException ignored) {
            dialogService.showErrorMessage("Please enter a valid slide number.");
        }
    }
}
