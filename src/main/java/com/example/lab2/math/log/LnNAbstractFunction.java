package com.example.lab2.math.log;

import com.example.lab2.math.AFunction;

public class LnNAbstractFunction extends AFunction {
    private LnFunction lnFunction;

    protected int n;

    public LnNAbstractFunction() {
    }

    public LnNAbstractFunction(LnFunction lnFunction, int n, double delta) {
        super(delta);
        this.lnFunction = lnFunction;
        this.n = n;
    }

    @Override
    protected Double proceedInternal(Double x) {
        double a = lnFunction.proceed(x);
        double b = lnFunction.proceed((double) n);
        return a / b;
    }

}
