package com.example.lab2.mock;


import com.example.lab2.math.trig.CosFunction;
import com.example.lab2.math.trig.SinFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CosFunctionTest {

    @InjectMocks
    private CosFunction cosFunction;

    @Mock
    private SinFunction sinFunction;

    private static final double EPSILON = 1e-5;

    @ParameterizedTest
    @DisplayName("cos(x) - корректно использует sin(π/2 - x)")
    @MethodSource("com.example.lab2.module.CosFunctionTest#cosArguments")
    void testCosFunction(double x, double expected) {
        // Arrange
        when(sinFunction.proceed(Math.PI/2 - x)).thenReturn(Math.sin(Math.PI/2 - x));

        // Act
        double actual = cosFunction.proceed(x);

        // Assert
        assertEquals(expected, actual, EPSILON);
    }
}
