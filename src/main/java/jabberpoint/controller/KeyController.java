package jabberpoint.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.HashMap;
import java.util.Map;

import jabberpoint.command.Command;
import jabberpoint.command.context.CommandContext;

public class KeyController extends KeyAdapter {
	private final Map<Integer, Class<? extends Command>> commandClasses;
	private final CommandContext commandContext;

	public KeyController(CommandContext commandContext) {
		this.commandContext = commandContext;
		this.commandClasses = new HashMap<>();
	}

	public void addBind(int keyCode, Class<? extends Command> commandClass) {
		if (commandClass == null) {
			throw new IllegalArgumentException("Command class cannot be null");
		}
		commandClasses.put(keyCode, commandClass);
	}

	public boolean removeBind(Integer keyCode) {
		return commandClasses.remove(keyCode) != null;
	}

	private void executeCommandForKey(int keyCode) {
		Class<? extends Command> commandClass = commandClasses.get(keyCode);
		if (commandClass != null) {
			try {
				// Instantiate a new command instance via reflection
				Command command = commandClass.getDeclaredConstructor().newInstance();
				command.execute(commandContext);
			} catch (Exception e) {
				e.printStackTrace(); // handle instantiation errors here
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		if (keyEvent == null) return;
		executeCommandForKey(keyEvent.getKeyCode());
	}
}
