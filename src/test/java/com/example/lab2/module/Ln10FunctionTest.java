package com.example.lab2.module;


import com.example.lab2.math.log.Ln10Function;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class Ln10FunctionTest {

    private static final double EPSILON = 1e-5;

    @Autowired
    private ApplicationContext context;

    @ParameterizedTest
    @MethodSource("ln10Arguments")
    public void testLn10Function(double x, double expected) {
        Ln10Function ln10Function = context.getBean(Ln10Function.class);
        if (x <= 0) {
            assertThrows(IllegalArgumentException.class, () -> ln10Function.proceed(x));
        } else {
            assertEquals(expected, ln10Function.proceed(x), EPSILON);
        }
    }

    private static Stream<Arguments> ln10Arguments() {
        return Stream.of(
                Arguments.of(1.0, 0.0),
                Arguments.of(10.0, 1.0),
                Arguments.of(100.0, 2.0),
                Arguments.of(100000.0, 5.0),
                Arguments.of(0.1, -1.0),
                Arguments.of(0.001, -3.0),
                Arguments.of(2.0, 0.30102999566398114),
                Arguments.of(20.0, 1.3010299956639813),
                Arguments.of(0.0, Double.NaN),
                Arguments.of(-10.0, Double.NaN)
        );
    }
}
