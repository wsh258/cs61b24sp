import java.util.List;
import java.lang.Math;
import java.util.ArrayList;
public class ArrayDeque61B<T> implements Deque61B<T>{

    private T[] items;
    private int front;
    private int back;
    private int size;
    private static int INIT_CAPACITY = 8;


    public ArrayDeque61B() {
        items = (T[]) new Object[INIT_CAPACITY];
        front = 0;
        back = 1;
        size = 0;
    }

    private void resize(int newCapacity) {
        T[] newItems = (T[]) new Object[newCapacity];
        // Recopy elements in proper order
        for (int i = 0; i < size; i++) {
            newItems[i] = get(i);
        }
        items = newItems;
        front = newCapacity - 1;
        back = size;
    }

    @Override
    public void addFirst(T x) {

        if (size == items.length) {
            resize(items.length * 2);
        }
        items[front] = x;
        front=Math.floorMod(front-1,items.length);
        size++;

    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[back]=x;
        back=Math.floorMod(back+1,items.length);
        size++;
    }

    @Override
    public List<T> toList() {
        List <T> returnList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            returnList.add(get(i));
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if(isEmpty())
            return null;
        if (size <= items.length / 4) {
            resize(items.length / 2);
        }
        front=Math.floorMod(front+1,items.length);
        T x = items[front];
        items[front]=null;
        size--;
        return x;
    }

    @Override
    public T removeLast() {
        if(isEmpty())
            return null;
        if (size <= items.length / 4) {
            resize(items.length / 2);
        }
        back=Math.floorMod(back-1,items.length);
        T x = items[back];
        items[back]=null;
        size--;
        return x;
    }

    @Override
    public T get(int index) {
       if(index>=size||index<0)
            return null;
       return items[Math.floorMod(front+index+1,items.length)];
    }

    @Override
    public T getRecursive(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        return getRecursiveHelper(front, index, 0);
    }

    /**
     * 辅助递归方法
     * @param currentPos 当前数组中的位置
     * @param targetIndex 目标索引
     * @param currentIndex 当前递归的索引
     * @return 目标元素
     */
    private T getRecursiveHelper(int currentPos, int targetIndex, int currentIndex){
        if (currentIndex == targetIndex) {
            // 计算实际数组位置
            int actualPos = Math.floorMod(currentPos + 1, items.length);
            return items[actualPos];
        } else {
            // 递归调用，移动到下一个位置
            return getRecursiveHelper(Math.floorMod(currentPos + 1, items.length), targetIndex, currentIndex + 1);
        }

    }
}
