import jh61b.utils.Reflection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    @Test
    @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }
    @Test
    public void testAddFirstAndGet() {
        ArrayDeque61B<Integer> deque = new ArrayDeque61B<>();
        deque.addFirst(10);
        deque.addFirst(20);
        Assertions.assertEquals(Integer.valueOf(20), deque.get(0));
        Assertions.assertEquals(Integer.valueOf(10), deque.get(1));
    }

    @Test
    public void testAddLastAndRemoveFirst() {
        ArrayDeque61B<String> deque = new ArrayDeque61B<>();
        deque.addLast("A");
        deque.addLast("B");
        Assertions.assertEquals("A", deque.removeFirst());
        Assertions.assertEquals("B", deque.removeFirst());
    }
}
