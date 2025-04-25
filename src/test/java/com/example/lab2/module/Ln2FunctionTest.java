package com.example.lab2.module;


import com.example.lab2.math.log.Ln2Function;
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
public class Ln2FunctionTest {


    private static final double EPSILON = 1e-5;

    @Autowired
    private ApplicationContext context;

    @ParameterizedTest
    @MethodSource("ln2Arguments")
    public void testLn2Function(double x, double expected) {
        Ln2Function ln2Function = context.getBean(Ln2Function.class);
        if (x <= 0) {
            assertThrows(IllegalArgumentException.class, () -> ln2Function.proceed(x));
        } else {
            assertEquals(expected, ln2Function.proceed(x), EPSILON);
        }
    }

    private static Stream<Arguments> ln2Arguments() {
        return Stream.of(
                Arguments.of(0.5, -1.0),
                Arguments.of(1.0, 0.0),
                Arguments.of(4.0, 2.0),
                Arguments.of(16.0, 4.0),
                Arguments.of(64.0, 6.0),
                Arguments.of(256.0, 8.0),
                Arguments.of(1024.0, 10.0),
                Arguments.of(0.125, -3.0),
                Arguments.of(3.0, 1.5849625007211556),
                Arguments.of(0.0, Double.NaN)
        );
    }

}
