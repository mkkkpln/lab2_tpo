package com.example.lab2.math.trig;

import com.example.lab2.math.AFunction;


public class CosFunction extends AFunction {

    private SinFunction sinFunction;

    public CosFunction() {
    }

    public CosFunction(SinFunction sinFunction) {
        super(Math.PI/6); // (30°)
        this.sinFunction = sinFunction;
    }

    @Override
    protected Double proceedInternal(Double x) {
        Double angle = Math.PI / 2 - x;
        return sinFunction.proceed(angle);
    }
}
