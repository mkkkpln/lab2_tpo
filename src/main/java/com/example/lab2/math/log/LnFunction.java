package com.example.lab2.math.log;

import com.example.lab2.math.AFunction;

public class LnFunction extends AFunction {

    public LnFunction() {
        super(0.2);
    }

    @Override
    protected Double proceedInternal(Double n) {

        if (n <= 0) {
            throw new IllegalArgumentException("x must be greater than 0 and not equal to 1");
        }

        double num, mul, cal, sum = 0;
        num = (n - 1) / (n + 1);


        for (int i = 1; i <= 1000000; i++)
        {
            mul = (2 * i) - 1;
            cal = Math.pow(num, mul);
            cal = cal / mul;
            sum = sum + cal;
        }
        sum = 2 * sum;
        return sum;
    }
}
