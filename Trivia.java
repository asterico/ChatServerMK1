public class Trivia {
	static private String[][] Players = new String[100][2]; 
	static private String[][] questions = 
		{{"COWER!", 					"/cower"},
		{"DANCE!", 					"/dance"},
		{"CRY!", 						"/cry"},
		{"LAUGH!", 					"/laugh"},
		{"BOW!", 						"/bow"},};
	static private String host = "Mad King Thorn";
	static private String correct = host + " approves of ";
	static private String incorrect = host + " is not amused by ";
	static public boolean isRunning=false;
	static private int total=5;
	static public int count=0;
	
	public static String Trivia(String flag, String name, String s2, String input){
		String output = flag + name + s2 + input;
		addPlayer(name);
		if(isRunning==false){
			if (input.equals("/madkingsays") && isRunning==false){
				startGame();
				output = flag + host + s2 + "The game begins... NOW! " + questions[0][0];
				return output;
			}
			else{
				return output;
			}
		}
		if(isRunning!=false){
			if(input.equalsIgnoreCase(questions[count][1])){
				count++;
				if (count<total){
					output = flag + host + s2 + questions[count][0];
					return output;
				}
				else {
					output = flag + host + s2 + "HAHAHAHAHAHA";
					endGame();
					return output;
				}
			}
			else{
				return output;
			}
		}
		else {
			return output;
		}
	}
	
	public static void addPlayer(String name){
	first: for (int i=0; i<100; i++){
			  if(Players[i][0]!=null){
				  Players[i][0]=name;
				  break first;
			  }
			  else { i++; }
		}
	}
	
	private static void startGame() {
		isRunning=true;
	}
	
	private static void endGame() {
		isRunning=false;
		count=0;
		for (int i=0; i<100; i++){
			 Players[i][1]=null;
			 Players[i][1]=null;
			 i++;
		}
	}
	
}
