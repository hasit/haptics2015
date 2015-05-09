package haptics.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * @author impaler
 *
 */
public class Box {

	private int x;			// the X coordinate
	private int y;			// the Y coordinate
    private boolean touched;	// if droid is touched/picked up
    private Paint paint;    //Paint object to draw blue rect

	public Box(int x, int y) {
		//this.bitmap = bitmap;
		this.x = x;
		this.y = y;

        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(10);
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}
	
	public void draw(Canvas canvas) {
        canvas.drawRect(x, y, x+75, y+75, paint);
	}

	/**
	 * Handles the {@link MotionEvent.ACTION_DOWN} event. If the event happens on the 
	 * bitmap surface then the touched state is set to <code>true</code> otherwise to <code>false</code>
	 * @param eventX - the event's X coordinate
	 * @param eventY - the event's Y coordinate
	 */
	public void handleActionDown(int eventX, int eventY) {
		if (eventX >= (x - 75 / 2) && (eventX <= (x + 75/2))) {
			if (eventY >= (y - 75 / 2) && (y <= (y + 75 / 2))) {
				// droid touched
				setTouched(true);
			} else {
				setTouched(false);
			}
		} else {
			setTouched(false);
		}
	}
}
