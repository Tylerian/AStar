package com.luotti.utilities.pathfinder.dao;

public class Node {

    private short iX;
    private short iY;

    private double iF;
    private double iG;
    private double iH;

    private Node mParent;
    private boolean bOpenList;
    private boolean bWalkable;
    private boolean bClosedList;

    public short getX()
    {
        return this.iX;
    }

    public short getY()
    {
        return this.iY;
    }

    public double getF()
    {
        return this.iF;
    }

    public double getG()
    {
        return this.iG;
    }

    public double getH()
    {
        return this.iH;
    }

    public Node getParent()
    {
        return this.mParent;
    }

    public boolean hasParent()
    {
        return this.mParent != null;
    }

    public boolean isWalkable()
    {
        return this.bWalkable;
    }

    public boolean inOpenList()
    {
        return this.bOpenList;
    }

    public boolean inClosedList()
    {
        return this.bClosedList;
    }

    public void destruct()
    {
        this.iX = 0;
        this.iY = 0;
        this.mParent = null;
        this.bWalkable = true;
    }

    public void resetScore()
    {
        this.iF = 0.0d;
        this.iG = 0.0d;
    }

    public void removeParent()
    {
        this.mParent = null;
    }

    public void setX(short value)
    {
        this.iX = value;
    }

    public void setY(short value)
    {
        this.iY = value;
    }

    public void setF(double value)
    {
        this.iF = value;
    }

    public void setG(double value)
    {
        this.iG = value;
    }

    public void setH(double value)
    {
        this.iH = value;
    }

    public void setParent(Node value)
    {
        this.mParent = value;
    }

    public void setInOpenList(boolean value)
    {
        this.bOpenList = value;
    }

    public void setInClosedList(boolean value)
    {
        this.bClosedList = value;
    }

    public void setWalkable(boolean value)
    {
        this.bWalkable = value;
    }

    public Node construct(short x, short y)
    {
        this.iX = x;
        this.iY = y;
        this.bWalkable = true; return this;
    }

    public Node construct(short x, short y, boolean walkable)
    {
        this.iX = x;
        this.iY = y;
        this.bWalkable = walkable; return this;
    }

    @Override
    public String toString()
    {
        return "[" + this.iX + ", " + this.iY + "]";
    }

    public boolean equals(Node node)
    {
        return (node.getX() == this.iX && node.getY() == this.iY);
    }
}