package com.example.lab2;

import com.example.lab2.math.MyFunction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MyFunctionTest {

    @Autowired
    private MyFunction myFunction;

    private static final double EPSILON = 1e-3;

    @ParameterizedTest
    @MethodSource("testPointsProvider")
    public void testMyFunctionAtSpecificPoints(double x, double expected) {
        double actual = myFunction.proceed(x);

        if (Double.isNaN(expected)) {
            assertTrue(Double.isNaN(actual),
                    "Expected NaN for x = " + x + " but got " + actual);
        } else {
            assertEquals(expected, actual, EPSILON,
                    "Function value mismatch at x = " + x);
        }
    }

    private static Stream<Arguments> testPointsProvider() {
        return Stream.of(
                // Точки, где функция равна 0
                Arguments.of(0.13878, 0.0),
                Arguments.of(3.05454, 0.0),
                Arguments.of(-1.24905, 0.0),
                Arguments.of(-4.39064, 0.0),

                // Точки с ненулевыми значениями
                Arguments.of(5.0, 2.03964),
                Arguments.of(0.24655, 5.0),
                Arguments.of(-2.24305, 0.44532),
                Arguments.of(-5.37977, 0.40804),

                // Невидимые точки
                Arguments.of(-3.76296, -182.62821),
                Arguments.of(-10.04615, -182.62821),
                Arguments.of(-22.61252, -182.62821),

                // Граничные случаи
                Arguments.of(0.0, Double.NaN),  // Нет значения
                Arguments.of(1.0, Double.NaN)   // Логарифм от 1 дает 0 → деление на 0
        );
    }
}