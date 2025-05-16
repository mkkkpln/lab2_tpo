package com.example.lab2.mock;

import com.example.lab2.math.trig.CosFunction;
import com.example.lab2.math.trig.SecFunction;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
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

    private static final double EPSILON = 1e-5;


    @ParameterizedTest
    @DisplayName("sec(x) - корректно использует 1/cos(x)")
    @MethodSource("com.example.lab2.module.SecFunctionTest#secArguments")
    void testSecFunction(double x, double expected) {
        // Arrange
        when(cosFunction.proceed(x)).thenReturn(Math.cos(x));

        // Act
        double actual = secFunction.proceed(x);

        // Assert
        assertEquals(expected, actual, EPSILON);
    }

}
