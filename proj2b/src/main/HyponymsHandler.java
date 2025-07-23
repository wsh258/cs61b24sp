package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import wordnet.WordNet;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringJoiner;

public class HyponymsHandler extends NgordnetQueryHandler {
    private WordNet wordNet;
    HyponymsHandler(WordNet wordNet) {
        this.wordNet = wordNet;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        StringBuilder output = new StringBuilder("[");
        //words 只有一个 返回该词的同义词与所有下位词
        if (words.size() == 1) {
            String oneWord = words.getFirst();
            PriorityQueue<String> hyponyms =  wordNet.getHyponyms(oneWord);
            StringJoiner joiner = new StringJoiner(", ");
            while (!hyponyms.isEmpty()) {
                String p = hyponyms.poll();
                joiner.add(p);
            }

            output.append(joiner).append("]");
        }
        else if (words.size() > 1) {
            HashSet<String> commonHyponyms = new HashSet<>(wordNet.getHyponyms(words.getFirst()));
            for (String word : words) {
                commonHyponyms.retainAll(wordNet.getHyponyms(word));
            }
            PriorityQueue<String> commonHyponymsPriorityQueue = new PriorityQueue<>(commonHyponyms);
            StringJoiner joiner = new StringJoiner(", ");
            while (!commonHyponymsPriorityQueue.isEmpty()) {
                String p = commonHyponymsPriorityQueue.poll();
                joiner.add(p);
            }
            output.append(joiner).append("]");
        }


        return output.toString();

    }
}
