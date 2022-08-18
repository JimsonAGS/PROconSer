import java.util.Scanner;
import java.io.*;
import java.net.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
 
public class Producer
{
	public static void main(String[] args) throws IOException
	{
		InetAddress ip = InetAddress.getLocalHost();
		int port =5000;
		
		String mode="w";
		Scanner input = new Scanner(System.in);

		

		Socket s = null;
		DataOutputStream out =null;
		String line="";
		
		while (!line.equals("over"))
		{
			try
			{
				s = new Socket(ip, port);
				out = new DataOutputStream(s.getOutputStream());
				line = input.nextLine();
				out.writeUTF(args[0]+" "+mode+" "+line);
			}
			catch(IOException i)
			{
				System.out.println(i);
			}
		}
		
		try
		{
			input.close();
			out.close();
			s.close();
		}
		catch(IOException i)
		{
			System.out.println(i);
		}
	}
		
}

