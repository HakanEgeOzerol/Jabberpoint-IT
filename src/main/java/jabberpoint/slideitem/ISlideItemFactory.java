package jabberpoint.slideitem;

public interface ISlideItemFactory {
    /**
     * Creates a SlideItem with type specification
     */
    SlideItem createSlideItem(String type, int level, String content);
    
    /**
     * Creates a SlideItem with level and content
     */
    void createSlideItem(int level, String content);
} 