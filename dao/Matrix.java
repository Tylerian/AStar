package com.luotti.utilities.pathfinder.dao;

import java.util.List;
import java.util.ArrayList;

public class Matrix implements Cloneable {

    private byte width;
    private byte height;
    private Node[][] mNodes;

    @Override
    protected Matrix clone()
    {
        try
        {
            return (Matrix) super.clone();
        }

        catch (CloneNotSupportedException ex)
        {
            // howdy
        }

        return null; // Never raises that
    }

    private void generate(String pattern)
    {
        String[] matrix = pattern.split("\\|");

        this.height = (byte) matrix.length;
        this.width  = (byte) matrix[0].length();
        this.mNodes = new Node[ width ][ height ];

        for (byte row = 0; row < this.height; row++)
        {
            for (byte col = 0; col < this.width; col++)
            {
                this.mNodes[col][row] = new Node().construct(
                    col, row, (matrix[row].charAt(col) != 'x')
                );
            }
        }
    }

    public Node getNodeAt(short x, short y)
    {
        return this.mNodes[x][y];
    }

    public Matrix construct(String pattern)
    {
        this.generate(pattern);
        return this;
    }

    public boolean isInside(short x, short y)
    {
        return (0 <= x && this.width  > x) &&
                (0 <= y && this.height > y);
    }

    public boolean isWalkableAt(short x, short y)
    {
        return (this.isInside(x, y) && this.mNodes[x][y].isWalkable());
    }

    public void setWalkableAt(short x, short y, boolean walkable)
    {
        this.mNodes[x][y].setWalkable(walkable);
    }

    public List<Node> getNeighbors(Node node, boolean diagonal, boolean corners)
    {
        short x = node.getX(), y = node.getY();
        List<Node> neighbors = new ArrayList<>();
        boolean d0 = false, d1 = false, d2 = false, d3 = false;
        boolean s0 = false, s1 = false, s2 = false, s3 = false;

        // ↑
        if (this.isWalkableAt(x, (short) (y - 1)))
        {
            s0 = true;
            neighbors.add(this.mNodes[x][y - 1]);
        }
        // →
        if (this.isWalkableAt((short) (x + 1), y))
        {
            s1 = true;
            neighbors.add(this.mNodes[x + 1][y]);
        }
        // ↓
        if (this.isWalkableAt(x, (short) (y + 1)))
        {
            s2 = true;
            neighbors.add(this.mNodes[x][y + 1]);
        }
        // ←
        if (this.isWalkableAt((short) (x - 1), y))
        {
            s3 = true;
            neighbors.add(this.mNodes[x - 1][y]);
        }

        if (!diagonal) return neighbors;

        if (corners)
        {
            d0 = (s3 && s0);
            d1 = (s0 && s1);
            d2 = (s1 && s2);
            d3 = (s2 && s3);
        }

        else
        {
            d0 = (s3 || s0);
            d1 = (s0 || s1);
            d2 = (s1 || s2);
            d3 = (s2 || s3);
        }

        // ↖
        if (d0 && this.isWalkableAt((short) (x - 1), (short) (y - 1)))
        {
            neighbors.add(this.mNodes[x - 1][y - 1]);
        }
        // ↗
        if (d1 && this.isWalkableAt((short) (x + 1), (short) (y - 1)))
        {
            neighbors.add(this.mNodes[x + 1][y - 1]);
        }
        // ↘
        if (d2 && this.isWalkableAt((short) (x + 1), (short) (y + 1)))
        {
            neighbors.add(this.mNodes[x + 1][y + 1]);
        }
        // ↙
        if (d3 && this.isWalkableAt((short) (x - 1), (short) (y + 1)))
        {
            neighbors.add(this.mNodes[x - 1][y + 1]);
        }

        return neighbors;
    }
}