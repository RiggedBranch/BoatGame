import java.util.ArrayList;

public class River {
	private int numberTraps;//From User
	private int numberCurrents;//From User
	private ArrayList<String> boatRiver = new ArrayList<String>();
	private ArrayList<RiverEvent> events= new ArrayList<RiverEvent>();
	
	//for loop create river flow, current, trap
	public void createRiver(int traps, int currents) {
		boatRiver.removeAll(boatRiver);
		for (int i= 0; i < 100; i++) {
			setnumberTraps(traps);
			setnumberCurrents(currents);
			events.removeAll(events);
			boatRiver.add("~");
		}
		
		int loop =0;
		//Trap[Can Use For Loop Here instead but still requires a else statement if used]
		while (loop < getnumberTraps()) {
			int possibleLocation = (int) Math.floor(Math.random() * 98 + 1);
			if (boatRiver.get(possibleLocation) == "~") {
				events.add(new Trap(possibleLocation));
				boatRiver.set(possibleLocation, "#");
				loop++;
			}
		}
		loop = 0;
		
		//Current
		while (loop < getnumberCurrents()) {
			int possibleLocation = (int) Math.floor(Math.random() * 98 + 1);
			if (boatRiver.get(possibleLocation) == "~") {
				events.add(new Current(possibleLocation));
				boatRiver.set(possibleLocation, "C");
				loop++;
			}
		}
		
	}
	public ArrayList<RiverEvent> getEvents(){
		return events;
	}
	
	public void setnumberTraps(int numberTraps) {
		this.numberTraps = numberTraps;
	}
	public void setnumberCurrents(int numberCurrents) {
		this.numberCurrents = numberCurrents;
	}
	//Display River
	public void displayRiver() {
		for (int i = 0 ; i < 100; i++) {
			System.out.print(boatRiver.get(i));
		}
		System.out.println();
	}
	public int getnumberTraps() {
		return numberTraps;
	}
	public int getnumberCurrents() {
		return numberCurrents;
	}

}
