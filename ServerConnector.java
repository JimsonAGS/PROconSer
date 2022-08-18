import java.io.*;
import java.net.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Scanner;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;
public class ServerConnector
{    
    public static void main(String[] args ) throws IOException 
    {
    	ServerSocket server=null;

        int port=5000;
        LinkedHashMap<String,String> save =new LinkedHashMap<>();
        LinkedHashMap<String,String> Psave =new LinkedHashMap<>();


    	try {
    	server=new ServerSocket(port);
        
    	server.setReuseAddress(true);
    	while(true)
    	{
        Socket client=server.accept(); 
        Multirunner clientSock= new Multirunner(client,save,Psave);
        new Thread(clientSock).start();
            }
    	}
        catch (IOException e) {
        e.printStackTrace();
        }
    	finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
    	}
    }
}

class Multirunner implements Runnable {
    private final Socket clientSocket;

    private final LinkedHashMap<String,String> Psave;
    private final LinkedHashMap<String,String> save;

    public Multirunner(Socket socket,LinkedHashMap<String,String> save,LinkedHashMap<String,String> Psave)
    {
        this.clientSocket = socket;
        this.save=save;
        this.Psave=Psave;

    }

    public void write(String top,StringTokenizer st){
                    String i="";
                    //prefect code
                    while(st.hasMoreTokens()){
                        if (save.containsKey(top)){
                            i=st.nextToken();
                            i=save.get(top)+" "+i+" ";
                            save.put(top,i);
                        }
                        else{
                            save.put(top,st.nextToken());
                        }
                    }
    }
    public void show(String top){
        try{
        
        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
        if (save.containsKey(top)){

            dos.writeUTF(save.get(top));

        } 

        else{

            while (!save.containsKey(top)){

                try{
                    Thread.sleep(500);
                }
                catch(Exception e){
                    System.out.println(e);
                }
            }
            dos.writeUTF(save.get(top));

        }
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    } 
/*
    public void backup(String str,String top,StringTokenizer st){

        try{
        InetAddress ip = InetAddress.getLocalHost();
        Socket socketForMangaer = new Socket(ip, 2324);
        DataOutputStream dos = new DataOutputStream(socketForMangaer.getOutputStream());
        dos.writeUTF(str);
        write(top,st);
        socketForMangaer.close();
        }
        catch(Exception e){

            System.out.println("Please start the managing server!!!");
        }
    }
    public void backup(String top,String mode){
        try{
            InetAddress ip = InetAddress.getLocalHost();
            Socket socketForMangaer = new Socket(ip, 2324);
            DataOutputStream dos = new DataOutputStream(socketForMangaer.getOutputStream());
            dos.writeUTF(top+" "+mode);
            socketForMangaer.close();
            
        }
        catch(Exception e){

        }

    }*/

    public void run()
    {
        try {
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
            String str = dis.readUTF();
            
            StringTokenizer st = new StringTokenizer(str); 
            String d="";
            String hi="";
            
				String top =st.nextToken();
                String mode =st.nextToken();
				if (mode.equals("w"))
                {

                    d=st.nextToken();
                    while (st.hasMoreTokens()){
                        d =d +" "+st.nextToken();
                    }
                    save.put(top,d);
                    if(Psave.containsKey(top)){
                       d=Psave.get(top)+" "+d;
                       Psave.put(top,d);
                    }
                    else{

                        Psave.put(top,d);
                    }
                    //backup(str,top,st);
                    
                    //write(top,st);
                    
                }
                else if(mode.equals("pr")){
                    hi=Psave.get(top);
                    dos.writeUTF(hi);
                }
                else {
                    //backup(top,mode);
                    
                    
                  try{
                    if (!save.containsKey(top)){
                        hi="e123456789";
                        dos.writeUTF(hi);
                        }
                        else{
                            String c=st.nextToken();
                            while(st.hasMoreTokens()){
                            c=c+" "+st.nextToken();
                            }
                            while(true){
                               hi= save.get(top);
                              if (c.equals(hi)){    
                                Thread.sleep(500);
                              }
                              else{
                                break;
                              }
                              
                             }
                            dos.writeUTF(hi);
                        }
                  }
                  catch(Exception e)
{
    System.out.println(e);
}

                    //show(top);                    
                }
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
/* .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
.\bin\windows\kafka-server-start.bat .\config\server.properties
.\bin\windows\kafka-console-consumer.bat --topic topic-example --bootstrap-server localhost:9092(--from beginning)
.\bin\windows\kafka-console-producer.bat --topic topic-example --bootstrap-server localhost:9092

 */
