
public class BookstoreSale
{
  public static void main(String[] args)
  {
    
    // Test values
    double init=50, discount=4, floor=25, budget=300;
    
    // Pull input from command line -- otherwise 
    if (args.length >= 4) {
      try {
      	init = Double.parseDouble(args[0]);
		discount = Double.parseDouble(args[1]);
        floor = Double.parseDouble(args[2]);
        budget = Double.parseDouble(args[3]);

      } catch (NumberFormatException e) {
          System.out.println("Invalid inputs.  Using default values.");
          init =50; discount =4; floor =25; budget =300;
      }
    }
    
    
    double[] ret = calcMaxPurchase( init, discount, floor, budget );
    System.out.println("Money leftover $" + ret[0] + "\nBooks purchased " + ret[1]);
    
    
    double[] ret2 = fastCalcMaxPurchase( init, discount, floor, budget );
    System.out.println("Money leftover $" + ret2[0] + "\nBooks purchased " + ret2[1]);
    
  }
 
 
  public static double[] calcMaxPurchase (double init, double discount, double floor, double budget) {
    
    double booksPurchased = 0;
    
    // We will loop by counting down the current price && reducing the budget until no more budget for next book
    double currentPrice = init;    
    while (budget >= currentPrice) {
      
      // Make sure no bad values/ infinite loop
      if (currentPrice <= 0) return new double[]{ 0, 0 };
      
      // Subtract book price from budget && increase books purchased
      budget -= currentPrice;
      booksPurchased++;
      
      // Subtract discount from current price (for next book) ... until it hits floor
      currentPrice = (currentPrice - discount) < floor ? floor : currentPrice - discount;
            
    }
    
    return new double[]{ budget, booksPurchased };
    
  }
 
 
 
  public static double[] fastCalcMaxPurchase (double init, double discount, double floor, double budget) {
    
    if (init <= 0) return new double[]{ 0,0 };
      
    double moneyRemaining = 0;
    double booksPurchased = 0;
    
    
    // This approach is constant time if you get to "floor" pricing.  There's a few bits of math that make less clearly understandable though
    //        If you buy enough books to invoke "floor" pricing:  Const  O(1)
    //        If you don't buy enough books for "floor" pricing:    Linear O(n)
    
    // CPU-saving Approach: Determine what the cost would be if you made it down to "floor" pricing.  We can do that via a one-line equation.  
    
    
    // Number of books you can buy before the "floor" kicks in
    // "Number Before Floor"
    int nbf = (int) ((init - floor) / discount) +1;
    
    // Total cost if you bought all the books down to the floor price
    // This is a quick way to do this using math
    // "Cost To Floor"
    int ctf = (int)(nbf*init) - (int)(discount*nbf*nbf /2.0) + (int)(discount*nbf /2.0);
    
    moneyRemaining = budget - ctf;
        
    // If the "CostToFloor" is less than the budget:
    // Then the rest of the books are bought at floor price, and we can simply divide & avoid a loop
    if (ctf <= budget) {      
      int floorBooks = (int) (moneyRemaining / floor);
      
      moneyRemaining = moneyRemaining - (floorBooks * floor);
      booksPurchased = nbf + floorBooks;
    }
    // If we can't take the shortcut above, we use classic iterative approach  
    else {
      return calcMaxPurchase(init, discount, floor, budget);
   
    }
    
    
    return new double[]{ moneyRemaining, booksPurchased };
    
  }
 
 
}



