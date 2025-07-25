package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import browser.NgordnetQueryType;
import ngrams.NGramMap;
import ngrams.TimeSeries;
import wordnet.WordNet;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    private final WordNet wordNet;
    private final NGramMap nGramMap;
    HyponymsHandler(NGramMap nGramMap, WordNet wordNet) {
        this.nGramMap = nGramMap;
        this.wordNet = wordNet;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();
        String output = "";
        //words 只有一个 返回该词的同义词与所有下位词
        if (q.ngordnetQueryType().equals(NgordnetQueryType.HYPONYMS)) {

            if (k == 0) {
                if (words.size() == 1) {
                    String oneWord = words.getFirst();
                    PriorityQueue<String> hyponyms = wordNet.getHyponyms(oneWord);
                    output = formattedOutput(hyponyms);

                } else if (words.size() > 1) {
                    PriorityQueue<String> hyponyms = wordNet.getHyponyms(words.getFirst());
                    HashSet<String> commonHyponyms = new HashSet<>(hyponyms);
                    for (String word : words) {
                        commonHyponyms.retainAll(wordNet.getHyponyms(word));
                    }
                    PriorityQueue<String> commonHyponymsPriorityQueue = new PriorityQueue<>(commonHyponyms);
                    output = formattedOutput(commonHyponymsPriorityQueue);
                }

            } else if (k > 0) {
                if (words.size() == 1) {
                    String oneWord = words.getFirst();
                    PriorityQueue<String> hyponyms = wordNet.getHyponyms(oneWord);
                    output = kNot0formattedOutput(hyponyms, startYear, endYear, k);
                } else if (words.size() > 1) {
                    HashSet<String> commonHyponyms = new HashSet<>(wordNet.getHyponyms(words.getFirst()));
                    for (String word : words) {
                        commonHyponyms.retainAll(wordNet.getHyponyms(word));
                    }
                    PriorityQueue<String> commonHyponymsPriorityQueue = new PriorityQueue<>(commonHyponyms);
                    output = kNot0formattedOutput(commonHyponymsPriorityQueue, startYear, endYear, k);
                }
            }
        }


        else if (q.ngordnetQueryType().equals(NgordnetQueryType.ANCESTORS)){
            if (k == 0) {
                if (words.size() == 1) {
                    String oneWord = words.getFirst();
                    PriorityQueue<String> ancestors = wordNet.getAncestors(oneWord);
                    output = formattedOutput(ancestors);

                } else if (words.size() > 1) {
                    PriorityQueue<String> ancestors = wordNet.getAncestors(words.getFirst());
                    HashSet<String> commonHyponyms = new HashSet<>(ancestors);
                    for (String word : words) {
                        commonHyponyms.retainAll(wordNet.getAncestors(word));
                    }
                    PriorityQueue<String> commonHyponymsPriorityQueue = new PriorityQueue<>(commonHyponyms);
                    output = formattedOutput(commonHyponymsPriorityQueue);
                }

            } else if (k > 0) {
                if (words.size() == 1) {
                    String oneWord = words.getFirst();
                    PriorityQueue<String> hyponyms = wordNet.getAncestors(oneWord);
                    output = kNot0formattedOutput(hyponyms, startYear, endYear, k);
                } else if (words.size() > 1) {
                    HashSet<String> commonHyponyms = new HashSet<>(wordNet.getAncestors(words.getFirst()));
                    for (String word : words) {
                        commonHyponyms.retainAll(wordNet.getAncestors(word));
                    }
                    PriorityQueue<String> commonHyponymsPriorityQueue = new PriorityQueue<>(commonHyponyms);
                    output = kNot0formattedOutput(commonHyponymsPriorityQueue, startYear, endYear, k);
                }
            }
        }
        return output;

    }

        private String formattedOutput (PriorityQueue < String > pq) {
            StringBuilder output = new StringBuilder("[");
            StringJoiner joiner = new StringJoiner(", ");
            while (!pq.isEmpty()) {
                String p = pq.poll();
                joiner.add(p);
            }
            output.append(joiner).append("]");
            return output.toString();
        }

        private String kNot0formattedOutput (PriorityQueue < String > pq,int startYear, int endYear, int k){
            HashMap<String, Double> wordCountMap = new HashMap<>();

            while (!pq.isEmpty()) {
                String p = pq.poll();
                TimeSeries wordCount = nGramMap.countHistory(p, startYear, endYear);
                double sum = 0.0;
                for (double value : wordCount.values()) {
                    sum += value;
                }
                if (sum <= 0.1) {
                    continue;
                }
                wordCountMap.put(p, sum);
                System.out.print(p + "\tcount:" + sum + "\n");
            }
            if (wordCountMap.isEmpty()) {
                return "";
            }
            PriorityQueue<Map.Entry<String, Double>> wordCountPq =
                    new PriorityQueue<>((a, b) -> Double.compare(b.getValue(), a.getValue()));
            wordCountPq.addAll(wordCountMap.entrySet());
            PriorityQueue<String> outputPq = new PriorityQueue<>();
            while (k > 0) {
                Map.Entry<String, Double> entry = wordCountPq.poll();
                if (entry != null && entry.getValue() <= 0.1) {
                    break;
                }
                String p = null;
                if (entry != null) {
                    p = entry.getKey();
                }
                k--;
                outputPq.add(p);
            }
            return formattedOutput(outputPq);
        }

}
