package com.fivetrue.brokencar.controller.output;

import android.util.Log;

import com.fivetrue.brokencar.LL;
import com.fivetrue.brokencar.pin.DefinedPIN;

import ioio.lib.android.api.DigitalOutput;
import ioio.lib.android.api.IOIO;
import ioio.lib.android.api.exception.ConnectionLostException;

/**
 * Created by kwonojin on 2017. 1. 20..
 */

public class IOIODigitalOutput implements IOIOOutput {

    private static final String TAG = "IOIODigitalOutput";

    public final DefinedPIN definedPIN;
    public DigitalOutput digitalOutput;
    public boolean enable;

    public IOIODigitalOutput(DefinedPIN definedPIN){
        this.definedPIN = definedPIN;
    }


    @Override
    public void openOutput(IOIO ioio) throws ConnectionLostException {
        if(LL.D) Log.d(TAG, "openOutput: " + this);
        digitalOutput = ioio.openDigitalOutput(definedPIN.pin);
    }

    @Override
    public void closeOutput() throws ConnectionLostException {
        if(digitalOutput != null){
            digitalOutput.write(false);
        }
    }

    @Override
    public void run() throws ConnectionLostException {
        if(digitalOutput != null){
            digitalOutput.write(enable);
        }
    }
}
