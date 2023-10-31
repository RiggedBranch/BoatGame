
public class Boat {
	private int boatPosition;
	
	//Constructor
	public Boat(int pos) {
		setBoatPosition(pos);
	}

	//Getter Setter
	public int getBoatPosition() {
		return boatPosition;
	}

	public void setBoatPosition(int BoatPosition) {
		this.boatPosition = BoatPosition;
	}

	@Override
	public String toString() {
		return String.format("Boat Position: %d", getBoatPosition());
	}
	
	
}
