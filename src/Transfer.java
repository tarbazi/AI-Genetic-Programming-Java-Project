public class Transfer extends Thread{
    Node myNode;
    double[][] terminalVals;
    int[] results;

    public Transfer(Node myNode, double[][] terminalVals, int[] results){
        this.myNode = myNode;
        this.terminalVals = terminalVals;
        this.results = results;
    }

    public void run(){
        myNode.populate(new String[terminalVals[0].length]);
        myNode.setTerminalValsAndResults(terminalVals, results);
    }
}