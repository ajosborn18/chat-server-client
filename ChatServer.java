import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServer {
    static ArrayList<ProcessConnection> connections;
    public static void main(String[] args) {
        System.out.println("In server\n");
        connections = new ArrayList<ProcessConnection>();
        try {
            ServerSocket ss = new ServerSocket(5190);
            while (true) {
                Socket client_sock = ss.accept();
                ProcessConnection new_connection = new ProcessConnection(client_sock, connections);
                new_connection.start();
                connections.add(new_connection);
            }
        } catch (IOException ex) {
            System.out.println("Could not listen on Port 5190");
        }

        
    }
}

class ProcessConnection extends Thread {
    Socket client_sock;
    Scanner sin;
    PrintStream sout;
    ArrayList<ProcessConnection> all_connections;
    String client_name;

    ProcessConnection(Socket new_client_sock, ArrayList<ProcessConnection> connections) {
        client_sock = new_client_sock; 
        all_connections = connections;
        client_name = "";
    }

    // public Boolean write(String msg) {
    //     sout.println(msg);
    // }
    public void write_to_all(String clientname, String msg) {
        for(ProcessConnection connect: all_connections) {
            connect.sout.println("@"+clientname+": "+msg);
        }
        System.out.println("@"+clientname+": "+msg);
    }
    
    public void run() {
        try {
            Scanner sin = new Scanner(client_sock.getInputStream());
            PrintStream sout = new PrintStream(client_sock.getOutputStream());

            while(client_sock.isConnected())
            {
                String line = sin.nextLine();
                if(client_name.equals("")) { 
                    client_name = line;
                }
                else { 
                    //write_to_all(client_name, line);
                    System.out.println(client_name);
                }
                if(line.equalsIgnoreCase("EXIT")) { 
                    client_sock.close();
                }
            }
            // sout.println("@"+username+": "+line);
        } catch(Exception e){}     
    }
}