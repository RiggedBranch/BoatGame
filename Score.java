import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;

public class Score {
	private Scanner input;
	private Formatter output;
	File scoresFile = new File("TopScores.txt");
	
	public void loadScores() {
		if (scoresFile.length() == 0) {
				setDefaultHighScores();
		}
	}
	
	public void setDefaultHighScores() {
		try {//Default ScoreBoard Holder
			output = new Formatter("TopScores.txt");
			writeScores("Wilson", 30);
			writeScores("Maia",31);
			writeScores("Deric", 32);
			writeScores("NAHW", 33);
			writeScores("Kong", 35);

			if (output != null) {
				output.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File Cannot Be Created");
			System.exit(0);
		}
	}
	
	public void readScores() {
		loadScores();
		try {
			input = new Scanner(scoresFile);
			int i = 1;
			System.out.println("+-----------------------------+");
			System.out.println("|          Scoreboard         |");
			System.out.println("+--+---------------+----------+");
			System.out.println("|No|           Name|     Score|");
			System.out.println("+--+---------------+----------+");
			
			while (input.hasNext()) {
				String name = input.next();
				int score =input.nextInt();
				System.out.printf("|%2d|%15s|%10d|\n",i,name,score);
				i++;
			}
			if (input != null) {
				System.out.println("+--+---------------+----------+");
				input.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("ERROR FINDING FILE");
			System.exit(0);
		}
	}
	
	public void writeScores(String name, int score) {
		output.format("%10s%10d\n", name, score);
	}
	
	public void compareScores(String playername, int playerscore) {
		loadScores();
		try {
			input = new Scanner(scoresFile);

			String[] name = new String[5] ;
			int[] score = new int[5];
			int i =0;
			while (input.hasNext()) {//Can Use Map Instead
				name[i] = input.next();
				score[i] = input.nextInt();	
				i++;
			}
			if (input != null) {
				input.close();
			}
			for (int x=0; x < 5; x++) {
				if (playerscore <= score[x]) {
					for(int y=4; y != x; y--) {
						score[y]= score[y-1];
						name[y]= name[y-1];
					}
					score[x]= playerscore;
					name[x] = playername;
					break;	
					}
				}
			output = new Formatter("TopScores.txt");
			for (int y=0; y < score.length; y++) {
				writeScores(name[y], score[y]);
			}
			if (output != null) {
				output.close();
			}

		} catch (FileNotFoundException e) {
			System.out.println("ERROR FINDING FILE");
			System.exit(0);
		}
	}
}
