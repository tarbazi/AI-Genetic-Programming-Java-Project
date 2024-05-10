import java.util.Random;

public class Transfer extends Thread{
    Node myNode;
    double[][] terminalVals;
    int[] results;
    Random myRandom;

    public Transfer(Node myNode, double[][] terminalVals, int[] results, int i){
        this.myNode = myNode;
        this.terminalVals = terminalVals;
        this.results = results;
        myRandom = new Random(i);
    }

    public void run(){
        myNode.populate(new String[terminalVals[0].length]);
        myNode.setTerminalValsAndResults(terminalVals, results);
        myNode.adaptTerminals(terminalVals, results, myRandom);
        myNode.evaluate();
    }
}