package jabberpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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

public class Presentation implements Publisher {
	private String showTitle; // title of the presentation
	private ArrayList<Slide> showList = null; // an ArrayList with Slides
	private int currentSlideNumber = -1; // the slidenummer of the current Slide
	private Map<Subscriber, Boolean> subscribers; // Subscribers to this presentation

	public Presentation() {
		subscribers = new HashMap<>();
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

	// give the number of the current slide
	public int getSlideNumber() {
		return currentSlideNumber;
	}

	// change the current slide number and notify subscribers
	public void setSlideNumber(int number) {
		if (number < getSize() && number >= 0) {
			currentSlideNumber = number;
			// Notify subscribers using the Observer pattern
			notifySubscribers(Event.SLIDE_CHANGED, getCurrentSlide());
		}
	}

	// go to the previous slide unless at the beginning of the presentation
	public void prevSlide() {
		if (currentSlideNumber > 0) {
			setSlideNumber(currentSlideNumber - 1);
	    }
	}

	// go to the next slide unless at the end of the presentation.
	public void nextSlide() {
		if (currentSlideNumber < (showList.size()-1)) {
			setSlideNumber(currentSlideNumber + 1);
		}
	}

	// Delete the presentation to be ready for the next one.
	public void clear() {
		showList = new ArrayList<Slide>();
		setSlideNumber(-1);
		notifySubscribers(Event.PRESENTATION_CLEARED, null);
	}

	// Add a slide to the presentation
	public void append(Slide slide) {
		showList.add(slide);
	}

	// Get a slide with a certain slidenumber
	public Slide getSlide(int number) {
		if (number < 0 || number >= getSize()){
			return null;
	    }
		return showList.get(number);
	}

	// Give the current slide
	public Slide getCurrentSlide() {
		return getSlide(currentSlideNumber);
	}

	public void exit(int n) {
		System.exit(n);
	}
	
	// Publisher interface methods
	
	@Override
	public void addSubscriber(Subscriber subscriber) {
		subscribers.put(subscriber, true);
	}
	
	@Override
	public Subscriber removeSubscriber(Subscriber subscriber) {
		if (subscribers.remove(subscriber) != null) {
			return subscriber;
		}
		return null;
	}
	
	@Override
	public void notifySubscribers(Event event, Object data) {
		for (Subscriber subscriber : subscribers.keySet()) {
			subscriber.update(event, data, this);
		}
	}
}
