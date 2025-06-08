package jabberpoint.command;

import jabberpoint.command.context.CommandContext;
import jabberpoint.command.context.DefaultCommandContext;

/**
 * Command to show the about box
 */
public class AboutBoxCommand implements Command {
    @Override
    public void execute(CommandContext context) {
        ((DefaultCommandContext) context).getDialogs().showAboutBox();
    }
} 