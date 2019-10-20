package edu.fullsail.mgems.cse.pather.finngeer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.util.PriorityQueue;

public class DrawSurface extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private Rect mScreenDim = new Rect();
    private SurfaceHolder mHolder;
    private static final int CELL_SIZE = 32;
    private NavCell[][] myCells;
    private NavCell startCell;
    private NavCell endCell;
    private Bitmap bmpStart;
    private Bitmap bmpEnd;
    private Bitmap bmpBlocker;




    public void init() {
        getHolder().addCallback(this);
        setOnTouchListener(this);
        setWillNotDraw(false);
        mHolder = getHolder();
        bmpStart = BitmapFactory.decodeResource(getResources(), R.drawable.start);
        bmpEnd = BitmapFactory.decodeResource(getResources(), R.drawable.end);
        bmpBlocker = BitmapFactory.decodeResource(getResources(), R.drawable.blocker);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawBitmap(bmpStart,startCell.getBounds().left, startCell.getBounds().top, null);
        //canvas.drawBitmap(bmpEnd,endCell.getBounds().left, endCell.getBounds().top, null);

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
    public void surfaceCreated(SurfaceHolder holder) {
        Canvas c = mHolder.lockCanvas();
        mScreenDim.set(0,0,c.getWidth(), c.getHeight());
        mHolder.unlockCanvasAndPost(c);

        loadNewMap();
        invalidate();

    }

    public void loadNewMap(){
        // Calculates the number of cells based on screen size
        int cellCols = (int) Math.ceil((float) mScreenDim.width() / (float) (CELL_SIZE));
        int cellRows = (int) Math.ceil((float) mScreenDim.height() / (float) (CELL_SIZE));

        // Creates Cells in Grid
        myCells = new NavCell[cellRows][cellCols];
        for(int j = 0; j< cellRows; j++){
            for(int i = 0; i< cellCols; i++){
                myCells[j][i] = new NavCell();
                myCells[j][i].setBounds(i * CELL_SIZE, j * CELL_SIZE,(i * CELL_SIZE) + CELL_SIZE,(j * CELL_SIZE)+ CELL_SIZE);
            }
        }
        // Set the START CELL
        startCell = myCells[cellRows /4][cellCols /2];
        endCell = null;

        // Sets the blocker cells
        int midRow = cellRows / 2;
        for(int j = 0; j< cellRows; j++){
            for(int i = 0; i< cellCols; i++){
                if(j == midRow && i > 0 && i < cellCols -1){
                    myCells[j][i].setPassable(false);
                }
            }
        }

        // Connect the Cells to their neighbors
        for(int j = 0; j < cellRows; j++){
            for(int i = 0; i < cellCols; i++){
                if(i > 0 && myCells[j][i-1].isPassable())           myCells[j][i].setNeighbor(0,myCells[j][i-1]);
                if(j > 0 && myCells[j-1][i].isPassable())           myCells[j][i].setNeighbor(1, myCells[j-1][i]);
                if(i < cellCols -1 && myCells[j][i+1].isPassable())  myCells[j][i].setNeighbor(2,myCells[j][i+1]);
                if(j < cellRows -1 && myCells[j+1][i].isPassable())  myCells[j][i].setNeighbor(3, myCells[j+1][i]);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Point eventPoint = new Point((int)event.getX(), (int)event.getY());
        int chosenCol = (int) Math.ceil(eventPoint.y / CELL_SIZE);
        int chosenRow = (int) Math.ceil(eventPoint.x / CELL_SIZE);

        // makes sure that chosen end cell is passable
        if (myCells[chosenCol][chosenRow].isPassable()){
            endCell = myCells[chosenCol][chosenRow];
            FindPath();
        }
        else{
            Toast.makeText(getContext(), "Cannot choose blocker", Toast.LENGTH_SHORT).show();
        }
        invalidate();
        return false;
    }

    private void FindPath() {
        startCell.update(0, heuristic(startCell, endCell),null);
        PriorityQueue<NavCell> cellQueue = new PriorityQueue<>();

        cellQueue.add(startCell);
        while(!cellQueue.isEmpty()){
            // Remove next open cell (top of queue cell with cheapest final cost)
            NavCell current = cellQueue.remove();
            if(current == endCell){
                return;
            }
            else{
                current.setVisited(true);
                for (NavCell neighbor: current.getNeighbors()) {
                    float tmpCost = current.getCost() + 1.0f;
                    if(neighbor.isVisited()){
                        if(tmpCost < neighbor.getCost()){
                            neighbor.update(tmpCost, heuristic(neighbor,endCell),current);
                            cellQueue.remove(neighbor);
                            cellQueue.add(neighbor);
                        }
                    }
                    else{
                        neighbor.update(tmpCost,heuristic(neighbor,endCell),current);
                        cellQueue.add(neighbor);
                        neighbor.setVisited(true);
                    }
                }
            }

            // On exit, the end cell should have a previous pointer to its previous cell used to get there
            endCell.setPreviousCell(current);
        }
    }

    private float heuristic(NavCell from, NavCell to) {
        // The distance formula
        Point p1 = from.getCentroid();
        Point p2 = to.getCentroid();
        float dx = (p2.x-p1.x)*(p2.x-p1.x);
        float dy = (p2.y - p1.y)*(p2.y-p1.y);
        return (float)Math.sqrt(dx+dy);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


}
