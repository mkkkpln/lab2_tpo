package com.example.lab2.math;


public abstract class AFunction {
    protected static final double EPSILON = 1e-6; // Погрешность разложения ряда
    protected final double DELTA;

    protected AFunction(double delta) {
        this.DELTA = delta;
    }

    protected AFunction() {
        this(Math.PI/12); // π/12 (15°)
    }


    public Double proceed(Double x) {
        return proceedInternal(x);
    }

    protected abstract Double proceedInternal(Double x);

    public double delta() {
        return DELTA;
    }
}
