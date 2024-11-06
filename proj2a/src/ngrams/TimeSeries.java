package ngrams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        int i = startYear;
        while (i<=endYear){
            if (ts.containsKey(i)) {
                this.put(i, ts.get(i));
            }
            i++;
        }
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        List<Integer>allYears = new ArrayList<Integer>();
        for(Map.Entry<Integer, Double> entry: this.entrySet()) {
            int key = entry.getKey();
            allYears.add(key);
        }
        return allYears;
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        List<Double> allData = new ArrayList<Double>();
        for(Map.Entry<Integer, Double> entry: this.entrySet()) {
            double value = entry.getValue();
            allData.add(value);
        }
        return allData;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries sumTimeSeries = new TimeSeries(this,MIN_YEAR,MAX_YEAR);
        for(Map.Entry<Integer, Double> entry: ts.entrySet()) {
            int key = entry.getKey();
            double value = entry.getValue();
            if(sumTimeSeries.containsKey(key)) {
                value = sumTimeSeries.get(key)+value;
            }
            sumTimeSeries.put(key,value);
        }
        return sumTimeSeries;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries devidedTimeSeries = new TimeSeries(this,MIN_YEAR,MAX_YEAR);
        for(Map.Entry<Integer, Double> entry: devidedTimeSeries.entrySet()) {
            int key = entry.getKey();
            double value = entry.getValue();
            if(!ts.containsKey(key)) {
                throw new IllegalArgumentException("Year " + key + " is missing in the divisor TimeSeries.");
            }
           else {
                double value1 = ts.get(key);
                value=value/value1;
            }
            devidedTimeSeries.put(key,value);
        }
        return devidedTimeSeries;
    }
}
