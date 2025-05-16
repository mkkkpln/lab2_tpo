package com.example.lab2;

import com.example.lab2.math.AFunction;
import com.example.lab2.math.MyFunction;
import com.example.lab2.math.log.Ln10Function;
import com.example.lab2.math.log.Ln2Function;
import com.example.lab2.math.log.Ln3Function;
import com.example.lab2.math.log.LnFunction;
import com.example.lab2.math.trig.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * ИЕРАРХИЯ ФУНКЦИЙ:
 * <p>
 * Верхний уровень:
 *   MyFunction
 * <p>
 * Уровень 3:
 *   tg → ctg
 * Уровень 2:
 *   sec → cos
 *   ctg → sin, cos
 * <p>
 * Уровень 1:
 *   cos → sin
 *   csc → sin
 *   ln2 → ln
 *   ln3 → ln
 *   ln10 → ln
 * <p>
 * Базовые функции:
 *   sin
 *   ln
 */
public class MyFunctionIntegrationTest {

    private static final Map<Double, Double> SIN_VALUES = Map.ofEntries(
            Map.entry(0.0, 0.0),
            Map.entry(Math.PI/2, 1.0),
            Map.entry(Math.PI, 0.0),
            Map.entry(3*Math.PI/2, -1.0),
            Map.entry(Math.PI/6, 0.5),
            Map.entry(1.0, 0.8414709848),
            Map.entry(Math.PI/4, 0.7071067812)
    );

    private static final Map<Double, Double> LN_VALUES = Map.ofEntries(
            Map.entry(1.0, 0.0),
            Map.entry(Math.E, 1.0),
            Map.entry(2.0, 0.69314718056),
            Map.entry(0.5, -0.69314718056),
            Map.entry(10.0, 2.30258509299),
            Map.entry(3.0, 1.09861228867),
            Map.entry(9.0, 2.1972245773),
            Map.entry(27.0, 3.2958368660),
            Map.entry(100.0, 4.6051701860),
            Map.entry(1000.0, 6.9077552790)
    );

    private static final Map<Double, Double> COS_VALUES = Map.ofEntries(
            Map.entry(0.0, 1.0),
            Map.entry(Math.PI/2, 0.0),
            Map.entry(Math.PI, -1.0),
            Map.entry(3*Math.PI/2, 0.0),
            Map.entry(Math.PI/3, 0.5),
            Map.entry(1.0, 0.54030230586),
            Map.entry(Math.PI/4, 0.7071067812),
            Map.entry(Math.PI/6, 0.8660254037)
    );

    // ==================== Базовые функции TESTS ====================

    @ParameterizedTest
    @DisplayName("BASE: sin(x) - вычисляет корректные значения")
    @MethodSource("provideSinTestData")
    void testSinFunction(double x, double expected) {
        SinFunction sin = new SinFunction();
        assertEquals(expected, sin.proceed(x), EPSILON);
    }

