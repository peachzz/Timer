package com.example.tao.timer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et;
    private Button getTime;
    private TextView time;
    private Button start;
    private Button stop;
    private Button reset;

    private Timer timer;
    private TimerTask task;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            time.setText(msg.arg1 + "");
            if (msg.arg1 <= 0) {
                stopTime();
            } else {
                startTime();
            }
        }
    };

    private void initEvent() {

        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        getTime.setOnClickListener(this);
        reset.setOnClickListener(this);

    }

    private void initView() {

        et = (EditText) findViewById(R.id.et);
        getTime = (Button) findViewById(R.id.get_time);
        time = (TextView) findViewById(R.id.time);
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        reset = (Button) findViewById(R.id.reset);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                startTime();
                break;
            case R.id.stop:
                stopTime();
                break;
            case R.id.get_time:
                time.setText(et.getText().toString());
                i = Integer.parseInt(et.getText().toString());
                break;
            case R.id.reset:
                resetTime();
                break;
        }
    }

    private void resetTime() {
        i = 1;
        startTime();
    }

    private void startTime() {
        if (i > 0) {
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    i--;
                    Message msg = handler.obtainMessage();
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                }
            };
            timer.schedule(task, 100);
        }else {
            stopTime();
        }
    }

    private void stopTime() {
        timer.cancel();
    }
}
