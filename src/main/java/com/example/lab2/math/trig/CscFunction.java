package com.example.lab2.math.trig;

import com.example.lab2.math.AFunction;

public class CscFunction extends AFunction {

    private SinFunction sinFunction;

    public CscFunction() {
    }

    public CscFunction(SinFunction sinFunction) {
        super(Math.PI/6); // (30°)
        this.sinFunction = sinFunction;
    }

    @Override
    protected Double proceedInternal(Double x) {
        Double sinValue = sinFunction.proceed(x);
        if (Math.abs(sinValue) <= EPSILON) return Double.NaN;
        return 1.0 / sinValue;
    }
}
