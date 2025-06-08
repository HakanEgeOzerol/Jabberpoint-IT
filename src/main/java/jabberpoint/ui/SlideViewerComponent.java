package jabberpoint.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JFrame;

import jabberpoint.constants.Constants;
import jabberpoint.observer.Event;
import jabberpoint.observer.Publisher;
import jabberpoint.observer.Subscriber;
import jabberpoint.presentation.Presentation;
import jabberpoint.presentation.Slide;


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
		
	private Slide slide;
	private int maxSlides;
	private int slideNumber;
	private Font labelFont;
	private JFrame frame;

	private static final long serialVersionUID = 227L;

	public SlideViewerComponent(JFrame frame) {
		setBackground(Constants.UI.BGCOLOR); 
		labelFont = new Font(Constants.UI.FONTNAME, Constants.UI.FONTSTYLE, Constants.UI.FONTHEIGHT);
		this.frame = frame;
	}

	public Dimension getPreferredSize() {
		return new Dimension(Constants.UI.WIDTH, Constants.UI.HEIGHT);
	}
	
	@Override
	public void update(Event event, Object data) {
		if (data instanceof Event.PresentationState) {
			Event.PresentationState state = (Event.PresentationState) data;
			
			if (event == Event.SLIDE_CHANGED) {
				this.slide = (Slide) state.getSlide();
				this.slideNumber = state.getSlideNumber();
				this.maxSlides = state.getTotalSlides();
				
			} else if (event == Event.PRESENTATION_CLEARED) {
				this.slide = null;
				this.slideNumber = -1;
				this.maxSlides = 0;
			}
			
			repaint();
			frame.setTitle(state.getTitle());
		}
	}

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
