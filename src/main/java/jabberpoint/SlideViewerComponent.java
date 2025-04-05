package jabberpoint;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JFrame;


/** <p>SlideViewerComponent is a graphical component that can show slides.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 1.7 2023/03/28 Updated to implement Subscriber interface
 */

public class SlideViewerComponent extends JComponent implements Subscriber {
		
	private Slide slide; // current slide
	private int maxSlides;
	private int slideNumber;

	private Font labelFont = null; // font for labels
	private JFrame frame = null;
	
	private static final long serialVersionUID = 227L;
	
	// Using constants from Constants.UI
	// private static final Color BGCOLOR = Color.white;
	// private static final Color COLOR = Color.black;
	// private static final String FONTNAME = "Dialog";
	// private static final int FONTSTYLE = Font.BOLD;
	// private static final int FONTHEIGHT = 10;
	// private static final int XPOS = 1100;
	// private static final int YPOS = 20;

	/**
	 * Constructor for the viewer component
	 * @param frame The frame in which the component is shown
	 */
	public SlideViewerComponent(JFrame frame) {
		setBackground(Constants.UI.BGCOLOR); 
		labelFont = new Font(Constants.UI.FONTNAME, Constants.UI.FONTSTYLE, Constants.UI.FONTHEIGHT);
		this.frame = frame;
	}

	/**
	 * Get the preferred size of the component
	 * @return The preferred dimension
	 */
	public Dimension getPreferredSize() {
		return new Dimension(Constants.UI.WIDTH, Constants.UI.HEIGHT);
	}
	
	/**
	 * Implementation of the Subscriber interface update method
	 * @param event The event that occurred
	 * @param data The data associated with the event (typically a Slide)
	 * @param publisher The publisher that sent the notification
	 */
	@Override
	public void update(Event event, Object data, Publisher publisher) {
		if (publisher instanceof Presentation) {
			Presentation presentation = (Presentation) publisher;
			
			if (event == Event.SLIDE_CHANGED && data instanceof Slide) {
				this.slide = (Slide) data;
				this.slideNumber = presentation.getSlideNumber();
				this.maxSlides = presentation.getSize();
				
			} else if (event == Event.PRESENTATION_CLEARED) {
				this.slide = null;
			}
			repaint();
			frame.setTitle(presentation.getTitle());
		}
	}

	/**
	 * Draw the slide
	 * @param g The Graphics object to draw on
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Constants.UI.BGCOLOR);
		g.fillRect(0, 0, getSize().width, getSize().height);
		if (this.slideNumber < 0 || slide == null) {
			return;
		}
		g.setFont(labelFont);
		g.setColor(Constants.UI.COLOR);
		g.drawString("Slide " + (1 + this.slideNumber) + " of " +
                 this.maxSlides, Constants.UI.XPOS, Constants.UI.YPOS);
		Rectangle area = new Rectangle(0, Constants.UI.YPOS, getWidth(), (getHeight() - Constants.UI.YPOS));
		slide.draw(g, area, this);
	}
}
