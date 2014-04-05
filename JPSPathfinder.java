/**********************************************
 * --------------------------------------------
 * A* Pathfinder | BFS <  > Manhattan heuristic
 * An A* Implementation focusing at performance
 * --------------------------------------------
 * Made by: Jairo T. Eög
 * Copyright (c) 2014 - Jairo T. Eög
 * --------------------------------------------
 **********************************************/

package com.luotti.utilities.pathfinder;

import java.util.*;

import com.luotti.utilities.pathfinder.dao.Node;
import com.luotti.utilities.pathfinder.dao.Matrix;

public class JPSPathfinder implements IPathfinder {

    private static Comparator<Node> COMPARATOR;

    static
    {
        JPSPathfinder.COMPARATOR = new Comparator<Node>() {
            @Override
            public int compare(Node nodeA, Node nodeB) {
                return (int) (nodeA.getF() - nodeB.getF());
            }
        };
    }

    /**
     * Pathfinder.calculate(. . .)
     * Calculates the shortest path from origin to ending.
     *
     * @param matrix The matrix
     * @param origin The origin tile
     * @param ending The ending tile
     *
     * @return {List<Node>} the path, including both start and end positions.
     */

    public static List<Node> calculate(Matrix matrix, Node origin, Node ending)
    {
        short threshold = 0x00;

        PriorityQueue<Node> openlist = new PriorityQueue<>(
            Heuristic.PATH_THRESHOLD, JPSPathfinder.COMPARATOR
        );

        // Reset the origin tile
        origin.resetScore();
        openlist.offer(origin);
        origin.setInOpenList(true);

        // While openlist is not empty
        while (!openlist.isEmpty())
        {
            // Pop the node with lower 'f'
            Node node = openlist.poll();

            node.setInClosedList(true);

            // If running for too long... break the loop
            if (Heuristic.LOOP_THRESHOLD < threshold++) break;

            // If reached the ending, construct the path and return it!
            if (node.equals(ending)) return Heuristic.backtrace(node);

            // Get the neighbors of the current Node     // diag | cross
            List<Node> neighbors = matrix.getNeighbors(node, true, true);

            for (Node neighbor : neighbors)
            {
                if (neighbor.inClosedList()) continue;

                short x = neighbor.getX();
                short y = neighbor.getY();

                // Get the distance between two neighbors and calculate the next 'g' score
                double ng = node.getG() + ((x - node.getX() == 0 || y - node.getY() == 0) ? 1 : Heuristic.SQUIRT);

                if (!neighbor.inOpenList() || ng < neighbor.getG())
                {
                    neighbor.setG(ng);
                    neighbor.setParent(node);
                    neighbor.setH(neighbor.getH() == 0 ? Heuristic.manhattan(
                        (short) Math.abs(x - ending.getX()),
                        (short) Math.abs(y - ending.getY())) : neighbor.getH()
                    );

                    neighbor.setF(neighbor.getG() + neighbor.getH());

                    if (!neighbor.inOpenList())
                    {
                        openlist.offer(neighbor);
                        neighbor.setInOpenList(true);
                    }

                    else
                    {
                        // Update the openlist
                        openlist.remove(neighbor);
                        openlist. offer(neighbor);
                    }
                }
            }
        }

        // Damn, any path found ;(
        return Collections.EMPTY_LIST;
    }
}