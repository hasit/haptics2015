package haptics2015.boxprototypev2.models;

/**
 * Created by ashik on 5/16/15.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class BoxView extends View {

    private float x = 300;			// the X coordinate
    private float y = 500;			// the Y coordinate
    private boolean touched;	    // if droid is touched/picked up
    private Paint paint;            //Paint object to draw blue rect
    private Paint platformPaint;    //Paint object to draw platform

    public static final int size = 150;
    public static final int bgColor = Color.parseColor("#0099CC");
    public static final int boxColor = Color.parseColor("#0000A0");

    private static final String TAG = BoxView.class.getSimpleName();

    public BoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }
    public BoxView(Context context) {
        super(context);
        initPaint();
    }

    public void initPaint() {
        paint = new Paint();
        paint.setColor(boxColor);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(10);

        platformPaint = new Paint();
        platformPaint.setColor(Color.RED);
        platformPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        platformPaint.setStrokeWidth(10);
    }

    public float getBoxX() {
        return x;
    }
    public void setBoxX(float x) {
        this.x = x;
    }
    public float getBoxY() {
        return y;
    }
    public void setBoxY(float y) {
        this.y = y;
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    @Override
    public void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        canvas.drawColor(bgColor);
        canvas.drawRect(x - size / 2, y - size / 2, x + size / 2, y + size / 2, paint);
        canvas.drawLine(0, y+10+size/2, getWidth(), y+10+size/2, platformPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float eventX = e.getX();
        float eventY = e.getY();

        if (eventX >= (x - size / 2) && (eventX <= (x + size/2))) {
            if (eventY >= (y - size / 2) && (eventY <= (y + size / 2))) {
                // droid touched
                Log.d(TAG, "Touch at x=" + eventX + ",y=" + eventY);
                Log.d(TAG, "Box at x=" + x + ",y=" + y);
                setTouched(true);
            } else {
                setTouched(false);
            }
        } else {
            setTouched(false);
        }

        return false;
    }
}
