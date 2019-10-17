package edu.fullsail.mgems.cse.pather.finngeer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class DrawSurface extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {


    private static final int CELL_SIZE = 1;

    private void init(){
        this.setWillNotDraw(false);
        getHolder().addCallback(this);
        setOnTouchListener(this);

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Lock canvas and get size for field & draw
        Canvas c = holder.lockCanvas();
        if (c != null) {
            //Bitmap mField = Bitmap.createScaledBitmap(field, c.getWidth(), c.getHeight(), false);
            //scaledBitmap = Bitmap.createScaledBitmap(hole, 100, 100, false);
            //c.drawBitmap(mField, 0, 0, null);
            // Sets random locations
            //loadNewMap(c);
            holder.unlockCanvasAndPost(c);
            invalidate();
        }

    }

    /*
    private void loadNewMap(Canvas c) {
        // Calculate number of cells based on screen
        int mCellCols = (int)Math.ceil((float)c.getWidth() / (float)(CELL_SIZE));
        int mCellRows = (int)Math.ceil((float)c.getHeight() / (float)(CELL_SIZE));

        // create cells
        NavCell[][] mCells = new NavCell[mCellRows][mCellCols];
        for(int j=0; j<mCellRows; j++){
            for(int i=0;i<mCellCols;i++){
                mCells[j][i] = new NavCell();
                mCells[j][i].setBounds( // issues
                        i * CELL_SIZE,
                        j * CELL_SIZE,
                        (i*CELL_SIZE)+ CELL_SIZE,
                        (j * CELL_SIZE)+CELL_SIZE);
            }
        }
        // set start cell
        NavCell mCellStart = mCells[mCellRows / 4][mCellCols / 2];
        NavCell mCellEnd = null;

        // set blockers
        int midRow = mCellRows / 2;
        for(int j=0; j<mCellRows;j++){
            for(int i=0; i<mCellCols; i++){
                if(j == midRow && i > 0 && i<mCellCols-1){
                    mCells[j][i].setImpassable();
                }
            }
        }


    }*/

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
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
