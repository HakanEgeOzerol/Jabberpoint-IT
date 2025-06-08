package jabberpoint.command;

import jabberpoint.command.context.CommandContext;
import jabberpoint.presentation.Presentation;
import jabberpoint.ui.DialogService;


public class ExitCommand implements Command {
    public ExitCommand() {}
    
    /**
     * Execute the command to exit the application
     */
    @Override
    public void execute(CommandContext context) {
        if (context.hasReceiver(Presentation.class) && context.hasReceiver(DialogService.class)) {
            Presentation presentation = context.getReceiver(Presentation.class);
            DialogService dialogService = context.getReceiver(DialogService.class);
            boolean confirm = dialogService.confirmExit();
            if (confirm) {
                presentation.exit(0);
            }
        }
    }
} 