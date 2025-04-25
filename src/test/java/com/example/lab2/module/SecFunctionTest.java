package com.example.lab2.module;


import com.example.lab2.math.trig.SecFunction;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SecFunctionTest {

    private static final double EPSILON = 1e-5;

    @Autowired
    private ApplicationContext context;

    @ParameterizedTest
    @MethodSource("secArguments")
    public void testSecFunction(double x, double expected) {
        SecFunction secFunction = context.getBean(SecFunction.class);
        if (Double.isNaN(expected)) {
            assertTrue(Double.isNaN(secFunction.proceed(x)));
        } else {
            assertEquals(expected, secFunction.proceed(x), EPSILON);
        }
    }

    private static Stream<Arguments> secArguments() {
        return Stream.of(
                Arguments.of(0.0, 1.0),
                Arguments.of(Math.PI/6, 2.0/Math.sqrt(3)),
                Arguments.of(Math.PI/4, Math.sqrt(2)),
                Arguments.of(Math.PI/2, Double.NaN),
                Arguments.of(2*Math.PI/3, -2.0),
                Arguments.of(5*Math.PI/6, -2.0/Math.sqrt(3)),
                Arguments.of(Math.PI, -1.0),
                Arguments.of(5*Math.PI/4, -Math.sqrt(2)),
                Arguments.of(3*Math.PI/2, Double.NaN),
                Arguments.of(11*Math.PI/6, 2.0/Math.sqrt(3)),
                Arguments.of(2*Math.PI, 1.0),
                Arguments.of(-Math.PI/4, Math.sqrt(2))
        );
    }
}
