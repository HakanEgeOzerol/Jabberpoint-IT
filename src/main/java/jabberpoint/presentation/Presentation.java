package jabberpoint.presentation;

import java.util.ArrayList;

import jabberpoint.observer.Event;
import jabberpoint.observer.Publisher;


/**
 * <p>Presentation maintains the slides in the presentation.</p>
 * <p>There is only instance of this class.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 * @version 1.7 2023/03/28 Updated to implement Publisher interface
 */

public class Presentation extends Publisher {
	private String showTitle;
	private ArrayList<Slide> showList = null;
	private int currentSlideNumber = -1;

	public Presentation() {
		super();
		clear();
	}

	public int getSize() {
		return showList.size();
	}

	public String getTitle() {
		return showTitle;
	}

	public void setTitle(String nt) {
		showTitle = nt;
	}

	public int getSlideNumber() {
		return currentSlideNumber;
	}

	public void setSlideNumber(int number) {
		if (number < getSize() && number >= 0) {
			currentSlideNumber = number;
			notifySubscribers(Event.SLIDE_CHANGED, 
				new Event.PresentationState(getCurrentSlide(), currentSlideNumber, getSize(), showTitle));
		}
	}

	public void prevSlide() {
		if (currentSlideNumber > 0) {
			setSlideNumber(currentSlideNumber - 1);
	    }
	}

	public void nextSlide() {
		if (currentSlideNumber < (showList.size()-1)) {
			setSlideNumber(currentSlideNumber + 1);
		}
	}

	public void clear() {
		showList = new ArrayList<Slide>();
		currentSlideNumber = -1;
		notifySubscribers(Event.PRESENTATION_CLEARED, 
			new Event.PresentationState(null, -1, 0, showTitle));
	}

	public void append(Slide slide) {
		showList.add(slide);
	}

	public Slide getSlide(int number) {
		if (number < 0 || number >= getSize()){
			return null;
	    }
		return showList.get(number);
	}

	public Slide getCurrentSlide() {
		return getSlide(currentSlideNumber);
	}

	public void exit(int n) {
		System.exit(n);
	}
}
