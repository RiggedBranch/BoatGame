import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BoatGame {
	private ArrayList<Player> players = new ArrayList<Player>();
	private River river = new River();
	ArrayList<RiverEvent> riverEvents;
	Score scoreBoard = new Score();
	
	public void startGame() {
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to Boat Game!");
		
		int choice = 0;
		
		while (choice != 4) {
			try {
				if (choice == 1) {
					System.out.println("Game is Restarting......");
				}
				else {
					getGameMenu();
					System.out.print("Choose Your Option [1-4]:");
					choice = input.nextInt();
				}
			} catch (InputMismatchException ime) {
				 System.out.println("ERROR, PLEASE ENTER A NUMBER.");
				 input.next();
				 continue;
			} 
			
			switch (choice) {
				case 0:
					break;
				
				case 1:
					players.removeAll(players);
					scoreBoard.readScores();
					playGame();
					scoreBoard.readScores();
					choice = replayGame();
					break;
					
				case 2:
					getInstructions();
					break;
				
				case 3:
					scoreBoard.readScores();
					break;
					
				case 4:
					System.out.println("Exiting......");
					break;
					
				default: 
					System.out.println("Error, Please Choose a Number in the List.");
			}
		}
		System.out.println("The Scoreboard has been printed out in the TopScores.txt.\nThank you for playing!! ");
	}
	
	public void createPlayers() {
		Scanner input = new Scanner(System.in);
		int numberPlayers = 0;
		
		while (numberPlayers == 0) {
			try {
				System.out.print("Number of Players: ");
				 numberPlayers= input.nextInt();
				 if (numberPlayers == 1) {
						System.out.println("SINGLEPLAYER MODE. A bot will be added to the game.");
						System.out.printf("Name of Player : ");
						players.add(new Player(input.next()));
						players.add(new Player("BOT"));
					}
					
				 else if (numberPlayers < 0) {
					 numberPlayers = 0;
					 System.out.println("Players cannot be less than 0.");
					 continue; 
				 }
				 else {
					for (int i =0; i < numberPlayers;i++) {
						System.out.printf("\nName of Player %d: ", (i+1));
						players.add(new Player(input.next()));
						}
					}
			} catch(InputMismatchException ime) {
				System.out.println("Error, Please Enter A Valid Number.");
				input.next();
			}
		}	
	}
	
	public void displayBoat() {
		for (Player p: players) {
			for (int pos = 0; pos < 100;pos++) {
				if (pos == p.getPlayerBoatPos()) 
					System.out.print("B");
				else {
					System.out.print(" ");
				}
		}
		System.out.println();
		}
		
	}

	public void playGame() {
		Scanner input = new Scanner(System.in);
		int numberTraps =100;
		int numberCurrents =100;
		
		while (numberTraps > 20 || numberTraps < 0) {
			try {
				System.out.print("Number of Traps [0-20]: ");
				numberTraps = input.nextInt();
				if (numberTraps > 20 || numberTraps < 0) {
					System.out.println("Please Input Within 0 to 20.");
				}
			} catch (InputMismatchException ime) {
				System.out.println("Error, Please Enter A Valid Number.");
				input.next();
			}
		}
		
		while (numberCurrents > 20 || numberCurrents < 0) {
			try {
				System.out.print("Number of Currents [0-20]: ");
				numberCurrents = input.nextInt();
				if (numberCurrents > 20 || numberCurrents < 0) {
					System.out.println("Please Input Within 0 to 20.");
				}
			} catch (InputMismatchException ime) {
				System.out.println("Error, Please Enter A Valid Number.");
				input.next();
			}
		}
		
		river.createRiver(numberTraps, numberCurrents);
		riverEvents = river.getEvents();
		
		createPlayers();
		
		river.displayRiver();
		displayBoat();
		river.displayRiver();
		
		boolean gameEnd = false;
		System.out.printf("%s rolls the dice first.\n",players.get(0).getName());
		int turn = 0;
		
		while (!gameEnd) {
			turn++;
			
			System.out.printf("\n==============================================Turn %2d===============================================\n",turn);
			
			for (Player player: players) {
				if (player.getName().equals("BOT")) {
					try {//Provides a minor delay for the bot to feel more interactive
						Thread.sleep(300);
						int dicevalue = player.rollDice();
						System.out.printf("\n%s rolls %d.\n",player.getName(), dicevalue);
					} catch (InterruptedException e) {
						System.out.println("I am interrupted. I am Sad Bot.");
					}
				}
				else {
					System.out.println("\nHit enter to continue...");
					input.nextLine();
					int diceValue = player.rollDice();
					System.out.printf("%s rolls %d.\n",player.getName(), diceValue);
				}
				
				for (int boatPosition = 0; boatPosition < (numberTraps + numberCurrents); boatPosition++) {
					if (player.getPlayerBoatPos() == riverEvents.get(boatPosition).getEventLocation()) {
						if (riverEvents.get(boatPosition) instanceof Current) {
							player.setPlayerBoatPos(player.getPlayerBoatPos() + riverEvents.get(boatPosition).getEventStrength());
							System.out.printf("Current. Player moves to the front by %d.\n", riverEvents.get(boatPosition).getEventStrength());
							boatPosition = 0;
						}
						else {
							player.setPlayerBoatPos(player.getPlayerBoatPos() - riverEvents.get(boatPosition).getEventStrength());
							System.out.printf("Trap. Player moves back by %d.\n", riverEvents.get(boatPosition).getEventStrength());
							boatPosition = 0;
						}
					}
				}	
			
				//Determines if the player wins or not
				if (player.getPlayerBoatPos() >= 99) {
					player.setPlayerBoatPos(99);
					river.displayRiver();
					displayBoat();
					river.displayRiver();
					System.out.printf("%s Wins in %d Turns!! \n",player.getName(),turn);
					scoreBoard.compareScores(player.getName(),turn);
					gameEnd = true;
					break;
				}
			
				// Just in case the player somehow got an unlucky trap that sets them out of bounds
				if (player.getPlayerBoatPos() < 0) {   
					player.setPlayerBoatPos(0);
				}
				river.displayRiver();
				displayBoat();
				river.displayRiver();
			}	
		}
	}
	
	//Gaming Controls Related Methods
	public void getGameMenu() {
		System.out.println("\n1. Play\n2. Instructions\n3. Scoreboard\n4. Exit");
	}
	
	public void getInstructions() {
		System.out.println("\n=============================================Instructions=======================================================");
		System.out.println("1.This is a Boat Game in which players race each other to the end.");
		System.out.println("2.Start the game off by declaring the number of traps, currents and players.");
		System.out.println("3.Traps will make your boat fall back whereas currents will push the boat forward.");
		System.out.println("4.Each trap and current has their own strength and the maximum displcement for both events are 6.");
		System.out.println("5.The first person to reach the end will win the game.");
		System.out.println("6.The scoreboard contains the top 5 players that used the least amount of turns to win. Bots are also included.");
		System.out.println("================================================================================================================");
	}
	
	public int replayGame() {
		char replay = 'y';
		Scanner input = new Scanner (System.in);
		
		while(replay != 'y' || replay != 'n') {
			System.out.print("Do you want to replay the game? [y/n]: ");
			replay = input.next().charAt(0);
			if (replay == 'y') {
				return 1;
			}
			else if (replay == 'n') {
				return 0;
			}
			else {
				System.out.print("Error, please input y for YES and n for NO.");
			}
		}
		return 0;
	}
}
