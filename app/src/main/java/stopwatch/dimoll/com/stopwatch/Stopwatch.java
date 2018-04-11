package stopwatch.dimoll.com.stopwatch;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Stopwatch extends AppCompatActivity {

    private int seconds=0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running=savedInstanceState.getBoolean("running");
            wasRunning=savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onPause(){
        super.onPause();
        wasRunning=running;
        running=false;
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(wasRunning) running=true;
    }

    private void runTimer(){
        final TextView timeView = (TextView)findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long startTime=System.currentTimeMillis();
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format("%d:%02d:%02d",
                            hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                long timeSpent = System.currentTimeMillis() - startTime;
                handler.postDelayed(this, (1000-timeSpent));
                }
            });
    }

    public void onClickStart(View view){
        running=true;
    }

    public void onClickStop(View view){
        running=false;
    }

    public void onClickReset(View view){
        seconds=0;
        running=false;
    }
}
