package jabberpoint.slideitem;

import java.util.HashMap;
import java.util.Map;

public class SlideItemFactory {
    private final Map<String, ISlideItemFactory> factoryRegistry;
    
    public SlideItemFactory() {
        this.factoryRegistry = new HashMap<>();
        registerFactory("text", new TextItemFactory());
        registerFactory("image", new BitmapItemFactory());
    }
    
    /**
     * Constructor for dependency injection
     */
    public SlideItemFactory(Map<String, ISlideItemFactory> factories) {
        this.factoryRegistry = new HashMap<>(factories);
    }
    
    /**
     * Register factory for a specific type
     */
    public void registerFactory(String type, ISlideItemFactory factory) {
        if (type == null || factory == null) {
            throw new IllegalArgumentException("Type and factory cannot be null");
        }
        factoryRegistry.put(type.toLowerCase(), factory);
    }
    
    /**
     * Creates a SlideItem with type specification
     */
    public SlideItem createSlideItem(String type, int level, String content) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        
        ISlideItemFactory factory = factoryRegistry.get(type.toLowerCase());
        if (factory == null) {
            throw new IllegalArgumentException("Unknown slide item type: " + type);
        }
        
        return factory.createSlideItem(type, level, content);
    }
    
    /**
     * Creates a SlideItem with level and content
     * Defaults to text type
     */
    public SlideItem createSlideItem(int level, String content) {
        return createSlideItem("text", level, content);
    }
} 