package edu.fullsail.mgems.cse.pather.finngeer;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.Comparator;

public class NavCell {
    // set-up once
    private Rect bounds;
    private Point centroid;
    private boolean passable;
    private NavCell[] neighbors;

    // for path finding
    private float cost;
    private float finalCost;
    private NavCell previousCell;
    private boolean visited;

    void update(float co, float costFinal, NavCell prev){
        cost = co;
        finalCost = costFinal;
        previousCell = prev;
    }

    void reset(){
        cost = finalCost = Float.MAX_VALUE;
        previousCell = null;
    }

    public static Comparator<NavCell> NavCellByFinalCostComparator = new Comparator<NavCell>() {
        @Override
        public int compare(NavCell x, NavCell y) {
            if(x.finalCost < y.finalCost){
                return -1;
            }
            if(x.finalCost > y.finalCost){
                return 1;
            }
            return 0;
        }
    };


    // Getters and Setters

    public Rect getBounds() {
        return bounds;
    }

    void setBounds(Rect bounds) {
        this.bounds = bounds;
    }

    Point getCentroid() {
        return centroid;
    }

    public void setCentroid(Point centroid) {
        this.centroid = centroid;
    }

    boolean isPassable() {
        return passable;
    }

    void setPassable(boolean passable) {
        this.passable = passable;
    }

    NavCell[] getNeighbors() {
        return neighbors;
    }

    void setNeighbors(NavCell[] neighbors) {
        this.neighbors = neighbors;
    }

    float getCost() {
        return cost;
    }

    void setCost(float cost) {
        this.cost = cost;
    }

    float getFinalCost() {
        return finalCost;
    }

    void setFinalCost(float finalCost) {
        this.finalCost = finalCost;
    }

    NavCell getPreviousCell() {
        return previousCell;
    }

    void setPreviousCell(NavCell previousCell) {
        this.previousCell = previousCell;
    }

    void setBounds(int i, int i1, int i2, int i3) {
        setBounds(new Rect(i,i1,i2, i3));
    }

    void setNeighbor(int i, NavCell navCell) {
        neighbors[i] = navCell;
    }

    boolean isVisited() {
        return visited;
    }

    void setVisited(boolean visited) {
        this.visited = visited;
    }
}


