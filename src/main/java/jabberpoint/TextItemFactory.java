package jabberpoint;

public class TextItemFactory implements SlideItemFactory {
    @Override
    public SlideItem createSlideItem(String type, int level, String content) {
        if (type == null || !type.equals("text")) {
            throw new IllegalArgumentException("Invalid type for TextItemFactory: " + type);
        }
        if (content == null) {
            content = ""; // Empty string for null content
        }
        return new TextItem(level, content);
    }

    @Override
    public void createSlideItem(int level, String content) {
        // This void method could be used for logging or validation
        if (level < 0) {
            throw new IllegalArgumentException("Level cannot be negative");
        }
        new TextItem(level, content != null ? content : "");
    }
} 