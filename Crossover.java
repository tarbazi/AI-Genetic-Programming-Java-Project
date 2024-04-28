import java.util.Random;

public class Crossover extends Thread{
   Node parent1;
   Node parent2;
   int[] initDir = {-1,1};
   int[] finalDir = {-1, 0, 0, 1};
   Random myRandom;
   
   public Crossover(){
      }
      
   public Crossover(Node parent1, Node parent2, int i){
      this.parent1 = parent1;
      this.parent2 = parent2;
      myRandom = new Random(i);
      }  
       
   public void run(){
      crossover();
      }   
      
   public void crossover(){
      int dir = initDir[(int)(myRandom.nextDouble()*initDir.length)];
      Node traverse1 = parent1;
      Node traverse2 = parent2;
      Node temp1;
      Node temp2;
      
      while (true){
         if (traverse1.leftChild != null){
            if (dir == -1){
               traverse1 = traverse1.leftChild;
               }
               
            else if (dir == 1){
               traverse1 = traverse1.rightChild;
               }
               
            else{
               temp1 = traverse1;
               break;
               }
            }
         else{
            temp1 = traverse1;
            break;
            } 
         dir = finalDir[(int)(myRandom.nextDouble()*finalDir.length)];        
         }
      
      dir = initDir[(int)(myRandom.nextDouble()*initDir.length)];
      while (true){
         if (traverse2.leftChild != null){
            if (dir == -1){
               traverse2 = traverse2.leftChild;
               }
               
            else if (dir == 1){
               traverse2 = traverse2.rightChild;
               }
               
            else{
               temp2 = traverse2;
               break;
               }
            }
         else{
            temp2 = traverse2;
            break;
            } 
         dir = finalDir[(int)(myRandom.nextDouble()*finalDir.length)];     
         }
         
      if (temp1.left == true){
         traverse1.parent.leftChild = temp2;
         } 
          
      else{
         traverse1.parent.rightChild = temp2;
         } 
      
      if (temp2.left == true){
         traverse2.parent.leftChild = temp1;
         }
         
      else{
         traverse2.parent.rightChild = temp1;
         }        
      }
   public void cloneNode(){
      }   
}