import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatClient {
    static JFrame jf;
    static JPanel jp;

    static JLabel ip;
    static JTextField IP_address;

    static JLabel user;
    static JTextField username;

    static JButton ip_button;
    static JButton login_button;

    static JFrame chatframe;

    static JPanel messenger;
    static JLabel message_display;

    static JTextField message;
    static JButton send_message;

    static String ip_input = "";
    static String username_input = "";
    static String msg = "";

    static Socket sock;
    static PrintStream sout;
    static Scanner sin;


    public static void main(String[] args) {

        jf = new JFrame("Chat Room");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(400, 350);
        jf.setResizable(false);

        jp = new JPanel();
        ip_button = new JButton("Connect");
        login_button = new JButton("Sign In");
        IP_address = new JTextField(20);
        username = new JTextField(20);

        ip = new JLabel("IP Address:");
        user = new JLabel("Username:");

        jp.setBackground(Color.WHITE);
        jp.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        jp.add(ip,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        jp.add(IP_address,gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        jp.add(user,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        jp.add(username,gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        jp.add(ip_button);
        gbc.gridx = 1;
        gbc.gridy = 3;
        jp.add(login_button,gbc);

        chatframe = new JFrame("Chat Room");
        chatframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatframe.setSize(600, 700);
        chatframe.setResizable(false);

        jf.add(jp);

        messenger = new JPanel();
        messenger.setBackground(Color.WHITE);
        message_display = new JLabel();
        message = new JTextField(50);
        send_message = new JButton("Send");

        messenger.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 2;
        gbc.gridwidth = 3;
        messenger.add(message_display);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        messenger.add(message);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        messenger.add(send_message);

        chatframe.add(messenger);
        

        ip_button.addActionListener(new ConnectListener());
        login_button.addActionListener(new UsernameListener());
        send_message.addActionListener(new MessageListener());
        jf.setVisible(true);
        chatframe.setVisible(false);

    }

    static class ConnectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) { 
            ip_input = IP_address.getText(); 
            try {
                sock = new Socket(ip_input, 5190);
                sout = new PrintStream(sock.getOutputStream());
                sin = new Scanner(sock.getInputStream());
            } catch (IOException ex) {
                System.out.println("Socket could not connect!");
            }    
        }
    }
    static class UsernameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0){
            username_input = username.getText();
            try {
                sout.println(username_input);
            } catch (Exception ex) {}

            jf.setVisible(false);
            chatframe.setVisible(true);
        }
    }

    static class MessageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            msg = message.getText();
            sout.println(msg);
            message.setText("");
        }
    }

    static class Read extends Thread {
        Socket s;
        Read(Socket new_s) { s = new_s; }
        synchronized public void run() {
            Scanner sin;
            

        }
    }
}



