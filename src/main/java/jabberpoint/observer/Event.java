package jabberpoint.observer;

import jabberpoint.presentation.Slide;

public enum Event {
    SLIDE_CHANGED,
    PRESENTATION_LOADED,
    PRESENTATION_CLEARED,
    UNKNOWN;
    
    public static class PresentationState {
        private final Slide slide;
        private final int slideNumber;
        private final int totalSlides;
        private final String title;
        
        public PresentationState(Slide slide, int slideNumber, int totalSlides, String title) {
            this.slide = slide;
            this.slideNumber = slideNumber;
            this.totalSlides = totalSlides;
            this.title = title;
        }
        
        public Slide getSlide() { return slide; }
        public int getSlideNumber() { return slideNumber; }
        public int getTotalSlides() { return totalSlides; }
        public String getTitle() { return title; }
    }
} 