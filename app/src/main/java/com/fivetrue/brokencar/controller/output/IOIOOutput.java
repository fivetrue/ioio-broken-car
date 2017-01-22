package com.fivetrue.brokencar.controller.output;

import ioio.lib.android.api.IOIO;
import ioio.lib.android.api.exception.ConnectionLostException;

/**
 * Created by kwonojin on 2017. 1. 20..
 */

public interface IOIOOutput {

    void openOutput(IOIO ioio) throws ConnectionLostException;

    void closeOutput() throws ConnectionLostException;

    void run() throws ConnectionLostException;
}
