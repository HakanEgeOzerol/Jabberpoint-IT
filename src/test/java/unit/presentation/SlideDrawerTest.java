package unit.presentation;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jabberpoint.presentation.Slide;
import jabberpoint.presentation.SlideDrawer;
import jabberpoint.presentation.Style;
import jabberpoint.slideitem.SlideItem;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

class SlideDrawerTest {

    @Mock
    private Slide mockSlide;
    
    @Mock
    private Graphics mockGraphics;
    
    @Mock
    private ImageObserver mockImageObserver;
    
    @Mock
    private SlideItem mockSlideItem;

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
    void testDrawWithSlideItems() {
        when(mockSlide.getTitle()).thenReturn("Test Title");
        when(mockSlide.getSize()).thenReturn(2);
        when(mockSlide.getSlideItem(0)).thenReturn(mockSlideItem);
        when(mockSlide.getSlideItem(1)).thenReturn(mockSlideItem);
        when(mockSlideItem.getLevel()).thenReturn(1);
        when(mockSlideItem.getBoundingBox(any(), any(), anyFloat(), any()))
            .thenReturn(new Rectangle(0, 0, 100, 50));

        SlideDrawer.draw(mockSlide, mockGraphics, testArea, mockImageObserver);

        verify(mockSlide).getTitle();
        verify(mockSlide).getSize();
        verify(mockSlide).getSlideItem(0);
        verify(mockSlide).getSlideItem(1);
        verify(mockSlideItem, atLeast(3)).draw(anyInt(), anyInt(), anyFloat(), 
                                              any(Graphics.class), any(Style.class), 
                                              any(ImageObserver.class));
    }

    @Test
    void testDrawWithDifferentAreaSize() {
        Rectangle smallArea = new Rectangle(5, 10, 400, 300);
        when(mockSlide.getTitle()).thenReturn("Small Title");
        when(mockSlide.getSize()).thenReturn(1);
        when(mockSlide.getSlideItem(0)).thenReturn(mockSlideItem);
        when(mockSlideItem.getLevel()).thenReturn(0);
        when(mockSlideItem.getBoundingBox(any(), any(), anyFloat(), any()))
            .thenReturn(new Rectangle(0, 0, 50, 25));

        SlideDrawer.draw(mockSlide, mockGraphics, smallArea, mockImageObserver);

        verify(mockSlide).getTitle();
        verify(mockSlide).getSize();
        verify(mockSlide).getSlideItem(0);
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