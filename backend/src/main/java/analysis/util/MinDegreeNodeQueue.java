package analysis.util;

import analysis.util.graph.Node;
import ir.temp.Temp;
import util.List;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

// A min-degree priority queue for Temp nodes
public class MinDegreeNodeQueue {
    private class TempNode {
        final Node<Temp> node;
        int degree;

        TempNode(Node<Temp> node, int degree) {
            this.node = node;
            this.degree = degree;
        }
    }

    private PriorityQueue<TempNode> queue;

    // Map Node<Temp> to TempNode for easy lookup,
    // since our graphs work on Node<Temp> instances
    private Map<Node<Temp>, TempNode> map;

    public MinDegreeNodeQueue() {
        queue = new PriorityQueue<>(Comparator.comparingInt(t -> t.degree));
        map = new HashMap<>();
    }

    // Add a new node to the queue
    public void add(Node<Temp> node) {
        TempNode t = new TempNode(node, numUniqueConnectedNodes(node));
        queue.add(t);
        map.put(node, t);
    }

    // Count unique nodes connected by an edge
    private int numUniqueConnectedNodes(Node<Temp> node) {
        return node.succ().union(node.pred()).size();
    }

    // Pop the first element off of the queue
    public Node<Temp> next() {
        TempNode t = queue.poll();
        if (t == null) {
            return null;
        }
        map.remove(t.node);
        if (t != null) {
            for (Node<Temp> connectedNode : connectedNodes(t)) {
                decrementDegree(connectedNode);
            }
        }
        return t.node;
    }

    // Decrement the degree of a node in the queue
    private void decrementDegree(Node<Temp> node) {
        TempNode t = map.remove(node);
        if (t != null) {
            queue.remove(t);
            t = new TempNode(t.node, t.degree - 1);
            queue.add(t);
            map.put(node, t);
        }
    }

    // Return nodes directly connected to t in the graph
    private List<Node<Temp>> connectedNodes(TempNode t) {
        Node<Temp> node = t.node;
        return node.succ().union(node.pred());
    }

    // Return true iff queue is empty
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
