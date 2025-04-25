package com.example.lab2.module;


import com.example.lab2.math.trig.SinFunction;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SinFunctionTest {

    private static final double EPSILON = 1e-5;

    @Autowired
    private ApplicationContext context;

    @ParameterizedTest
    @MethodSource("sinArguments")
    public void testSinFunction(double x, double expected) {
        SinFunction sinFunction = context.getBean(SinFunction.class);
        assertEquals(expected, sinFunction.proceed(x), EPSILON);
    }

    private static Stream<Arguments> sinArguments() {
        return Stream.of(
                Arguments.of(0.0, 0.0),
                Arguments.of(Math.PI/6, 0.5),
                Arguments.of(Math.PI/4, Math.sqrt(2)/2),
                Arguments.of(Math.PI/2, 1.0),
                Arguments.of(2*Math.PI/3, Math.sqrt(3)/2),
                Arguments.of(5*Math.PI/6, 0.5),
                Arguments.of(5*Math.PI/4, -Math.sqrt(2)/2),
                Arguments.of(3*Math.PI/2, -1.0),
                Arguments.of(5*Math.PI/3, -Math.sqrt(3)/2),
                Arguments.of(11*Math.PI/6, -0.5),
                Arguments.of(2*Math.PI, 0.0),
                Arguments.of(-Math.PI/2, -1.0)
        );
    }
}
