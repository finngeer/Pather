package edu.fullsail.mgems.cse.pather.finngeer;

import android.graphics.PointF;
import android.graphics.Rect;

import java.util.Comparator;
import java.util.List;

public class NavCell {
    // set up once
    Rect mBounds;
    PointF mCentroid;
    Boolean mPassable;
    NavCell[] mNeighbors;

    // for path-finding
    private float mCost;
    private float mCostFinal;
    private NavCell mPrevious;

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
}
