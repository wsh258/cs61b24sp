import deque.Deque61B;
import deque.LinkedListDeque61B;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class LinkedListDeque61BTest {

    @Test
    public void testEquality() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");

        lld2.addLast("back");

        lld2.addLast("middle");

        // Use Truth framework to check equality
        assertThat(lld1).isEqualTo(lld2);
    }
    @Test
    public void testTostring() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();


        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        // Use Truth framework to check equality
        assertThat(lld1.toString()).isEqualTo("[front, middle, back]");
    }
}
