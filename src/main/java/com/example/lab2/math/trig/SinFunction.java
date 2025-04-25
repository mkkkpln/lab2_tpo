package com.example.lab2.math.trig;

import com.example.lab2.math.AFunction;

public class SinFunction extends AFunction {


    public SinFunction() {
        super(Math.PI/6); // (30°)
    }

    @Override
    protected Double proceedInternal(Double x) {
        double result = 0.0d;
        Double term = x;
        int n = 1;
        while (Math.abs(term) > EPSILON) {
            result += term;
            term = -term * x * x / ((2 * n) * (2 * n + 1));
            n++;
        }
        return result;
    }
}
