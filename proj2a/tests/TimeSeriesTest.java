import ngrams.TimeSeries;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        List<Integer> expectedYears = new ArrayList<>(Arrays.asList(1991, 1992, 1994, 1995));
        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>(Arrays.asList(0.0, 100.0, 600.0, 500.0));
        for (int i = 0; i < expectedTotal.size(); i++) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }

    @Test
    public void testPlusWithMissingYears() {
        TimeSeries ts1 = new TimeSeries();
        ts1.put(2000, 100.0);
        ts1.put(2001, 200.0);

        TimeSeries ts2 = new TimeSeries();
        ts2.put(2002, 300.0);
        ts2.put(2003, 400.0);

        TimeSeries result = ts1.plus(ts2);
        List<Integer> expectedYears = new ArrayList<>(Arrays.asList(2000, 2001, 2002, 2003));
        assertThat(result.years()).isEqualTo(expectedYears);

        List<Double> expectedData = new ArrayList<>(Arrays.asList(100.0, 200.0, 300.0, 400.0));
        for (int i = 0; i < expectedData.size(); i++) {
            assertThat(result.data().get(i)).isWithin(1E-10).of(expectedData.get(i));
        }
    }

    @Test
    public void testDividedByMissingYearThrows() {
        TimeSeries ts1 = new TimeSeries();
        ts1.put(2000, 100.0);
        ts1.put(2001, 200.0);

        TimeSeries ts2 = new TimeSeries();
        ts2.put(2001, 100.0); // 缺少2000年

        // 这里应抛出异常
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ts1.dividedBy(ts2);
        });
        assertThat(exception).hasMessageThat().contains("Year 2000 is missing in the divisor TimeSeries.");
    }

    @Test
    public void testDividedByWithEmptySeriesThrows() {
        TimeSeries ts1 = new TimeSeries();
        ts1.put(2000, 100.0);

        TimeSeries ts2 = new TimeSeries(); // 空的 TimeSeries

        // 应抛出异常
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ts1.dividedBy(ts2);
        });
        assertThat(exception).hasMessageThat().contains("Year 2000 is missing in the divisor TimeSeries.");
    }

    @Test
    public void testDividedByEmpty() {
        TimeSeries ts1 = new TimeSeries();
        ts1.put(2000, 100.0);

        TimeSeries ts2 = new TimeSeries(); // 空的 TimeSeries。

        try {
            ts1.dividedBy(ts2);
        } catch (IllegalArgumentException e) {
            assertThat(e).isNotNull();
        }
    }

    @Test
    public void testConstructWithRange() {
        TimeSeries original = new TimeSeries();
        original.put(1990, 10.0);
        original.put(1991, 20.0);
        original.put(1992, 30.0);
        original.put(1993, 40.0);

        TimeSeries rangeTs = new TimeSeries(original, 1991, 1992);
        List<Integer> expectedYears = new ArrayList<>(Arrays.asList(1991, 1992));
        assertThat(rangeTs.years()).isEqualTo(expectedYears);

        List<Double> expectedData = new ArrayList<>(Arrays.asList(20.0, 30.0));
        for (int i = 0; i < expectedData.size(); i++) {
            assertThat(rangeTs.data().get(i)).isWithin(1E-10).of(expectedData.get(i));
        }
    }

    @Test
    public void testYearsAndDataConsistency() {
        TimeSeries ts = new TimeSeries();
        ts.put(2000, 1.0);
        ts.put(2001, 2.0);
        ts.put(2002, 3.0);

        List<Integer> years = ts.years();
        List<Double> data = ts.data();

        assertThat(years.size()).isEqualTo(data.size());
        for (int i = 0; i < years.size(); i++) {
            assertThat(data.get(i)).isWithin(1E-10).of(ts.get(years.get(i)));
        }
    }
}
