package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import ngrams.TimeSeries;

import java.util.List;


public class HistoryTextHandler extends NgordnetQueryHandler {
    NGramMap map ;
    TimeSeries MapTimeSeries = new TimeSeries();

    public HistoryTextHandler(NGramMap map){
          this.map=map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        StringBuilder response = new StringBuilder();
        for(String word : words){
            response.append(word).append(": {");
            MapTimeSeries = map.weightHistory(word,startYear,endYear);
            List<Integer> years = MapTimeSeries.years();
            List<Double> weight = MapTimeSeries.data();
            int size = years.size();
            for(int i = 0; i<size-1;i++){
                response.append(years.get(i)).append("=").append(weight.get(i)).append(", ");
            }
            response.append(years.get(size-1)).append("=").append(weight.get(size-1));
            response.append("}\n");
        }

        return response.toString();
    }
}
