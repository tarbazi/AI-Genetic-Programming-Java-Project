import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class Main{
   public static void main(String[] args){
      Random myRandom = new Random(27);
      double[][] myData = new double[768][8];
      double[] results = new double[768];
      int height = Integer.parseInt(args[0]);
      //int[] arr1 = {1, 2, 3, 4};
      
      try{
         File myFile = new File("../file/diabetes.csv");
         Scanner myFileObj = new Scanner(myFile);
         myFileObj.nextLine();
         int i = 0;
         while (myFileObj.hasNextLine()){
            String[] myLine = myFileObj.nextLine().split(",");
            for (int j = 0; j < 8; j++){
               myData[i][j] = Double.parseDouble(myLine[j]); //stores treatment data into myData[][] array
               }
            //myData[i][8] = arr1[(int)(myRandom.nextDouble()*arr1.length)]; 
            results[i] = Double.parseDouble(myLine[8]);  //stores outcomes data into results arrray
            i++; 
            } 
         myFileObj.close();
         }
         
      catch(FileNotFoundException e){
         System.out.println("File not found.");
         System.exit(0);
         } 
      //code to read and pre-process the files data   
      
      double t = System.currentTimeMillis();  
      
      Node[] myNode = new Node[8];
      for (int a = 2; a < 17; a += 2){
         myNode[(a/2)-1] = new Node(myData, results, a, height);
         myNode[(a/2)-1].start();
         }

      for (int a = 0; a < 8; a += 1){
         try{
            myNode[a].join();
            System.out.println(myNode[a].getFitness());
            }
         catch (InterruptedException e){
            System.out.println(e);
            }   
         }
      System.out.println("\nInitial population created. Ranked population will follow.\n");
      //sort(myNode);   
      for (int i = 0; i < myNode.length; i++){
         System.out.println(myNode[i].getFitness());
         }
      
      for (int i = 0; i < 5; i++){
         int[] arr = {0, 1, 2, 3, 4, 5, 6, 7};
         //System.out.println((int)myRandom.nextDouble()*8);
         int a = arr[(int)myRandom.nextDouble()*8];
         int b = arr[(int)myRandom.nextDouble()*8];
         int c = arr[(int)myRandom.nextDouble()*8];
         int d = arr[(int)myRandom.nextDouble()*8];
         while (a == b){
            b = arr[(int)(myRandom.nextDouble()*arr.length)];
            //System.out.println(b);
            }
         while (c == d){
            d = arr[(int)(myRandom.nextDouble()*arr.length)];
            //System.out.println(d);
            }
         Crossover myCrossover1 = new Crossover(myNode[a], myNode[b], i);
         Crossover myCrossover2 = new Crossover(myNode[c], myNode[d], (i+30));
         myCrossover1.start();
         myCrossover2.run();
         try{
            myCrossover1.join();
            }
         catch (InterruptedException e){
            System.out.println(e);
            }  
         System.out.println("\nGeneration "+(i+1)+"\n");  
         myNode[0].evaluate();  
         myNode[1].evaluate();
         myNode[2].evaluate();
         myNode[3].evaluate();
         System.out.println(myNode[0].getFitness());
         System.out.println(myNode[1].getFitness());
         System.out.println(myNode[2].getFitness());
         System.out.println(myNode[3].getFitness());
         //sort(myNode);
         }         
      
      System.out.println("Done in: "+(System.currentTimeMillis()-t));
      try{
         Thread.sleep(1);
         System.exit(0);
      }   
      catch (Exception e){

      }
      }


   /*public static void sort(Node[] node){
      int i = 0;
      int upper = (node.length)-1;
      Node temp;
      while (i < (upper)){
         if (node[i].getFitness() < node[i+1].getFitness()){
            temp = node[i];
            node[i] = node[i+1];
            node[i+1] = temp;
            i = 0;
            }
         else{
            i++;
            }   
         }*/
      
      //} 
}
