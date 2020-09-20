/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalquestionthree;

import java.util.LinkedList;

/**
 *
 * @author josh
 */
public class FinalQuestionThree {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

    }
    
}



class CafeQueue {
    LinkedList<String> queue;
    
    CafeQueue(){
        queue = new LinkedList<String>();
    }
    synchronized void enterQueue(String s){
        queue.add(s);
    }
    synchronized String serveCustomer(){
        if(queue.isEmpty()){
            return "";
        }
        return queue.removeFirst();
    }
    
}
