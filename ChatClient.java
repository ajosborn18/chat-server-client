import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatClient {
    public static void main(String[] args) {

        JFrame jf = new JFrame("Chat Room");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(400, 250);
        jf.setResizable(false);

        JPanel jp = new JPanel();
        JButton login_button = new JButton("Sign In");
        JTextField IP_address = new JTextField(20);
        JTextField username = new JTextField(20);

        JLabel ip = new JLabel("IP Address:");
        JLabel user = new JLabel("Username:");

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
        gbc.gridy = 3;
        jp.add(login_button,gbc);

        JFrame chatframe = new JFrame("Chat Room");
        chatframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chatframe.setSize(600, 700);
        chatframe.setResizable(false);

        jf.add(jp);

        login_button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {   
                try {
                    String ip_addr = IP_address.getText();
                    Socket sock = new Socket(ip_addr, 5190);
                    PrintStream sout = new PrintStream(sock.getOutputStream());
                    Scanner sin = new Scanner(sock.getInputStream());

                    String username_input = username.getText();
                    sout.println(username_input);
        
                    sock.close();
                    if(sock.isConnected()){
                        jf.setVisible(false);
                        chatframe.setVisible(true);
                    } 
        
                } catch (IOException ex) {
                    System.out.println("Socket could not connect!");
                } 
            }
        });

        JPanel messenger = new JPanel();
        messenger.setBackground(Color.WHITE);
        JLabel message_display = new JLabel();
        JTextField message = new JTextField(50);
        JButton send_message = new JButton("Send");

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

        send_message.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e){

            }
        });

        jf.setVisible(true);
    }
}
