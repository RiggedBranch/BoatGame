
public class Dice {
	public  int rollNumber;
	
	public void setRollNumber() {
		rollNumber = (int) Math.floor(Math.random() * 6 + 1);
	}
	public int getRollNumber() {
		setRollNumber();
		return rollNumber;
	}
}
