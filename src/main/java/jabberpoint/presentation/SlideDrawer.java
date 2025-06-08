package jabberpoint.presentation;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import jabberpoint.constants.Constants;
import jabberpoint.slideitem.SlideItem;
import jabberpoint.slideitem.TextItem;

public class SlideDrawer {
    
    public static void draw(Slide slide, Graphics g, Rectangle area, ImageObserver view) {
        float scale = calculateScale(area);
        int y = area.y;
        
        SlideItem slideItem = new TextItem(0, slide.getTitle());
        Style style = Style.getStyle(slideItem.getLevel());
        slideItem.draw(area.x, y, scale, g, style, view);
        y += slideItem.getBoundingBox(g, view, scale, style).height;
        
        for (int number = 0; number < slide.getSize(); number++) {
            slideItem = slide.getSlideItem(number);
            style = Style.getStyle(slideItem.getLevel());
            slideItem.draw(area.x, y, scale, g, style, view);
            y += slideItem.getBoundingBox(g, view, scale, style).height;
        }
    }
    
    private static float calculateScale(Rectangle area) {
        return Math.min(((float)area.width) / ((float)Constants.UI.WIDTH), 
                       ((float)area.height) / ((float)Constants.UI.HEIGHT));
    }
} 