import java.util.Arrays;
import java.util.ArrayList;
public class UnionFind {
    // TODO: Instance variables
    private int[] Union;
    private int size;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        Union = new int[N];
        Arrays.fill(Union, -1);
        size = N;
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        return -Union[find(v)];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        return Union[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        int root1 = find(v1), root2 = find(v2);
        return root1 == root2;
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
//        if (v >= 0 && v < size) {
//            ArrayList<Integer> TempArray = new ArrayList<>();
//            while (Union[v] >= 0) {
//                TempArray.addLast(v);
//                v = Union[v];
//            }
//            for (Integer integer : TempArray) {
//                Union[integer] = v;
//            }
//            return v;
//        } else {
//            throw new IllegalArgumentException("Some comment to describe the reason for throwing.");
//        }
            if (v < 0 || v >= size) {
                throw new IllegalArgumentException("v is out of bounds.");
            }
            if (Union[v] < 0) {
                return v; // 如果v是根节点，直接返回
            }
            // 路径压缩：递归地找到根节点，并将当前节点的父节点直接设为根节点
            Union[v] = find(Union[v]);
            return Union[v];
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        int root1 = find(v1), root2 = find(v2);
        if (root1 == root2) {
            return;
        }
        if (Union[root1] >= Union[root2]) {
            Union[root2] += Union[root1];
            Union[root1] = root2;
        }
        else {
            Union[root1] += Union[root2];
            Union[root2] = root1;
        }
    }
}