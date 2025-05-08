import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    int size = 0;

    private class Node {
        K k;
        V v;
        Node left;
        Node right;

        Node(K k, V v) {
            this.k = k;
            this.v = v;
        }
    }

    private class BSTree {
        private Node root;

        // 添加节点
        private void add(K k, V v) {
            root = addRecursive(root, k, v);
        }

        private Node addRecursive(Node current, K k, V v) {
            if (current == null) {
                return new Node(k, v);
            }
            int cmp = k.compareTo(current.k);
            if (cmp < 0) {
                current.left = addRecursive(current.left, k, v);
            } else if (cmp > 0) {
                current.right = addRecursive(current.right, k, v);
            } else {
                // 如果键已经存在，更新值
                current.v = v;
            }
            return current;
        }

        private Node find(K k) {
            Node current = root;
            return findRecursive(current, k);
        }

        private Node findRecursive(Node current, K k) {
            if (current == null) {
                return null;
            }
            int cmp = k.compareTo(current.k);
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                return current;
            }
            return findRecursive(current, k);
        }
        private void delete(K key) {
            root = deleteRecursive(root, key);
        }
        private Node deleteRecursive(Node current,K key) {
            if (current == null) {
                return null;
            }
            int cmp = key.compareTo(current.k);
            //需要删除的节点小于当前节点
            if(cmp<0){
                current.left= deleteRecursive(current.left,key);
            }
            else if(cmp>0){
                current.right= deleteRecursive(current.right,key);
            }
            else {//找到要删除的节点
                size--;
                if (current.left == null && current.right == null) {
                    return null;
                }else if (current.left == null) {
                    return current.right;
                }else if (current.right == null) {
                    return current.left;
                } else {//有两个子节点
                    Node temp = findMax(current.left);
                    current.k=temp.k;
                    current.v=temp.v;
                    current.left = deleteRecursive(current.left,temp.k);
                }
            }
            return current;
        }
        private Node findMax(Node node){
            while (node.right!=null)
                node = node.right;
            return node;
        }
    }

    private BSTree tree;

    public BSTMap() {
        tree = new BSTree();
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     */
    @Override
    public void put(K key, V value) {
        if (!containsKey(key)) {
            size++;
        }
        tree.add(key, value);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     */
    @Override
    public V get(K key) {
        if (!containsKey(key)) {
            return null;
        }
        return tree.find(key).v;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     */
    @Override
    public boolean containsKey(K key) {
        return tree.find(key) != null;
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
        size = 0;
        this.tree = new BSTree();
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override

    public Set<K> keySet() {
        if (tree.root == null) {
            return null;
        }
        TreeSet<K> ks = new TreeSet<>();
        addKey(tree.root, ks);
        return ks;
    }

    private void addKey(Node root, TreeSet<K> ks) {
        if (root == null) {
            return;
        }
        ks.add(root.k);
        addKey(root.left, ks);
        addKey(root.right, ks);
    }



    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     */
    @Override
    public V remove(K key) {
        Node temp = tree.find(key);
        if (temp ==null){
            return null;
        }
        tree.delete(key);
        return temp.v;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */

    @Override
    public Iterator<K> iterator() {
//        return new BSTreeIterator();
//    }
//    private class BSTreeIterator implements Iterator<K>{
//        private Stack<Node> stack;
//
//        public BSTreeIterator() {
//            stack = new Stack<>();
//            pushLeft(tree.root);
//        }
//        private void pushLeft(Node node){
//            while (node!=null){
//                stack.push(node);
//                node=node.left;
//            }
        return keySet().iterator();
        }


//            return !stack.empty();




//            if (!hasNext()) {
//                throw new NoSuchElementException();
//            }
//            Node current = stack.pop();
//            K key = current.k;
//
//            if (current.right != null) {
//                pushLeft(current.right);
//            }
//            return key;

//
}





