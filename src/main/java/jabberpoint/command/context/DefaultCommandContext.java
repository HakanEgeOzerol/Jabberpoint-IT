package jabberpoint.command.context;

import jabberpoint.presentation.Presentation;
import jabberpoint.ui.DialogService;

import java.awt.*;
import java.util.HashMap;

/*
* Class that stores all receivers used by JabberPoint Commands
* If you wish to add a new set of commands or modify the receivers,
* define a new implementation of CommandContext with new receivers
 */

public class DefaultCommandContext implements CommandContext {

    private HashMap<Class<?>, Object> receivers;
    private Presentation presentation;
    private Frame frame; // it's easier to handle the ui from inside the commands
    private DialogService dialogs; //

    public DefaultCommandContext(Presentation presentation, Frame frame, DialogService dialogs) {
        this.receivers = new HashMap<>();
        this.presentation = presentation;
        this.frame = frame;
        this.dialogs = dialogs;
        this.addReceiver(Presentation.class, presentation);
        this.addReceiver(Frame.class, frame);
        this.addReceiver(DialogService.class, dialogs);
    }

    public Presentation getPresentation() {
        return presentation;
    }

    public Frame getFrame() {
        return frame;
    }

    public DialogService getDialogs() {
        return this.dialogs;
    }

    protected <T> void addReceiver(Class<T> clazz, T receiver) throws IllegalArgumentException {
        if (!clazz.isInstance(receiver)){ // inserting mismatching types should lead to an error, making this type-safe even at runtime
            throw new IllegalArgumentException("Receiver must match declared type " + clazz.getName());
        }
        this.receivers.put(clazz, receiver);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getReceiver(Class<T> clazz) {
        return (T) receivers.get(clazz); // unsafe if types are inserted incorrectly
    }
    @Override
    public boolean hasReceiver(Class<?> clazz) {
        return receivers.containsKey(clazz);
    }
}
