/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hw5clockk;

import java.io.*;
import java.net.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 *
 * @author josh
 */
public class Hw5clockk {

    /**
     * @param args the command line arguments
     */
    static int degsec;
    static int deghour;
    static int degmin;
    static Clock cpanel;
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame jf = new JFrame("CLock");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(400,400);
        
        TimeUpdate getTimePerMin = new TimeUpdate();
        getTimePerMin.start();
        
        ClockUpdate updateClockTicks = new ClockUpdate();
        updateClockTicks.start();
        
        cpanel = new Clock();
        jf.add(cpanel);
        
        jf.setVisible(true);       
    }
}

class TimeUpdate extends Thread{
    @Override
    public void run(){
        while (true){
            try{
                Socket s = new Socket("time-b-g.nist.gov",13);
                Scanner sin = new Scanner(s.getInputStream());
                String line="";
                while(sin.hasNext()){
                    line = sin.nextLine(); 
                }
                String[] time = line.split(" ");
                int hour = Integer.parseInt(time[2].substring(0, 2));
                int minute = Integer.parseInt(time[2].substring(3, 5));
                int second = Integer.parseInt(time[2].substring(6, 8));
                //total amount of degrees for the day
                Hw5clockk.degsec = 360*60*hour + 360*minute + 6*second;
            }
            catch(IOException ex) {}
            // getting the time once per minute
            try{sleep(60000);}catch(InterruptedException ex) {} 
        }
        
    }
}

class ClockUpdate extends Thread{
    @Override
    public void run(){
        while (true){
            Hw5clockk.degsec++;
            Hw5clockk.degmin = Hw5clockk.degsec/60;
            Hw5clockk.deghour = Hw5clockk.degsec/720;
            //the second hand rotates 6 degrees every sec...
            try{sleep(1000/6);}catch(InterruptedException ex) {}    
            Hw5clockk.cpanel.repaint();
        }
        
    }
}






class Clock extends JPanel{
    
    Clock(){
       super();
    }
    
    Endpoint endpointRet(int deg, double len){
        return new Endpoint((int)(Math.sin(Math.toRadians(deg))*len),(int)(Math.cos(Math.toRadians(deg))*len));
    }
    

    protected void paintComponent(Graphics g){
        int height = this.getSize().height;
        int width = this.getSize().width;
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        g.drawOval(10, 10, height-20, height-20);

        
        Endpoint p; 
        g.setColor(Color.RED);
        p = endpointRet(Hw5clockk.degsec,160);
        g.drawLine(width/2, height/2, width/2+p.x, height/2-p.y);
        g.setColor(Color.BLUE);
        p = endpointRet(Hw5clockk.degmin,130);
        g.drawLine(width/2, height/2, width/2+p.x, height/2-p.y);
        p = endpointRet(Hw5clockk.deghour,70);
        g.drawLine(width/2, height/2, width/2+p.x, height/2-p.y);
    }
    

    
}

//used for endpoint caculation on the panel
class Endpoint{
    int x;
    int y;
    Endpoint(int xx, int yy){
        x=xx;
        y=yy;
    }
}
