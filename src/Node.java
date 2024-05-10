import java.util.Random;

public class Node extends Thread{
   Node parent;    //store parent node address
   Node leftChild, rightChild; //stores left and right child nodes address
   
   String var; //stores the nodes functional or terminal sets variable
   double val; //stores the nodes numeric values

   double leftVal, rightVal; //stores the numeric values returned from the left and right subtree of the node

   String[] terminalSet;  //terminal set variables
   String[] functionalSet = {"+","-","x"}; //functional set values

   double[][] terminalVals;   //stores an array of the exact values from the input file
   int[] results;
   boolean visited;    //tell us if a node has beem visited or not

   int seed;
   int maxHeight, currentHeight;
   Random myRandom;
   double fitness;
   double trueNegatives;
   double truePositives;
   double falseNegatives;
   double falsePositives;
   
   boolean left;
   
   public Node(){
      var = "<";
      leftVal = -1;
      rightVal = -1;
      visited = false;
   }
    
   public Node(double[][] terminalVals, int[] results, int seed, int maxHeight){
      var = "<";
      leftVal = -1;
      rightVal = -1;
      visited = false;
      this.seed = seed;
      this.maxHeight = maxHeight;
      currentHeight = 1;
      myRandom = new Random(seed);
      left = false;
      terminalSet = new String[terminalVals[0].length];
      populate(terminalSet);
      this.terminalVals = terminalVals;
      this.results = results;        
   }


   public Node(Node parent, double[][] terminalVals, String var, double val, Random seed, int maxHeight, int currentHeight, boolean left){
      this.parent = parent;
      this.var = var;
      this.myRandom = seed;
      this.maxHeight = maxHeight;
      this.currentHeight = currentHeight;
      this.terminalVals = terminalVals;
      leftVal = -1;
      rightVal = -1;
      visited = false;
      this.left = left;
      terminalSet = new String[terminalVals[0].length];
      populate(terminalSet);
   }
    
   public void insert(int nodeSide){
      
      if ((currentHeight < maxHeight) && nodeSide == 0){

         if (this.leftChild == null){
            String temp = functionalSet[(int)(myRandom.nextDouble()*functionalSet.length)];
            this.leftChild = new Node(this, this.terminalVals, temp, 12, this.getSeed(), this.maxHeight, this.currentHeight+1, true);
            }

         this.leftChild.insert(0);
      
      }//insert functional node on the left of the currentnode

      else if ((currentHeight >= maxHeight) && nodeSide == 0){

         String temp = terminalSet[(int)(myRandom.nextDouble()*terminalSet.length)];
         this.leftChild = new Node(this, this.terminalVals, temp, 12, this.getSeed(), this.maxHeight, this.currentHeight+1, true);
         this.insert(1);

      }//insert terminal node on the left of any node

      else if (currentHeight < maxHeight && nodeSide == 1){
         
         if (this.rightChild == null){
            String temp = functionalSet[(int)(myRandom.nextDouble()*functionalSet.length)];
            this.rightChild = new Node(this, this.terminalVals, temp, 12, this.getSeed(), this.maxHeight, this.currentHeight+1, false);
            this.rightChild.insert(0);
         }//insert functional node on the right of the current node

         else{
            if (this.parent != null){  
               this.parent.insert(1);
               //move one step up on the tree
            }

            else{
               //System.out.println("Initial population created");
               //System.out.println("***************************");
               //full tree with desireable height will have been created
            }
         }
      }

      else if (currentHeight >= maxHeight && nodeSide == 1){

         if (this.rightChild == null){
            String temp = terminalSet[(int)(myRandom.nextDouble()*terminalSet.length)];
            this.rightChild = new Node(this, this.terminalVals, temp, 12, this.getSeed(), this.maxHeight, this.currentHeight+1, false);
            //System.out.println("Inserted some terminal node on the right.");
            if (this.parent != null){
               //System.out.println("Went up");
               this.parent.insert(1);
            }
            else{
               //System.out.println("Initial population created");
               //System.out.println("***************************");
               //full tree with desireable height will have been created
            }
         }

         else{
            if (this.parent != null){
               //System.out.println("Went up");
               this.parent.insert(1);
               //move on step upper on the tree
            }
            else{
               //System.out.println("Initial population created");
               //System.out.println("***************************");
               //full tree with desireable height will have been created
            }
         }
      }
   }
   
