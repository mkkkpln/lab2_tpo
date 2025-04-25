package com.example.lab2.mock;

import com.example.lab2.math.trig.CosFunction;
import com.example.lab2.math.trig.CtgFunction;
import com.example.lab2.math.trig.SinFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.lang.Double.NaN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CtgFunctionTest {

    @InjectMocks
    private CtgFunction ctgFunction;

    @Mock
    private SinFunction sinFunction;

    @Mock
    private CosFunction cosFunction;

    @Test
    public void testCtgFunction() {

        Double x = 1.0;
        Double sinValue = 0.8414709848078965;
        Double cosValue = 0.5403023058681398;
        Double expectedResult = cosValue / sinValue;

        // Act
        when(sinFunction.proceed(x)).thenReturn(sinValue);
        when(cosFunction.proceed(x)).thenReturn(cosValue);
        Double actualResult = ctgFunction.proceed(x);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testCtgFunction_undefined() {

        Double x = Math.PI / 2;
        Double sinValue = 1.0;

        // Act
        when(sinFunction.proceed(x)).thenReturn(sinValue);
        Double actualResult = ctgFunction.proceed(x);

        // Assert
        assertEquals(0.0, actualResult);
    }

    @Test
    public void testCtgFunction_zero() {

        Double x = 0.0;
        Double sinValue = 0.0;
        Double cosValue = 1.0;
        Double expectedResult = NaN;

        // Act
        when(sinFunction.proceed(x)).thenReturn(sinValue);
//        when(cosFunction.proceed(x)).thenReturn(cosValue);
        Double actualResult = ctgFunction.proceed(x);

        // Assert
        assertEquals(expectedResult, actualResult);
    }
}
