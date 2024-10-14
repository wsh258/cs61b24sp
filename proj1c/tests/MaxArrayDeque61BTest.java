import deque.MaxArrayDeque61B;
import org.junit.jupiter.api.Test;
import java.util.Comparator;
import static com.google.common.truth.Truth.assertThat;

public class MaxArrayDeque61BTest {

    private static class StringLengthComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.length() - b.length();
        }
    }

    private static class ReverseStringComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return b.compareTo(a); // 逆序比较
        }
    }

    private static class IntegerComparator implements Comparator<Integer> {
        public int compare(Integer a, Integer b) {
            return a - b;  // 升序比较
        }
    }

    private static class ReverseIntegerComparator implements Comparator<Integer> {
        public int compare(Integer a, Integer b) {
            return b - a;  // 逆序比较
        }
    }

    @Test
    public void basicTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addFirst("");
        mad.addFirst("2");
        mad.addFirst("fury road");
        mad.addFirst("fury road");
        assertThat(mad.max()).isEqualTo("fury road");
    }

    // 测试空队列
    @Test
    public void emptyDequeTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());


        assertThat(mad.max()).isNull(); // 队列为空，应该返回null
    }

    // 测试不同的 Comparator: 逆序比较器
    @Test
    public void CustomComparatorTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new StringLengthComparator());
        mad.addFirst("apple");
        mad.addFirst("banana");
        mad.addFirst("cherry");
        mad.addFirst("duck");
        assertThat(mad.max(String::compareTo)).isEqualTo("duck");
    }

    // 使用不同的Comparator，通过 max(Comparator)
    @Test
    public void reverseComparatorTest() {
        MaxArrayDeque61B<String> mad = new MaxArrayDeque61B<>(new ReverseStringComparator());
        mad.addFirst("apple");
        mad.addFirst("banana");
        mad.addFirst("cherry");
        assertThat(mad.max(new ReverseStringComparator())).isEqualTo("apple"); // 使用自定义Comparator
    }

    // 测试整型数据
    @Test
    public void integerTest() {
        MaxArrayDeque61B<Integer> mad = new MaxArrayDeque61B<>(new IntegerComparator());
        mad.addFirst(10);
        mad.addFirst(20);
        mad.addFirst(5);
        assertThat(mad.max()).isEqualTo(20); // 最大值是 20
    }

    // 测试整型数据并使用自定义比较器
    @Test
    public void integerReverseTest() {
        MaxArrayDeque61B<Integer> mad = new MaxArrayDeque61B<>(new IntegerComparator());
        mad.addFirst(10);
        mad.addFirst(20);
        mad.addFirst(5);
        assertThat(mad.max(new ReverseIntegerComparator())).isEqualTo(5); // 逆序比较器，最小值 5 被认为是最大值
    }

    // 测试添加和删除元素后的 max 行为
    @Test
    public void addRemoveTest() {
        MaxArrayDeque61B<Integer> mad = new MaxArrayDeque61B<>(new IntegerComparator());
        mad.addFirst(10);
        mad.addFirst(20);
        mad.addFirst(5);

        assertThat(mad.max()).isEqualTo(20); // 初始最大值 20

        mad.removeFirst();
        assertThat(mad.max()).isEqualTo(20); // 删除 20 后，最大值应该是 10

        mad.removeLast();
        assertThat(mad.max()).isEqualTo(20); // 删除 5 后，仍然是 10
    }

    // 测试重复元素的情况
    @Test
    public void duplicateElementsTest() {
        MaxArrayDeque61B<Integer> mad = new MaxArrayDeque61B<>(new IntegerComparator());
        mad.addFirst(5);
        mad.addFirst(10);
        mad.addFirst(10); // 添加重复元素
        assertThat(mad.max()).isEqualTo(10); // 最大值应该是 10
    }

}
