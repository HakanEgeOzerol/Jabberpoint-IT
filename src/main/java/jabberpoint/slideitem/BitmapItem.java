package jabberpoint.slideitem;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.InputStream;
import javax.imageio.ImageIO;

import jabberpoint.constants.Constants;
import jabberpoint.presentation.Style;

import java.io.IOException;

/**
 * <p>The class for a Bitmap item</p>
 * <p>Bitmap items have the responsibility to draw themselves.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
*/

public class BitmapItem extends SlideItem {
	private BufferedImage bufferedImage;
	private String imageName;
	
	// Using constants from Constants.ErrorMessages
	// protected static final String FILE = "File ";
	// protected static final String NOTFOUND = " not found";

	/**
	 * Creates a BitmapItem with given level and image name
	 * @param level The level of the item
	 * @param name The name of the image file
	 */
	public BitmapItem(int level, String name) {
		super(level);
		imageName = name;
		if (name != null) {
			bufferedImage = loadImage(name);
		}
	}

	/**
	 * Creates an empty BitmapItem
	 */
	public BitmapItem() {
		this(0, null);
	}

	/**
	 * Returns the name of the image file
	 */
	public String getName() {
		return imageName;
	}

	/**
	 * Loads an image from the resources directory
	 * @param name The name of the image file
	 * @return The loaded BufferedImage or null if loading fails
	 */
	protected BufferedImage loadImage(String name) {
		try {
			InputStream imageStream = getClass().getResourceAsStream("/images/" + name);
			if (imageStream == null) {
				throw new IOException(Constants.ErrorMessages.FILE + name + Constants.ErrorMessages.NOTFOUND);
			}
			return ImageIO.read(imageStream);
		} catch (IOException e) {
			System.err.println(Constants.ErrorMessages.FILE + name + Constants.ErrorMessages.NOTFOUND);
			return null;
		}
	}

	/**
	 * Returns the bounding box of the image
	 */
	@Override
	public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle) {
		if (bufferedImage == null) {
			return new Rectangle((int) (myStyle.getIndent() * scale), 0, 0, 0);
		}
		return new Rectangle(
			(int) (myStyle.getIndent() * scale),
			0,
			(int) (bufferedImage.getWidth(observer) * scale),
			((int) (myStyle.getLeading() * scale)) + (int) (bufferedImage.getHeight(observer) * scale)
		);
	}

	/**
	 * Draws the image
	 */
	@Override
	public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer) {
		if (bufferedImage == null) {
			return;
		}
		int width = x + (int) (myStyle.getIndent() * scale);
		int height = y + (int) (myStyle.getLeading() * scale);
		g.drawImage(
			bufferedImage,
			width,
			height,
			(int) (bufferedImage.getWidth(observer) * scale),
			(int) (bufferedImage.getHeight(observer) * scale),
			observer
		);
	}

	@Override
	public String toString() {
		return "BitmapItem[" + getLevel() + "," + imageName + "]";
	}

	@Override
	public String toXMLString() {
		return jabberpoint.constants.Constants.XML.IMAGE + "\" " + 
			   jabberpoint.constants.Constants.XML.LEVEL + "=\"" + getLevel() + "\">" + 
			   getName();
	}
}
