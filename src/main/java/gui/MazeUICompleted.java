package gui;

import generation.AlgorithmType;
import generation.DisjointSets;
import generation.Graph;
import javafx.scene.paint.Color;

import java.util.Map;
import java.util.Random;

/**
 * @author Keny Dutton-Gillespie
 * @version 1.0
 *
 * A class that creates a maze of a given size and then
 * solves it using either dfs or bfs
 */
public class MazeUICompleted extends MazeUI
{

    // Add rows * columns sets to a new DisjointSets objects
    private int cols = getCols();
    private int rows = getRows();
    private int size =  rows * cols;
    private Graph graph = new Graph();

    @Override
    public void runAlgorithm(AlgorithmType type)
    {
        if(type == AlgorithmType.GENERATE_MAZE)
        {
            graph.clear();
            clearScreen();
            generateMaze();
        }
        else if (type == AlgorithmType.BFS)
        {
            solveMazeBFS(graph);
        }
        else if (type == AlgorithmType.DFS)
        {
            solveMazeDFS(graph);
        }
    }

    /**
     * Solve the maze using breadth-first-search
     * @param maze the maze to be solved
     */
    private void solveMazeBFS(Graph maze)
    {
        int start = 0;
        int end = (getRows() * getCols()) - 1;


        // solve the maze using BFS and show the solution
        Map<Integer, Integer> traversal = maze.bfs(0);

        Integer path = size - 1;
        setFillColor(Color.GREEN);

        while (path != null)
        {
            fillCell(path);
            path = traversal.get(path);
        }
    }

    /**
     * Solve the maze using depth-first-search
     * @param maze the maze to be solved
     */
    private void solveMazeDFS(Graph maze)
    {
       Map<Integer, Integer> traversal = maze.dfs(0);

        Integer path = size - 1;
        setFillColor(Color.MAGENTA);

        while (path != null)
        {
            fillCell(path);
            path = traversal.get(path);
        }
    }

    /**
     * Generates a randomized maze of a given size
     */
    private void generateMaze()
    {
        drawBackgroundGrid();
        drawBorder();

        rows = getRows();
        cols = getCols();
        size = rows * cols;
        DisjointSets sets = new DisjointSets(size);

        // Add rows * columns vertices to a new graph
        // (rows * cols) - 1 to the vertices.
        for (int i = 0; i < size - 1 ; i++)
        {
            graph.addVertex(i);
        }

        // add edges
        for (int i = 0; i < size - 1; i++)
        {
            // find neighboring vertices
            int[] neighbors = findNeighbors(i);
            // create edges/ union a randomized neighbor cell to the current cell
            join(i, neighbors, sets);
        }

        // draw the maze
        drawMaze(size - 1);

        for (int i = 0; i < size -1 ; i++)
        {
            graph.removeVertex(i);
        }
    }

    /**
     * Returns an array of all the neighboring cells of the current cell
     *  or -1 if the neighboring left/right call is not in a neighboring column
     *
     * @param currentCell the current cell
     * @return an array of the indexes that are neighbors on the grid
     * to the current cell
     */
    public int[] findNeighbors(int currentCell)
    {
        int col = getCols();

        // find all neighbors
        int north = currentCell - col;
        int south = currentCell + col;
        int east = currentCell + 1;
        int west = currentCell - 1;

        // find the column of the current cell
        int currCol = currentCell % col;

        // find the column of the east/ west cells
        int eastCol = east % col;
        int westCol = west % col;

        // check if the east neighbor is in an
        // adjacent column
        if(eastCol < currCol)
        {
            east = -1;
        }

        // check if the west neighbor is in an
        // adjacent column
        if (westCol > currCol)
        {
            west = -1;
        }

        return new int[]{north, south, east, west};
    }


    /**
     * A method that loops
     *
     * @param index the index of the current cell
     * @param neighbors an array of the indexes of the neighboring cells
     * @param sets a disjoint sets object to add to
     */
    public void join(int index, int[] neighbors, DisjointSets sets)
    {
        int neighbor;

        // select a random neighbor
        do
        {
            Random rand = new Random();
            int upperBound = 4;
            int random = rand.nextInt(upperBound);

            neighbor = neighbors[random];
        }
        // Determine if they are part of the same set
        // (i.e. are they connected cells in the maze)
        // Determine if the neighbor is within bounds
        // Repeat until you find a valid option

        while (neighbor < 0 || neighbor >= size || sets.sameSet(index, neighbor));

        // perform a union() on the sets containing those elements in the DisjointSets object
        // add an edge between the two elements in your graph
       sets.union(index, neighbor);
       graph.addEdge(index, neighbor);
    }

    /**
     * A method that draws the created maze.
     * If a cell has an edge with its neighbor,
     * there will be no wall between them.
     * If there is no edge, or if it is a border,
     * there will be a wall
     *
     * @param size the number of vertices in the graph
     */
    public void drawMaze(int size)
    {
        setStrokeColor(Color.BLACK);
        setStrokeWidth(2);

        // use a loop to draw each path in the maze
        for (int cell = 0; cell < size; cell++) {

            boolean north = true;
            boolean south = true;
            boolean east = true;
            boolean west = true;

            int[] neighbors = findNeighbors(cell);

            int northCell = neighbors[0];
            int eastCell = neighbors[2];
            int southCell = neighbors[1];
            int westCell = neighbors[3];

            // if the cell has a neighbor
            if(northCell < 0 || northCell > size)
            {
                north = true;
            }
            else if(graph.containsEdge(cell, northCell))
            {
                north = false;
            }
            if(eastCell > size || eastCell < 0)
            {
                east = true;
            }
            else if (graph.containsEdge(cell, eastCell)) {
                east = false;
            }
            if(southCell < 0 || southCell > size)
            {
                south = true;
            }
            else if (graph.containsEdge(cell, southCell)) {
                south = false;
            }
            if(westCell < 0)
            {
                west = true;
            }
            else if (graph.containsEdge(cell, westCell)) {
                west = false;
            }

            boolean[] neighborWalls = {north, east, south, west};
            drawCell(cell, neighborWalls);
        }
    }

    /**
     * Draws a border around the maze
     */
    public void drawBorder()
    {
        int rows = getRows();
        int cols = getCols();
        int size = rows * cols;

        int lastRow = (rows * cols) - cols;
        setStrokeWidth(4);

       // WEST BORDER
        for (int i = 0; i < size; i= i + cols) {
            setStrokeColor(Color.BLACK);
            drawCell(i, new boolean[]{false, false, false, true});
        }

        // EAST BORDER
        for (int i = cols - 1 ; i < size ; i = i + cols) {
            setStrokeColor(Color.BLACK);
            drawCell(i, new boolean[]{false, true, false, false});
        }

        // NORTH BORDER
        for (int i = 0; i < cols; i++) {

            setStrokeColor(Color.BLACK);
            drawCell(i, new boolean[]{true, false, false, false});
        }

        // SOUTH BORDER
        for (int i = lastRow; i < size  ; i++)
        {
            setStrokeColor(Color.BLACK);
            drawCell(i, new boolean[]{false, false, true, false});
        }
    }

}
