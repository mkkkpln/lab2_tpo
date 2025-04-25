package com.example.lab2.math;

import com.example.lab2.math.log.Ln10Function;
import com.example.lab2.math.log.Ln2Function;
import com.example.lab2.math.log.Ln3Function;
import com.example.lab2.math.trig.*;

public class MyFunction  {

    private SinFunction sinFunction;
    private CosFunction cosFunction;
    private TgFunction tgFunction;
    private CtgFunction ctgFunction;
    private SecFunction secFunction;
    private CscFunction cscFunction;
    private Ln2Function ln2Function;
    private Ln3Function ln3Function;
    private Ln10Function ln10Function;

    public MyFunction(SinFunction sinFunction, CosFunction cosFunction, TgFunction tgFunction, CtgFunction ctgFunction, SecFunction secFunction, CscFunction cscFunction, Ln2Function ln2Function, Ln3Function ln3Function, Ln10Function ln10Function) {
        this.sinFunction = sinFunction;
        this.cosFunction = cosFunction;
        this.tgFunction = tgFunction;
        this.ctgFunction = ctgFunction;
        this.secFunction = secFunction;
        this.cscFunction = cscFunction;
        this.ln2Function = ln2Function;
        this.ln3Function = ln3Function;
        this.ln10Function = ln10Function;
    }

    public Double proceed(Double x) {
        if (x <= 0) {
            // Верхняя часть дроби
            Double tanX = tan(x);
            Double cosX = cos(x);
            Double sinX = sin(x);
            Double numerator = Math.pow(
                    (
                            ((((((tanX + cosX) + sinX) + cosX) - (sinX / cosX)) + cosX))
                                    / (csc(x) * Math.pow(Math.pow(tanX, 3), 2))
                    ), 2
            );

            // Нижняя часть дроби
            Double inner = csc(x) * sec(x) - cosX + csc(x);
            Double denominator = (cot(x) * (tanX + inner)) + (sinX - (Math.pow(tanX, 3) + csc(x)));

            return numerator / denominator;

        } else {
            Double log3 = log3(x);
            Double log10 = log10(x);
            Double log2 = log2(x);

            Double numerator = (
                    (log3 * log10 / Math.pow(log10, 3)) + (log10 * log2)
            ) * (
                    ((log2 + log10) * log10) - log3
            );

            return numerator / log3;
        }
    }

    // Вспомогательные обёртки:
    private Double sin(Double x) {
        return sinFunction.proceed(x);
    }

    private Double cos(Double x) {
        return cosFunction.proceed(x);
    }

    private Double tan(Double x) {
        return tgFunction.proceed(x);
    }

    private Double cot(Double x) {
        return ctgFunction.proceed(x);
    }

    private Double sec(Double x) {
        return secFunction.proceed(x);
    }

    private Double csc(Double x) {
        return cscFunction.proceed(x);
    }

    private Double log2(Double x) {
        return ln2Function.proceed(x);
    }

    private Double log3(Double x) {
        return ln3Function.proceed(x);
    }

    private Double log10(Double x) {
        return ln10Function.proceed(x);
    }
}
