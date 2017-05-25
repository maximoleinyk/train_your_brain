package com.home;

import java.util.*;
import java.util.LinkedList;

public class BellmanFord {
    Vertex graph;

    BellmanFord(Vertex graph) {
        this.graph = graph;
    }

    private static final class Edge {
        double value;
        Vertex v;

        Edge(double value, Vertex v) {
            this.value = value;
            this.v = v;
        }
    }

    private static final class Vertex {
        Vertex previous;
        List<Edge> edges;
        String name;

        Vertex(String name) {
            this.edges = new LinkedList<>();
            this.name = name;
        }

        void addEdge(double value, Vertex v) {
            edges.add(new Edge(value, v));
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    public static void main(String[] args) {
        Vertex s = new Vertex("S");
        Vertex a = new Vertex("A");
        Vertex b = new Vertex("B");
        Vertex c = new Vertex("C");
        Vertex d = new Vertex("D");
        Vertex e = new Vertex("E");

        s.addEdge(8., e);
        s.addEdge(10., a);
        a.addEdge(2., c);
        c.addEdge(-2., b);
        b.addEdge(1., a);
        e.addEdge(1., d);
        d.addEdge(-4., a);
        d.addEdge(-1., c);

        BellmanFord bf = new BellmanFord(s);
        double path = bf.findShortestPathTo(b);

        System.out.println("Shortest path from S to B is: " + path);
    }

    private double findShortestPathTo(Vertex vertex) {
        if (vertex == null || this.graph == null) {
            return -1;
        }

        Map<Vertex, Double> map = this.getAllVertices(this.graph);

        Stack<Vertex> s = new Stack<>();
        s.push(this.graph);
        map.put(this.graph, 0d);

        while (!s.isEmpty()) {
            Vertex next = s.pop();
            List<Edge> edges = next.edges;

            for (Edge e : edges) {
                double currentWeight = map.get(e.v);
                double newWeight = map.get(next) + e.value;

                if (newWeight < currentWeight) {
                    map.put(e.v, newWeight);
                    e.v.previous = next;
                    s.push(e.v);
                }
            }
        }

        return map.get(vertex);
    }

    private Map<Vertex, Double> getAllVertices(Vertex vertex) {
        Map<Vertex, Double> result = new HashMap<>();

        Stack<Vertex> s = new Stack<>();
        s.push(vertex);

        result.put(vertex, Double.POSITIVE_INFINITY);

        while (!s.isEmpty()) {
            Vertex next = s.pop();

            for (Edge e : next.edges) {
                if (!result.containsKey(e.v)) {
                    result.put(e.v, Double.POSITIVE_INFINITY);
                    s.push(e.v);
                }
            }
        }

        return result;
    }
}
