package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;

import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    String WORDSFILENAME,COUNTSFILENAME;
    /**
     * Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME.
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        WORDSFILENAME = wordsFilename;
        COUNTSFILENAME = countsFilename;
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        In in = new In(WORDSFILENAME);
        TimeSeries WordTimeSeries = new TimeSeries();

        while (!in.isEmpty()){
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split("\t");

           boolean b = (Integer.parseInt(splitLine[1]) >= startYear) && (Integer.parseInt(splitLine[1]) <= endYear);
            if(splitLine[0].equals(word)&&b){
                WordTimeSeries.put(Integer.parseInt(splitLine[1]),Double.parseDouble(splitLine[2]));
            }
        }
        return WordTimeSeries;
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        return countHistory(word,MIN_YEAR,MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        In in = new In(COUNTSFILENAME);
        TimeSeries WordTimeSeries = new TimeSeries();

        while (!in.isEmpty()){
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            WordTimeSeries.put(Integer.parseInt(splitLine[0]),Double.parseDouble(splitLine[1]));
        }
        return WordTimeSeries;
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries total = totalCountHistory();
        TimeSeries wordCount = countHistory(word,startYear,endYear);
        return wordCount.dividedBy(total);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        TimeSeries total = totalCountHistory();
        TimeSeries wordCount = countHistory(word,MIN_YEAR,MAX_YEAR);
        return wordCount.dividedBy(total);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        TimeSeries weightHistory= new TimeSeries();
        for(String k : words) {
            TimeSeries a =  weightHistory(k, startYear,endYear);
            weightHistory= weightHistory.plus(a);
        }
        return weightHistory;
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words,MIN_YEAR,MAX_YEAR);
    }
}
