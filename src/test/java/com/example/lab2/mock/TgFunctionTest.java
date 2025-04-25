package com.example.lab2.mock;

import com.example.lab2.math.trig.CtgFunction;
import com.example.lab2.math.trig.TgFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class TgFunctionTest {


    @InjectMocks
    private TgFunction tgFunction;

    @Mock
    private CtgFunction cotFunction;

    @Test
    public void testTgFunction() {

        Double x = 1.0;
        Double cotValue = 1.0;
        Double expectedResult = 1 / cotValue;

        // Act
        when(cotFunction.proceed(x)).thenReturn(cotValue);
        Double actualResult = tgFunction.proceed(x);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testTgFunction_negativeValue() {
        Double x = -1.0;
        Double cotValue = -1.0;
        Double expectedResult = 1 / cotValue;

        // Act
        when(cotFunction.proceed(x)).thenReturn(cotValue);
        Double actualResult = tgFunction.proceed(x);

        // Assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testTgFunction_zeroValue() {
        Double x = 0.0;
        Double cotValue = Double.POSITIVE_INFINITY;
        Double expectedResult = 1 / cotValue;

        // Act
        when(cotFunction.proceed(x)).thenReturn(cotValue);
        Double actualResult = tgFunction.proceed(x);

        // Assert
        assertEquals(expectedResult, actualResult);
    }
}
