package worldpeace;
/* 
 * ServerTCP.java
 *
 * @author Kang Sun
 * 
 * A TCP Server is set to deal with client requests for Immigration WorldPeace Porject.
 *
 */

import java.io.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerTCP
{
   public static void main(String args[]) throws Exception
   {
      System.out.println("[NORMAL] Status: Running.");
      int clientNumber = 0;
      int port = 0;
      if(args.length == 0)  // Where is this args coming from?
      {
         System.out.println("[SEVERE] Error: Please specify a port in the command line.");
         System.exit(0);
      }
      if(Integer.decode(args[0]) >= 1 && Integer.decode(args[0]) <= 65535)
      {
         port = Integer.decode(args[0]);
         System.out.println("[NORMAL] Status: Port successfully selected (" + port + ")");
      }
      else
      {
         System.out.println("[SEVERE] Error in args: Invalid port number. Must be between 1 and 65535.");
         System.exit(0);
      }
      ServerSocket server = new ServerSocket(port); // Why does socket need to take a port as argument?
      try
      {
         System.out.println("[NORMAL] Status: Waiting for connections.");
         while(true)
         {
            //spawn new thread to handle each connection to allow for simultaneous
            //connections and therefore better response times
            
            new RequestHandler(server.accept(), clientNumber++).start();
         }
      }
      finally
      {
         server.close();
      }
   }
 

	private static ArrayList<String> TransferStringToArrayList(String target)
	{
                //String target="1, HelloAuburn, 123456, male, bcd2341@aubrn.edu, 3345564534, 5.5, 2, 3, 3, 4 ,5";
                String[] result = new String[20];
                ArrayList<String> resultSet = new ArrayList<String>();
                result = target.split(",");
                int i = 0;
                for(;i<result.length;i++)
                        resultSet.add(result[i].trim());

                //for(i=0;i<resultSet.size();i++)
                //       System.out.println(resultSet.get(i));
		return resultSet;
	}
  
   private static class RequestHandler extends Thread
   {
      private Socket socket;
      private int clientNumber;
      
      public RequestHandler(Socket socket, int clientNumber)
      {
         this.socket = socket;
         this.clientNumber = clientNumber;
         System.out.println("[NORMAL] Client " + clientNumber + ":Connected at " + socket + ".");
      }
      
      public void run()  // Automatically calling run()?
      {
         try
         {
	    //read data from client and write something back
	    BufferedReader inFromClient =
               new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
            String clientSentence = inFromClient.readLine();
            System.out.println("Received: " + clientSentence);


		ArrayList<String> result = TransferStringToArrayList(clientSentence);
		String returnString = String.valueOf(0);
		/*
		String returnString = "";
		for(int i=0;i<result.size();i++)
		{	
			returnString.concat(result.get(i));
			if(i!=result.size()-1)
				returnString = returnString.concat("-");
			//System.out.println(result.get(i));
		}
		*/
		/*
			String to integer	int foo = Integer.parseInt("1234");
			integer to String 	String.valueOf(number);
		*/
		
		if(result.size()!=0)
		{
			switch(Integer.parseInt(result.get(0)))
			{
				/*1 for register, 2 for log-in check, 3 for request service*/
				case 1:
					Register register = new Register();
					returnString = register.insertToUser(result);
				break;
				case 2: 
					Login login = new Login();
					returnString = login.isValidUser(result);
				break;
				case 3: 
					Request request = new Request();
					returnString = request.findHelp(result);
				break;
				default: System.out.println("Invalid type of request!"); break;
			}
		}
		
            outToClient.writeBytes(returnString);
	    //send the reply
            System.out.println("[NORMAL] Client " + clientNumber
               + ":Reply sent.");
            
         }
         
         catch(IOException e)
         {
            System.out.println("[SEVERE] Client " + clientNumber + ":Exception Error:" + e);
         }
         
         finally
         {
            try
            {
               socket.close();
            }
            catch(IOException e)
            {
               System.out.println("[SEVERE] Client " + clientNumber + ":Could not close socket.");
            }
         }
      }
      
   }
}
