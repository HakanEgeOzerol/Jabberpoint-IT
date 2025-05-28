package jabberpoint.slideitem;

public class SlideItemFactory {
    private final BitmapItemFactory bitmapFactory;
    private final TextItemFactory textFactory;
    
    public SlideItemFactory() {
        this.bitmapFactory = new BitmapItemFactory();
        this.textFactory = new TextItemFactory();
    }
    
    /**
     * Creates a SlideItem with type specification
     * Automatically routes to the appropriate factory based on type
     */
    public SlideItem createSlideItem(String type, int level, String content) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        
        switch (type.toLowerCase()) {
            case "text":
                return textFactory.createSlideItem(type, level, content);
            case "image":
                return bitmapFactory.createSlideItem(type, level, content);
            default:
                throw new IllegalArgumentException("Unknown slide item type: " + type);
        }
    }
    
    /**
     * Creates a SlideItem with level and content
     * Defaults to text type
     */
    public void createSlideItem(int level, String content) {
        textFactory.createSlideItem(level, content);
    }
} 