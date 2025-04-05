package jabberpoint;

public class BitmapItemFactory implements SlideItemFactory {
    @Override
    public SlideItem createSlideItem(String type, int level, String content) {
        if (type == null || !type.equals("image")) {
            throw new IllegalArgumentException("Invalid type for BitmapItemFactory: " + type);
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Image path cannot be empty");
        }
        return new BitmapItem(level, content);
    }

    @Override
    public void createSlideItem(int level, String content) {
        // This void method could be used for logging or validation
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Image path cannot be empty");
        }
        new BitmapItem(level, content);
    }
} 