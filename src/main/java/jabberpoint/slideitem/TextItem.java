package jabberpoint.slideitem;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextLayout;
import java.awt.font.TextAttribute;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.List;
import java.util.ArrayList;

import jabberpoint.constants.Constants;
import jabberpoint.presentation.Style;

/** <p>A tekst item.</p>
 * <p>A TextItem has drawingfunctionality.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class TextItem extends SlideItem {
	private String text;
	
	// Using constant from Constants.TextItems
	// private static final String EMPTYTEXT = "No Text Given";

// a textitem of level level, with the text string
	public TextItem(int level, String string) {
		super(level);
		text = string;
	}

// an empty textitem
	public TextItem() {
		this(0, Constants.TextItems.EMPTYTEXT);
	}

// give the text
	public String getText() {
		return text == null ? "" : text;
	}

// geef de AttributedString voor het item
	public AttributedString getAttributedString(Style style, float scale) {
		AttributedString attrStr = new AttributedString(getText());
		attrStr.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, getText().length());
		return attrStr;
	}

// give the bounding box of the item
	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, 
			float scale, Style myStyle) {
		List<TextLayout> layouts = getLayouts(g, myStyle, scale);
		int xsize = 0, ysize = (int) (myStyle.getLeading() * scale);
		
		if (layouts != null) {
			for (TextLayout layout : layouts) {
				Rectangle2D bounds = layout.getBounds();
				if (bounds.getWidth() > xsize) {
					xsize = (int) bounds.getWidth();
				}
				ysize += layout.getAscent() + layout.getDescent() + layout.getLeading();
			}
		}
		
		return new Rectangle((int) (myStyle.getIndent() * scale), 0, xsize, ysize);
	}

// draw the item
	public void draw(int x, int y, float scale, Graphics g, 
			Style myStyle, ImageObserver o) {
		if (getText().length() == 0) {
			return;
		}
		
		List<TextLayout> layouts = getLayouts(g, myStyle, scale);
		Point pen = new Point(x + (int)(myStyle.getIndent() * scale),
					y + (int) (myStyle.getLeading() * scale));
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(myStyle.getColor());
		
		for (TextLayout layout : layouts) {
			pen.y += layout.getAscent();
			layout.draw(g2d, pen.x, pen.y);
			pen.y += layout.getDescent() + layout.getLeading();
		}
	}

	private List<TextLayout> getLayouts(Graphics g, Style s, float scale) {
		List<TextLayout> layouts = new ArrayList<TextLayout>();
		AttributedString attrStr = getAttributedString(s, scale);
    	Graphics2D g2d = (Graphics2D) g;
    	FontRenderContext frc = g2d.getFontRenderContext();
    	LineBreakMeasurer measurer = new LineBreakMeasurer(attrStr.getIterator(), frc);
    	float wrappingWidth = (Constants.UI.WIDTH - s.getIndent()) * scale;
    	while (measurer.getPosition() < getText().length()) {
    		TextLayout layout = measurer.nextLayout(wrappingWidth);
    		layouts.add(layout);
    	}
    	return layouts;
	}

	public String toString() {
		return "TextItem[" + getLevel()+","+getText()+"]";
	}

	@Override
	public String toXMLString() {
		return jabberpoint.constants.Constants.XML.TEXT + "\" " + 
			   jabberpoint.constants.Constants.XML.LEVEL + "=\"" + getLevel() + "\">" + 
			   getText();
	}
}
