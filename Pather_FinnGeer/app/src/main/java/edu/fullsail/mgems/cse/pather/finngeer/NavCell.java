package edu.fullsail.mgems.cse.pather.finngeer;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.List;

public class NavCell {
    // set up once
    private Rect mBounds;
    private PointF mCentroid;
    private Boolean mPassable;
    private int neighborNum;
    private NavCell[] mNeighbors = new NavCell[neighborNum];

    // for path-finding
    private float mCost;
    private float mCostFinal;
    private NavCell mPrevious;


    NavCell(PointF centroid) {

        this.mCentroid = centroid;

    }


    public NavCell[] getmNeighbors() {
        return mNeighbors;
    }

    PointF getCentroid() {
        return mCentroid;
    }

    void setBounds(int a, int b, int c, int d) {
        this.mBounds = new Rect(a, b, c, d);
    }

    // might be issues
    void setNeighbor(int index, NavCell cell){
        mNeighbors[index] = cell;

    }

    Boolean isPassable() {
        return mPassable;
    }

    public void setmPassable(Boolean mPassable) {
        this.mPassable = mPassable;
    }

    public void update(float cost, float costFinal, NavCell prev){
        mCost = cost;
        mCostFinal = costFinal;
        mPrevious = prev;
    }

    public void reset(){
        mCost = mCostFinal = Float.MAX_VALUE;
        mPrevious = null;
    }

    public static Comparator<NavCell> NavCellByFinalCostComparator = new Comparator<NavCell>() {
        @Override
        public int compare(NavCell x, NavCell y) {
            return Float.compare(x.mCostFinal, y.mCostFinal);
        }
    };

    void setImpassable() {
        mPassable = false;
    }
}
