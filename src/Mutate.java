import java.util.Random;

public class Mutate extends Thread{
    Node parent;
    Random myRandom;

    String[] terminalSet = {"X1", "X2", "X3", "X4", "X5", "X6", "X7", "X8"};  //terminal set variables
    String[] functionalSet = {"+","-","x"}; //functional set values

    int[] initDir = {-1,1};
    int[] finalDir = {-1, 0, 1};


    public Mutate(){
        }
    
    public Mutate(Node parent, int i){
        this.parent = parent;
        myRandom = new Random(i);
        }

    public void run(){
        mutate();
        }

    public void mutate(){
        int dir = initDir[(int)(myRandom.nextDouble()*initDir.length)];
        Node traverse = parent;

        while (true){
            if (traverse.leftChild != null){
                if (dir == 1){
                    traverse = traverse.rightChild;
                    }
                else if (dir == -1){
                    traverse = traverse.leftChild;
                    }
                else{
                    traverse.setVar(functionalSet[(int)(myRandom.nextDouble()*functionalSet.length)]);
                    break;
                    }
                dir = finalDir[(int)(myRandom.nextDouble()*finalDir.length)];
                }
            else{
                traverse.setVar(terminalSet[(int)(myRandom.nextDouble()*terminalSet.length)]);
                break;
                }
            }
        }
}