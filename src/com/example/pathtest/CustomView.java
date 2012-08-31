package com.example.pathtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * This is the custom View used to demonstrate two issues I have with Android's
 * Path class when target API-level is set to 14 or higher. Both of these issues
 * don't occur on the emulator running Android 4.1 but do occur on my Nexus 7
 * (Android 4.1 as well). Issues are described in detail in the onDraw() method.
 * 
 * @author Georg Wiese
 * 
 */
public class CustomView extends View {

	Path path1, path2;
	Paint paint;

	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);

		paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(3);

		path1 = new Path();
		path1.lineTo(100, 100);
		path1.lineTo(200, 300);

		path2 = new Path();
		for (float x = 0; x < 400; x++)
			path2.lineTo(x, x * x);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		/**
		 * Issue Nr. 1: This code should offset path1 one by 1 pixel in each
		 * dimension each time onDraw() is called. The emulator does exactly
		 * this, my Nexus 7 offsets it once (as one can check e.g. by setting
		 * offset values to 100) and never again...
		 */
		path1.offset(1, 1);
		paint.setColor(Color.RED);
		canvas.drawPath(path1, paint);

		/**
		 * Issue Nr. 2: path2 is kind of special because y-values get really
		 * high. Nevertheless, a portion of the path should be seen. Also, this
		 * does work on the emulator, Nexus 7 completely ignores this path.
		 */
		paint.setColor(Color.BLUE);
		canvas.drawPath(path2, paint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.d("Developer", "Calling invalidate() now.");
		invalidate();
		return super.onTouchEvent(event);
	}
}
