/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw4server;

/**
 *
 * @author josh
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class Hw4server {

    /**
     * @param args the command line arguments
     */
    static ArrayList<Socket> list = new ArrayList<Socket>();
    static int portnum = 5190;
    public static void main(String[] args) {
        // TODO code application logic here
        try{
            ServerSocket ss = new ServerSocket(portnum);
            while (true){             
                Socket client = ss.accept();
                Scanner sin = new Scanner(client.getInputStream());
                String username = sin.nextLine();
                list.add(client);
                new process(client,username).start();
            }
        }catch(IOException e){
            System.out.println("IOError: "+e.toString());
        }
    }
    
}

class process extends Thread{
    Socket client;
    String user;
    process(Socket newclient,String username){
        client = newclient;
        user = username;
    }
    @Override
    public void run(){
        try{
            Scanner sin = new Scanner(client.getInputStream());
            String line = "";
            while(!line.equalsIgnoreCase("EXIT")){
                line = sin.nextLine();
                for(Socket soc: Hw4server.list){
                    PrintStream sout = new PrintStream(soc.getOutputStream());
                    sout.print(user+ ": "+ line + "\r\n");           
                }
            }
            client.close();
        }
        catch(IOException e){
            
        }
        
    }
    
}