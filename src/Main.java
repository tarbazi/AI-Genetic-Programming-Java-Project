import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class Main{
   public static void main(String[] args){
      Random myRandom = new Random(27);
      int height = Integer.parseInt(args[0]);

      int numFiles = Integer.parseInt(args[1]);
      double[][] fileData1;
      double[][] fileData2;
      double[][] fileData3;

      int[] results1;
      int[] results2;
      int[] results3;

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

         }

      else if(numFiles == 2){

         }

      else{

         }
      
   }

   public static double[][] processData(String[][] rawData){
      return null;
   }
   
   public static int[] getResults(String fileName){
      return null;
   }

   public static String[][] readMyFile(String fileName){
      String[][] myFileData;

      try{

         File myFile = new File("../file/"+fileName);
         Scanner myFileObj = new Scanner(myFile);

         int x = 0;
         int y = myFileObj.nextLine().split(",").length;

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
