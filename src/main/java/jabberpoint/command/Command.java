package jabberpoint.command;

import jabberpoint.command.context.CommandContext;

/*
* General Command interface
*/
public interface Command {
    void execute(CommandContext context);
}