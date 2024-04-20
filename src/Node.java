import java.util.Random;

public class Node extends Thread{
    Node parent;    //store parent node address
    Node leftChild, rightChild; //stores left and right child nodes address
    
    String var; //stores the nodes functional or terminal sets variable
    double val; //stores the nodes numeric values

    double leftVal, rightVal; //stores the numeric values returned from the left and right subtree of the node

    String[] terminalSet = {"X1", "X2", "X3", "X4", "X5", "X6", "X7", "X8"};  //terminal set variables
    String[] functionalSet = {"+","+","+","-","x","x","/"}; //functional set values
    
    int[] setChoice = {0, 1};   //value 0 represents fuctional set, value 1 represents terminal set

    double[][] terminalVals;   //stores an array of the exact values from the input file
    double[] results;
    boolean visited;    //tell us if a node has beem visited or not
   
    int seed;
    Random myRandom;
    double fitness;
    
    boolean left;
    
    public Node(){
        var = "<";
        leftVal = -1;
        rightVal = -1;
        visited = false;
    }
    
    public Node(double[][] terminalVals, double[] results, int seed){
        var = "<";
        leftVal = -1;
        rightVal = -1;
        visited = false;
        this.seed = seed;
        myRandom = new Random(seed);
        left = false;
        this.terminalVals = terminalVals;
        this.results = results;        
    }


    public Node(Node parent, String var, double val, Random seed, boolean left){
        this.parent = parent;
        this.var = var;
        this.myRandom = seed;
        leftVal = -1;
        rightVal = -1;
        visited = false;
        this.left = left;
    }
    
    public void insert(int nodeSide){
        //System.out.println();
        //int a = ;
        //System.out.println();
        int chosenSet = setChoice[(int)(myRandom.nextDouble()*setChoice.length)];
        //System.out.println("Chosen set = "+chosenSet+"Node side = "+nodeSide);
        
        if (chosenSet == 0 && nodeSide == 0){//1
            //System.out.println("Evaluated 1");
            String temp = functionalSet[(int)(myRandom.nextDouble()*functionalSet.length)];
            this.leftChild = new Node(this, temp, 12, this.getSeed(), true);
            //System.out.println("Inserted "+temp+" on the left.");
            this.leftChild.insert(0);
        }

        else if (chosenSet == 1 && nodeSide == 0){//2
            //System.out.println("Evaluated 2");
            String temp = terminalSet[(int)(myRandom.nextDouble()*terminalSet.length)];
            this.leftChild = new Node(this, temp, 12, this.getSeed(), true);
            //System.out.println("Inserted "+temp+" on the left.");
            this.insert(1);
        }

        else if (chosenSet == 0 && nodeSide == 1){//3
            //System.out.println("Evaluated 3");
            if (this.rightChild == null){
                String temp = functionalSet[(int)(myRandom.nextDouble()*functionalSet.length)];
                this.rightChild = new Node(this, temp, 12, this.getSeed(), false);
                //System.out.println("Inserted "+temp+" on the right.");
                this.rightChild.insert(0);
            }
            else{
                if (this.parent != null){
                    //System.out.println("Went up.");
                    this.parent.insert(1);
                }
                else{
                    /*System.out.println("Initial population created");
                    System.out.println("***************************");
                    //terminate*/
                }
            }
            //System.out.println("Go right.");
        }

        else if (chosenSet == 1 && nodeSide == 1){//4
            //System.out.println("Evaluated 4");
            if (this.rightChild == null){
                String temp = terminalSet[(int)(myRandom.nextDouble()*terminalSet.length)];
                this.rightChild = new Node(this, temp, 12, this.getSeed(), false);
                //System.out.println("Inserted "+temp+" on the right.");
                if (this.parent != null){
                    //System.out.println("Went up.");
                    this.parent.insert(1);
                }
                else{
                  /*System.out.println("Initial population created");
                  System.out.println("***************************");
                  //terminate*/
                  }
            }
            else{
                if (this.parent != null){
                    //System.out.println("Went up.");
                    this.parent.insert(1);
                }
                else{
                    /*System.out.println("Initial population created");
                    System.out.println("***************************");
                    //terminate*/
                }
            }
            //System.out.println("Go right.");
        }

        //System.out.println(chosenSet);
    }
    
    public void evaluate(){
      int count = 0;
      for (int i = 0; i < 768; i++){
         boolean check = (results[i] == this.getResult(i));
         if (check == true){
            count++;
            }
         }
      this.fitness = (100*count)/768;
      //System.out.println(count+" hits");
      }
    
