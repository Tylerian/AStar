package com.luotti.utilities.pathfinder;

import java.util.List;
import java.util.ArrayList;

import com.luotti.utilities.pathfinder.dao.Node;

public class Heuristic {

    public static final byte PATH_THRESHOLD = 0x79;
    public static final short LOOP_THRESHOLD = 0x400;
    public static final double SQUIRT = Math.sqrt(2);

    /**
     * Backtrace according to the parent
     * records and return the path.
     *
     *  @param node ending node
     * @return {List<Node>} the path
     */
    public static List<Node> backtrace(Node node)
    {
        List<Node> path = new ArrayList<>();

        while (node.hasParent())
        {
            node = node.getParent();
            path.add(node); // add it!
        }

        /*Collections.reverse(path);*/ return path;
    }


    public static double manhattan(short x, short y)
    {
        return x + y;
    }

    public static double chebyshev(short x, short y)
    {
        return Math.max(x, y);
    }

    public static double bestfirst(short x, short y)
    {
        return ((x + y) * 1000000.0d);
    }

    public static double euclidean(short x, short y)
    {
        return Math.sqrt((x * x) + (y * y));
    }

    /**
     * Heuristic.expand( . )
     * Given a compressed path, return a new path
     * that has all the segments in it interpolated.
     *
     * @param path The path
     * @return {List<Node>} The expanded path
     */
    public static List<Node> expand(List<Node> path)
    {
        Node node0, node1;
        List<Node> interpolated = null;
        List<Node> expanded = new ArrayList<>();

        // Enough nodes?
        if (path.size() < 2) return expanded;

        for (int i = 0; i < (path.size() - 1); ++i)
        {
            node0 = path.get(i);
            node1 = path.get(i + 1);

            interpolated = Heuristic.interpolate(
                node0.getX(), node0.getY(), node1.getX(), node1.getY()
            );

            for (int j = 0; j < (interpolated.size() - 1); ++j)
            {
                expanded.add(interpolated.get(j));
            }
        }

        expanded.add(path.get(path.size() - 1)); return expanded;
    }

    /**
     * Heuristic.getDistance( . . )
     * Given two Nodes, get distance between them
     *
     * @param node0 The origin node
     * @param node1 The ending node
     * @return The distance between the Nodes
     */
    public static int getDistance(Node node0, Node node1)
    {
        return Math.abs(node0.getX() - node1.getX()) +
               Math.abs(node0.getY() - node1.getY()) ;
    }

    /**
     * Heuristic.interpolate( . . . . )
     * Given the start and end coordinates, return all the coordinates lying
     * on the line formed by these coordinates, based on Bresenham's algorithm.
     *
     * @param x0 origin X
     * @param y0 origin Y
     * @param x1 ending X
     * @param y1 ending Y
     * @return {List<Node>} The coordinates on the line
     */
    public static List<Node> interpolate(short x0, short y0, short x1, short y1)
    {
        short sx, sy, dx, dy, pow, offset;
        List<Node> line = new ArrayList<>();

        dx = (short) Math.abs(x1 - x0);
        dy = (short) Math.abs(y1 - y0);

        sx = (short) ((x0 < x1) ? 1 : -1);
        sy = (short) ((y0 < y1) ? 1 : -1);

        offset = (short) (dx - dy); while (true)
        {
            line.add(
                new Node().construct(x0, y0)
            );

            if (x0 == x1 && y0 == y1) break;

            pow = (short) (2 * offset);

            if (pow > -dy)
            {
                x0 = (short) (x0 + sx);
                offset = (short) (offset - dy);
            }

            if (pow <  dx)
            {
                y0 = (short) (y0 + sy);
                offset = (short) (offset + dx);
            }
        }

        return line;
    }
}