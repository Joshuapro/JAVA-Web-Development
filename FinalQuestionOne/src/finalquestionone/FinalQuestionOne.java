/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalquestionone;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.*;

/**
 *
 * @author josh
 */
public class FinalQuestionOne {

    /**
     * @param args the command line arguments
     */
    static boolean action = false;
    public static void main(String[] args) {
        JFrame jf = new JFrame("Grid");
        Grid g = new Grid();
        jf.add(g);
        g.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent m){
                MyPoint p = new MyPoint(m.getX(),m.getY());
                g.addPoint(p);
                g.repaint();
                action = true;
            }
        });
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(400,400);
        jf.setVisible(true);
    }
    
}

class Grid extends JPanel{
    ArrayList<MyPoint> list = new ArrayList<MyPoint>();
    Grid(){
        super();
    }
    void addPoint(MyPoint p){list.add(p);}
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        int height = this.getSize().height;
        int width = this.getSize().width;
        g.setColor(Color.GRAY);
        for (int i = 0; i < width; i+=width/10){
            g.drawLine(i, 0, i, height);
        }     
        for (int i = 0; i < height; i+=height/10){
            g.drawLine(0, i, width, i);
        }
        
        NumberFormat formatter = new DecimalFormat("#0.00");
        g.setColor(Color.RED);
        if (FinalQuestionOne.action){
            MyPoint prev = list.get(0);
            for (MyPoint p:list){
                g.fillOval(p.x-5/2, p.y-5/2, 5, 5);
                g.drawLine(prev.x,prev.y,p.x,p.y);
                String s = "(" + p.x + "," + p.y + ")";
                g.drawString(s, p.x, p.y+13);
                // does not display distance on the first click
                if (prev != p){
                    double distance = Math.sqrt(Math.pow(p.x-prev.x, 2) + Math.pow(p.y-prev.y, 2));
                    g.drawString(formatter.format(distance), (p.x + prev.x)/2, (p.y + prev.y)/2);
                }
                prev = p;
            }
        }
        
        
        
    }
} 

class MyPoint {
    int x;
    int y;
    MyPoint(int xx, int yy){
        x = xx;
        y = yy;
    }
    MyPoint(){
        this(0,0);
    }   
}