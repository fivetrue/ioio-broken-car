package com.fivetrue.brokencar.controller;

import android.util.Log;

import com.fivetrue.brokencar.controller.output.IOIOOutput;

import java.util.List;

import ioio.lib.android.api.IOIO;
import ioio.lib.android.api.exception.ConnectionLostException;
import ioio.lib.android.util.IOIOLooper;
import ioio.lib.android.util.BaseIOIOLooper;

/**
 * Created by Fivetrue on 2015-04-25.
 */
public class ControlLooper extends BaseIOIOLooper {

    private static final String TAG = "ControlLooper";

    public interface DeviceControlImpl{
        void showVersionInfo(IOIO ioio, String msg);
        void disconnected();
    }

    private List<IOIOOutput> mIOIOOutputs;

    private IOIO mIOIO = null;

    private DeviceControlImpl mControlImpl = null;

    public ControlLooper(DeviceControlImpl impl, List<IOIOOutput> outputs){
        mControlImpl = impl;
        mIOIOOutputs = outputs;
    }

    /**
     * Called every time a connection with IOIO has been established.
     * Typically used to open pins.
     *
     * @throws ConnectionLostException
     *             When IOIO connection is lost.
     *
     */

    @Override
    protected void setup() throws ConnectionLostException {
        mIOIO = ioio_;
        mControlImpl.showVersionInfo(mIOIO, "IOIO connected!");
        for(IOIOOutput output : mIOIOOutputs){
            output.openOutput(mIOIO);
        }
    }

    /**
     * Called repetitively while the IOIO is connected.
     *
     * @throws ConnectionLostException
     *             When IOIO connection is lost.
     * @throws InterruptedException
     * 				When the IOIO thread has been interrupted.
     *
     * @see IOIOLooper#loop()
     */
    @Override
    public void loop() throws ConnectionLostException, InterruptedException {
        for(IOIOOutput output : mIOIOOutputs){
            output.run();
        }
        Thread.sleep(10L);
    }
    /**
     * Called when the IOIO is disconnected.
     *
     * @see IOIOLooper#disconnected()
     */
    @Override
    public void disconnected() {
        mControlImpl.disconnected();
        for(IOIOOutput output : mIOIOOutputs){
            try {
                output.closeOutput();
            } catch (ConnectionLostException e) {
                Log.e(TAG, "disconnected: ", e);
            }
        }
    }

    /**
     * Called when the IOIO is connected, but has an incompatible firmware version.
     *
     * @see IOIOLooper#incompatible(IOIO)
     */
    @Override
    public void incompatible() {
        mControlImpl.showVersionInfo(mIOIO, "Incompatible firmware version!");
    }
}
