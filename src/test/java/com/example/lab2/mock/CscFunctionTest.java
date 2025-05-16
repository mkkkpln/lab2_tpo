package com.example.lab2.mock;

import com.example.lab2.math.trig.SinFunction;
import com.example.lab2.math.trig.CscFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
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

    private static final double EPSILON = 1e-5;

    @ParameterizedTest
    @DisplayName("csc(x) - корректно использует 1/sin(x)")
    @MethodSource("com.example.lab2.module.CscFunctionTest#cscArguments")
    void testCscFunction(double x, double expected) {
        // Arrange
        when(sinFunction.proceed(x)).thenReturn(Math.sin(x));

        // Act
        double actual = cscFunction.proceed(x);

        // Assert
        assertEquals(expected, actual, EPSILON);
    }
}
