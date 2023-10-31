
public class Player {
	private String name;
	private Boat boat;
	private static Dice dice = new Dice();
	
	//Constructor
	public Player(String name) {
		setName(name);
		boat = new Boat(0);//All Boats start at first position 
	}
	
	//Getter Setter
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + "]";
	}

	// Other Methods
	public int rollDice() {
		int roll = dice.getRollNumber();
		boat.setBoatPosition(boat.getBoatPosition()+roll);
		return roll;
	}
	public int getPlayerBoatPos() {
		return boat.getBoatPosition();
	}
	public void setPlayerBoatPos(int pos) {
		boat.setBoatPosition(pos);
	}
}
