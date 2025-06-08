package unit.presentation;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jabberpoint.presentation.Slide;
import jabberpoint.presentation.SlideDrawer;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

class SlideDrawerTest {

    @Mock
    private Slide mockSlide;
    
    @Mock
    private Graphics2D mockGraphics;
    
    @Mock
    private ImageObserver mockImageObserver;

    private Rectangle testArea;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testArea = new Rectangle(10, 20, 800, 600);
    }

    @Test
    void testDrawWithEmptySlide() {
        when(mockSlide.getTitle()).thenReturn("Test Title");
        when(mockSlide.getSize()).thenReturn(0);

        SlideDrawer.draw(mockSlide, mockGraphics, testArea, mockImageObserver);

        verify(mockSlide).getTitle();
        verify(mockSlide).getSize();
        verify(mockSlide, never()).getSlideItem(anyInt());
    }

    @Test
    void testDrawWithNullTitle() {
        when(mockSlide.getTitle()).thenReturn(null);
        when(mockSlide.getSize()).thenReturn(0);

        SlideDrawer.draw(mockSlide, mockGraphics, testArea, mockImageObserver);

        verify(mockSlide).getTitle();
        verify(mockSlide).getSize();
    }
} 