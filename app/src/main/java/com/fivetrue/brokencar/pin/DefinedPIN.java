package com.fivetrue.brokencar.pin;

/**
 * Created by kwonojin on 2017. 1. 20..
 */

public enum DefinedPIN implements Runnable{

    FRONT_MOTOR_LEFT(43), FRONT_MOTOR_RIGHT(44), REAR_MOTOR_BACKWARD(45), REAR_MOTOR_FORWARD(46);


    public final int pin;

    DefinedPIN(int pin){
        this.pin = pin;
    }

    @Override
    public void run() {

    }
}
