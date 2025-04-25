package com.example.lab2.math.trig;

import com.example.lab2.math.AFunction;

public class TgFunction extends AFunction {

    private CtgFunction cotFunction;

    public TgFunction() {
    }

    public TgFunction(CtgFunction cotFunction) {
        super(Math.PI/12); // (15°)
        this.cotFunction = cotFunction;
    }

    @Override
    protected Double proceedInternal(Double x) {
        Double cotValue = cotFunction.proceed(x);
        if (Double.isNaN(cotValue)) {
            return (double) 0;
        }
        if (Math.abs(cotValue) < EPSILON) {
            return Double.NaN;
        }
        return 1 / cotValue;
    }
}
