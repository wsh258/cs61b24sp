package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import wordnet.HashGraph;
import wordnet.WordNet;

import java.util.List;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wordNet;
    HyponymsHandler(WordNet wordNet) {
        this.wordNet = wordNet;
    }
    @Override
    public String handle(NgordnetQuery q) {
        HashGraph wordNetGraph = wordNet.getHashGraph();
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        List<String> hyponyms = q.words();
        //words 只有一个 返回该词的同义词与所有下位词
        if (hyponyms.size() == 1) {
            String oneWord = hyponyms.getFirst();
            wordNetGraph.getNeighbors(oneWord);
        }
    }
}
