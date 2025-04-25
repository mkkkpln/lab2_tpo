package com.example.lab2.math.trig;

import com.example.lab2.math.AFunction;

public class CtgFunction extends AFunction {

    private SinFunction sinFunction;
    private CosFunction cosFunction;

    public CtgFunction() {
    }

    public CtgFunction(SinFunction sinFunction, CosFunction cosFunction) {
        super(Math.PI/12); // (15°)
        this.sinFunction = sinFunction;
        this.cosFunction = cosFunction;
    }

    @Override
    protected Double proceedInternal(Double x) {
        Double sinValue = sinFunction.proceed(x);
        if (Math.abs(sinValue) <= EPSILON) {
            return Double.NaN; // Cotangent is undefined when sine is 0
        }
        double cosValue = cosFunction.proceed(x);
        return cosValue / sinValue;
    }
}
