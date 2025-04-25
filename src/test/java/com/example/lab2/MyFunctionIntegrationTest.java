package com.example.lab2;

import com.example.lab2.math.MyFunction;
import com.example.lab2.math.log.Ln10Function;
import com.example.lab2.math.log.Ln2Function;
import com.example.lab2.math.log.Ln3Function;
import com.example.lab2.math.log.LnFunction;
import com.example.lab2.math.trig.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * ИЕРАРХИЯ ФУНКЦИЙ:
 * <p>
 * Верхний уровень:
 *   MyFunction
 * <p>
 * Уровень 2:
 *   tg → ctg
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
    private static final double EPSILON = 1e-6;

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
    @DisplayName("LEVEL 2: tg(x) - корректно использует ctg")
    void testTgFunction() {
        CtgFunction ctgMock = mock(CtgFunction.class);
        when(ctgMock.proceed(1.0)).thenReturn(1.5574);

        TgFunction tg = new TgFunction(ctgMock);
        assertEquals(1.0/1.5574, tg.proceed(1.0), 1e-4);
    }

    @Test
    @DisplayName("LEVEL 2: sec(x) - корректно использует cos")
    void testSecFunction() {
        CosFunction cosMock = mock(CosFunction.class);
        when(cosMock.proceed(1.0)).thenReturn(0.5403);

        SecFunction sec = new SecFunction(cosMock);
        assertEquals(1.0/0.5403, sec.proceed(1.0), 1e-4);
    }

    // ==================== Верхний уровень TESTS ====================

    @Test
    @DisplayName("TOP LEVEL: MyFunction - корректно использует все зависимости")
    void testMyFunctionIntegration() {
        // Инициализируем все моки
        SinFunction sinMock = mock(SinFunction.class);
        CosFunction cosMock = mock(CosFunction.class);
        CtgFunction ctgMock = mock(CtgFunction.class);
        TgFunction tgMock = mock(TgFunction.class);
        SecFunction secMock = mock(SecFunction.class);
        CscFunction cscMock = mock(CscFunction.class);
        Ln2Function ln2Mock = mock(Ln2Function.class);
        Ln3Function ln3Mock = mock(Ln3Function.class);
        Ln10Function ln10Mock = mock(Ln10Function.class);

        // Настраиваем цепочку зависимостей:
        // Для тригонометрической ветки (x <= 0)
        when(sinMock.proceed(anyDouble())).thenReturn(0.5);
        when(cosMock.proceed(anyDouble())).thenReturn(0.5);
        when(ctgMock.proceed(anyDouble())).thenReturn(1.0);
        when(tgMock.proceed(anyDouble())).thenReturn(1.0);
        when(secMock.proceed(anyDouble())).thenReturn(2.0);
        when(cscMock.proceed(anyDouble())).thenReturn(2.0);

        // Для логарифмической ветки (x > 0)
        when(ln2Mock.proceed(anyDouble())).thenReturn(0.693);
        when(ln3Mock.proceed(anyDouble())).thenReturn(1.098);
        when(ln10Mock.proceed(anyDouble())).thenReturn(2.302);

        MyFunction myFunction = new MyFunction(
                sinMock, cosMock, tgMock, ctgMock,
                secMock, cscMock, ln2Mock, ln3Mock, ln10Mock
        );

        // Проверяем оба случая
        assertNotNull(myFunction.proceed(1.0));  // Логарифмическая ветка
        assertNotNull(myFunction.proceed(-1.0)); // Тригонометрическая ветка

        // Проверяем вызовы зависимостей
        verify(sinMock, atLeastOnce()).proceed(anyDouble());
        verify(cosMock, atLeastOnce()).proceed(anyDouble());
        verify(ctgMock, atLeastOnce()).proceed(anyDouble());
        verify(tgMock, atLeastOnce()).proceed(anyDouble());
        verify(secMock, atLeastOnce()).proceed(anyDouble());
        verify(cscMock, atLeastOnce()).proceed(anyDouble());
        verify(ln2Mock, atLeastOnce()).proceed(anyDouble());
        verify(ln3Mock, atLeastOnce()).proceed(anyDouble());
        verify(ln10Mock, atLeastOnce()).proceed(anyDouble());
    }
}