    private static Stream<Arguments> provideSinTestData() {
        return SIN_VALUES.entrySet().stream()
                .map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

    @ParameterizedTest
    @DisplayName("BASE: ln(x) - вычисляет корректные значения")
    @MethodSource("provideLnTestData")
    void testLnFunction(double x, double expected) {
        LnFunction ln = new LnFunction();
        assertEquals(expected, ln.proceed(x), EPSILON);
    }

    private static Stream<Arguments> provideLnTestData() {
        return LN_VALUES.entrySet().stream()
                .map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

    // ==================== Уровень 1 TESTS ====================

    @ParameterizedTest
    @DisplayName("LEVEL 1: cos(x) - корректно использует sin")
    @MethodSource("provideCosTestData")
    void testCosFunction(double x, double expected) {
        SinFunction sinMock = mock(SinFunction.class);
        when(sinMock.proceed(Math.PI/2 - x)).thenReturn(COS_VALUES.get(x));

        CosFunction cos = new CosFunction(sinMock);
        assertEquals(expected, cos.proceed(x), 1e-4);
    }

    private static Stream<Arguments> provideCosTestData() {
        return COS_VALUES.entrySet().stream()
                .map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

    @ParameterizedTest
    @DisplayName("LEVEL 1: csc(x) - корректно использует sin")
    @MethodSource("provideCscTestData")
    void testCscFunction(double x, double expected) {
        SinFunction sinMock = mock(SinFunction.class);
        when(sinMock.proceed(x)).thenReturn(SIN_VALUES.get(x));

        CscFunction csc = new CscFunction(sinMock);
        assertEquals(expected, csc.proceed(x), 1e-4);
    }

    private static Stream<Arguments> provideCscTestData() {
        return Map.of(
                        Math.PI/2, 1.0,
                        Math.PI/6, 2.0,
                        1.0, 1.0/SIN_VALUES.get(1.0)
                ).entrySet().stream()
                .map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }



    @ParameterizedTest
    @DisplayName("LEVEL 1: ln3(x) - корректно использует ln")
    @MethodSource("provideLn3TestData")
    void testLn3Function(double x, double expected) {
        LnFunction lnMock = mock(LnFunction.class);
        when(lnMock.proceed(x)).thenReturn(LN_VALUES.get(x));
        when(lnMock.proceed(3.0)).thenReturn(LN_VALUES.get(3.0));

        Ln3Function ln3 = new Ln3Function(lnMock);
        assertEquals(expected, ln3.proceed(x), 1e-4);
    }

    private static Stream<Arguments> provideLn3TestData() {
        return Map.of(
                        3.0, 1.0,
                        9.0, 2.0,
                        1.0, 0.0,
                        27.0, 3.0
                ).entrySet().stream()
                .map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

    @ParameterizedTest
    @DisplayName("LEVEL 1: ln10(x) - корректно использует ln")
    @MethodSource("provideLn10TestData")
    void testLn10Function(double x, double expected) {
        LnFunction lnMock = mock(LnFunction.class);
        when(lnMock.proceed(x)).thenReturn(LN_VALUES.get(x));
        when(lnMock.proceed(10.0)).thenReturn(LN_VALUES.get(10.0));

        Ln10Function ln10 = new Ln10Function(lnMock);
        assertEquals(expected, ln10.proceed(x), 1e-4);
    }

    private static Stream<Arguments> provideLn10TestData() {
        return Map.ofEntries(
                        Map.entry(10.0, 1.0),
                        Map.entry(100.0, 2.0),
                        Map.entry(1.0, 0.0),
                        Map.entry(1000.0, 3.0)
                ).entrySet().stream()
                .map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

    // ==================== Уровень 2 TESTS ====================

    @ParameterizedTest
    @DisplayName("LEVEL 2: ctg(x) - корректно использует sin и cos")
    @MethodSource("provideCtgTestData")
    void testCtgFunction(double x, double expected) {
        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);

        when(sinMock.proceed(x)).thenReturn(SIN_VALUES.get(x));
        when(cosMock.proceed(x)).thenReturn(COS_VALUES.get(x));

        CtgFunction ctg = new CtgFunction(sinMock, cosMock);
        assertEquals(expected, ctg.proceed(x), 1e-4);
    }

    private static Stream<Arguments> provideCtgTestData() {
        return Map.ofEntries(
                        Map.entry(Math.PI/4, 1.0),
                        Map.entry(Math.PI/6, Math.sqrt(3)),
                        Map.entry(1.0, COS_VALUES.get(1.0)/SIN_VALUES.get(1.0))
                ).entrySet().stream()
                .map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

    @ParameterizedTest
    @DisplayName("LEVEL 2: sec(x) - корректно использует cos")
    @MethodSource("provideSecTestData")
    void testSecFunction(double x, double expected) {
        CosFunction cosMock = mock(CosFunction.class);
        when(cosMock.proceed(x)).thenReturn(COS_VALUES.get(x));

        SecFunction sec = new SecFunction(cosMock);
        assertEquals(expected, sec.proceed(x), 1e-4);
    }

    private static Stream<Arguments> provideSecTestData() {
        return Map.ofEntries(
                        Map.entry(0.0, 1.0),
                        Map.entry(Math.PI, -1.0),
                        Map.entry(1.0, 1.0/COS_VALUES.get(1.0))
                ).entrySet().stream()
                .map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }


    // ==================== Уровень 3 TESTS ====================


    @ParameterizedTest
    @DisplayName("LEVEL 3: tg(x) - корректно использует ctg")
    @MethodSource("provideTgTestData")
    void testTgFunction(double x, double expected) {
        CtgFunction ctgMock = mock(CtgFunction.class);
        when(ctgMock.proceed(x)).thenReturn(1.0/Math.tan(x));

        TgFunction tg = new TgFunction(ctgMock);
        assertEquals(expected, tg.proceed(x), 1e-4);
    }

    private static Stream<Arguments> provideTgTestData() {
        return Map.ofEntries(
                        Map.entry(Math.PI/4, 1.0),
                        Map.entry(Math.PI/6, 1.0/Math.sqrt(3)),
                        Map.entry(1.0, Math.tan(1.0))
                ).entrySet().stream()
                .map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }


    // ==================== Верхний уровень TESTS ====================

    /**
     * Координаты точек для логарифмической функции:
     * (0.13878, 0), (3.05454, 0) - нули функции
     * (0.24655, 5), (8.92552, 5) - точки на уровне y=5
     * (10, 5.75419) - точка в верхней границе
     * (16.43874, 10) - крайняя точка при y=10
     * <p>
     * Координаты точек для тригонометрической функции:
     * Нули функции:
     * (-1.28588, 0), (-4.42726, 0), (-7.56906, 0), (-10.71044, 0)
     * Локальные максимумы:
     * (-2.24305, 0.44532), (-5.37977, 0.40804), (-8.52623, 0.44532), (-11.66295, 0.40804)
     * Локальные минимумы:
     * (-3.76296, -182.62821), (-10.04615, -182.62821),
     * (-16.32933, -182.62821), (-22.61252, -182.62821)
     */


    // Логарифмическая ветка (x > 0)

    @ParameterizedTest
    @DisplayName("Логарифмическая ветка: проверка всех случаев")
    @CsvFileSource(resources = "/log/my_function.csv")
    void testLogarithmicBranch(double x, double expected) {
        // Моки логарифмических функций
        Ln2Function ln2Mock = mock(Ln2Function.class);
        Ln3Function ln3Mock = mock(Ln3Function.class);
        Ln10Function ln10Mock = mock(Ln10Function.class);

        // Загрузка тестовых данных для моков из CSV
        setupMockFromCsv(ln2Mock, "/log/ln2.csv", x);
        setupMockFromCsv(ln3Mock, "/log/ln3.csv", x);
        setupMockFromCsv(ln10Mock, "/log/ln10.csv", x);

        // Моки тригонометрических функций (не должны вызываться)
        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        TgFunction tgMock = mock(TgFunction.class);
        CtgFunction ctgMock = mock(CtgFunction.class);
        SecFunction secMock = mock(SecFunction.class);
        CscFunction cscMock = mock(CscFunction.class);

        MyFunction function = new MyFunction(
                sinMock, cosMock, tgMock, ctgMock,
                secMock, cscMock,
                ln2Mock, ln3Mock, ln10Mock
        );

        assertEquals(expected, function.proceed(x), EPSILON);

        // Проверка вызовов логарифмических функций
        verify(ln2Mock).proceed(x);
        verify(ln3Mock).proceed(x);
        verify(ln10Mock).proceed(x);

        // Проверка что тригонометрические функции не вызывались
        verifyNoInteractions(sinMock, cosMock, tgMock, ctgMock, secMock, cscMock);
    }

    @ParameterizedTest
    @DisplayName("Тригонометрическая ветка: проверка всех случаев")
    @CsvFileSource(resources = "/trig/my_function.csv")
    void testTrigonometricBranch(double x, double expected) {
        // Моки тригонометрических функций
        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        TgFunction tgMock = mock(TgFunction.class);
        CtgFunction ctgMock = mock(CtgFunction.class);
        SecFunction secMock = mock(SecFunction.class);
        CscFunction cscMock = mock(CscFunction.class);

        // Загрузка тестовых данных из CSV
        setupMockFromCsv(sinMock, "/trig/sin.csv", x);
        setupMockFromCsv(cosMock, "/trig/cos.csv", x);
        setupMockFromCsv(tgMock, "/trig/tg.csv", x);
        setupMockFromCsv(ctgMock, "/trig/ctg.csv", x);
        setupMockFromCsv(secMock, "/trig/sec.csv", x);
        setupMockFromCsv(cscMock, "/trig/csc.csv", x);

        // Моки логарифмических функций (не должны вызываться)
        Ln2Function ln2Mock = mock(Ln2Function.class);
        Ln3Function ln3Mock = mock(Ln3Function.class);
        Ln10Function ln10Mock = mock(Ln10Function.class);

        MyFunction function = new MyFunction(
                sinMock, cosMock, tgMock, ctgMock,
                secMock, cscMock,
                ln2Mock, ln3Mock, ln10Mock
        );

        assertEquals(expected, function.proceed(x), EPSILON);

        // Проверка вызовов тригонометрических функций
        verify(sinMock).proceed(x);
        verify(cosMock).proceed(x);
        verify(tgMock).proceed(x);
        verify(ctgMock).proceed(x);
        verify(secMock).proceed(x);
        verify(cscMock, atLeastOnce()).proceed(x);

        // Проверка что логарифмические функции не вызывались
        verifyNoInteractions(ln2Mock, ln3Mock, ln10Mock);
    }

    private void setupMockFromCsv(AFunction mock, String csvResource, double x) {
        try (InputStream is = getClass().getResourceAsStream(csvResource);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                double currentX = Double.parseDouble(parts[0]);
                if (Math.abs(currentX - x) < 0.01) {
                    double expected = Double.parseDouble(parts[1]);
                    doReturn(expected).when(mock).proceed(x);
                    return;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read test data", e);
        }
        throw new IllegalArgumentException("No test data found for x=" + x);
    }

    private static final double EPSILON = 5;
}
