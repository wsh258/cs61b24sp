package wordnet;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashGraph {
    // 外层使用 HashMap 映射顶点名称到其邻接列表
    private Map<Integer, List<Integer>> adj;
    // 如果是加权图，内层列表可以存储边对象或 Pair<String, Integer>
    // private Map<String, List<Edge>> adj;
    private Map<Integer, List<Integer>> reverseGraph;

    public HashGraph() {
        adj = new HashMap<>();
        reverseGraph = new HashMap<>();
    }

    public void addVertex(Integer vertex) {
        adj.putIfAbsent(vertex, null);
        reverseGraph.putIfAbsent(vertex,null);
    }

    public void addEdge(Integer src, Integer destination) {
        // 确保顶点存在
        addVertex(src);
        addVertex(destination);

        // 对于有向图

        adj.get(src).add(destination);
        reverseGraph.get(destination).add(src);
        // 如果是无向图，还需要添加反向边
        // adj.get(dest).add(src);
    }

    public List<Integer> getNeighbors(Integer vertex) {
        return adj.getOrDefault(vertex, new ArrayList<>());
    }

    public boolean hasEdge(Integer src, Integer destination) {
        return adj.containsKey(src) && adj.get(src).contains(destination);
    }

    public void printGraph() {
        for (Map.Entry<Integer, List<Integer>> entry : adj.entrySet()) {
            System.out.println("Vertex " + entry.getKey() + " is connected to: " + entry.getValue());
        }
    }

    public List<Integer> getAncestors(Integer vertex) {
        return reverseGraph.getOrDefault(vertex, new ArrayList<>());
    }




}

