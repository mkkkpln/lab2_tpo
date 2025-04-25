package com.example.lab2.math.trig;

import com.example.lab2.math.AFunction;

public class SecFunction extends AFunction {

    private CosFunction cosFunction;

    public SecFunction() {
    }

    public SecFunction(CosFunction cosFunction) {
        super(Math.PI/6); // (30°)
        this.cosFunction = cosFunction;
    }

    @Override
    protected Double proceedInternal(Double x) {
        Double cosValue = cosFunction.proceed(x);
        if (Math.abs(cosValue) <= EPSILON) return Double.NaN;
        return 1.0 / cosValue;
    }
}
