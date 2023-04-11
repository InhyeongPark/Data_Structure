import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        checkGraph(graph);
        checkStartVertex(start);
        if (!graph.getVertices().contains(start)) {
            throw new java.lang.IllegalArgumentException("The start vertex should be in the graph");
        }

        ArrayList<Vertex<T>> visited = new ArrayList<>();
        visited.add(start);
        if (graph.getVertices().size() == 1) {
            return visited;
        }

        Queue<Vertex<T>> queueCheck = new LinkedList<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjacent = graph.getAdjList();
        queueCheck.add(start);
        while (!queueCheck.isEmpty()) {
            Vertex<T> v = queueCheck.remove();
            List<VertexDistance<T>> adjVertices = adjacent.get(v);
            for (VertexDistance<T> neighborVertex : adjVertices) {
                if (!visited.contains(neighborVertex.getVertex())) {
                    visited.add(neighborVertex.getVertex());
                    queueCheck.add(neighborVertex.getVertex());
                }
            }
        }
        return visited;
    }

    /**
     * A private method that checks if the graph is null
     *
     * @param <T> the generic typing of the data
     * @param graph the graph to search
     * @throws IllegalArgumentException if the graph is null
     */
    private static <T> void checkGraph(Graph<T> graph) {
        if (graph == null) {
            throw new java.lang.IllegalArgumentException("The graph should not be null");
        }
    }

    /**
     * A private method that checks if the start vertex is null
     *
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the algorithm
     * @throws IllegalArgumentException if the start vertex is null
     */
    private static <T> void checkStartVertex(Vertex<T> start) {
        if (start == null) {
            throw new java.lang.IllegalArgumentException("The start vertex should not be null");
        }
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        checkGraph(graph);
        checkStartVertex(start);
        if (!graph.getVertices().contains(start)) {
            throw new java.lang.IllegalArgumentException("The start vertex should be in the graph");
        }
        ArrayList<Vertex<T>> visited = new ArrayList<>();
        if (graph.getVertices().size() == 1) {
            visited.add(start);
        } else {
            helpDfs(start, graph, visited);
        }
        return visited;
    }

    /**
     * A private method that helps performing a depth first search
     *  and save the visited vertex to the array list
     *
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @param visited array list of vertices in visited order
     * @throws IllegalArgumentException if the vertex is null
     */
    private static <T> void helpDfs(Vertex<T> start, Graph<T> graph, ArrayList<Vertex<T>> visited) {
        checkStartVertex(start);
        visited.add(start);
        Map<Vertex<T>, List<VertexDistance<T>>> adjacent = graph.getAdjList();
        List<VertexDistance<T>> adjVertices = adjacent.get(start);
        for (VertexDistance<T> neighborVertex : adjVertices) {
            if (!visited.contains(neighborVertex.getVertex())) {
                helpDfs(neighborVertex.getVertex(), graph, visited);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        checkGraph(graph);
        checkStartVertex(start);
        if (!graph.getVertices().contains(start)) {
            throw new java.lang.IllegalArgumentException("The start vertex should be in the graph");
        }

        HashMap<Vertex<T>, Integer> distanceMap = new HashMap<>();
        for (Vertex<T> oneVertex : graph.getVertices()) {
            distanceMap.put(oneVertex, Integer.MAX_VALUE);
        }
        if (graph.getVertices().size() == 1) {
            distanceMap.put(start, 0);
            return distanceMap;
        }

        ArrayList<Vertex<T>> visited = new ArrayList<>();
        PriorityQueue<VertexDistance<T>> pq = new PriorityQueue<>();
        Map<Vertex<T>, List<VertexDistance<T>>> adjacent = graph.getAdjList();

        pq.add(new VertexDistance<T>(start, 0));  // Can I not initiate a new Vertex distance?
        while (!pq.isEmpty() && visited.size() != graph.getVertices().size()) {
            VertexDistance<T> vd = pq.remove();
            if (!visited.contains(vd.getVertex())) {
                visited.add(vd.getVertex());
                distanceMap.put(vd.getVertex(), vd.getDistance());
                List<VertexDistance<T>> adjVertices = adjacent.get(vd.getVertex());
                for (VertexDistance<T> neighborVertex : adjVertices) {
                    if (!visited.contains(neighborVertex.getVertex())) {
                        pq.add(new VertexDistance<T>(neighborVertex.getVertex(),
                                vd.getDistance() + neighborVertex.getDistance()));
                    }
                }
            }
        }
        return distanceMap;
    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * You should NOT allow self-loops or parallel edges into the MST.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        checkGraph(graph);
        DisjointSet<Vertex<T>> djSet = new DisjointSet<>();
        HashSet<Edge<T>> edgeSet = new HashSet<>();
        PriorityQueue<Edge<T>> pq = new PriorityQueue<>(graph.getEdges());

        while (!pq.isEmpty() && edgeSet.size() < (graph.getEdges().size() - 1)) {
            Edge<T> edge = pq.remove();
            Vertex<T> startVertex = edge.getU();
            Vertex<T> endVertex = edge.getV();
            if (djSet.find(startVertex) != djSet.find(endVertex)) {
                djSet.union(djSet.find(startVertex), djSet.find(endVertex));
                Edge<T> reverseEdge = new Edge<>(endVertex, startVertex, edge.getWeight());
                edgeSet.add(edge);
                edgeSet.add(reverseEdge);
            }
        }
        if (edgeSet.size() < 2 * (graph.getVertices().size() - 1)) {
            return null;
        }
        return edgeSet;
    }
}