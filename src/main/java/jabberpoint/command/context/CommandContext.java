package jabberpoint.command.context;

/*
* command context interface: implementations should store all necessary data for the commands to access
* a relatively flexible structure that permits any receivers to be stored with CommandContext implementations
* this is only type-safe as long as receivers are assigned to matching types (implementations must take care of this)
* allows only one receiver per type in this implementation
 */

public interface CommandContext {
    <T> T getReceiver(Class<T> clazz);
    boolean hasReceiver(Class<?> clazz);
}

