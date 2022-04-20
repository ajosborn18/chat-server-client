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
        try {
        client_sock = new_client_sock; 
        Scanner sin = new Scanner(client_sock.getInputStream());
        PrintStream sout = new PrintStream(client_sock.getOutputStream());
        all_connections = connections;
        } catch(IOException e){}
    }

    // public Boolean write(String msg) {
    //     sout.println(msg);
    // }

    public void write_to_all(String msg) {
        for(ProcessConnection connect: all_connections) {
            connect.sout.println("@"+client_name+": "+msg);
        }
    }
    
    public void run() {
        System.out.println("Client Connected");
        try{
            client_name = sin.nextLine();
            try {
                sout.println("Welcome to the Chat Server. Please enter username: ");
                String line = sin.nextLine();
                while(line.strip().compareToIgnoreCase("EXIT") != 0) {
                    write_to_all(line);
                    try{ line = sin.nextLine(); }
                    catch (Exception e) { break; }
                } 
                // sout.println("@"+username+": "+line);
            } catch(Exception e){}
            finally{
                client_sock.close();
                all_connections.remove(this);
            }
        } catch(IOException e){}
        
    }
}