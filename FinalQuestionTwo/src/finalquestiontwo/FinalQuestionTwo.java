/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalquestiontwo;

/**
 *
 * @author josh
 */

import java.sql.*;
import java.io.*;
import java.util.*;

public class FinalQuestionTwo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        HashMap<Integer,String> map = new HashMap<>();
        HashMap<Integer,Double> mapTwo = new HashMap<>();
        ArrayList<Double> avgs = new ArrayList<>();
        ArrayList<Integer> pIDs = new ArrayList<>();
        
        Connection conn = null;
        try{
            String url = "localhost";
            conn = DriverManager.getConnection(url,"CS3913","GettingAnA+");
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("Select * from Products");
           
            while (rs.next()){
                
                String query = "Select Rating from Reviews where PID =" + rs.getInt("PID");
                ResultSet rsTwo = s.executeQuery(query);
                double totRating = 0;
                double num = 0; 
                
                //getting the average
                while(rsTwo.next()){
                    num += 1; 
                    totRating += rsTwo.getInt(1);
                }
                double avg;
                if(totRating==0 || num == 0){
                   avg = 0;
                }
                else{
                    avg = totRating/num;
                }
                
                int pID = rs.getInt("PID");
                String product = rs.getString("ProductName");
                
                pIDs.add(pID);
                avgs.add(avg);
                mapTwo.put(pID, avg);
                map.put(pID,product);
            }
            Collections.sort(avgs);
            for (int i = avgs.size()-1;i >= 0;i--){
                for(int j:pIDs){
                    if(Objects.equals(mapTwo.get(j), avgs.get(i))){
                        System.out.println(map.get(j));   
                        pIDs.remove((Integer) j);
                        break;
                    } 
                }
            }
        }catch (Exception e){System.out.println("Error: "+e.toString());}
    }
    
}
