import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class Main{
   public static void main(String[] args){
      Random myRandom = new Random(27);
      int height = Integer.parseInt(args[0]);

      int numFiles = Integer.parseInt(args[1]);
      double[][] fileData1 = null;
      double[][] fileData2 = null;
      //double[][] fileData3 = null;

      int[] results1 = null;
      int[] results2 = null;
      //int[] results3 = null; 

      for (int i = 0; i < numFiles; i++){
         if (i == 0){
            fileData1 = processData(readMyFile(args[2]));
            results1 = getResults(args[2]);
            }
         else if (i == 1){
            fileData2 = processData(readMyFile(args[3]));
            results2 = getResults(args[3]);
            }
         /*else{
            fileData3 = processData(readMyFile(args[4]));
            results3 = getResults(args[4]);
            }*/
         }

      if (numFiles == 1){
         targetCompute(fileData1, results1, height, myRandom);

      }

      else if(numFiles == 2){
         double initTime = System.currentTimeMillis();
         Node[] firstGenerationsNodes = targetCompute(fileData1, results1, height, myRandom);
         double finTime = System.currentTimeMillis();
         System.out.println("Entire Genetic Program Rand in "+(finTime-initTime)+"\n");

         System.out.println("Transfering Learning Takes Place From Source File: "+args[2]+" To Target File "+ args[3]+"\n");

         Transfer[] myTransfer = new Transfer[4];

         initTime = System.currentTimeMillis();
         for (int i = 0; i < 4; i++){
            myTransfer[i] = new Transfer(firstGenerationsNodes[i], fileData2, results2, i);
            myTransfer[i].start();
         }

         for (int i = 0; i < 4; i++){
            try{
               myTransfer[i].join();
            }
            catch(Exception e){
               System.out.println(e);
            }
         }
         finTime = System.currentTimeMillis();
         DecimalFormat df = new DecimalFormat("###");
         
         sort(firstGenerationsNodes);
         
         for (int i = 0; i < 4; i++){
            System.out.println("Fitness:");
            System.out.println(df.format(firstGenerationsNodes[i].getFitness())+"%");
            System.out.println("Classified as:  Positives | Negative");
            System.out.println("True Postive       "+ df.format(firstGenerationsNodes[i].getTruePositives()) +"       "+ df.format(firstGenerationsNodes[i].getFalseNegatives()));
            System.out.println("True Negatives:    "+ df.format(firstGenerationsNodes[i].getFalsePositives()) +"       " + df.format(firstGenerationsNodes[i].getTrueNegatives()));
            System.out.println("Accuracy Score");
            System.out.println(df.format(firstGenerationsNodes[i].getAccuracy())+"% \n");
         }
         System.out.println("Generation of initial population with transfer learning took "+(finTime-initTime));

         for (int i = 1; i < 11; i++){
         
            if (i%4 != 0){
               Mutate[] myMutate = new Mutate[4];
   
               for (int j = 0; j < 4; j++){
                  myMutate[j] = new Mutate(firstGenerationsNodes[j], (int)(myRandom.nextDouble()*100));
                  myMutate[j].start();
               }
               
               initTime = System.currentTimeMillis();
               for (int j = 0; j < 4; j++){
                  try{
                     myMutate[j].join();
                  }
   
                  catch(Exception e){
                     System.out.println(e);
                  }
   
                  firstGenerationsNodes[j].evaluate();
               }
               finTime = System.currentTimeMillis();
               sort(firstGenerationsNodes);
   
               System.out.println("Generation "+i+" from mutation.");
               for (int j = 0; j < 4; j++){
                  System.out.println("Fitness:");
                  System.out.println(df.format(firstGenerationsNodes[j].getFitness())+"%");
                  System.out.println("Classified as:  Positives | Negative");
                  System.out.println("True Postive       "+ df.format(firstGenerationsNodes[j].getTruePositives()) +"       "+ df.format(firstGenerationsNodes[j].getFalseNegatives()));
                  System.out.println("True Negatives:    "+ df.format(firstGenerationsNodes[j].getFalsePositives()) +"       " + df.format(firstGenerationsNodes[j].getTrueNegatives()));
                  System.out.println("Accuracy Score");
                  System.out.println(df.format(firstGenerationsNodes[j].getAccuracy())+"% \n");
               }
               System.out.println("This generation executed in "+(finTime-initTime)+"\n");
            }
   
            else{
               Crossover[] myCrossover = new Crossover[4];
               int a, b, c, d;
      
               a = (int)(myRandom.nextDouble()*4);
               b = (int)(myRandom.nextDouble()*4);
               c = (int)(myRandom.nextDouble()*4);
               d = (int)(myRandom.nextDouble()*4);
   
               while (a == b | a == c | a == d | b == c | b == d | c == d){
                  a = (int)(myRandom.nextDouble()*4);
                  b = (int)(myRandom.nextDouble()*4);
                  c = (int)(myRandom.nextDouble()*4);
                  d = (int)(myRandom.nextDouble()*4);
               }

               initTime = System.currentTimeMillis();

               for (int j = 0; j < 4; j++){
                  myCrossover[j] = new Crossover(firstGenerationsNodes[a], firstGenerationsNodes[b], (int)(myRandom.nextDouble()*100));
                  myCrossover[j].start();
               }
   
               for (int j = 0; j < 4; j++){
                  try{
                  myCrossover[j].join();
                  }
   
                  catch(Exception e){
                     System.out.println(e);
                  }
   
               firstGenerationsNodes[j].evaluate();
               }
               
               finTime = System.currentTimeMillis();
               sort(firstGenerationsNodes);
   
               System.out.println("Generation "+i+" from crossover");
               for (int j = 0; j < 4; j++){
                  System.out.println("Fitness:");
                  System.out.println(df.format(firstGenerationsNodes[j].getFitness())+"%");
                  System.out.println("Classified as:  Positives | Negative");
                  System.out.println("True Postive       "+ df.format(firstGenerationsNodes[j].getTruePositives()) +"       "+ df.format(firstGenerationsNodes[j].getFalseNegatives()));
                  System.out.println("True Negatives:    "+ df.format(firstGenerationsNodes[j].getFalsePositives()) +"       " + df.format(firstGenerationsNodes[j].getTrueNegatives()));
                  System.out.println("Accuracy Score");
                  System.out.println(df.format(firstGenerationsNodes[j].getAccuracy())+"% \n");
               }
               
               System.out.println("This generation executed in "+(finTime-initTime)+"\n");
            }
         }
      }

      /*else{
         targetCompute(fileData1, results1, height, myRandom);
         targetCompute(fileData2, results2, height, myRandom);
         }*/
      
   }

   public static Node[] targetCompute(double[][] fileData, int[] results, int height, Random myRandom){
      
      DecimalFormat df = new DecimalFormat("###");
      Node[] myNodes = new Node[4];
      double initTime = System.currentTimeMillis();
      for (int i = 0; i < 4; i++){
         myNodes[i] = new Node(fileData, results, i, height);
         myNodes[i].start();
         }

      for (int i = 0; i < 4; i++){
         try{
            myNodes[i].join();
            }
         catch(Exception e){
            System.out.println(e);
            }
         }
      
      sort(myNodes);
      
      System.out.println("Initial Population Was Created with the following attributes");
      System.out.println("************************************************************\n");

      for (int i = 0; i < 4; i++){
         
         System.out.println("Fitness:");
         System.out.println(df.format(myNodes[i].getFitness())+"%");
         System.out.println("Classified as:  Positives | Negative");
         System.out.println("True Postive       "+ df.format(myNodes[i].getTruePositives()) +"       "+ df.format(myNodes[i].getFalseNegatives()));
         System.out.println("True Negatives:    "+ df.format(myNodes[i].getFalsePositives()) +"       " + df.format(myNodes[i].getTrueNegatives()));
         System.out.println("Accuracy Score");
         System.out.println(df.format(myNodes[i].getAccuracy())+"% \n");

      }
      
      double finTime = System.currentTimeMillis();
      
      System.out.println("This generation executed in "+(finTime-initTime)+"\n");

      for (int i = 1; i < 11; i++){
         
         if (i%4 != 0){
            Mutate[] myMutate = new Mutate[4];

            initTime = System.currentTimeMillis();
            for (int j = 0; j < 4; j++){
               myMutate[j] = new Mutate(myNodes[j], (int)(myRandom.nextDouble()*100));
               myMutate[j].start();
            }

            for (int j = 0; j < 4; j++){
               try{
                  myMutate[j].join();
               }

               catch(Exception e){
                  System.out.println(e);
               }

               myNodes[j].evaluate();
            }
            
            sort(myNodes);

            System.out.println("Generation "+i+" from mutation.");
            for (int j = 0; j < 4; j++){
               System.out.println("Fitness:");
               System.out.println(df.format(myNodes[j].getFitness())+"%");
               System.out.println("Classified as:  Positives | Negative");
               System.out.println("True Postive       "+ df.format(myNodes[j].getTruePositives()) +"       "+ df.format(myNodes[j].getFalseNegatives()));
               System.out.println("True Negatives:    "+ df.format(myNodes[j].getFalsePositives()) +"       " + df.format(myNodes[j].getTrueNegatives()));
               System.out.println("Accuracy Score");
               System.out.println(df.format(myNodes[j].getAccuracy())+"% \n");
            }
            finTime = System.currentTimeMillis();
            System.out.println("This generation executed in "+(finTime-initTime)+"\n");
         }

         else{
            Crossover[] myCrossover = new Crossover[4];
            int a, b, c, d;
            initTime = System.currentTimeMillis();
            a = (int)(myRandom.nextDouble()*4);
            b = (int)(myRandom.nextDouble()*4);
            c = (int)(myRandom.nextDouble()*4);
            d = (int)(myRandom.nextDouble()*4);

            while (a == b | a == c | a == d | b == c | b == d | c == d){
               a = (int)(myRandom.nextDouble()*4);
               b = (int)(myRandom.nextDouble()*4);
               c = (int)(myRandom.nextDouble()*4);
               d = (int)(myRandom.nextDouble()*4);
            }

            for (int j = 0; j < 4; j++){
               myCrossover[j] = new Crossover(myNodes[a], myNodes[b], (int)(myRandom.nextDouble()*100));
               myCrossover[j].start();
            }

            for (int j = 0; j < 4; j++){
               try{
               myCrossover[j].join();
               }

               catch(Exception e){
                  System.out.println(e);
               }

               myNodes[j].evaluate();
            }
            
            sort(myNodes);

            System.out.println("Generation "+i+" from crossover");
            for (int j = 0; j < 4; j++){
               System.out.println("Fitness:");
               System.out.println(df.format(myNodes[j].getFitness())+"%");
               System.out.println("Classified as:  Positives | Negative");
               System.out.println("True Postive       "+ df.format(myNodes[j].getTruePositives()) +"       "+ df.format(myNodes[j].getFalseNegatives()));
               System.out.println("True Negatives:    "+ df.format(myNodes[j].getFalsePositives()) +"       " + df.format(myNodes[j].getTrueNegatives()));
               System.out.println("Accuracy Score");
               System.out.println(df.format(myNodes[j].getAccuracy())+"% \n");
            }
            finTime = System.currentTimeMillis();
            System.out.println("This generation executed in "+(finTime-initTime)+"\n");
         }
      }
      return myNodes;
   }

   public static double[][] processData(String[][] rawData){
      int x = rawData.length;
      int y = rawData[0].length;

      double[][] myProcessedData = new double[x][y];
      ArrayList<String> myList = new ArrayList<>();

      for (int j = 0; j < y; j++){
         for (int i = 0; i < x; i++){
            try{
               myProcessedData[i][j] = Double.parseDouble(rawData[i][j]);
               //System.out.println(myProcessedData[i][j]);
            }
            catch(Exception e){
               if (myList.contains(rawData[i][j])){
                  myProcessedData[i][j] = myList.indexOf(rawData[i][j]);
                  //System.out.println(myProcessedData[i][j]);
               }
               else{
                  myList.add(rawData[i][j]);
                  myProcessedData[i][j] = myList.indexOf(rawData[i][j]);
                  //System.out.println(myProcessedData[i][j]);
               }
            }
         }

      }

      return myProcessedData;
   }
   
   public static int[] getResults(String fileName){
      int[] results;

      try{

         File myFile = new File("../file/"+fileName);
         Scanner myFileObj = new Scanner(myFile);

         int x = 0;
         int y = (myFileObj.nextLine().split(",").length)-1;

         while (myFileObj.hasNextLine()){
            myFileObj.nextLine();
            x++; 
         } 
         
         myFileObj.close();
         myFileObj = new Scanner(myFile);

         results = new int[x];
         myFileObj.nextLine();
         
         int i = 0;

         while (myFileObj.hasNextLine()){
            results[i] = Integer.parseInt(myFileObj.nextLine().split(",")[y]);
            i++;
         }

         myFileObj.close();
      }
         
      catch(FileNotFoundException e){
         results = null;
         System.out.println("File not found. Check filename carefully and make sure file the target file is stored in the \"file\"");
         System.exit(0);
      }

      return results;
   }

   public static String[][] readMyFile(String fileName){
      String[][] myFileData;

      try{

         File myFile = new File("../file/"+fileName);
         Scanner myFileObj = new Scanner(myFile);

         int x = 0;
         int y = (myFileObj.nextLine().split(",").length)-1;
         
         while (myFileObj.hasNextLine()){
            myFileObj.nextLine();
            x++; 
         }

         myFileObj.close();
         myFileObj = new Scanner(myFile);

         myFileData = new String[x][y];
         myFileObj.nextLine();
         
         int i = 0;

         while (myFileObj.hasNextLine()){
            String[] temp = myFileObj.nextLine().split(",");
            for (int j = 0; j < y; j++){
               myFileData[i][j] = temp[j];
            }
            i++;
         }

         myFileObj.close();
      }
         
      catch(FileNotFoundException e){
         myFileData = null;
         System.out.println("File not found. Check filename carefully and make sure file the target file is stored in the \"file\"");
         System.exit(0);
      }
      return myFileData;
   }

   public static void sort(Node[] node){
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
      }
   } 
}
