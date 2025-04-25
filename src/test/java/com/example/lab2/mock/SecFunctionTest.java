package com.example.lab2.mock;

import com.example.lab2.math.trig.CosFunction;
import com.example.lab2.math.trig.SecFunction;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SecFunctionTest {

    @InjectMocks
    private SecFunction secFunction;

    @Mock
    private CosFunction cosFunction;

    @Test
    public void testSecFunction() {
        double x = Math.PI;
        Double cosValue = -1.0;
        when(cosFunction.proceed(x)).thenReturn(cosValue);
        assertEquals(-1.0, secFunction.proceed(x));
    }

    @Test
    public void testSecFunction_zero() {
        double x = 0.0;
        when(cosFunction.proceed(x)).thenReturn(1.0);
        assertEquals(1.0, secFunction.proceed(x));
    }

    @Test
    public void testSecFunction_undefined() {
        double x = Math.PI / 2;
        when(cosFunction.proceed(x)).thenReturn(0.0);
        assertEquals(Double.NaN, secFunction.proceed(x));
    }
}
