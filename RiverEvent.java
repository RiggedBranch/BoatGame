
public class RiverEvent {
	private int eventLocation;
	private int eventStrength;
	private final int MAX_STRENGTH = 6;
	
	//Constructor
	public RiverEvent(int loc) {
		setEventLocation(loc);
		setEventStrength();
	}
	
	//Getter Setter
	public int getEventLocation() {
		return eventLocation;
	}

	public void setEventLocation(int eventLocation) {
		this.eventLocation = eventLocation;
	}

	public int getEventStrength() {
		return eventStrength;
	}

	public void setEventStrength() {
		eventStrength = (int) Math.floor(Math.random() * MAX_STRENGTH + 1);
	}

}
