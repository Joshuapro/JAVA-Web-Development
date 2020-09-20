/*
CS3393 JAVA HW3 - Cindy Dai
Create a program which displays an analog clock with hours, minutes and seconds
*/

package cs3393hw3;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.net.ssl.*;
import javax.swing.*;

public class CS3393HW3 {
    /**
     * @param args the command line arguments
     */
    static int second, minute, hour;
    public static void main(String[] args) {
        // set up jframe
        JFrame jf = new JFrame("Clock");
        jf.setSize(400, 400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // clock interface
        ClockPanel cp = new ClockPanel();
        jf.add(cp);
        
        new Thread(){ // update the time
            public void run(){
                while (true) {
                    try{
                        // connect to the server
                        SSLSocketFactory factory =(SSLSocketFactory)SSLSocketFactory.getDefault();
                        SSLSocket socket =(SSLSocket)factory.createSocket("nist.time.gov", 443);
                        socket.startHandshake();
                        PrintStream sout = new PrintStream(socket.getOutputStream());
                        Scanner sin = new Scanner(socket.getInputStream());
                        sout.print("GET /actualtime.cgi HTTP/1.0\r\nHOST: nist.time.gov\r\n\r\n");
                        // get time
                        long time = 0;
                        while(sin.hasNext()){
                            String line = sin.nextLine();
                            
                            if (line.contains("<timestamp time=")) {
                                
                                int beginIndex = 17;
                                int endIndex = line.indexOf('"', beginIndex);
                                String timeStr = line.substring(beginIndex, endIndex);
                                System.out.println(timeStr);
                                time = Long.parseLong(timeStr)/1000;
                                break;
                            }
                        }
                        // update time
                        if (time != 0) { // if successfully got time
                            System.out.println("Update the time from http://nist.time.gov/actualtime.cgi!");
                            Date date = new Date(time);
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date);
                            System.out.println(Calendar.HOUR);
                            int correctTime = 360*60*cal.get(Calendar.HOUR) + 360*cal.get(Calendar.MINUTE) + 6*cal.get(Calendar.SECOND);
                            updateSecond(correctTime);
//                            System.out.println(correctTime);
                        }
                    }
                    catch(Exception e){System.out.println("OOpsie: "+e.toString());}
                    try {
                        sleep(1000*60); // request time every minute
                    } catch (InterruptedException ex) { }
                } 
            }
        }.start();
        
        new Thread(){ // run the clock
            public void run(){
                while (true){
                    // second hand moves 1 degree in 1/6 second
                    updateSecond(0);
                    // minute hand moves 1 degree when second moves 60 degree
                    CS3393HW3.minute = CS3393HW3.second/60;
                    
                    // hour hand moves 1 degree when second moves 720 degree
                    CS3393HW3.hour = CS3393HW3.second/720;
                    System.out.println(CS3393HW3.second);
                    cp.repaint();
                    try {
                        sleep(1000/6);
                    } catch (InterruptedException ex) { }
                }
            }
        }.start();
        
        jf.setVisible(true);
    }
    synchronized static void updateSecond(int newTime) { 
        // CS3393HW.second is shared between 2 threads, need a lock when updating
        if (newTime == 0) // clock thread
            CS3393HW3.second ++;
        else // request thread
            CS3393HW3.second = newTime;
    }
}

class ClockPanel extends JPanel{
    int r;
    ClockPanel(){
        super();
        Calendar cal = Calendar.getInstance();
        int correctTime = 360*60*cal.get(Calendar.HOUR) + 360*cal.get(Calendar.MINUTE) + 6*cal.get(Calendar.SECOND);
        CS3393HW3.updateSecond(correctTime);
    }
    
    Point calcLocation(int degree, double len){
        Point p = new Point();
        System.out.println(Math.sin(Math.toRadians(degree))*len);
        p.x = (int)(Math.sin(Math.toRadians(degree))*len);
        p.y = (int)(Math.cos(Math.toRadians(degree))*len);
        return p;
    }
    
    void drawClock(Graphics g){
        int height = this.getHeight();
        int width = this.getWidth();
        int centerX = width/2;
        int centerY = height/2;
        
        g.setColor(Color.BLACK);
        g.drawOval(width/2 - r, height/2 - r, 2 * r, 2 * r);
        
        Point p;
        for (int i = 1; i <= 12; ++i) {
            // each number are 30 degree apart
            p = calcLocation(i * 30, r - 10);
            g.drawString("" + i, centerX + p.x, centerY - p.y + 5);
        }
    }
    
    protected void paintComponent(Graphics g){
        int height = this.getHeight();
        int width = this.getWidth();
        int centerX = width/2;
        int centerY = height/2;
        r = height/2;
        
        // set background color
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        
        // clock interface
        drawClock(g);
        
        // hour hand
        Point p = calcLocation(CS3393HW3.hour, r * 0.4);
        g.drawLine(centerX, centerY, centerX+p.x, centerY-p.y);
        // minute hand
        p = calcLocation(CS3393HW3.minute, r * 0.6);
        g.drawLine(centerX, centerY, centerX+p.x, centerY-p.y);
        // second hand
        g.setColor(Color.RED);
        p = calcLocation(CS3393HW3.second, r * 0.8);
        g.drawLine(centerX, centerY, centerX+p.x, centerY-p.y);
    }
}

class Point{
    int x;
    int y;
    Point(){this(0,0);}
    Point(int newx, int newy){x = newx; y = newy;}
}