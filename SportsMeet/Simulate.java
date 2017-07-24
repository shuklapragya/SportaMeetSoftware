package assignment;

import java.io.*;
import java.util.regex.*;
public class Simulate {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		System.out.println(startTime);
		EventHeap ehp = new EventHeap();
		 BufferedReader BR = null;
		try{
		  BR = new BufferedReader(new FileReader("q4_small.txt"));
			String line;
			while(true){
			  line = BR.readLine();
			  if(line == null){
				  break;
			  }
			 String[] word = line.split("[,]\\s");
			 String[] w = word[0].split("\\s");
 
			 if( word.length==3 && w.length == 3){
					if(w[0].equals("ADD") && w[1].equals("PARTICIPANT"))
					{
						//ehp.addDefaultParticipant(int particiant_key, String partcipant_id, String participant_name, String participant_university)
						ehp.addDefaultParticipant(0,w[2],word[1], word[2]);
					}
					else if(w[0].equals("ADD") && w[1].equals("EVENT")){
						//insertEvent(int key, String id, String name, String des)
						ehp.insertEvent(0,w[2],word[1],word[2]);
						//System.out.println(ehp.removeMaxEvent());
					}
					else if(w[0].equals("UPDATE") && w[1].equals("SCORE")){
						//updateScoreOfParticipant (String p_id , String e_id ,int key)
						 ehp.updateScoreOfParticipant(w[2],word[1],Integer.parseInt(word[2]));
					}
				 }
				  
				  if( word.length==2 && w.length == 2){
					if(w[0].equals("ADD")){
						//addPToEvent (String p_id , String e_id)
						 ehp.addPToEvent(w[1], word[1]);
					}
				 }
				  
				 if(w.length == 3){
					if(w[0].equals("DELETE") && w[1].equals("EVENT")){
						//deletEventWithID(String e_id)
						 	ehp.deletEventWithID(w[2]);
						}
					else if(w[0].equals("DELETE") && w[1].equals("PARTICIPANT")){
						//deleteParticipantFromAllEvent(String p_id)
						 ehp.deleteParticipantFromAllEvent(w[2]);
					}
				 }
				 if( w.length == 4){
					if(w[0].equals("DELETE") && w[1].equals("EVENT") && w[2].equals("PARTICIPANT"))
						{
						//deleteParticipantInEvent(String p_id , String e_id)
						ehp.deleteParticipantInEvent(w[3],word[1]);
						} 
				 }
				  
				  if( w.length == 4){
					if(w[0].equals("TOP3") && w[2].equals("EVENT")){
						//printTopInEvent(String e_id)
						//System.out.println("Hello From In Event");
						ehp.printTopInEvent(w[3]);
					}
				 }
				  if( w.length == 1 && word.length==1){
					if(w[0].equals("TOP3")){
						ehp.TopInAll();
					}
				 }
//				 else{
//					 System.out.println("Exception No Commond Executed");
//				}
			}
			BR.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);
		
	}
}
	/* ADD EVENT E1, Jeopardy, Quizzing
	 * ADD PARTICIPANT P1, Vijay, BCCI
	 * ADD P1, E1
	 * UPDATE SCORE P00001, E0001, 7
	 * DELETE EVENT PARTICIPANT P00001, E0001
	 * DELETE PARTICIPANT P00001
	 * DELETE EVENT E0001
	 * TOP3 IN EVENT E0002
	 * TOP3
	 * 
	 * 
	 */
	
	 