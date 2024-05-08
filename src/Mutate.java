import java.util.Random;

public class Mutate extends Thread{
    Node parent;
    Random myRandom;

    String[] terminalSet;  //terminal set variables
    String[] functionalSet = {"+","-","x"}; //functional set values

    int[] initDir = {-1,1};
    int[] finalDir = {-1, 0, 1};


    public Mutate(){
        }
    
    public Mutate(Node parent, int i){
        this.parent = parent;
        terminalSet = parent.getTerminalSet();
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
                    String temp = traverse.getVar();
                    //System.out.println("initially: "+traverse.getVar());
                    while (temp.equals(traverse.getVar())){
                        temp = functionalSet[(int)(myRandom.nextDouble()*functionalSet.length)];
                    }
                    traverse.setVar(temp);
                    //System.out.println("finally: "+traverse.getVar());
                    break;
                    }
                dir = finalDir[(int)(myRandom.nextDouble()*finalDir.length)];
                }
            else{
                String temp = traverse.getVar();
                //System.out.println("initially: "+traverse.getVar());
                while (temp.equals(traverse.getVar())){
                    temp = terminalSet[(int)(myRandom.nextDouble()*terminalSet.length)];
                }
                traverse.setVar(temp);
                //System.out.println(traverse.getVar());
                break;
                }
            }
        }
}