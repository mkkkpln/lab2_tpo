package com.example.lab2.module;


import com.example.lab2.math.trig.TgFunction;
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
public class TanFunctionTest {

    private static final double EPSILON = 1e-5;

    @Autowired
    private ApplicationContext context;



    @ParameterizedTest
    @MethodSource("tanArguments")
    public void testTanFunction(double x, double expected) {
        TgFunction tanFunction = context.getBean(TgFunction.class);
        if (Double.isNaN(expected)) {
            assertTrue(Double.isNaN(tanFunction.proceed(x)));
        } else {
            assertEquals(expected, tanFunction.proceed(x), EPSILON);
        }
    }

    private static Stream<Arguments> tanArguments() {
        return Stream.of(
                Arguments.of(0.0, 0.0),
                Arguments.of(Math.PI/6, Math.sqrt(3)/3),
                Arguments.of(Math.PI/4, 1.0),
                Arguments.of(Math.PI/3, Math.sqrt(3)),
                Arguments.of(Math.PI/2, Double.NaN),
                Arguments.of(3*Math.PI/4, -1.0),
                Arguments.of(Math.PI, 0.0),
                Arguments.of(5*Math.PI/4, 1.0),
                Arguments.of(4*Math.PI/3, Math.sqrt(3)),
                Arguments.of(5*Math.PI/3, -Math.sqrt(3)),
                Arguments.of(11*Math.PI/6, -Math.sqrt(3)/3),
                Arguments.of(-Math.PI/4, -1.0)
        );
    }
}
