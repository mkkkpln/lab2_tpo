package com.example.lab2.mock;

import com.example.lab2.math.trig.SinFunction;
import com.example.lab2.math.trig.CscFunction;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CscFunctionTest {

    @InjectMocks
    private CscFunction cscFunction;

    @Mock
    private SinFunction sinFunction;

    @Test
    public void testCscFunction() {
        double x = Math.PI / 2;
        when(sinFunction.proceed(x)).thenReturn(1.0);
        assertEquals(1.0, cscFunction.proceed(x));
    }

    @Test
    public void testCscFunction_zero() {
        double x = 0.0;
        when(sinFunction.proceed(x)).thenReturn(0.0);
        assertEquals(Double.NaN, cscFunction.proceed(x));
    }

    @Test
    public void testCscFunction_negative() {
        double x = -Math.PI / 2;
        when(sinFunction.proceed(x)).thenReturn(-1.0);
        assertEquals(-1.0, cscFunction.proceed(x));
    }
}