    public void run(){
      this.insert(0);  
      this.evaluate();  
    }
    
    public Random getSeed(){
      return myRandom;
      }
      
    public double getResult(int i){
      Node root = this;
      Node traverse = this;
      root.leftVal = -1;
      root.rightVal = -1;
      root.leftChild.visited = false;
      root.rightChild.visited = false;
      
      
      
      while(true){
         //System.out.println("Stuck here");
         //System.out.println("Left "+root.leftChild.visited+" right "+root.rightChild.visited);
         if (traverse.leftChild != null && traverse.leftChild.visited == false){
            //System.out.println("Traversed to left child.");
            traverse = traverse.leftChild;
            } //traverse  left if  left most child has not been reached
            
         else if (traverse.leftChild != null && traverse.leftChild.visited == true){
         
            if (/*traverse.rightChild != null &&*/ traverse.rightChild.visited == false){
               //System.out.println("Traversed to right child.");
               traverse = traverse.rightChild;
               }
               
            else /*if (/*traverse.rightChild != null && traverse.rightChild.visited == true)*/{
            
               if(traverse.parent != null){
                  
                  if (traverse.left == true){
                     traverse.parent.leftVal = traverse.val;
                     traverse.leftChild.visited = false;
                     traverse.rightChild.visited = false;
                     traverse.visited = true;
                     traverse = traverse.parent;
                     System.out.println("always true");
                     }
                     
                  else{
                     traverse.leftChild.visited = false;
                     traverse.rightChild.visited = false;
                     traverse.visited = true;
                     traverse.parent.rightVal = traverse.val;
                     traverse = traverse.parent;
                     traverse.operate();
                     //System.out.println("Move back");
                     }   
                  }
               else{
                  traverse.leftChild.visited = false;
                  traverse.rightChild.visited = false;
                  traverse.operate();
                  break;
                  }      
               }
               
            /*else{
               if (traverse.left == false){
                  traverse.parent.rightVal = terminalVals[i][this.getIndex(this.var)];
                  traverse.visited = true;
                  traverse = traverse.parent;
                  traverse.operate();
                  System.out.println(traverse.rightVal+" at parent. A");
                  }
               else{
                  traverse.parent.leftVal = terminalVals[i][this.getIndex(this.var)];
                  traverse.visited = true;
                  traverse = traverse.parent;
                  System.out.println(traverse.leftVal+" at parent. B");
                  }   
               }*/   
            } 
             
         else {
            if (traverse.left == false){
               //System.out.println("Var is "+traverse.var+" val is "+this.getIndex(traverse.var));
               traverse.parent.rightVal = terminalVals[i][this.getIndex(traverse.var)];
               traverse.visited = true;
               traverse = traverse.parent;
               traverse.operate();
               /*System.out.println(traverse.rightVal+" from child "+ traverse.rightChild.var+" then went back.");
               System.out.println(root.leftVal+" "+root.rightVal);
               System.out.println();*/
               }
            else{
               //System.out.println("Var is "+traverse.var+" val is "+this.getIndex(traverse.var));
               traverse.parent.leftVal = terminalVals[i][this.getIndex(traverse.var)];
               traverse.visited = true;
               traverse = traverse.parent;
               /*System.out.println(traverse.leftVal+" from child "+ traverse.leftChild.var+" then went back.");
               System.out.println(root.leftVal+" "+root.rightVal);
               System.out.println();*/
               }
            }    
         }
      //System.out.println("Done");
      //root.operate();   
      return root.val;   
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
      
   public void operate(){
   
      if (this.var.equals("+")){
         this.val = this.leftVal + rightVal;
         }
         
      else if (this.var.equals("-")){
         this.val = this.leftVal - rightVal;
         } 
          
      else if (this.var.equals("x")){  
         this.val = this.leftVal * this.rightVal;
         }
         
      else if (this.var.equals("/")){
         this.val = this.leftVal/this.rightVal;
         } 
         
      else if (this.var.equals("<")){
         if (this.leftVal < this.rightVal){
            this.val = 1;
            }
         else{
            this.val = 0;
            }   
         }  
      else{ 
         if (this.leftVal >= this.rightVal){
            this.val = 1;
            }
         else{
            this.val = 0;
            }
         }            
      }       
    
    public int getSeedVal(){
      return this.seed;
      }
   public double getFitness(){
      return this.fitness;
      }   
}
