package com.example.lab2.mock;

import com.example.lab2.math.trig.CtgFunction;
import com.example.lab2.math.trig.TgFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
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


    private static final double EPSILON = 1e-5;

    @ParameterizedTest
    @DisplayName("tg(x) - корректно использует 1/ctg(x)")
    @MethodSource("com.example.lab2.module.TanFunctionTest#tanArguments")
    void testTgFunction(double x, double expected) {
        // Arrange
        when(cotFunction.proceed(x)).thenReturn(1.0 / Math.tan(x));

        // Act
        double actual = tgFunction.proceed(x);

        // Assert
        assertEquals(expected, actual, EPSILON);
    }

}
