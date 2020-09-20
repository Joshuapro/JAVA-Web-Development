/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ingredient;

/**
 *
 * @author josh
 */
public class Recipe {
    Ingredient[] ingredients = new Ingredient[5];
    String[] steps = new String[7];
    @Override
    public String toString(){
        String ret = "Ingredients: ";
        for (Ingredient i : ingredients) {
            ret += i.toString() + ", ";
        }
        ret += "\r\n\r\nSteps: \r\n";
        for (String step : steps) {
            ret += step + "\r\n";
        }
        return ret;
    }
    public static void main(String[] args) {
        Recipe cookie = new Recipe();
        //creating ingredients
        Ingredient flour = new Ingredient(); 
        Ingredient salt = new Ingredient();
        Ingredient bakingpowder = new Ingredient();
        Ingredient eggs = new Ingredient();
        Ingredient vanilla = new Ingredient();
        
        flour.measurment = 5;
        flour.item = " cups of flour";
        salt.measurment = 1;
        salt.item = " teaspoon of salt";
        bakingpowder.measurment = 0.5;
        bakingpowder.item = " teaspoon of baking powder";
        eggs.measurment = 2;
        eggs.item = " large eggs";
        vanilla.measurment = 2;
        vanilla.item = " tea spoons of vanilla extract";
        
        cookie.ingredients[0] = salt;
        cookie.ingredients[1] = flour;
        cookie.ingredients[2] = eggs;
        cookie.ingredients[3] = bakingpowder;
        cookie.ingredients[4] = vanilla;
        
        
        //creating steps
        cookie.steps[0] = "Preheat oven to 375 degrees F.";
        cookie.steps[1] = "mix flour, baking soda, salt, baking powder. Set aside.";
        cookie.steps[2] = "mix eggs and vanilla until fluffy.";
        cookie.steps[3] = "Mix in the dry ingredients until combined";
        cookie.steps[4] = "dough the cookies into balls and place them evenly on sheets";
        cookie.steps[5] = "BAKE for 8-10 minutes";
        cookie.steps[6] = "Sit on the baking pan for 2 minutes before serving";
        
        //print receipe
        System.out.println(cookie.toString());
        
    }
    
}

class Ingredient{
    double measurment;
    String item;
    @Override
    public String toString(){
        return  measurment + item;
    }
}
