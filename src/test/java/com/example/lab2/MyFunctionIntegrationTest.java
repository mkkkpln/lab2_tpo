package com.example.lab2;

import com.example.lab2.math.AFunction;
import com.example.lab2.math.MyFunction;
import com.example.lab2.math.log.Ln10Function;
import com.example.lab2.math.log.Ln2Function;
import com.example.lab2.math.log.Ln3Function;
import com.example.lab2.math.log.LnFunction;
import com.example.lab2.math.trig.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    private static final double EPSILON = 5;

    // ==================== Базовые функции TESTS ====================

    @Test
    @DisplayName("BASE: sin(x) - вычисляет корректные значения")
    void testSinFunction() {
        SinFunction sin = new SinFunction();
        assertEquals(0.0, sin.proceed(0.0), EPSILON);
        assertEquals(1.0, sin.proceed(Math.PI/2), EPSILON);
    }

    @Test
    @DisplayName("BASE: ln(x) - вычисляет корректные значения")
    void testLnFunction() {
        LnFunction ln = new LnFunction();
        assertEquals(0.0, ln.proceed(1.0), EPSILON);
        assertEquals(1.0, ln.proceed(Math.E), EPSILON);
    }

    // ==================== Уровень 1 TESTS ====================

    @Test
    @DisplayName("LEVEL 1: cos(x) - корректно использует sin")
    void testCosFunction() {
        SinFunction sinMock = mock(SinFunction.class);
        when(sinMock.proceed(Math.PI/2 - 1.0)).thenReturn(0.5403);

        CosFunction cos = new CosFunction(sinMock);
        assertEquals(0.5403, cos.proceed(1.0), EPSILON);
    }

    @Test
    @DisplayName("LEVEL 1: csc(x) - корректно использует sin")
    void testCscFunction() {
        SinFunction sinMock = mock(SinFunction.class);
        when(sinMock.proceed(1.0)).thenReturn(0.8415);

        CscFunction csc = new CscFunction(sinMock);
        assertEquals(1.0/0.8415, csc.proceed(1.0), 1e-4);
    }

    @Test
    @DisplayName("LEVEL 1: ln3(x) - корректно использует ln")
    void testLn3Function() {
        LnFunction lnMock = mock(LnFunction.class);
        when(lnMock.proceed(3.0)).thenReturn(1.0986);

        Ln3Function ln3 = new Ln3Function(lnMock);
        assertEquals(1.0, ln3.proceed(3.0), 1e-4);
    }

    @Test
    @DisplayName("LEVEL 1: ln10(x) - корректно использует ln")
    void testLn10Function() {
        LnFunction lnMock = mock(LnFunction.class);
        when(lnMock.proceed(10.0)).thenReturn(2.3026);

        Ln10Function ln10 = new Ln10Function(lnMock);
        assertEquals(1.0, ln10.proceed(10.0), 1e-4);
    }

    // ==================== Уровень 2 TESTS ====================

    @Test
    @DisplayName("LEVEL 2: ctg(x) - корректно использует sin и cos")
    void testCtgFunction() {
        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        when(sinMock.proceed(1.0)).thenReturn(0.8415);
        when(cosMock.proceed(1.0)).thenReturn(0.5403);

        CtgFunction ctg = new CtgFunction(sinMock, cosMock);
        assertEquals(0.5403/0.8415, ctg.proceed(1.0), 1e-4);
    }

    @Test
    @DisplayName("LEVEL 2: sec(x) - корректно использует cos")
    void testSecFunction() {
        CosFunction cosMock = mock(CosFunction.class);
        when(cosMock.proceed(1.0)).thenReturn(0.5403);

        SecFunction sec = new SecFunction(cosMock);
        assertEquals(1.0/0.5403, sec.proceed(1.0), 1e-4);
    }


    // ==================== Уровень 3 TESTS ====================

    @Test
    @DisplayName("LEVEL 3: tg(x) - корректно использует ctg")
    void testTgFunction() {
        CtgFunction ctgMock = mock(CtgFunction.class);
        when(ctgMock.proceed(1.0)).thenReturn(1.5574);

        TgFunction tg = new TgFunction(ctgMock);
        assertEquals(1.0/1.5574, tg.proceed(1.0), 1e-4);
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
}
