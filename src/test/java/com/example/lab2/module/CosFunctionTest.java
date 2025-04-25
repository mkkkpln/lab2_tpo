package com.example.lab2.module;

import com.example.lab2.math.trig.CosFunction;
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
public class CosFunctionTest {
    private static final double EPSILON = 1e-5;

    @Autowired
    private ApplicationContext context;

    @ParameterizedTest
    @MethodSource("cosArguments")
    public void testCosFunction(double x, double expected) {
        CosFunction cosFunction = context.getBean(CosFunction.class);
        assertEquals(expected, cosFunction.proceed(x), EPSILON);
    }

    private static Stream<Arguments> cosArguments() {
        return Stream.of(
                Arguments.of(0.0, 1.0),
                Arguments.of(Math.PI/6, Math.sqrt(3)/2),
                Arguments.of(Math.PI/3, 0.5),
                Arguments.of(2*Math.PI/3, -0.5),
                Arguments.of(3*Math.PI/4, -Math.sqrt(2)/2),
                Arguments.of(Math.PI, -1.0),
                Arguments.of(5*Math.PI/4, -Math.sqrt(2)/2),
                Arguments.of(3*Math.PI/2, 0.0),
                Arguments.of(7*Math.PI/4, Math.sqrt(2)/2),
                Arguments.of(2*Math.PI, 1.0),
                Arguments.of(-Math.PI/3, 0.5)
        );
    }
}
