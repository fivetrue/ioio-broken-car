package com.fivetrue.brokencar.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.fivetrue.brokencar.R;
import com.fivetrue.brokencar.controller.ControlLooper;
import com.fivetrue.brokencar.controller.output.IOIOOutput;
import com.fivetrue.brokencar.pin.DefinedPIN;
import com.fivetrue.brokencar.controller.output.IOIODigitalOutput;

import java.util.ArrayList;

import ioio.lib.android.api.IOIO;
import ioio.lib.android.util.IOIOLooper;
import ioio.lib.android.util.android.IOIOActivity;

public class MainActivity extends IOIOActivity implements ControlLooper.DeviceControlImpl, View.OnTouchListener, View.OnClickListener{

    private static final String TAG = "MainActivity";

    private ControlLooper mControlLooper;

    private final IOIOOutput mFrontMotorLeft = new IOIODigitalOutput(DefinedPIN.FRONT_MOTOR_LEFT);
    private final IOIOOutput mFrontMotorRight = new IOIODigitalOutput(DefinedPIN.FRONT_MOTOR_RIGHT);
    private final IOIOOutput mRearMotorBackward = new IOIODigitalOutput(DefinedPIN.REAR_MOTOR_BACKWARD);
    private final IOIOOutput mRearMotorForward = new IOIODigitalOutput(DefinedPIN.REAR_MOTOR_FORWARD);

    private ImageView mFrontRight;
    private ImageView mFrontLeft;
    private ImageView mBackward;
    private ImageView mForward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    @Override
    public void showVersionInfo(IOIO ioio, String msg) {
        Log.d(TAG, "showVersionInfo() called with: ioio = [" + ioio + "], msg = [" + msg + "]");
    }

    @Override
    public void disconnected() {
        Log.d(TAG, "disconnected() called");

    }

    private void initData(){
        ArrayList<IOIOOutput> outputs = new ArrayList<>();
        outputs.add(mFrontMotorLeft);
        outputs.add(mFrontMotorRight);
        outputs.add(mRearMotorBackward);
        outputs.add(mRearMotorForward);
        mControlLooper = new ControlLooper(this, outputs);
    }

    private void initView(){
        mFrontRight = (ImageView) findViewById(R.id.btn_main_left);
        mFrontLeft = (ImageView) findViewById(R.id.btn_main_right);
        mBackward = (ImageView) findViewById(R.id.btn_main_backward);
        mForward = (ImageView) findViewById(R.id.btn_main_forward);

        mFrontRight.setTag(mFrontMotorRight);
        mFrontLeft.setTag(mFrontMotorLeft);
        mForward.setTag(mRearMotorForward);
        mBackward.setTag(mRearMotorBackward);

        mFrontRight.setOnTouchListener(this);
        mFrontLeft.setOnTouchListener(this);
        mBackward.setOnTouchListener(this);
        mForward.setOnTouchListener(this);
        mFrontRight.setOnClickListener(this);
        mFrontLeft.setOnClickListener(this);
        mBackward.setOnClickListener(this);
        mForward.setOnClickListener(this);
    }

    @Override
    protected IOIOLooper createIOIOLooper() {
        return mControlLooper;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(view.getTag() != null && view.getTag() instanceof IOIODigitalOutput){
            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN :
                    ((IOIODigitalOutput) view.getTag()).enable = true;
                    return false;

                case MotionEvent.ACTION_UP :
                    ((IOIODigitalOutput) view.getTag()).enable = false;
                    return false;

            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {

    }
}
