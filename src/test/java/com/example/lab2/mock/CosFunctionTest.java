package com.example.lab2.mock;


import com.example.lab2.math.trig.CosFunction;
import com.example.lab2.math.trig.SinFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CosFunctionTest {

    @InjectMocks
    private CosFunction cosFunction;

    @Mock
    private SinFunction sinFunction;

    @Test
    public void testCosFunction() {
        double x = 1.0;
        Double expectedResult = 0.0;

        // Act
        when(sinFunction.proceed(Math.PI / 2 - x)).thenReturn(expectedResult);
        Double actualResult = cosFunction.proceed(x);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    // cos(x) = sin(Pi/2 - x)

    @Test
    public void testCosFunction_pi() {

        double x = Math.PI;
        Double expectedResult = -1.0;

        // Act
        when(sinFunction.proceed(Math.PI / 2 - x)).thenReturn(expectedResult);
        Double actualResult = cosFunction.proceed(x);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testCosFunction_zero() {

        double x = 0.0;
        Double expectedResult = 1.0;

        // Act
        when(sinFunction.proceed(Math.PI / 2 - x)).thenReturn(expectedResult);
        Double actualResult = cosFunction.proceed(x);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testCosFunction_negative() {

        double x = -1.0;
        Double expectedResult = 0.0;

        // Act
        when(sinFunction.proceed(Math.PI / 2 - x)).thenReturn(expectedResult);
        Double actualResult = cosFunction.proceed(x);

        // Assert
        assertEquals(expectedResult, actualResult);
    }
}
