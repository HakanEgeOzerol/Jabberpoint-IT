package jabberpoint.presentation;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import jabberpoint.constants.Constants;

/** <p>Style is for Indent, Color, Font and Leading.</p>
 * <p>Direct relation between style-number and item-level:
 * in Slide style if fetched for an item
 * with style-number as item-level.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Style {
	private static Map<Integer, Style> styles = new HashMap<>();
	
	private int indent;
	private Color color;
	private Font font;
	private int fontSize;
	private int leading;

	public static void createStyles() {
		styles.clear();
		registerStyle(0, new Style(0, Color.red, 48, 20));
		registerStyle(1, new Style(20, Color.blue, 40, 10));
		registerStyle(2, new Style(50, Color.black, 36, 10));
		registerStyle(3, new Style(70, Color.black, 30, 10));
		registerStyle(4, new Style(90, Color.black, 24, 10));
	}
	
	public static void registerStyle(int level, Style style) {
		styles.put(level, style);
	}

	public static Style getStyle(int level) {
		if (styles.containsKey(level)) {
			return styles.get(level);
		}
			return styles.values().stream()
			.reduce((first, second) -> second)
			.orElse(styles.get(0));
	}

	public Style(int indent, Color color, int points, int leading) {
		this.indent = indent;
		this.color = color;
		font = new Font(Constants.UI.STYLE_FONTNAME, Font.BOLD, fontSize=points);
		this.leading = leading;
	}

	public String toString() {
		return "["+ indent + "," + color + "; " + fontSize + " on " + leading +"]";
	}

	public Font getFont(float scale) {
		return font.deriveFont(fontSize * scale);
	}

	public int getIndent() {
		return indent;
	}

	public Color getColor() {
		return color;
	}

	public Font getFont() {
		return font;
	}

	public int getFontSize() {
		return fontSize;
	}

	public int getLeading() {
		return leading;
	}
}
