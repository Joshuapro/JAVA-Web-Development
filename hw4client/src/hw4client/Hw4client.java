/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw4client;

/**
 *
 * @author josh
 */
import static hw4client.Hw4client.s;
import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Hw4client {

    /**
     * @param args the command line arguments
     */

    static JTextField text = new JTextField();
    static Socket s;
    static ArrayList<String> arr = new ArrayList<String>();
    static JTextPane jtp = new JTextPane();
    static int counter = 2;
    static String server;
    static Boolean flag = true;
    
            
    public static void build(){
        JFrame jf = new JFrame("Chat");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(400,600);
        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(2,1));
        jtp.setText("Please enter the server you like to connect and press send.");
        jp.add(jtp);
        JPanel jplast = new JPanel();
        jplast.setLayout(new GridLayout(1,2));
        JButton send = new JButton("send");
        send.addActionListener(new ButtonListener());
        jplast.add(text);
        jplast.add(send);
        jp.add(jplast);
        
        jf.add(jp);
        jf.setVisible(true);
    }
    
    public static void chatSetText(){
        String strng = "";
        if(arr.size()>18){
           arr.remove(0);
        }
        for (String i:arr){
            strng += i;
            strng += "\n";
        }
        jtp.setText(strng);
    }
    
    
    public static void main(String[] args) {
        try {
            build();
            //wait for user input
            while(flag){try{Thread.sleep(100);}catch(InterruptedException e){}};
            s = new Socket(server, 5190);
            if (s.isConnected()){
                Scanner sin = new Scanner(s.getInputStream());
                while (sin.hasNext()){                    
                    String line = sin.nextLine();
                    arr.add(line);
                    chatSetText();
                }        
            }       
        }catch(IOException ex) {}
    }
}

class ButtonListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (Hw4client.counter == 2){
            Hw4client.server = Hw4client.text.getText();
            Hw4client.counter -= 1;
            Hw4client.flag = false;
            Hw4client.text.setText("");
            Hw4client.jtp.setText("Please enter a username and press send");         
        }
        else{
            if (Hw4client.counter == 1){
                Hw4client.jtp.setText("");
                Hw4client.counter -= 1;
            }
            try{
                PrintStream sout = new PrintStream(Hw4client.s.getOutputStream());
                String line = Hw4client.text.getText();
                sout.print(line + "\r\n");
                Hw4client.text.setText("");
            }catch (IOException ex) {}
        }
    }
    
}
