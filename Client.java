package worldpeace;
import java.io.*;
import java.net.*;

class Client
{
	 public static void main(String argv[]) throws Exception
	 {
		  String sentence;
		  String modifiedSentence;
		  
		  Socket clientSocket = new Socket("162.209.109.193", 10021);//172.17.36.85
		  
		  DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		  
		  BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		  
		  
		  sentence = "2,sun kang, Xy891204,\nkzs0045@auburn.edu,334443480,3,4,4,5,6,67,8,aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";//inFromUser.readLine();
		  
		  sentence = sentence.replace('\n','\0');
		  
		  outToServer.writeBytes(sentence + '\n');
		  modifiedSentence = inFromServer.readLine();
		  System.out.println("FROM SERVER: " + modifiedSentence);
		  clientSocket.close();
	 }
}




