
import java.util.Scanner;

import javax.swing.text.AbstractDocument.Content;

import java.io.*;
import java.net.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Consumer 
{
	public static void main(String[] args) throws IOException
	{
		InetAddress ip = InetAddress.getLocalHost();
		

		

		String mode =args[1];
		DataInputStream dis = null;
		DataOutputStream dos = null;
		Socket s=null;
		String content="hi";
	try{
		
		while(true){
			s = new Socket(ip, 5000);
			   //here we doing reciveing from 
		   dis = new DataInputStream(s.getInputStream());
		   dos = new DataOutputStream(s.getOutputStream());
   
		   Thread.sleep(500);
		   
		   dos.writeUTF(args [0]+" "+mode+" "+content);
   
   
		   content = dis.readUTF();
		   if(content.equals("over")){
			   break;
		   }
		   if(!content.equals("e123456789")){
		   System.out.println(content);
		   }
		   if (mode.equals("pr")){
			mode="r";
		   }
		   }
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
		s.close();
	}
}

