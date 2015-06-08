import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Handler;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Server {

    private static final int PORT = 9001;

    private static HashSet<String> names = new HashSet<String>();
    private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
	
    public static void main(String[] args) throws Exception {
        System.out.println("The chat server is running.");
        ServerSocket listener = new ServerSocket(PORT);       
        InetAddress ip;        
        ip = InetAddress.getLocalHost();
		System.out.println("Current IP address : " + ip.getHostAddress());		
        try {
            while (true) {
                new Handler(listener.accept()).start();	
            }
        } finally {
            listener.close();
        }
    }
    
    private static class Handler extends Thread {
        private String name;
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;
        public Handler(Socket socket) {
            this.socket = socket;
        }
        
        public void run() {     	
            try {
                in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                while (true) {
                    out.println("SUBMITNAME");
                    name = in.readLine();
                    if (name == null) {
                        return;
                    }
                    synchronized (names) {
                        if (!names.contains(name)) {
                            names.add(name);
                            break;
                        }
                    }
                }
                out.println("NAMEACCEPTED");
                writers.add(out);
                while(true) {
                while (true) {
                    String input = in.readLine();
                    if (input == null) {
                    	return;
                    }
                    else {
                        for (PrintWriter writer : writers) {
                        	writer.println("MESSAGE " + name + ": " + input);
                        }
                        Trivia(name,input);
                    }
                }    
                }
            }
              catch (IOException e) {
                System.out.println(e);
            } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        
            finally {     	
                if (name != null) {
                    names.remove(name);
                }
                if (out != null) {
                    writers.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }    

    //--------------------------------------------------------------------------
    
	static private String[] Players = new String[100]; 
	static private int[] Score = new int[100]; 
	static private String[][] questions = 
		{{"BECKON!", 					"/beckon",			"My loving subjects, did I hear you beckon me? Your Mad King says..." },
		{"BOW!", 					"/bow",				"What a king says is law. And your Mad King says..."},
		{"CHEER!", 						"/cheer",				"It's far too quiet around here. Your Mad King says..."},
		{"COWER!", 					"/cower",				"You must all come to my daunting ferocity. Your Mad King says..."},
		{"DANCE! Dance for me!", 					"/dance",				"This Mad King Says..."},
		{"KNEEL! Kneel before your maniacal king!", 						"/kneel",				"Your Mad King says..."},
		{"LAUGH!", 					"/laugh",				"Let resounding waves of laughter shake the trees and doors! Your Mad King says..."},
		{"Say NO!", 					"/no",				"Shake your heads in disapproval, because someone somewhere has undoubtedly proposed something darkly treasonous! Your Mad King says..."},
		{"POINT!", 						"/point",				"I'm fond of assigning blame to other people, aren't you? Your Mad King says..."},
		{"PONDER!", 					"/ponder",				"I'll not have my subjects looking like dimwits—even if they are. Your Mad King says..."},
		{"SALUTE!", 					"/salute",				"Display a little respect before you lose your head. Your Mad King says..."},
		{"SHRUG!", 					"/shrug",				"Subjects! Do you wish to learn the secret to effective diplomacy? Show indifference to everything. Your Mad King says..."},
		{"SIT!", 					"/sit",				"Just looking at you standing around makes me feel tired. Your Mad King says..."},
		{"Go to SLEEP!", 					"/sleep",				"Oh, poor little subjects. Have my games worn you all out? Your Mad King says..."},
		{"Act SURPRISED!", 					"/surprised",				"Though I have always been a paragon of generosity, some of my subjects—ungrateful pigs—once expressed discontent. Your Mad King says..."},
		{"THREATEN!", 					"/threaten",				"My enemies stand before you. What do you do? Your Mad King says..."},
		{"WAVE!", 					"/wave",				"All my subjects crave my attention. Perhaps, I will notice you this time. Your mad King says..."},
		{"Say YES!", 						"/yes",				"Are you enjoying yourselves? Your Mad King says..."},};
    	static private String host = "Mad King Thorn";
    	static private String correct = host + " approves of ";
    	static private String incorrect = host + " is not amused by ";
    	static public boolean isRunning=false;
    	static public boolean ready=false;
    	static private int total=5;
    	static public int count=0;
    	static public int number;
    	
    	public static void Trivia(String name, String input) throws InterruptedException{
    		addPlayer(name);
    		if(isRunning==false){
    			if (input.equals("/madkingsays") && isRunning==false){
    				placeBlock();
    				resetGame();
    				startGame();
                	Thread.sleep(1000);
    				for (PrintWriter writer : writers) {
                    	writer.println("MESSAGE " + host + ": " + "My subjects! It is time for you-know-what to start!");
    				}
                	Thread.sleep(3000);
    				for (PrintWriter writer : writers) {
                    	writer.println("MESSAGE " + host + ": " + "Mad King Says!");
    				}
                	Thread.sleep(2000);
    				for (PrintWriter writer : writers) {
                    	writer.println("MESSAGE " + host + ": " + "Ready? If not, that's unfortunate for you, because the game begins...");
                    }
                	Thread.sleep(rand(5000,10000));
    				for (PrintWriter writer : writers) {
                    	writer.println("MESSAGE " + host + ": " + "NOW!!!");
                    }
    				number=rand(0,17);
                	Thread.sleep(1000);
    				for (PrintWriter writer : writers) {
                    	writer.println("MESSAGE " + host + ": " + questions[number][2]);
                    }
    				Thread.sleep(5000);
    				for (PrintWriter writer : writers) {
                	writer.println("MESSAGE " + host + ": " + questions[number][0]);
    				}
                	liftBlock();
    				return;
    			}
    			else{
    				return;
    			}
    		}
    		if(isRunning!=false){
    			if(input.equalsIgnoreCase(questions[number][1])){
    				placeBlock();
    				count++;
    				number=rand(0,17);
    				addScore(name);
    				if (count<total){
        				for (PrintWriter writer : writers) {
                        	writer.println("MESSAGE " + host + ": " + questions[number][2]);
                        }
        				Thread.sleep(5000);
        				for (PrintWriter writer : writers) {
                        	writer.println("MESSAGE " + host + ": " + questions[number][0]);
        				}
        				liftBlock();
    					return;
    				}
    				else {
                    	Thread.sleep(1000); 
        				for (PrintWriter writer : writers) {
                        	writer.println("MESSAGE " + host + ": " + "That was interesting, for a time. You did as well as could be expected.");
        				}
                    	Thread.sleep(5000); 
        				for (PrintWriter writer : writers) {
                        	writer.println("MESSAGE " + host + ": " + "But I tire of this. Good riddance, my faithful subjects!");
        				}
        				for (PrintWriter writer : writers) {
        					for (int i=0; i<100; i++){
        		    			  if(Players[i]!=null){
        		    				  writer.println("MESSAGE " + "Score" + ": " + Players[i] + " " + Score[i]);
        		    			  }
        					}
        				}
        				resetGame();
    					return;
    				}
    			}
    			else{
    				return;
    			}
    		}
    		else {
    			return;
    		}
    	}
    	
    	public static int rand(int min, int max) {										// Conjures random numbers within the specified range
    	    Random random = new Random();
    	    int randomNum = random.nextInt((max - min) + 1) + min;
    	    return randomNum;
    	}
    	
    	public static int addPlayer(String name){
    	first: for (int i=0; i<100; i++){
    			  if(Players[i]==name){
    				  return 0;
    			  }
    			  else if(Players[i]==null){
    				  Players[i]=name;
    				  return 0;
    			  }
    			}
    		return 0;
    	}
    	
    	private static void addScore(String name){
    	int i;
    	for (i=0; i<100; i++){
    			  if(Players[i].equals(name)){
    				  Score[i]++;
    				  return;
    			  }
    		}
    	}
    	
    	private static void liftBlock() {
    		ready=true;
    	}
    	
    	private static void placeBlock() {
    		ready=false;
    	}
    	
    	private static void startGame() {
    		isRunning=true;
    	}
    	
    	private static boolean isReady(){
    		return ready;
    	}
    	
    	private static void resetGame() {
    		isRunning=false;
    		count=0;
    		for (int i=0; i<100; i++){
    			 Players[i]=null;
    			 Score[i]=0;
    			 i++;
    		}
    	}
}

