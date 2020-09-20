/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package survey;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author josh
 */



public class Survey {
    static Question [] questions = new Question[5];
    static String[] responses = new String[]{"NO RESPONSE", "NO RESPONSE", "NO RESPONSE","NO RESPONSE","NO RESPONSE"};
    static int index = 0;
    static MakeThread m;
    static JLabel jl;
    static JButton left;
    static JButton right;
    static boolean finish = false;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        questions[0] = new Question("Favorite Ice Cream", "Vanilla", "Chocolate");
        questions[1] = new Question("Which season is better", "Winter", "Summer");
        questions[2] = new Question("Which pet is better", "Dog", "Cat");
        questions[3] = new Question("Unicorns are real");
        questions[4] = new Question("Text or call","Text","Call");
        
        JFrame jf = new JFrame("Survey");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(400,400);
        
        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(3,1));
        JPanel jp2 = new JPanel();
        jp2.setLayout(new GridLayout(1,3));
        
        left = new JButton(questions[index].left);
        left.addActionListener(new ButtonListener());
        
        left.setContentAreaFilled(false);
        left.setOpaque(true);
        left.setBorderPainted(true);
        
        right = new JButton(questions[index].right);
        right.addActionListener(new ButtonListener());
        
        right.setContentAreaFilled(false);
        right.setOpaque(true);
        right.setBorderPainted(true);
        
        jp2.add(left);
        jp2.add(new JPanel());
        jp2.add(right);
        
        jl = new JLabel(questions[index].question);
        
        jp.add(jl);
        jp.add(new JPanel());
        jp.add(jp2);
        
        jf.add(jp);
        jf.setVisible(true);
        
        m = new MakeThread();
        m.start();    
    }
    
}

class Question{
    String left = "True";
    String right = "False";
    String question;
    Question(String thequestion){
        question = thequestion;
    }
    Question(String thequestion, String theleft, String theright){
        question = thequestion;
        left = theleft;
        right = theright;
    }
}

class MakeThread extends Thread{
    @Override
    public void run(){
        try {
            sleep(5000);
            String str = "" + Survey.responses[0]+ ", " + Survey.responses[1] + ", " + Survey.responses[2]+ ", " + Survey.responses[3]+ ", " + Survey.responses[4];
            Survey.jl.setText(str);
            Survey.finish = true;
        } catch (InterruptedException ex) {
        }
    }
}

class ButtonListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (!Survey.finish){
            Survey.m.interrupt();
            if(arg0.getSource() == Survey.left){
                Survey.responses[Survey.index] = Survey.questions[Survey.index].left;
            }else{
                Survey.responses[Survey.index] = Survey.questions[Survey.index].right;
            }
            if (Survey.index == 4) {
                String str = "" + Survey.responses[0]+ ", " + Survey.responses[1] + ", " + Survey.responses[2]+ ", " + Survey.responses[3]+ ", " + Survey.responses[4];
                Survey.jl.setText(str);
                Survey.finish = true;
            }else{
                Survey.index += 1;
                Survey.jl.setText(Survey.questions[Survey.index].question);
                Survey.left.setText(Survey.questions[Survey.index].left);
                Survey.right.setText(Survey.questions[Survey.index].right);
            }

            Survey.m = new MakeThread();
            Survey.m.start();
        }
    }
    
}
