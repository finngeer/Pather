package edu.fullsail.mgems.cse.pather.finngeer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class DrawSurface extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {


    private static final int CELL_SIZE = 32;

    public Rect myScreenDim;
    private NavCell[] mCells;
    private PriorityQueue<NavCell> cellQueue;
    private ArrayList<NavCell> visitedCells;

    private void init(){
        this.setWillNotDraw(false);
        getHolder().addCallback(this);
        setOnTouchListener(this);
        visitedCells = new ArrayList<>();

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Lock canvas and get size for field & draw
        Canvas c = holder.lockCanvas();
        if (c != null) {
            myScreenDim.set(0,0,c.getWidth(), c.getHeight());

            loadNewMap(c);
            holder.unlockCanvasAndPost(c);
            invalidate();
        }

    }


    private void loadNewMap(Canvas c) {
        // Calculate number of cells based on screen
        int mCellCols = (int) Math.ceil((float) c.getWidth() / (float) (CELL_SIZE));
        int mCellRows = (int) Math.ceil((float) c.getHeight() / (float) (CELL_SIZE));

        // create cells
        NavCell[][] mCells = new NavCell[mCellRows][mCellCols];
        for (int j = 0; j < mCellRows; j++) {
            for (int i = 0; i < mCellCols; i++) {
                mCells[j][i] = new NavCell();
                mCells[j][i].setBounds( // issues
                        i * CELL_SIZE,
                        j * CELL_SIZE,
                        (i * CELL_SIZE) + CELL_SIZE,
                        (j * CELL_SIZE) + CELL_SIZE);
            }
        }


        // set start cell
        NavCell mCellStart = mCells[mCellRows / 4][mCellCols / 2];

        // random end cell
        NavCell mCellEnd = new NavCell(getRandomPoint());     // might be some issues here

        // set blockers
        int midRow = mCellRows / 2;
        for (int j = 0; j < mCellRows; j++) {
            for (int i = 0; i < mCellCols; i++) {
                if (j == midRow && i > 0 && i < mCellCols - 1) {
                    mCells[j][i].setImpassable();
                }
            }
        }

        // link Neighbors together after
        for (int j = 0; j < mCellRows; j++) {
            for (int i = 0; i < mCellCols; i++) {
                if (i > 0 && mCells[j][i - 1].isPassable()) {
                    mCells[j][i].setNeighbor(0, mCells[j][i - 1]); }
                if (j > 0 && mCells[j - 1][i].isPassable()) {
                    mCells[j][i].setNeighbor(1, mCells[j - 1][i]); }
                if (i < mCellCols - 1 && mCells[j][i + 1].isPassable()) {
                    mCells[j][i].setNeighbor(2, mCells[j][i + 1]); }
                if (j < mCellCols - 1 && mCells[j + 1][i].isPassable()) {
                    mCells[j][i].setNeighbor(3, mCells[j + 1][i]); } } }

        //path finder

        //update the start cell to: 0 cost, heuristic(start, end) final cost, and null previous cell
        mCellStart.update(0, heuristic(mCellStart, mCellEnd), null);

        //insert the start cell into the PriorityQueue of open cells
        cellQueue.add(mCellStart);

        //while the PriorityQueue is not empty (i.e. there are still open cells)
        while (!cellQueue.isEmpty()){
            NavCell current = cellQueue.remove();

            if (current == mCellEnd){
                // return //(we found the cell we want and built a path along the way)
                return;
            }

            else{
                //mark current cell as visited (or put in visited Map for efficient lookup later)
                visitedCells.add(current);
            }

            for (NavCell:current.getmNeighbors()) {
                //tmpCost = current.getCost() + 1.0f;

            }
            //for each non-null neighbor cell of current
            // tmpcost = current’s cost + 1.0f
            //if neighbor is already visited
            //if tmpcost < neighbor’s cost
            //update neighbor: tmpcost, heuristic(neighbor, end) final cost, current
            //reorder neighbor in PriorityQueue (remove it and re-insert it)

            //else
            //update neighbor: tmpcost, heuristic(neighbor, end) final cost, current
            // insert neighbor into PriorityQueue (for first time)
            //mark neighbor as visited (or put in visited Map)
        }

    }

    private PointF getRandomPoint(){

        return new PointF( (float)(Math.random() * myScreenDim.width()), (float)(Math.random() * myScreenDim.height()));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        PointF eventPoint = new PointF(event.getX(), event.getY());
        PointF rowCol_Point = new PointF((float)(Math.floor(eventPoint.x/CELL_SIZE), Math.floor(eventPoint.y/CELL_SIZE));
        // make sure cell is passable before attempting to navigate to it

        // A* Algorithm

        return false;
    }

    private float heuristic(NavCell from, NavCell to){
        // Distance formula
        PointF p1 = from.getCentroid();
        PointF p2 = to.getCentroid();
        float dx = (p2.x-p1.x) * (p2.x - p1.x);
        float dy = (p2.y-p1.y) * (p2.y - p1.y);
        return (float)Math.sqrt(dx+dy);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public DrawSurface(Context context) {
        super(context);
        init();
    }

    public DrawSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
