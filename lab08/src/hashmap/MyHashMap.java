package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    // You should probably define some more!
    int initialCapacity = 16;
    double loadFactor = 0.75;
    private int size = 0;
    /** Constructors */
    public MyHashMap() {
        buckets = new Collection[initialCapacity];

    }

    public MyHashMap(int initialCapacity) {
        this.initialCapacity=initialCapacity;
        buckets = new Collection[initialCapacity];

    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
        buckets = new Collection[initialCapacity];

    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        // TODO: Fill in this method.
        return new LinkedList<>();
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        int i = Math.floorMod(key.hashCode(),initialCapacity);
        if(buckets[i]==null){
            buckets[i]=createBucket();
        }
        Node temp = new Node(key,value);
        V lookup = get(key);
        if (lookup == null) {
            size = size + 1;
            buckets[i].add(temp);
        } else {
            for (Node node : buckets[i]){
                if(node.key .equals(key) )
                    node.value=value;
            }
        }
        if(((double) size /initialCapacity)>loadFactor){
            resize();
        }
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        int i = Math.floorMod(key.hashCode(),initialCapacity);
        if(buckets[i]==null) return null;
        for (Node node : buckets[i]){
            if(node.key .equals(key) )
                return node.value;
        }
        return null;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        int i = Math.floorMod(key.hashCode(),initialCapacity);
        if(buckets[i]==null) return false;
        for (Node node : buckets[i]){
            if(node.key .equals(key) )
                return true;
        }
        return false;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        buckets = new Collection[initialCapacity];
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = createBucket();  // 使用 createBucket 方法初始化每个元素
        }
        size=0;
    }


    /**
     * Returns a Set view of the keys contained in this map. Not required for this lab.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();

    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
    private void resize() {
        int newCapacity = initialCapacity * 2;  // 新容量
        Collection<Node>[] newBuckets = new Collection[newCapacity];

        // 初始化新桶数组中的每个桶
        for (int i = 0; i < newCapacity; i++) {
            newBuckets[i] = createBucket();
        }

        // 遍历旧数组，将每个节点重新哈希到新数组中
        for (Collection<Node> bucket : buckets) {
            if (bucket != null) {
                for (Node node : bucket) {
                    int newIndex = Math.floorMod(node.key.hashCode(), newCapacity);
                    newBuckets[newIndex].add(node);
                }
            }
        }

        // 更新哈希表的桶数组和容量
        buckets = newBuckets;
        initialCapacity = newCapacity;
    }

}
