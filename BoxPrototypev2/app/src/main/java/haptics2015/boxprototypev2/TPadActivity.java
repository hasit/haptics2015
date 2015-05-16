package haptics2015.boxprototypev2;

import haptics2015.boxprototypev2.models.BoxView;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class TPadActivity extends Activity {

    private BoxView boxView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tpad);

        boxView = (BoxView)findViewById(R.id.fullscreen_content);

        final SeekBar frictionBar = (SeekBar) findViewById(R.id.frictionBar);
        final TextView frictionValue = (TextView) findViewById(R.id.frictionVal);

        frictionBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                frictionValue.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Friction Set to " + progress, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            // the gestures
            if (boxView.isTouched()) {
                // the box was picked up and is being dragged
                //mTpad.sendVibration(TPadVibration.SINUSOID, 350, 1.0f);
                if(event.getX() > 150 && event.getX() < boxView.getWidth() - 150) {
                    boxView.setBoxX((int) event.getX());
                    boxView.invalidate();
                }
                //box.setY((int)event.getY());
            } else {
                //mTpad.turnOff();
            }
        } if (event.getAction() == MotionEvent.ACTION_UP) {
            // touch was released
            //mTpad.turnOff();
            if (boxView.isTouched()) {
                boxView.setTouched(false);
            }
        }
        return true;
    }
}
