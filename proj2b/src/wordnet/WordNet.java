package wordnet;

import edu.princeton.cs.algs4.In;

import java.util.HashMap;

public class WordNet {
    private HashGraph hg;

    public WordNet(String synsetsFileName, String hyponymsFileName) {
        this.hg = new HashGraph();
        In sysIn = new In("./data/wordnet/" + synsetsFileName);
        In hyponymsIn = new In("./data/wordnet/" + hyponymsFileName);
        HashMap<Integer ,String> Num2WordMap = new HashMap<>();
        while (!sysIn.isEmpty()) {
            String sysNextLine = sysIn.readLine();
            String[] sysSplitLine = sysNextLine.split(",");

            Num2WordMap.put(Integer.parseInt(sysSplitLine[0]) ,sysSplitLine[1]);

            this.hg.addVertex(Integer.parseInt(sysSplitLine[0]));
        }
        while (!hyponymsIn.isEmpty()) {
            String hypoNextLine = hyponymsIn.readLine();
            String[] hypoSplitLine = hypoNextLine.split(",");
            for (int i = 1; i < hypoSplitLine.length; i++) {
                this.hg.addEdge(Integer.parseInt(hypoSplitLine[0]), Integer.parseInt(hypoSplitLine[i]));
            }
        }
    }
    public HashGraph getHashGraph() {
        return hg;
    }
}
