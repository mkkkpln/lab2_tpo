package com.example.lab2.mock;

import com.example.lab2.math.trig.CosFunction;
import com.example.lab2.math.trig.CtgFunction;
import com.example.lab2.math.trig.SinFunction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CtgFunctionTest {

    @InjectMocks
    private CtgFunction ctgFunction;

    @Mock
    private SinFunction sinFunction;

    @Mock
    private CosFunction cosFunction;

    private static final double EPSILON = 1e-5;

    @ParameterizedTest
    @DisplayName("ctg(x) - корректно использует cos(x)/sin(x)")
    @MethodSource("com.example.lab2.module.CotFunctionTest#cotArguments")
    void testCtgFunction(double x, double expected) {
        // Arrange
        doReturn(Math.sin(x)).when(sinFunction).proceed(x);
        doReturn(Math.cos(x)).when(cosFunction).proceed(x);

        // Act
        double actual = ctgFunction.proceed(x);

        // Assert
        assertEquals(expected, actual, EPSILON);
    }
}
