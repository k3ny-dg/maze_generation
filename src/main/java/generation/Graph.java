package generation;

import java.util.*;

/**
 *
 * @author Keny Dutton-Gillespie
 * @version 1.0
 *
 * A class that creates a graph that will store an adjacency list
 */
public class Graph {
    private Map<Integer, Node> adjacencyList = new HashMap<>();
    private int edgeCount;

    /**
     * @param cell the current cell
     * @return if the vertex was added successfully
     */
    public boolean addVertex(int cell)
    {
        // the vertices are part of a set
        if(containsVertex(cell))
        {
            return false;
        }
        adjacencyList.put(cell, null);
        return true;
    }

    /**
     * @param first the first vertex
     * @param second the second vertex
     * @return if the edge was added successfully
     */
    public boolean addEdge(Integer first, Integer second)
    {
        // edges are part of a set (no duplicates
        if(containsEdge(first, second))
        {
            return false;
        }

        addDirectedEdge(first, second);
        addDirectedEdge(second, first);
        edgeCount++;

        return true;
    }

    /**
     * Adds a directed edge to the graph
     *
     * @param first the first cell
     * @param second the second cell
     */
    private void addDirectedEdge(Integer first, Integer second)
    {
        Node oldHead = adjacencyList.get(first);
        if (oldHead == null)
        {
            adjacencyList.put(first, new Node(second));
        }
        else
        {
            // put a new node at the start of the linked list
            adjacencyList.put(first, new Node(second, oldHead));
        }
    }

    /**
     * Returns whether a vertex was found
     *
     * @param cell the index being searched for
     * @return if the index was found
     */
    public boolean containsVertex(int cell)
    {
        return adjacencyList.containsKey(cell);
    }

    /**
     * Returns whether an edge exits between
     * two vertices
     *
     * @param first the first index
     * @param second the second index
     * @return if an edge exists between the two vertices
     */
    public boolean containsEdge(int first, int second)
    {
        // precondition: make sure the vertices are in the graph
        if(containsVertex(first) && containsVertex(second))
        {
            // step 1- get the adjacency list of first
            Node current = adjacencyList.get(first);

            // step 2- loop over the list and look for second in the list
            while (current != null)
            {
                if (current.otherVertex == second)
                {
                    return true;
                }
                current = current.next;
            }
        }
        return false;
    }

    /**
     * @return the number of the vertices in the graph
     */
    public int vertexSize()
    {
        // the same as the number of keys in the map
        return adjacencyList.size();
    }

    /**
     * Returns a set of all the vertices in the graph
     * @return a set of the vertices
     */
    public Set<Integer> vertices()
    {
        return adjacencyList.keySet();
    }

    /**
     * @param search the vertex to be removed
     * @return if the vertex was removed from the graph
     */
    public boolean removeVertex(Integer search)
    {
        return false;
    }

    /**
     * Traverses the graph using depth-first search
     * @param source the initial position to start the search from
     * @return the traversal of the graph
     */
    public Map<Integer, Integer> dfs(Integer source)
    {
        Set<Integer> allVerts = vertices();

        if(allVerts.isEmpty())
        {
            return new HashMap<>();
        }

        Map<Integer, Integer> traversalResults = new HashMap<>();
        Set<Integer> vistedVerts = new HashSet<>();

        dfsRecursive(source, traversalResults, vistedVerts, null);

        return traversalResults;
    }

    /**
     *
     * @param current the current vertex
     * @param traversal the traversal list
     * @param visited the vertices that were visited
     * @param previous the previous vertex
     */
    // a list for our results - maintains the traversal order
    // a set for determining what we've seen already - fast!
    public void dfsRecursive(Integer current, Map<Integer, Integer> traversal, Set<Integer> visited, Integer previous)
    {

        // only traverse to this vertex if I haven't seen it before
        if (!visited.contains(current))
        {
            // mark that we have visited this vertex
            traversal.put(current, previous);
            visited.add(current);

            // try to visit adjacent vertices
            Node neighbor = adjacencyList.get(current);
            while(neighbor != null)
            {
                // visit this neighbor
                dfsRecursive(neighbor.otherVertex, traversal, visited, current);

                // then move on to the next
                neighbor = neighbor.next;
            }
        }
    }

    /**
     * Traverses the graph using breadth-first search
     * Traverses the graph using breadth-first search
     * @param source the initial position to start the search from
     * @return the traversal of the graph
     */
    public Map<Integer, Integer> bfs(Integer source) {
        Map<Integer, Integer> traversal = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> bfsQueue = new LinkedList<>();
        Integer previous = null;

        // perform BFS
        bfsQueue.add(source);
        int size = vertexSize();
        while (traversal.size() < size)
        {
            Integer next = bfsQueue.remove();
            if(!visited.contains(next))
            {
                visited.add(next);
                traversal.put(next, previous);

                Node neighbor = adjacencyList.get(next);
                previous = next;
                while (neighbor != null)
                {
                    if(!visited.contains(neighbor.otherVertex))
                    {
                        bfsQueue.add(neighbor.otherVertex);
                    }
                    neighbor = neighbor.next;
                }
            }
        }
        return traversal;
    }

    /**
     * Clears the graph of all vertices
     */
    public void clear()
    {
        adjacencyList.clear();
    }




    // inner classes
    private class Node {
        // data in the node
        private int otherVertex;

        // next node
        private Node next;


        public Node(int otherVertex) {
            this.otherVertex = otherVertex;
        }

        public Node(int otherVertex, Node next) {
            this.otherVertex = otherVertex;
            this.next = next;
        }

    }

    public class Edge<V> {
        private V source;
        private V destination;

        public Edge(V source, V destination) {
            this.source = source;
            this.destination = destination;
        }
    }

    @Override
    public String toString()
    {
        return "Graph{" +
                "adjacencyList=" + adjacencyList +
                ", edgeCount=" + edgeCount +
                '}';
    }
}
