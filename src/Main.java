import java.io.File;
import java.io.FileNotFoundException;
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
      double[][] fileData3 = null;

      int[] results1 = null;
      int[] results2 = null;
      int[] results3 = null; 

      for (int i = 0; i < numFiles; i++){
         if (i == 0){
            fileData1 = processData(readMyFile(args[2]));
            results1 = getResults(args[2]);
            }
         else if (i == 1){
            fileData2 = processData(readMyFile(args[3]));
            results2 = getResults(args[3]);
            }
         else{
            fileData3 = processData(readMyFile(args[4]));
            results3 = getResults(args[4]);
            }
         }

      if (numFiles == 1){
         Node[] myNodes = new Node[4];
         for (int i = 0; i < 4; i++){
            myNodes[i] = new Node(fileData1, results1, i, height);
            myNodes[i].start();
            }
         for (int i = 0; i < 4; i++){
            try{
               myNodes[i].join();
               System.out.println("Fitness:");
               System.out.println(myNodes[i].getFitness()+"\n");
               System.out.println("True Positives:");
               System.out.println(myNodes[i].getTruePositives()+"\n");
               System.out.println("True Negatives:");
               System.out.println(myNodes[i].getTrueNegatives()+"\n");
               System.out.println("False Positives:");
               System.out.println(myNodes[i].getFalsePositives()+"\n");
               System.out.println("False Negatives:");
               System.out.println(myNodes[i].getFalsePositives()+"\n");
               }
            catch(Exception e){
               System.out.println(e);
               }
            }
         
         }

      else if(numFiles == 2){
         Node[] myNodes = new Node[4];
         for (int i = 0; i < 4; i++){
            myNodes[i] = new Node(fileData1, results1, results1.length, height);
            myNodes[i].start();
            }
         }

      else{
         Node myNode1 = new Node(fileData1, results1, results1.length, height);
         myNode1.insert(0);

         Node myNode2 = new Node(fileData2, results2, results2.length, height);
         myNode2.insert(0);

         
         }
      
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