   public void setTerminalValsAndResults(double[][] terminalVals, int[] results){
      this.terminalVals = terminalVals;
      this.results = results;
   }

   public void evaluate(){
      int count = 0;
      truePositives = 0;
      trueNegatives = 0;
      falsePositives = 0;
      falseNegatives = 0;
      for (int i = 0; i < terminalVals.length; i++){
         double result = this.getResult(i);
         //System.out.println(result);
         boolean check = (results[i] == result);
         if (check == true){
            if (results[i] == 0){
               trueNegatives++;
               }

            else{
               truePositives++;
               }

            count++;
            }
         
         else{
            if(results[i] == 0){
               falsePositives++;
               }
            else{
               falseNegatives++;
               }
            }   

         }
         this.fitness = (100*count)/terminalVals.length;
      }

   public double getResult(int i){
      if (this.leftChild != null){
         this.leftVal = this.leftChild.getResult(i);
         this.rightVal = this.rightChild.getResult(i);
         return this.operate();
      }
      else{
         //System.out.println(terminalVals[i][getIndex(var)]);
         return terminalVals[i][getIndex(var)]; 
      }
   }
    
   public void run(){
      this.insert(0);  
      this.evaluate();  
   }

   public void setVar(String var){
      this.var = var;
   }

   public String getVar(){
      return var;
   }

   public Random getSeed(){
      return myRandom;
   }
      
   
      
    public int getIndex(String target){
      int ret = -1;
      
      for (int i = 0; i < 9; i++){
         ret++;
         if (target.equals(terminalSet[i])){
            return ret;
            }   
         }
      return ret;
      } 
      
   public double operate(){
   
      if (this.var.equals("+")){
         return (this.leftVal + rightVal);
         }
         
      else if (this.var.equals("-")){
         return (this.leftVal - rightVal);
         } 
          
      else if (this.var.equals("x")){  
         return (this.leftVal * this.rightVal);
         }
         
      else if (this.var.equals("/")){
         return (this.leftVal/this.rightVal);
         } 
         
      else if (this.var.equals("<")){
         if (this.leftVal < this.rightVal){
            return 1;
            }
         else{
            //System.out.println("Left  val = "+typeOf(this.leftVal)+" right val = "+this.rightVal);
            return 0;
            }   
         }  
      else{ 
         if (this.leftVal >= this.rightVal){
            return 1;
            }
         else{
            //System.out.println("Left  val = "+this.leftVal+" right val = "+this.rightVal);
            return 0;
            }
         }            
      }       
    
   public void populate(String[] terminalSet){
      String X = "X";
      for (int i = 0; i < terminalSet.length; i++){
         terminalSet[i] = X + i;
      }
   }

   public int getSeedVal(){
      return this.seed;
      }
   public double getFitness(){
      return this.fitness;
      }  

   public double getTrueNegatives(){
      return this.trueNegatives;
      } 

   public double getTruePositives(){
      return this.truePositives;
      }

   public double getFalseNegatives(){
      return this.falseNegatives;
      }
   
   public double getFalsePositives(){
      return this.falsePositives;
      }

   public double getAccuracy(){
      double num = this.truePositives + this.trueNegatives;
      double den = this.falsePositives + this.trueNegatives + this.falsePositives + this.falseNegatives;
      return 100*(num/den);
   }

   public String[] getTerminalSet(){
      return this.terminalSet;
   }
}
