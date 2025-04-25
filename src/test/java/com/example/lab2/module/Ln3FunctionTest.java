package com.example.lab2.module;


import com.example.lab2.math.log.Ln3Function;
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
public class Ln3FunctionTest {

    private static final double EPSILON = 1e-5;

    @Autowired
    private ApplicationContext context;

    @ParameterizedTest
    @MethodSource("ln3Arguments")
    public void testLn3Function(double x, double expected) {
        Ln3Function ln3Function = context.getBean(Ln3Function.class);
        if (x <= 0) {
            assertThrows(IllegalArgumentException.class, () -> ln3Function.proceed(x));
        } else {
            assertEquals(expected, ln3Function.proceed(x), EPSILON);
        }
    }

    private static Stream<Arguments> ln3Arguments() {
        return Stream.of(
                Arguments.of(1.0, 0.0),
                Arguments.of(9.0, 2.0),
                Arguments.of(81.0, 4.0),
                Arguments.of(2187.0, 7.0),
                Arguments.of(6561.0, 8.0),
                Arguments.of(1.0/3, -1.0),
                Arguments.of(4.0, 1.2618595071429164),
                Arguments.of(0.0, Double.NaN),
                Arguments.of(-27.0, Double.NaN)
        );
    }
}
