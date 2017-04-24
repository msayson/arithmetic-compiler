package test.analysis.util;

import analysis.util.MinDegreeNodeQueue;
import analysis.util.graph.Graph;
import analysis.util.graph.Node;
import ir.temp.Temp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestMinDegreeNodeQueue {
    @Test
    void next_EmptyGraph() {
        MinDegreeNodeQueue queue = new MinDegreeNodeQueue();
        Assertions.assertNull(queue.next());
    }

    @Test
    void next_OneNodeGraph() {
        MinDegreeNodeQueue queue = new MinDegreeNodeQueue();
        Graph<Temp> graph = new Graph();
        Node<Temp> node1 = graph.newNode(new Temp());
        queue.add(node1);
        Assertions.assertEquals(node1, queue.next());
        Assertions.assertNull(queue.next());
    }

    @Test
    void next_MultNodeGraph() {
        MinDegreeNodeQueue queue = new MinDegreeNodeQueue();
        Graph<Temp> graph = new Graph();
        Node<Temp> node1 = graph.newNode(new Temp());
        Node<Temp> node2 = graph.newNode(new Temp());
        Node<Temp> node3 = graph.newNode(new Temp());
        Node<Temp> node4 = graph.newNode(new Temp());
        graph.addEdge(node1, node2);
        graph.addEdge(node1, node3);
        queue.add(node1); // node1 has degree 2
        queue.add(node2); // node2 has degree 1
        queue.add(node3); // node3 has degree 1
        queue.add(node4); // node4 has degree 0
        Assertions.assertEquals(node4, queue.next());
        Node<Temp> second = queue.next();
        Assertions.assertTrue(node2.equals(second) || node3.equals(second));
        Node<Temp> third = queue.next();
        Node<Temp> candidateThird = node2.equals(second) ? node3 : node2;
        Assertions.assertTrue(candidateThird.equals(third) || node1.equals(third));
        Node<Temp> expectedFourth = candidateThird.equals(third) ? node1 : candidateThird;
        Assertions.assertEquals(expectedFourth, queue.next());
    }

    @Test
    void isEmpty() {
        MinDegreeNodeQueue queue = new MinDegreeNodeQueue();
        Assertions.assertTrue(queue.isEmpty());

        Graph<Temp> graph = new Graph();
        queue.add(graph.newNode(new Temp()));
        Assertions.assertFalse(queue.isEmpty());
    }

}