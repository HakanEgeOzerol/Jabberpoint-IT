package jabberpoint.command;
import jabberpoint.Presentation;
import jabberpoint.SlideViewerFrame;

public abstract class UICommand extends PresentationCommand {
    protected SlideViewerFrame frame;

    public UICommand(Presentation presentation, SlideViewerFrame frame) {
        super(presentation);
        this.frame = frame;
    }
}
