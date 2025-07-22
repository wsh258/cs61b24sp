package wordnet;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    private HashGraph hg;
    In sysIn;
    In hyponymsIn;
    HashMap<Integer ,Set<String>> Num2WordMap;
    HashMap<String ,Set<Integer>> Word2NumMap;
    public WordNet(String synsetsFileName, String hyponymsFileName) {
        this.hg = new HashGraph();
        sysIn = new In("./data/wordnet/" + synsetsFileName);
        hyponymsIn = new In("./data/wordnet/" + hyponymsFileName);
        Num2WordMap = new HashMap<>();
        Word2NumMap = new HashMap<>();

        while (!sysIn.isEmpty()) {
            String sysNextLine = sysIn.readLine();
            String[] sysSplitLine = sysNextLine.split(",");

            Num2WordMap.putIfAbsent(Integer.parseInt(sysSplitLine[0]),new HashSet<>());
            Num2WordMap.get(Integer.parseInt(sysSplitLine[0])).addAll(List.of(sysSplitLine[1].split(" ")));
            for (String word : List.of(sysSplitLine[1].split(" "))) {
                Word2NumMap.putIfAbsent(word, new HashSet<>());
                Word2NumMap.get(Integer.parseInt(sysSplitLine[0])).addAll(List.of(sysSplitLine[1].split(" ")));
            }


            hg.addVertex(Integer.parseInt(sysSplitLine[0]));
        }
        while (!hyponymsIn.isEmpty()) {
            String hypoNextLine = hyponymsIn.readLine();
            String[] hypoSplitLine = hypoNextLine.split(",");
            for (int i = 1; i < hypoSplitLine.length; i++) {
                this.hg.addEdge(Integer.parseInt(hypoSplitLine[0]), Integer.parseInt(hypoSplitLine[i]));
            }
        }
    }
    private int word2num(String word) {
        return Word2NumMap.keySet().;
    }
    public PriorityQueue<String> getHyponyms(String thisWord) {

    }

}
