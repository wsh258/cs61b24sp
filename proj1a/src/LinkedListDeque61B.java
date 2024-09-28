import java.util.List;
import java.util.ArrayList;
public class LinkedListDeque61B<T> implements Deque61B<T>{

    public class Node{
        T item;
        Node prev;
        Node next;
        Node(T i,Node p,Node n){
            item = i;
            prev = p;
            next = n;
        }
    }

    private int size ;
    private final Node sentinel;

    public LinkedListDeque61B(){
        sentinel = new Node(null, null, null);
        sentinel.prev=sentinel;
        sentinel.next=sentinel;
        size=0;
    }

    @Override
    public void addFirst(T x) {
    sentinel.next = new Node(x, sentinel, sentinel.next);
    sentinel.next.next.prev = sentinel.next;
    size++;
    }

    @Override
    public void addLast(T x) {
    sentinel.prev = new Node(x,sentinel.prev,sentinel);
    sentinel.prev.prev.next = sentinel.prev;
    size++;
    }

    @Override
    public List<T> toList() {
        List <T> returnList = new ArrayList<>();
        Node current = sentinel.next;
        while (current != sentinel) {
            returnList.add(current.item);
            current = current.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if(isEmpty())
            return null;
        T item = sentinel.next.item;
        sentinel.next=sentinel.next.next;
        sentinel.next.prev=sentinel;
        size--;
        return item;
    }

    @Override
    public T removeLast() {
        if(isEmpty())
            return null;
        T item = sentinel.prev.item;
        sentinel.prev.prev.next=sentinel;
        sentinel.prev=sentinel.prev.prev;
        size--;
        return item;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size)  // Check for valid index range
            return null;
        Node current = sentinel.next;
        for(int i = index;i>0;i--){
            current=current.next;
        }
        return current.item;
    }

    @Override
    public T getRecursive(int index) {
        // Base case: check for invalid index range
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursive(index, sentinel.next); // Start recursion with the first node
    }

    private T getRecursive(int index, Node current) {


        if (index == 0) {
            return current.item;  // Base case: return the item if index is 0
        }
        else {
            return getRecursive(index - 1, current.next);  // Recursive case: reduce index and move to next node
        }
    }
}
