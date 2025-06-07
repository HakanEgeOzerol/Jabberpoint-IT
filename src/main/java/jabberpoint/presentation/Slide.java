package jabberpoint.presentation;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Vector;

import jabberpoint.slideitem.SlideItem;
import jabberpoint.slideitem.TextItem;

/** <p>A slide. This class has a drawing functionality.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Slide {
	protected String title;
	protected Vector<SlideItem> items;

	public Slide() {
		items = new Vector<SlideItem>();
	}

	public void append(SlideItem anItem) {
		items.addElement(anItem);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String newTitle) {
		title = newTitle;
	}

	public void append(int level, String message) {
		append(new TextItem(level, message));
	}

	public SlideItem getSlideItem(int number) {
		return (SlideItem)items.elementAt(number);
	}

	public Vector<SlideItem> getSlideItems() {
		return items;
	}

	public int getSize() {
		return items.size();
	}

	public void draw(Graphics g, Rectangle area, ImageObserver view) {
		SlideDrawer.draw(this, g, area, view);
	}
}
