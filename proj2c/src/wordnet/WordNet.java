package wordnet;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    private HashGraph hg;
    In sysIn;
    In hyponymsIn;
    HashMap<Integer ,Set<String>> num2WordMap;
    HashMap<String ,Set<Integer>> word2NumMap;
    public WordNet(String synsetsFileName, String hyponymsFileName) {
        this.hg = new HashGraph();
        sysIn = new In(synsetsFileName);
        hyponymsIn = new In(hyponymsFileName);
        num2WordMap = new HashMap<>();
        word2NumMap = new HashMap<>();

        while (!sysIn.isEmpty()) {
            String sysNextLine = sysIn.readLine();

            String[] sysSplitLine = sysNextLine.split(",");

            num2WordMap.putIfAbsent(Integer.parseInt(sysSplitLine[0]),new HashSet<>());
            num2WordMap.get(Integer.parseInt(sysSplitLine[0])).addAll(List.of(sysSplitLine[1].split(" ")));
            for (String word : List.of(sysSplitLine[1].split(" "))) {
                word2NumMap.putIfAbsent(word, new HashSet<>());
                word2NumMap.get(word).add(Integer.parseInt(sysSplitLine[0]));
            }


            hg.addVertex(Integer.parseInt(sysSplitLine[0]));
        }
        while (!hyponymsIn.isEmpty()) {
            String hypoNextLine = hyponymsIn.readLine();
            String[] hypoSplitLine = hypoNextLine.split(",");
            for (int i = 1; i < hypoSplitLine.length; i++) {
                hg.addEdge(Integer.parseInt(hypoSplitLine[0]), Integer.parseInt(hypoSplitLine[i]));
            }
        }
    }
    private Set<Integer> word2num(String word) {
        return word2NumMap.get(word);
    }

    private Set<String> num2word(Integer num) {
        return num2WordMap.get(num);
    }

    public PriorityQueue<String> getHyponyms(String thisWord) {
    Set<Integer> wordIds = word2num(thisWord);
    Set<Integer> hyponymsID = new HashSet<>();
    Set<String> hyponyms = new HashSet<>();
    for (Integer id : wordIds) {
        idDFSHelper(id, hyponymsID);
    }
    for (Integer id : hyponymsID) {
        hyponyms.addAll(num2word(id));
    }
    return new PriorityQueue<>(hyponyms);
    }

    private void idDFSHelper(Integer StartId,Set<Integer>visitedAllHypo) {
        if (visitedAllHypo.contains(StartId)) return;
        visitedAllHypo.add(StartId);
        for (int neighbor : hg.getNeighbors(StartId)) {
            idDFSHelper(neighbor, visitedAllHypo);
        }
    }

    private void idDFSAncestorHelper(Integer StartId,Set<Integer>visitedAllHypo) {
        if (visitedAllHypo.contains(StartId)) return;
        visitedAllHypo.add(StartId);
        for (int neighbor : hg.getAncestors(StartId)) {
            idDFSAncestorHelper(neighbor, visitedAllHypo);
        }
    }

    public PriorityQueue<String> getAncestors(String thisWord) {
        Set<Integer> wordIds = word2num(thisWord);
        Set<Integer> ancestorID = new HashSet<>();
        Set<String> ancestors = new HashSet<>();
        for (Integer id : wordIds) {
            idDFSAncestorHelper(id, ancestorID);
        }
        for (Integer id : ancestorID) {
            ancestors.addAll(num2word(id));
        }
        return new PriorityQueue<>(ancestors);
    }
    /*
    public void printwordnet() {
        for (Map.Entry<String, Set<Integer>> entry : word2NumMap.entrySet()) {
            System.out.println(entry.getKey() + " => " + entry.getValue());
        }

    }
    */

}
