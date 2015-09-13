package worldpeace;
import java.util.*;
 
  //given user information and calculate the weight
 
  public class CalWeight{
   int id;
  // int n_ll;
  // double n_lt;
  // double n_th;
  // double n_tl; 
   
   int ll;
   double lt;
   int th;
   int tl;
    //level of language;
    //length of time;
    //time of helping others;
    //int tl; //times of likes;
    
    /**  1. creat a weight method; 
         2. using constructor creat an object of class HelpImmigrant
              
              **/
   public CalWeight(int id,int ll,double lt,int th,int tl) {
     this.id = id;
     this.ll = ll;
     this.lt = lt;
     this.th = th;
     this.tl = tl;
   }
   
   
   
 public double getWeight() {
   double a = 0.0;
   double b = 0.0;
   double c = 0.00;
   double d = 0.00;
   if (ll == 1) {
    a = 1.33;
   } 
   else if (ll == 2) {
    a = 2.66 ;
   } 
   else if (ll == 3) {
     a = 4;
   }  
 
   if (lt < 10) {
    b = (3/10)*lt;
   } 
   else {
     b = 3;
   }
   
   if (th >= 100) {
     c = 2;
   }
   else {
     c = 2*th/(double)100;
   }
 
   
   if (tl >=100) {
     d = 1;
   }
   else {
     d = tl/(double)100;
   }
     /*
     System.out.println(a);
     System.out.println(b);
     System.out.println(c);
     System.out.println(d);
     */
     double answer = (a + b)*(c + d);
     //System.out.println(answer);
     return answer;
   }
   
 
   
   
  public static void main(String[] args) {
      Map<Integer,Double> pair = new HashMap<Integer,Double>();
      CalWeight myHelpImmigrant = new CalWeight(1,3,25,3,2);
      myHelpImmigrant.getWeight();
      //System.out.println(myHelpImmigrant.weight());
      //pair.put(id,myHelpImmigrant.weight);
      //System.out.println(pair);
  }
  
 } 
