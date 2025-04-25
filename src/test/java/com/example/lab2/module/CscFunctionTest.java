package com.example.lab2.module;


import com.example.lab2.math.trig.CscFunction;
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
public class CscFunctionTest {

    private static final double EPSILON = 1e-5;

    @Autowired
    private ApplicationContext context;

    @ParameterizedTest
    @MethodSource("cscArguments")
    public void testCscFunction(double x, double expected) {
        CscFunction cscFunction = context.getBean(CscFunction.class);
        if (Double.isNaN(expected)) {
            assertTrue(Double.isNaN(cscFunction.proceed(x)));
        } else {
            assertEquals(expected, cscFunction.proceed(x), EPSILON);
        }
    }

    private static Stream<Arguments> cscArguments() {
        return Stream.of(
                Arguments.of(0.0, Double.NaN),
                Arguments.of(Math.PI/6, 2.0),
                Arguments.of(Math.PI/4, Math.sqrt(2)),
                Arguments.of(Math.PI/2, 1.0),
                Arguments.of(2*Math.PI/3, 2.0/Math.sqrt(3)),
                Arguments.of(5*Math.PI/6, 2.0),
                Arguments.of(Math.PI, Double.NaN),
                Arguments.of(5*Math.PI/4, -Math.sqrt(2)),
                Arguments.of(3*Math.PI/2, -1.0),
                Arguments.of(5*Math.PI/3, -2.0/Math.sqrt(3)),
                Arguments.of(2*Math.PI, Double.NaN),
                Arguments.of(-Math.PI/2, -1.0)
        );
    }
}
