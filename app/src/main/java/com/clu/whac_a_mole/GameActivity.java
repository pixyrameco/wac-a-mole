package com.clu.whac_a_mole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    Context context;
    int counter = 0, tick = 0;
    TextView counterText, secondsLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        counterText = (TextView) findViewById(R.id.mainText);
        secondsLeft = (TextView) findViewById(R.id.timer);

        Timer myTimer;
        myTimer = new Timer();

        context = this;
        myTimer.schedule(new TimerTask() {
            public void run() {
                tick++;
                if (tick <= 10) {
                    timerTick();
                }
                else{
                    myTimer.cancel();
                    Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                    intent.putExtra("score", counter);
                    startActivity(intent);
                    finish();
                }
            }
        }, 0, 500);
    }

    private void timerTick() {
        this.runOnUiThread(doTask);
    }

    private Runnable doTask = new Runnable() {
        public void run() {
            Resources res = getResources();
            Button[] buttons = new Button[9];
            boolean[] beaten = {false};
            for (int i = 1; i <= 9; i++) {
                buttons[i - 1] = (Button) findViewById(res.getIdentifier("b" + i, "id", context.getPackageName()));
                int finalI = i;
                buttons[finalI - 1].setBackgroundColor(context.getResources().getColor(R.color.purple_500));
                buttons[i - 1].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        buttons[finalI - 1].setBackgroundColor(context.getResources().getColor(R.color.black));
                    }
                });
            }
            int random = (int)(Math.random() * 8 + 1);
            buttons[random].setBackground(context.getResources().getDrawable(R.drawable.rabbit_foreground));
            buttons[random].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!beaten[0]){
                        counter++;
                        beaten[0] = true;
                        counterText.setText("Score: " +  counter);
                    }
                }
            });
            secondsLeft.setText((30 - (tick / 2)) + "s");
        }
    };
}