package jabberpoint;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.HashMap;
import java.util.Map;

/** <p>This is the KeyController (KeyListener)</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 1.7 2023/03/28 Updated to use Command pattern
*/

public class KeyController extends KeyAdapter {
	private Presentation presentation; // Reference to the presentation
	private Map<Integer, Command> commands; // Map of key codes to commands

	public KeyController(Presentation p) {
		if (p == null) {
			throw new IllegalArgumentException("Presentation cannot be null");
		}
		presentation = p;
		commands = new HashMap<>();
		initializeCommands();
	}
	
	/**
	 * Initialize default key bindings
	 */
	private void initializeCommands() {
		// Initialize commands
		Command nextSlideCommand = new NextSlideCommand(presentation);
		Command prevSlideCommand = new PreviousSlideCommand(presentation);
		Command exitCommand = new ExitCommand(presentation);
		
		// Bind keys to commands
		commands.put(KeyEvent.VK_PAGE_DOWN, nextSlideCommand);
		commands.put(KeyEvent.VK_DOWN, nextSlideCommand);
		commands.put(KeyEvent.VK_ENTER, nextSlideCommand);
		commands.put((int)'+', nextSlideCommand);
		
		commands.put(KeyEvent.VK_PAGE_UP, prevSlideCommand);
		commands.put(KeyEvent.VK_UP, prevSlideCommand);
		commands.put((int)'-', prevSlideCommand);
		
		commands.put((int)'q', exitCommand);
		commands.put((int)'Q', exitCommand);
	}
	
	/**
	 * Add a key binding for a command
	 * @param keyCode The key code
	 * @param command The command to execute
	 * @throws IllegalArgumentException if command is null
	 */
	public void addBind(int keyCode, Command command) {
		if (command == null) {
			throw new IllegalArgumentException("Command cannot be null");
		}
		commands.put(keyCode, command);
	}
	
	/**
	 * Remove a key binding
	 * @param keyCode The key code to remove
	 * @return true if a binding was removed, false otherwise
	 */
	public boolean removeBind(Integer keyCode) {
		return commands.remove(keyCode) != null;
	}
	
	/**
	 * Execute a command
	 * @param command The command to execute
	 */
	public void executeCommand(Command command) {
		if (command != null) {
			command.execute();
		}
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		if (keyEvent == null) {
			return;
		}
		
		int keyCode = keyEvent.getKeyCode();
		Command command = commands.get(keyCode);
		
		if (command != null) {
			executeCommand(command);
		}
	}
}
